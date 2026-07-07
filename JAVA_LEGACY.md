# Estado de cada rama. Punto de referencia

## `first-monolith`

El último cambio realizado fue el comentado de una de las dos soluciones admitidas de implementación del [Algoritmo actual](#algoritmo-actual), concretamente aquella que aborta tras un primer camino, sin importar el resultado (**comportamiento no deseado**).

SHA más reciente: `7e7ec1470c744a800e035f4b170e19ffc6f21246`.

## `monolith-to-aws`

La última actualización incluye la actualización del menifiesto (`pom.xml`) para aumentar la versión del módulo, a su vez que se implantan las dependencias faltantes para que el monolito pueda ser desplegado en una AWS Lambda.

SHA más reciente: `8ab1754cb7a6dc283009f811d12867cc28602494`

# Algoritmo actual

La versión más avanzada del algoritmo es aquella encontrada en el commit `8ab1754`:

```java
    public void rocketScience(
        State state,
        Integer target,
        OptimalSolution bestPath
    ) {
        /* Base case: Check if we have only one number (terminal solution) */
        if (state.getAvailableNumbers().size() == 1) {
            Integer result = state.getAvailableNumbers().get(0);
            int distance = Math.abs(result - target);

            /* Update best path if we found a better solution */
            if (distance < bestPath.getMinimalDistance()) {
                bestPath.setMinimalDistance(distance);
                bestPath.setBestResult(result);
                bestPath.setOptimalSolution(List.copyOf(state.getOperations()));
                bestPath.setOperationsNeeded(state.getOperations().size());

                // ...and if it's exact, mark it
                if (distance == 0) {
                    bestPath.setIsExact(true);
                }
            }
            return;
        }
        /* Pruning: If we already found the exact solution, stop exploring */
        if (bestPath.getIsExact()) {
            return;
        }
        /* Recursive case: Explore all possible combinations */
        Integer n = state.getAvailableNumbers().size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Integer n1 = state.getAvailableNumbers().get(i);
                Integer n2 = state.getAvailableNumbers().get(j);

                if (i == j) {
                    continue; // Skip same number
                }
                List<Operator> operations = List.of(Operator.values());
                for (Operator op : operations) {
                    Operation operation1 = new Operation(n1, n2, op);
                    if (operation1.isValid()) {
                        State newState = State.createStateFrom(state);
                        newState.removeNumbersByValues(n1, n2);
                        Integer result = operation1.computeResult();
                        newState.addNumber(result);
                        newState.addOperation(operation1);

                        // Check viability before continuing
                        if (isViable(newState, target, bestPath)) {
                            rocketScience(newState, target, bestPath);

                            // Prune if exact solution found
                            if (bestPath.getIsExact()) {
                                return;
                            }
                        }
                    }

                    // For subtraction and division, also try with swapped operands
                    // because they are not commutative
                    if (op == Operator.SUBTRACTION || op == Operator.DIVISION) {
                        Operation operation2 = new Operation(n2, n1, op);
                        if (operation2.isValid()) {
                            State newState = State.createStateFrom(state);
                            newState.removeNumbersByValues(n1, n2);
                            Integer result = operation2.computeResult();
                            newState.addNumber(result);
                            newState.addOperation(operation2);

                            if (isViable(newState, target, bestPath)) {
                                rocketScience(newState, target, bestPath);

                                if (bestPath.getIsExact()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
```

La función `rocketScience()` implementa una búsqueda recursiva con poda para resolver el juego: parte de un conjunto de números disponibles, prueba todas las combinaciones posibles entre dos números, aplica cada operación básica y continúa explorando el nuevo estado hasta que solo queda un número.

Su funcionamiento es el siguiente:

1. Si solo queda un número, se considera una solución terminal. Ese valor se compara con el objetivo y, si mejora la mejor solución encontrada hasta ese momento, se actualiza `bestPath` con la distancia mínima, el resultado obtenido y la secuencia de operaciones.
1. Si la solución exacta ya fue hallada, la búsqueda se interrumpe para evitar trabajo innecesario.
1. En cada nivel recursivo, se recorren todos los pares de números disponibles y se prueban las operaciones `+`, `-`, `*` y `/`.
1. Antes de profundizar, se valida que la operación sea válida y que el nuevo estado siga siendo prometedor mediante `isViable(...)`.
1. Para la resta y la división, también se prueba el orden inverso de los operandos, porque no son operaciones conmutativas.

En términos prácticos, el algoritmo explora el árbol completo de combinaciones posibles, pero corta ramas que ya no pueden mejorar la mejor solución conocida. Así consigue encontrar una solución exacta cuando existe o, en caso contrario, la aproximación más cercana al objetivo.

## Limitaciones

El algoritmo actual no es perfecto porque realiza una búsqueda casi exhaustiva de todas las combinaciones posibles. En un caso como `[1,2,3,4,5,6]` con objetivo `997`, el programa puede tardar demasiado tiempo porque no encuentra una solución exacta de forma temprana y acaba explorando muchas ramas innecesarias.

Aunque la profundidad de la recursión sí está acotada por la cantidad de números disponibles, el número de estados intermedios puede crecer muchísimo. Eso hace que el rendimiento sea muy malo en entradas difíciles y que el programa parezca no terminar en un tiempo razonable.

Propuesta: añadir una poda más agresiva basada en límites matemáticos y en la distancia mínima alcanzable desde cada estado.

# Instrucciones de ejecución _legacy_

## Requisitos

- Java 21
- Maven 3.5+

```sh
mvn clean install
```

A continuación se proporciona un ejemplo de ejecución de la aplicación:

```sh
mvn spring-boot:run -Dspring-boot.run.arguments="100 75 25 2 3 1 301"
```

### Salida

```
Target Number: 301
Numbers to use: 100 75 25 2 3 1

[1] Best solution found with distance 0 and 5 operations:

100 + 75 = 175
25 + 1 = 26
175 - 26 = 149
2 * 149 = 298
3 + 298 = 301
```