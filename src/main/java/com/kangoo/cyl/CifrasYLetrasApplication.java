package com.kangoo.cyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.service.Rand;
import com.kangoo.cyl.domain.service.RocketScience;
import com.kangoo.cyl.domain.service.Solver;

@SpringBootApplication
public class CifrasYLetrasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CifrasYLetrasApplication.class, args);

		int[] numToUse = new int[6];
		int targetNum;

		Rand rand = new Rand();
		Solver solver = new Solver();
		RocketScience rocketScience = new RocketScience();

		if (args.length == 7) {
			// If 7 arguments are provided, parse them as integers
			for (int i = 0; i < 6; i++) {
				numToUse[i] = Integer.parseInt(args[i]);
			}
			targetNum = Integer.parseInt(args[6]);
		} else {
			// If no arguments are provided, generate random numbers
			// Define target number and the six numbers to use
			targetNum = rand.randomTarget();
			numToUse = rand.randomNumbers();
		}

		// Show the target number and the numbers to use
		System.out.println("Target Number: " + targetNum);
		System.out.print("Numbers to use: ");
		for (int i = 0; i < numToUse.length; i++) {
			System.out.print(numToUse[i] + " ");
		}
		System.out.println();

		// Create the arrays which will be used to store the used numbers, temporary
		// numbers, operations and totals
		boolean[] used = new boolean[numToUse.length];
		int[] temp = new int[numToUse.length];
		char[] operation = new char[numToUse.length - 1];
		int[] totals = new int[temp.length];

		// Call the backtracking method with indexes and current total initialized to 0
		solver.rocketscience(numToUse, targetNum, used, temp, 0, 0, operation,
				totals, 0, false);

		System.exit(0);

		// Call the RocketScience solver
		Integer[] numToUseBoxed = new Integer[numToUse.length];
		for (int i = 0; i < numToUse.length; i++) {
			numToUseBoxed[i] = numToUse[i];
		}
		OptimalSolution solution = rocketScience.solve(numToUseBoxed, targetNum);
		if (solution.getOptimalSolution() != null) {
			System.out.println("Best solution found with distance " + solution.getMinimalDistance() + " and "
					+ solution.getOperationsNeeded() + " operations:");
			solution.getOptimalSolution().forEach(op -> {
				System.out.println(op.toString());
			});
		} else {
			System.out.println("No solution found.");
		}
		System.exit(0);
	}

	public static void showSolution(int targetNum, int[] temp, int tempIndex, char[] operation, int[] totals,
			int opIndex) {
		System.out.println("Solution found:");

		// Print the operations performed to reach the target number
		for (int i = 0; i < opIndex; i++) {
			System.out.println(totals[i] + " " + operation[i] + " " + temp[i + 1] + " = " + totals[i + 1]);
		}
	}

}
