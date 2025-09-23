package com.kangoo.cyl.domain.service;

import com.kangoo.cyl.CifrasYLetrasApplication;

public class Solver {

    // This method checks if the current total is within 10 of the target number,
    // which could be a great approximation
    public boolean approximation(int currentTotal, int targetNum) {
        Math.abs(currentTotal - targetNum);
        return Math.abs(currentTotal - targetNum) <= 10;
    }

    // Classic backtracking algorithm to find the solution
    public boolean rocketscience(int[] numToUse, int targetNum, boolean[] used, int[] temp, int tempIndex,
            int currentTotal, char[] operation, int[] totals, int opIndex) {

        // The base case: if the current total matches the target number or is a great
        // approximation, call showSolution on "main" to print the solution and return
        // true
        if (currentTotal == targetNum || approximation(currentTotal, targetNum)) {
            CifrasYLetrasApplication.showSolution(targetNum, temp, tempIndex, operation, totals, opIndex);
            return true;
        } else {

            // Iterate through the array of numbers to use
            for (int i = 0; i < numToUse.length; i++) {

                // If the number has not been used yet, mark it as used and add it to the
                // temporary array
                if (!used[i]) {
                    used[i] = true;
                    temp[tempIndex] = numToUse[i];

                    // If it's the first number, initialize the current total with it
                    if (tempIndex == 0) {
                        totals[0] = temp[0];
                        rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, numToUse[i], operation, totals,
                                opIndex);
                    } else {

                        // Try all the operations and find the best one
                        // Addition
                        operation[opIndex] = '+';
                        totals[opIndex + 1] = currentTotal + numToUse[i];
                        if (rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal + numToUse[i],
                                operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Subtraction
                        operation[opIndex] = '-';
                        totals[opIndex + 1] = currentTotal - numToUse[i];
                        if (rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal - numToUse[i],
                                operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Multiplication
                        operation[opIndex] = '*';
                        totals[opIndex + 1] = currentTotal * numToUse[i];
                        if (rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal * numToUse[i],
                                operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Division
                        if (numToUse[i] != 0 && currentTotal % numToUse[i] == 0) {
                            operation[opIndex] = '/';
                            totals[opIndex + 1] = currentTotal / numToUse[i];
                            if (rocketscience(numToUse, targetNum, used, temp, tempIndex + 1,
                                    currentTotal / numToUse[i], operation, totals, opIndex + 1)) {
                                return true;
                            }
                        }
                    }
                    used[i] = false;
                }
            }
        }
        return false;
    }
}
