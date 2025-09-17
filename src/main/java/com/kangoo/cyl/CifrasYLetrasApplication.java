package com.kangoo.cyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CifrasYLetrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CifrasYLetrasApplication.class, args);
		int targetNum = new com.kangoo.cyl.domain.service.Rand().randomTarget();
		int[] numToUse = new com.kangoo.cyl.domain.service.Rand().randomNumbers();
		System.out.println("Target Number: " + targetNum);
		System.out.print("Numbers to use: ");
		for (int i = 0; i < numToUse.length; i++) {
			System.out.print(numToUse[i] + " ");
		}
		System.out.println();
		boolean[] used = new boolean[numToUse.length];
		int[] temp = new int[numToUse.length];
		char[] operation = new char[numToUse.length - 1];
		int[] totals = new int[temp.length]; // Array to store totals
		new com.kangoo.cyl.domain.service.Solver().rocketscience(numToUse, targetNum, used, temp, 0, 0, operation, totals, 0);
	}

	public static void showSolution(int targetNum, int[] temp, int tempIndex, char[] operation, int[] totals, int opIndex) {
		System.out.println("Solution found:");
		for (int i = 0; i < opIndex; i++) {
			System.out.println(totals[i] + " " + operation[i] + " " + temp[i + 1] + " = " + totals[i + 1]);
		}
    }

}
