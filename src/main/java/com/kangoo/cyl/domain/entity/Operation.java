package com.kangoo.cyl.domain.entity;

import com.kangoo.cyl.domain.vo.Operator;

public class Operation {
    private Integer firstOperand;
    private Integer secondOperand;
    private Operator operator;
    private Integer result;

    public Integer getFirstOperand() {
        return firstOperand;
    }

    public Integer getSecondOperand() {
        return secondOperand;
    }

    public Operator getOperator() {
        return operator;
    }

    public Integer getResult() {
        return result;
    }

    public Operation(Integer firstOperand, Integer secondOperand, Operator operator) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operator = operator;
        this.result = null; // Result will be computed later
    }

    /* This function checks arithmetic validations */
    public boolean isValid() {
        boolean isValid = true;
        switch (operator) {
            case Operator.DIVISION:
                // Division by zero is not allowed
                if (secondOperand == 0) {
                    isValid = false;
                    // Division must result in an integer
                } else if (firstOperand % secondOperand != 0) {
                    isValid = false;
                }
                break;
            // Negative results are not allowed for subtraction
            case Operator.SUBTRACTION:
                if (firstOperand < secondOperand) {
                    isValid = false;
                }
                break;
            default:
                break;
        }
        return isValid;
    }

    public Integer computeResult() {
        return operator.doOperation(firstOperand, secondOperand);
    }

    @Override
    public String toString() {
        if (result == null) {
            result = computeResult();
        }
        return firstOperand + " " + operator + " " + secondOperand + " = " + result;
    }
}