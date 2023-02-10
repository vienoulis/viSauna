package ru.vienoulis.visauna.model.exception;

import ru.vienoulis.visauna.dto.PriceSlot;

import java.util.List;

public class RangeConflictException  extends Exception{
    private final List<PriceSlot> conflictedRanges;

    public RangeConflictException(List<PriceSlot> conflictedRanges) {
        this.conflictedRanges = conflictedRanges;
    }
}
