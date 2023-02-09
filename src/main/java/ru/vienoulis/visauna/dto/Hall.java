package ru.vienoulis.visauna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class Hall {
    private int capacity;
    private final Map<Long, Long> visitorPriceMap = new HashMap<>();

    //todo избавиться от проброса стандартных исключений
    public void setPriceFor(long countOfVisitor, long price) {
        if (countOfVisitor > capacity || countOfVisitor <= 0)
            throw new RuntimeException(String.format("Введенное значение (%d) не может быть меньше 1 и больше чем %d", countOfVisitor, capacity));
        if (price < 1)
            throw new RuntimeException(String.format("Ты ввел отрицательное число"));

        visitorPriceMap.merge(countOfVisitor, price, (a, b) -> price);
    }

    public long getPriceFor(long countOfVisitor) {
        if (countOfVisitor > capacity || countOfVisitor <= 0)
            throw new RuntimeException(String.format("Введенное значение (%d) не может быть меньше 1 и больше чем %d", countOfVisitor, capacity));

        if (visitorPriceMap.containsKey(countOfVisitor))
            return visitorPriceMap.get(countOfVisitor);

        return getPriceFor(--countOfVisitor);
    }
}
