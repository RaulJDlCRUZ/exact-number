package com.kangoo.cyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CifrasYLetrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CifrasYLetrasApplication.class, args);

		// Define target number and the six numbers to use
		int targetNum = new com.kangoo.cyl.domain.service.Rand().randomTarget();
		int[] numToUse = new com.kangoo.cyl.domain.service.Rand().randomNumbers();

		//Show the target number and the numbers to use
		System.out.println("Target Number: " + targetNum);
		System.out.print("Numbers to use: ");
		for (int i = 0; i < numToUse.length; i++) {
			System.out.print(numToUse[i] + " ");
		}
		System.out.println();

		// Create the arrays which will be used to store the used numbers, temporary numbers, operations and totals
		boolean[] used = new boolean[numToUse.length];
		int[] temp = new int[numToUse.length];
		char[] operation = new char[numToUse.length - 1];
		int[] totals = new int[temp.length]; 

		// Call the backtracking method with indexes and current total initialized to 0
		new com.kangoo.cyl.domain.service.Solver().rocketscience(numToUse, targetNum, used, temp, 0, 0, operation, totals, 0);
	}

	public static void showSolution(int targetNum, int[] temp, int tempIndex, char[] operation, int[] totals, int opIndex) {
		System.out.println("Solution found:");

		// Print the operations performed to reach the target number
		for (int i = 0; i < opIndex; i++) {
			System.out.println(totals[i] + " " + operation[i] + " " + temp[i + 1] + " = " + totals[i + 1]);
		}
    }

}
