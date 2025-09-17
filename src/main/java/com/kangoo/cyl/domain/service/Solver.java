package com.kangoo.cyl.domain.service;

import com.kangoo.cyl.CifrasYLetrasApplication;

public class Solver {


    public boolean approximation(int currentTotal, int targetNum) {
        Math.abs(currentTotal - targetNum);
        return Math.abs(currentTotal - targetNum) <= 10;
    }

    public boolean rocketscience(int[] numToUse, int targetNum, boolean[] used, int[] temp, int tempIndex, int currentTotal, char[] operation, int[] totals, int opIndex) {
        if (currentTotal == targetNum || approximation(currentTotal, targetNum)) {
            CifrasYLetrasApplication.showSolution(targetNum, temp, tempIndex, operation, totals, opIndex);
            return true;
        } else {
            for (int i = 0; i < numToUse.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    temp[tempIndex] = numToUse[i];
                    if (tempIndex == 0) {
                        totals[0] = temp[0];
                        rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, numToUse[i], operation, totals, opIndex);
                    } else {
                        // Addition
                        operation[opIndex] = '+';
                        totals[opIndex + 1] = currentTotal + numToUse[i];
                        if(rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal + numToUse[i], operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Subtraction
                        operation[opIndex] = '-';
                        totals[opIndex + 1] = currentTotal - numToUse[i];
                        if(rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal - numToUse[i], operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Multiplication
                        operation[opIndex] = '*';
                        totals[opIndex + 1] = currentTotal * numToUse[i];
                        if(rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal * numToUse[i], operation, totals, opIndex + 1)) {
                            return true;
                        }
                        // Division
                        if (numToUse[i] != 0 && currentTotal % numToUse[i] == 0) {
                            operation[opIndex] = '/';
                            totals[opIndex + 1] = currentTotal / numToUse[i];
                            if(rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, currentTotal / numToUse[i], operation, totals, opIndex + 1)) {
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
