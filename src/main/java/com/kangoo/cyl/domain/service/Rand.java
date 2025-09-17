package com.kangoo.cyl.domain.service;

public class Rand {

    public int randomTarget() {
        return (int) (Math.random() * 900) + 101; // Random number between 101 and 999
    }

    public int[] randomNumbers() {
        int[] numbers = new int[6];
        // Two large numbers: 25, 50, 75, 100
        int[] largeNumbers = {25, 50, 75, 100};
        // Four small numbers: 1 to 10
        for (int i = 0; i < 2; i++) {
            numbers[i] = largeNumbers[(int) (Math.random() * largeNumbers.length)];
        }
        for (int i = 2; i < 6; i++) {
            numbers[i] = (int) (Math.random() * 10) + 1;
        }
        return numbers;
    }
}
