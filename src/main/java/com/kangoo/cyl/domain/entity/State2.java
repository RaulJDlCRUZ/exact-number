package com.kangoo.cyl.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class State2 {
    private List<Integer> availableNumbers;
    private List<Operation> operations;
    private boolean[] used; // Para backtracking eficiente

    public State2 createInitState(List<Integer> numbers) {
        State2 state = new State2();
        state.availableNumbers = new ArrayList<>(numbers);
        state.operations = new ArrayList<>();
        state.used = new boolean[numbers.size()];
        return state;
    }

    // Operaciones mutables con undo
    public void applyOperation(Operation operation) {
        operations.add(operation);
    }

    public void undoOperation() {
        if (!operations.isEmpty()) {
            operations.remove(operations.size() - 1);
        }
    }

    public void markUsed(int index) {
        used[index] = true;
    }

    public void markUnused(int index) {
        used[index] = false;
    }

    public boolean isUsed(int index) {
        return used[index];
    }

    public int getAvailableCount() {
        int count = 0;
        for (boolean u : used) {
            if (!u)
                count++;
        }
        return count;
    }

    // Solo copiar cuando sea necesario (al guardar solución)
    public List<Operation> copyOperations() {
        return new ArrayList<>(operations);
    }

    public List<Integer> getAvailableNumbers() {
        return availableNumbers;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}