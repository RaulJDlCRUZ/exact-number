package com.kangoo.cyl.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Integer> availableNumbers = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();
    private Integer actualResult;

    /**
     * Factory to create an initial state from a list of numbers.
     */
    public static State createInitState(List<Integer> numbers) {
        State state = new State();
        state.availableNumbers = new ArrayList<>(numbers);
        state.operations = new ArrayList<>();
        state.actualResult = null;
        return state;
    }

    /**
     * Create a deep copy of the given state.
     */
    public static State createStateFrom(State source) {
        State newState = new State();
        newState.availableNumbers = new ArrayList<>(source.availableNumbers);
        newState.operations = new ArrayList<>(source.operations);
        newState.actualResult = source.actualResult;
        return newState;
    }

    /**
     * Trigger an operation against this state (instance method).
     * This mutates the current instance by computing the operation result,
     * removing the operands (by value), and adding the computed result.
     */
    public State triggerOperation(Operation operation) {
        this.actualResult = operation.computeResult();
        this.removeNumbersByValues(
            operation.getFirstOperand(),
            operation.getSecondOperand()
        );
        this.addNumber(this.actualResult);
        return this;
    }

    /**
     * Add an operation to this state's operation list.
     */
    public State addOperation(Operation operation) {
        this.operations.add(operation);
        return this;
    }

    /**
     * Remove numbers from availableNumbers by matching their values.
     * Safely handles nulls and missing values.
     */
    public State removeNumbersByValues(Integer n1, Integer n2) {
        if (n1 != null) {
            this.availableNumbers.remove(n1); // remove(Object) -> removes first occurrence of value
        }
        if (n2 != null) {
            this.availableNumbers.remove(n2);
        }
        return this;
    }

    /**
     * Remove a number by index (safe).
     */
    public State removeNumberByIndex(int index) {
        if (index >= 0 && index < this.availableNumbers.size()) {
            this.availableNumbers.remove(index);
        }
        return this;
    }

    /**
     * Add a new number to the availableNumbers list.
     */
    public State addNumber(Integer n) {
        if (n != null) {
            this.availableNumbers.add(n);
        }
        return this;
    }

    /* -- Standard getters/setters -- */

    public List<Integer> getAvailableNumbers() {
        return availableNumbers;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Integer getActualResult() {
        return actualResult;
    }

    public void setAvailableNumbers(List<Integer> availableNumbers) {
        this.availableNumbers = availableNumbers;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public void setActualResult(Integer actualResult) {
        this.actualResult = actualResult;
    }
}
