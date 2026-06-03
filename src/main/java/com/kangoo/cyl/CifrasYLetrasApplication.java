package com.kangoo.cyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.kangoo.cyl.application.CifrasYLetrasService;
import com.kangoo.cyl.domain.repository.SolutionRepository;
import com.kangoo.cyl.domain.service.Rand;
import com.kangoo.cyl.infrastructure.inmemory.InMemorySolutionRepository;

@SpringBootApplication
public class CifrasYLetrasApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(CifrasYLetrasApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = app.run(args);
		// SpringApplication.run(CifrasYLetrasApplication.class, args);

		SolutionRepository repo = new InMemorySolutionRepository(); // PARA EJECUCION DESDE CLI USO INMEMORY
		CifrasYLetrasService service = new CifrasYLetrasService(repo);

		int[] numToUse = new int[6];
		int targetNum;

		Rand rand = new Rand();

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

		// Call the RocketScience solver
		Integer[] numToUseBoxed = new Integer[numToUse.length];
		for (int i = 0; i < numToUse.length; i++) {
			numToUseBoxed[i] = numToUse[i];
		}
		service.solveA(numToUseBoxed, targetNum);
		SpringApplication.exit(context);
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
