package ru.vienoulis.visauna.dto;

import lombok.Data;

@Data
public class PriceSlot {
    public static final int MIN_HOURS = 2;
    public static final int MAX_POW = 9;
    public static final int MAX_HOURS = 17;
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

    public static int getMIN_HOURS() {
        return MIN_HOURS;
    }

    public static int getMAX_HOURS() {
        return MAX_HOURS;
    }

    @Override
    public String toString() {
        return String.format("%d - %d чел.", getMin(), getMax());
    }
}
