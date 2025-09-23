package com.kangoo.cyl.domain.entity;

import java.util.List;

public class OptimalSolution {
    private Integer minimalDistance;
    private Integer operationsNeeded;
    private List<Operation> optimalSolution;
    private Integer bestResult;
    private Boolean isExact;

    public OptimalSolution(Integer minimalDistance, Integer operationsNeeded, List<Operation> optimalSolution, Integer bestResult,
            Boolean isExact) {
        this.minimalDistance = minimalDistance;
        this.operationsNeeded = operationsNeeded;
        this.optimalSolution = optimalSolution;
        this.bestResult = bestResult;
        this.isExact = isExact;
    }

    public Integer getMinimalDistance() {
        return minimalDistance;
    }

    public void setMinimalDistance(Integer minimalDistance) {
        this.minimalDistance = minimalDistance;
    }

    public Integer getOperationsNeeded() {
        return operationsNeeded;
    }

    public void setOperationsNeeded(Integer operationsNeeded) {
        this.operationsNeeded = operationsNeeded;
    }

    public List<Operation> getOptimalSolution() {
        return optimalSolution;
    }

    public void setOptimalSolution(List<Operation> optimalSolution) {
        this.optimalSolution = optimalSolution;
    }

    public Integer getBestResult() {
        return bestResult;
    }

    public void setBestResult(Integer bestResult) {
        this.bestResult = bestResult;
    }

    public Boolean getIsExact() {
        return isExact;
    }

    public void setIsExact(Boolean isExact) {
        this.isExact = isExact;
    }

}
