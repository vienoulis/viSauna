package ru.vienoulis.visauna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
@AllArgsConstructor
public class Hall {
    @Getter
    private int capacity;
    private final Set<PriceSlot> visitorPriceMap = new LinkedHashSet<>();
    @Getter
    private String description;

    public Hall(int capacity) {
        this.capacity = capacity;
    }

    public Set<PriceSlot> getPriceList() {
        return visitorPriceMap;
    }

    public void setPriceFor(PriceSlot slot) {
        visitorPriceMap.add(slot);
    }

    public long getPriceFor(int countOfVisitor, boolean isWeekend) {
        return visitorPriceMap.stream()
                .filter(p -> p.inRange(countOfVisitor))
                .findFirst()
                .map(PriceSlot::getPrice)
                .map(l -> isWeekend ? l + getWeekendTax() : l)
                .orElseThrow();
    }

    public String getPriceListStringValue() {
        StringJoiner sj = new StringJoiner("\n");

        sj.add(getDescription().toUpperCase());
        sj.add(" ");
        getPriceList()
                .stream()
                .map(PriceSlot::getStringValue)
                .forEach(sj::add);
        return sj.toString();
    }

    public long calculatePrice(int countOfVisitor, int hours) {
        if (hours < 2 || countOfVisitor < 1)
            throw new ArithmeticException(String.format("Часы (%d) не могут быть меньше 2, а посетителей (%d) меньше 1",
                    hours, countOfVisitor));
        return Math.multiplyExact(calculatePricePerHourFor(countOfVisitor), hours);
    }

    private long calculatePricePerHourFor(int countOfVisitor) {
        return getPriceFor(countOfVisitor, isWeekends());
    }
    //    todo Определение не выходной ли

    private boolean isWeekends() {
        return false;
    }

    private Long getWeekendTax() {
        return 200L;
    }

}
