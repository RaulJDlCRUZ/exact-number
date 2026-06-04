package com.kangoo.cyl.domain.service;

import java.util.List;

import com.kangoo.cyl.domain.entity.Operation;
import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.vo.Operator;
import com.kangoo.cyl.domain.entity.State2;

public class NumbersSolver {

    // Métricas (opcional, para análisis)
    private int statesExplored = 0;
    private int cacheHits = 0;

    public OptimalSolution solve(Integer[] numbers, Integer target) {
        OptimalSolution bestPath = new OptimalSolution(
                Integer.MAX_VALUE, 0, null, null, false);

        // Caso trivial: target está en los números iniciales
        if (isTargetInInitialNumbers(numbers, target)) {
            bestPath.setMinimalDistance(0);
            bestPath.setOperationsNeeded(0);
            bestPath.setBestResult(target);
            bestPath.setIsExact(true);
            bestPath.setOptimalSolution(List.of());
            return bestPath;
        }

        // Crear estado inicial
        State2 state = new State2().createInitState(List.of(numbers));

        // Array para construir la secuencia de números en orden
        int[] sequence = new int[numbers.length];

        // Lanzar backtracking
        backtrack(state, target, bestPath, sequence, 0, 0);

        return bestPath;
    }

    /**
     * Backtracking principal - Estilo A pero con objetos de B
     * 
     * @param state        Estado mutable compartido
     * @param target       Número objetivo
     * @param bestPath     Mejor solución encontrada hasta ahora
     * @param sequence     Secuencia de números siendo construida
     * @param seqIndex     Índice actual en la secuencia
     * @param currentValue Valor acumulado hasta este punto
     */
    private boolean backtrack(
            State2 state,
            Integer target,
            OptimalSolution bestPath,
            int[] sequence,
            int seqIndex,
            int currentValue) {
        statesExplored++;

        // Poda: solución exacta ya encontrada
        if (bestPath.getIsExact()) {
            return true;
        }

        // Actualizar mejor aproximación
        int distance = Math.abs(currentValue - target);
        if (distance < bestPath.getMinimalDistance()) {
            bestPath.setMinimalDistance(distance);
            bestPath.setBestResult(currentValue);
            bestPath.setOptimalSolution(state.copyOperations());
            bestPath.setOperationsNeeded(state.getOperations().size());

            if (distance == 0) {
                bestPath.setIsExact(true);
                return true; // Solución exacta encontrada
            }
        }

        // Caso base: todos los números usados
        if (seqIndex == state.getAvailableNumbers().size()) {
            return false;
        }

        // Probar cada número disponible
        List<Integer> numbers = state.getAvailableNumbers();
        for (int i = 0; i < numbers.size(); i++) {
            if (state.isUsed(i)) {
                continue;
            }

            int number = numbers.get(i);
            state.markUsed(i);
            sequence[seqIndex] = number;

            // Primer número: sin operación previa
            if (seqIndex == 0) {
                if (backtrack(state, target, bestPath, sequence, seqIndex + 1, number)) {
                    state.markUnused(i);
                    return true; // Propagar salida temprana
                }
            } else {
                // Probar todas las operaciones con el número actual
                if (tryAllOperations(
                        state, target, bestPath, sequence, seqIndex,
                        currentValue, number, i)) {
                    state.markUnused(i);
                    return true; // Propagar salida temprana
                }
            }

            state.markUnused(i);
        }

        return false;
    }

    /**
     * Probar todas las operaciones posibles con el número actual
     */
    private boolean tryAllOperations(
            State2 state,
            Integer target,
            OptimalSolution bestPath,
            int[] sequence,
            int seqIndex,
            int currentValue,
            int number,
            int numberIndex) {
        // Orden de operaciones: priorizar por probabilidad de éxito
        // (podría ser dinámico según target y currentValue)

        // 1. SUMA
        if (tryOperation(
                state, target, bestPath, sequence, seqIndex, numberIndex,
                currentValue, number, Operator.ADDITION, currentValue + number)) {
            return true;
        }

        // 2. MULTIPLICACIÓN (si tiene sentido)
        if (shouldTryMultiplication(currentValue, number, target)) {
            if (tryOperation(
                    state, target, bestPath, sequence, seqIndex, numberIndex,
                    currentValue, number, Operator.MULTIPLICATION, currentValue * number)) {
                return true;
            }
        }

        // 3. RESTA (solo si resultado > 0)
        if (currentValue > number) {
            if (tryOperation(
                    state, target, bestPath, sequence, seqIndex, numberIndex,
                    currentValue, number, Operator.SUBTRACTION, currentValue - number)) {
                return true;
            }
        }

        // 4. DIVISIÓN (solo si es exacta y tiene sentido)
        if (number != 0 && currentValue % number == 0) {
            if (tryOperation(
                    state, target, bestPath, sequence, seqIndex, numberIndex,
                    currentValue, number, Operator.DIVISION, currentValue / number)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Probar una operación específica
     */
    private boolean tryOperation(
            State2 state,
            Integer target,
            OptimalSolution bestPath,
            int[] sequence,
            int seqIndex,
            int numberIndex,
            int operand1,
            int operand2,
            Operator operator,
            int result) {
        // Validación temprana
        Operation op = new Operation(operand1, operand2, operator);
        if (!op.isValid()) {
            return false;
        }

        // Poda por heurística: ¿vale la pena explorar este camino?
        if (!isViable(result, target, bestPath, seqIndex)) {
            return false;
        }

        // Aplicar operación
        state.applyOperation(op);

        // Continuar recursión
        boolean found = backtrack(
                state, target, bestPath, sequence, seqIndex + 1, result);

        // Deshacer operación (backtracking)
        state.undoOperation();

        return found;
    }

    /**
     * Heurística de viabilidad: ¿puede esta rama mejorar la mejor solución?
     */
    private boolean isViable(
            int currentValue,
            Integer target,
            OptimalSolution bestPath,
            int depth) {
        // Si ya tenemos una solución exacta, no explorar más
        if (bestPath.getIsExact()) {
            return false;
        }

        // Distancia actual al target
        int currentDistance = Math.abs(currentValue - target);

        // Si esta distancia ya es peor que la mejor conocida
        // y no quedan muchos números, probablemente no mejore
        if (currentDistance >= bestPath.getMinimalDistance() && depth > 3) {
            return false;
        }

        // Poda por distancia imposible de cerrar
        // (aquí se pueden añadir heurísticas más sofisticadas)

        return true;
    }

    /**
     * Heurística: ¿vale la pena intentar multiplicación?
     */
    private boolean shouldTryMultiplication(int current, int number, int target) {
        // Evitar multiplicaciones que alejen mucho del target
        if (current == 0 || number == 0)
            return false;
        if (current == 1 || number == 1)
            return true; // No cambia mucho

        long product = (long) current * number;

        // Si el producto se aleja mucho más que la suma, probablemente no ayude
        int distanceIfMultiply = Math.abs((int) product - target);
        int distanceIfAdd = Math.abs(current + number - target);

        // Solo multiplicar si no nos alejamos demasiado
        return distanceIfMultiply <= distanceIfAdd * 3;
    }

    /**
     * Verificar si el target está en los números iniciales
     */
    private boolean isTargetInInitialNumbers(Integer[] numbers, Integer target) {
        for (Integer number : numbers) {
            if (number.equals(target)) {
                return true;
            }
        }
        return false;
    }

    // Getters para métricas (opcional)
    public int getStatesExplored() {
        return statesExplored;
    }

    public int getCacheHits() {
        return cacheHits;
    }
}