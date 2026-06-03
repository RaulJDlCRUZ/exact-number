package com.kangoo.cyl.infrastructure.inmemory;

import com.kangoo.cyl.domain.entity.OptimalSolution;
import com.kangoo.cyl.domain.repository.SolutionRepository;

public class InMemorySolutionRepository implements SolutionRepository {
    
    @Override
    public void save(OptimalSolution solution) {
        if (solution.getOptimalSolution() == null) {
            System.err.println("[!!] No solution found.");
        } else {
            System.out.println("[OK] Best solution found with distance " + solution.getMinimalDistance() + " and "
            + solution.getOperationsNeeded() + " operations:");
            
            solution.getOptimalSolution().forEach(op -> {
                System.out.println(op.toString());
            });
        }
    }
}