package com.kangoo.cyl.domain.service;

import com.kangoo.cyl.domain.entity.Operation;
import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.entity.State;
import com.kangoo.cyl.domain.vo.Operator;
import java.util.List;

public class RocketScience {

    private boolean isViable(
        State state,
        Integer target,
        OptimalSolution bestPath
    ) {
        if (bestPath.getIsExact()) {
            return false; // Prune if exact solution already found
        }

        /* At this point, we'll check if actual state can improve the best path */
        /* NOTE: ACTUALLY IT'S A BASIC HEURISTIC */

        switch (state.getAvailableNumbers().size()) {
            case 0:
                return false; // No numbers left, can't improve
            case 1:
                // If we have only one number, check if distance is better
                Integer currentResult = state.getAvailableNumbers().get(0);
                int currentDistance = Math.abs(currentResult - target);
                return currentDistance < bestPath.getMinimalDistance();
            default:
                // With more than two numbers, ASSUME FOR NOW it's viable
                return true;
        }
    }

    private boolean isTargetInInitialNumbers(
        Integer[] numbers,
        Integer target
    ) {
        for (Integer number : numbers) {
            if (number.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public OptimalSolution solve(Integer[] numbers, Integer target) {
        OptimalSolution bestPath = new OptimalSolution(
            Integer.MAX_VALUE,
            0,
            null,
            null,
            false
        );

        if (isTargetInInitialNumbers(numbers, target)) {
            bestPath.setMinimalDistance(0);
            bestPath.setOperationsNeeded(0);
            bestPath.setBestResult(target);
            bestPath.setIsExact(true);
            bestPath.setOptimalSolution(List.of()); // No operations needed
            return bestPath;
        }

        // Proceed with backtracking algorithm
        State initialState = State.createInitState(List.of(numbers));
        rocketScience(initialState, target, bestPath);
        return bestPath;
    }

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
}
