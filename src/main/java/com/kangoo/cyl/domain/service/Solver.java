package com.kangoo.cyl.domain.service;

import com.kangoo.cyl.CifrasYLetrasApplication;

public class Solver {

    private int bestApprox = Integer.MAX_VALUE;
    private int[] bestTemp;
    private char[] bestOperation;
    private int[] bestTotals;
    private int bestTempIndex = 0;
    private int bestOpIndex = 0;

    public boolean approximation(int currentTotal, int targetNum) {
        return Math.abs(currentTotal - targetNum) <= 10;
    }

    // Added stopOnFirstExact parameter
    public boolean rocketscience(int[] numToUse, int targetNum, boolean[] used, int[] temp, int tempIndex,
            int currentTotal, char[] operation, int[] totals, int opIndex, boolean stopOnFirstExact) {

        int diff = Math.abs(currentTotal - targetNum);
        if (diff < Math.abs(bestApprox - targetNum)) {
            bestApprox = currentTotal;
            bestTemp = temp.clone();
            bestOperation = operation.clone();
            bestTotals = totals.clone();
            bestTempIndex = tempIndex;
            bestOpIndex = opIndex;
        }

        // Exact match found
        if (currentTotal == targetNum) {
            CifrasYLetrasApplication.showSolution(targetNum, temp, tempIndex, operation, totals, opIndex);
            return stopOnFirstExact; // If true, stop recursion; if false, keep searching
        }

        boolean found = false;
        for (int i = 0; i < numToUse.length; i++) {
            if (!used[i]) {
                used[i] = true;
                temp[tempIndex] = numToUse[i];

                if (tempIndex == 0) {
                    totals[0] = temp[0];
                    found = rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, numToUse[i], operation,
                            totals, opIndex, stopOnFirstExact);
                } else {
                    // Addition
                    operation[opIndex] = '+';
                    totals[opIndex + 1] = currentTotal + numToUse[i];
                    found = rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal + numToUse[i],
                            operation, totals, opIndex + 1, stopOnFirstExact);
                    if (stopOnFirstExact && found) {
                        used[i] = false;
                        break;
                    }

                    // Subtraction
                    operation[opIndex] = '-';
                    totals[opIndex + 1] = currentTotal - numToUse[i];
                    found = rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal - numToUse[i],
                            operation, totals, opIndex + 1, stopOnFirstExact);
                    if (stopOnFirstExact && found) {
                        used[i] = false;
                        break;
                    }

                    // Multiplication
                    operation[opIndex] = '*';
                    totals[opIndex + 1] = currentTotal * numToUse[i];
                    found = rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal * numToUse[i],
                            operation, totals, opIndex + 1, stopOnFirstExact);
                    if (stopOnFirstExact && found) {
                        used[i] = false;
                        break;
                    }

                    // Division
                    if (numToUse[i] != 0 && currentTotal % numToUse[i] == 0) {
                        operation[opIndex] = '/';
                        totals[opIndex + 1] = currentTotal / numToUse[i];
                        found = rocketscience(numToUse, targetNum, used, temp, tempIndex + 1,
                                currentTotal / numToUse[i], operation, totals, opIndex + 1, stopOnFirstExact);
                        if (stopOnFirstExact && found) {
                            used[i] = false;
                            break;
                        }
                    }
                }
                used[i] = false;
                if (stopOnFirstExact && found)
                    break;
            }
        }

        // Show best approximation if no exact match was found and this is the top-level call
        if (tempIndex == 0 && !found && bestTemp != null) {
            CifrasYLetrasApplication.showSolution(targetNum, bestTemp, bestTempIndex, bestOperation, bestTotals,
                    bestOpIndex);
        }

        return found;
    }
}
