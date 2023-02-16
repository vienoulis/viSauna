package ru.vienoulis.visauna.model.exception;

public class OutOfCapacityException extends Exception {
    private final int currentCapacity;
    private final int calculatedCapacity;

    public OutOfCapacityException(int currentCapacity, int calculatedCapacity) {
        this.currentCapacity = currentCapacity;
        this.calculatedCapacity = calculatedCapacity;
    }
}
