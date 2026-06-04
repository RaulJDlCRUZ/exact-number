package com.kangoo.cyl.domain.service;

import com.kangoo.cyl.domain.entity.Operation;
import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.entity.State;
import com.kangoo.cyl.domain.vo.Operator;
import java.util.List;

public class OptimizedSolver {

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

    /* Entry point. Check if target is in initial numbers */
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

        // Proceed with backtracking algorithm (not implemented here)
        State initialState = State.createInitState(List.of(numbers));
        rocketScienceII(initialState, target, bestPath);
        return bestPath;
    }

    public void rocketScienceII(
        State state,
        Integer target,
        OptimalSolution bestPath
    ) {
        /* Base case */
        switch (state.getAvailableNumbers().size()) {
            case 0:
                return; // No numbers left, can't improve
            case 1:
                Integer result = state.getAvailableNumbers().get(0);
                int distance = Math.abs(result - target);

                // Update best path if we found a better solution
                if (distance < bestPath.getMinimalDistance()) {
                    bestPath.setMinimalDistance(distance);
                    bestPath.setBestResult(result);
                    bestPath.setOptimalSolution(
                        List.copyOf(state.getOperations())
                    );
                    bestPath.setOperationsNeeded(state.getOperations().size());

                    // ...and if it's exact, mark it
                    if (distance == 0) {
                        bestPath.setIsExact(true);
                    }
                }
                return;
            default:
                break;
        }

        // Pruning: If we already found the exact solution, stop exploring
        if (bestPath.getIsExact()) {
            return;
        }

        /* Recursive case: Explore all possible combinations */
        List<Operator> operations = List.of(Operator.values());
        for (int i = 0; i < state.getAvailableNumbers().size(); i++) {
            int currentNum = state.getAvailableNumbers().get(i);

            if (state.getActualResult() == null) {
                State newState = State.createStateFrom(state);
                newState.setActualResult(currentNum);
                newState.removeNumberByIndex(i);
                rocketScienceII(newState, target, bestPath);
                // actualResult was null in the incoming state, so we've set it on the copy
                // and progressed that branch. Skip operator exploration for the original state
                // to avoid creating Operation objects with a null firstOperand.
                continue;
            }

            // At this point actualResult is guaranteed non-null for the current state,
            // so it's safe to construct Operation objects.
            for (Operator op : operations) {
                Operation operation = new Operation(
                    state.getActualResult(),
                    currentNum,
                    op
                );
                if (!operation.isValid()) continue;

                int result = operation.computeResult();

                if (!isViable(state, target, bestPath)) continue; // Prune non-viable states

                State newState = State.createStateFrom(state);
                newState.addOperation(operation);
                newState.removeNumberByIndex(i);
                newState.setActualResult(result);

                rocketScienceII(newState, target, bestPath);
                if (bestPath.getIsExact()) return;
            }
        }
    }
}
