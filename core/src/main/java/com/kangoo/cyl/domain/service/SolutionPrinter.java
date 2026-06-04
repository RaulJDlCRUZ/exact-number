package com.kangoo.cyl.domain.service;

public class SolutionPrinter {

    public static void showSolution(int targetNum, int[] temp, int tempIndex,
            char[] operation, int[] totals, int opIndex) {
        System.out.println("Solution found:");
        for (int i = 0; i < opIndex; i++) {
            System.out.println(totals[i] + " " + operation[i] + " " + temp[i + 1] + " = " + totals[i + 1]);
        }
    }
}