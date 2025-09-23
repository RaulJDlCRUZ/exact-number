package com.kangoo.cyl.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<Integer> availableNumbers = new ArrayList<>();
    private List<Operation> operations = new ArrayList<>();
    private Integer actualResult;

    public State createInitState(List<Integer> numbers) {
        State state = new State();
        state.availableNumbers = new ArrayList<>(numbers);
        state.operations = new ArrayList<>();
        state.actualResult = null;
        return state;
    }

    private State triggerOperation(Operation operation, State state) {
        state.actualResult = operation.computeResult();
        state = removeNumbers(operation.getFirstOperand(), operation.getSecondOperand(), state);
        state = addNumber(state.actualResult, state);
        return state;
    }

    public State createStateFrom(State source) {
        State newState = new State();
        newState.availableNumbers = new ArrayList<>(source.availableNumbers);
        newState.operations = new ArrayList<>(source.operations);
        newState.actualResult = source.actualResult;
        return newState;
    }

    public State addOperation(Operation operation, State state) {
        state.operations.add(operation);
        return state;
    }

    public State removeNumbers(Integer n1, Integer n2, State state) {
        state.availableNumbers.remove(n1);
        state.availableNumbers.remove(n2);
        return state;
    }

    public State addNumber(Integer n, State state) {
        state.availableNumbers.add(n);
        return state;
    }

    public List<Integer> getAvailableNumbers() {
        return availableNumbers;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public Integer getActualResult() {
        return actualResult;
    }
}
