package ru.vienoulis.visauna.model.exception;

import java.util.Set;

public class HasEmptySlotsException extends Exception {
    private final Set<Integer> emptySlons;

    public HasEmptySlotsException(Set<Integer> emptySlons) {
        this.emptySlons = emptySlons;
    }
}
