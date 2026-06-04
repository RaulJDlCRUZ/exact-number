package com.kangoo.cyl.domain.repository;

import com.kangoo.cyl.domain.entity.OptimalSolution;

public interface SolutionRepository {
    void save(OptimalSolution solution);
}