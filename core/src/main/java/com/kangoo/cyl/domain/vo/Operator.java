package com.kangoo.cyl.domain.vo;

public enum Operator {
    ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION;

    public Integer doOperation(Integer firstOperand, Integer secondOperand) {
        switch (this) {
            case ADDITION:
                return firstOperand + secondOperand;
            case SUBTRACTION:
                return firstOperand - secondOperand;
            case MULTIPLICATION:
                return firstOperand * secondOperand;
            case DIVISION:
                return firstOperand / secondOperand;
            default:
                throw new IllegalArgumentException("Unknown operator: " + this);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ADDITION:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                return "";
        }
    }

    public static Operator fromString(String symbol) {
        switch (symbol) {
            case "+":
                return ADDITION;
            case "-":
                return SUBTRACTION;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            default:
                throw new IllegalArgumentException("Unknown operator symbol: " + symbol);
        }
    }


}
