package ru.vienoulis.visauna.dto;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PriceSlot {
    int min;
    int max;
    long price;

    public PriceSlot(int min, int max, long price) {
        if (min > max)
            throw new RuntimeException("Минимальное значение не может быт больше максимального");
        this.min = min;
        this.max = max;
        this.price = price;
    }

    public boolean inRange(int value) {
        return value >= min && value <= max;
    }

    public String getStringValue() {
        return String.format("%d-%d чел. от %d руб/час.", min, max, price / 100);
    }
}
