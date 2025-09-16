package com.kangoo.cyl.domain.service;

public class Solver {

    public boolean approximation(int currentTotal, int targetNum) {
        // Implement your approximation logic here
        throw new UnsupportedOperationException("Unimplemented method 'approximation'");
    }

    public void showSolution(int targetNum, int[] temp, int tempIndex) {
        // Implement your solution display logic here
        throw new UnsupportedOperationException("Unimplemented method 'showSolution'");
    }

    public int operation() {
        // Implement your operation logic here
        throw new UnsupportedOperationException("Unimplemented method 'operation'");
    }

    public void rocketscience(int[] numToUse, int targetNum, boolean[] used, int[] temp, int tempIndex,
            int currentTotal) {
        if (currentTotal == targetNum || approximation(currentTotal, targetNum)) {
            showSolution(targetNum, temp, tempIndex);
        } else {
            for (int i = 0; i < numToUse.length; i++) {
                // BLANK SPACE
                if (!used[i]) {
                    used[i] = true;
                    temp[tempIndex] = numToUse[i];
                    rocketscience(numToUse, targetNum, used, temp, tempIndex + 1, operation(/* BLANK SPACE */));
                    used[i] = false;
                }
            }
        }
    }
}
