package ru.vienoulis.visauna.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

@Data
public class PriceSlot {
    private static final Set<DayOfWeek> WEEKENDS = Set.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
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
        return String.format("%d-%d чел. от %d руб/час.", min, max, getPricePerHours() / 100);
    }

    public long getPricePerHours() {
        return getPrice() + getWeekendTax();
    }

    private long getWeekendTax() {
        var dayOfWeek = LocalDate.now(ZoneId.of("Europe/Moscow")).getDayOfWeek();
        return isWeekend(dayOfWeek) ? 20000 : 0;
    }

    private static boolean isWeekend(DayOfWeek dayOfWeek) {
        return WEEKENDS.contains(dayOfWeek);
    }


    @Override
    public String toString() {
        return String.format("%d - %d чел.", getMin(), getMax());
    }
}
