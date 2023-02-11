package ru.vienoulis.visauna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Set;

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

    //todo избавиться от проброса стандартных исключений
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

    private Long getWeekendTax() {
        return 200L;
    }


    public Set<PriceSlot> getPriceList() {
        return visitorPriceMap;
    }
}
