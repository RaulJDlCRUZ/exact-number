package com.kangoo.cyl.application;

import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.repository.SolutionRepository;
import com.kangoo.cyl.domain.service.OptimizedSolver;
import com.kangoo.cyl.domain.service.RocketScience;

public class CifrasYLetrasService {

    private final RocketScience solver;
    private final OptimizedSolver solverII;

    private final SolutionRepository repository;

    public CifrasYLetrasService(SolutionRepository repository) {
        this.solver = new RocketScience();
        this.solverII = new OptimizedSolver();
        this.repository = repository;
    }

    public OptimalSolution solveA(Integer[] numbers, Integer target) {
        OptimalSolution solution = solver.solve(numbers, target);
        repository.save(numbers, target, solution);
        return solution;
    }

    public OptimalSolution solveB(Integer[] numbers, Integer target) {
        return solverII.solve(numbers, target);
    }
}
