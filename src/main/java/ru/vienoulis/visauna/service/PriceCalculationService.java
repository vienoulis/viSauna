package ru.vienoulis.visauna.service;

import org.springframework.stereotype.Component;
import ru.vienoulis.visauna.dto.Hall;
import ru.vienoulis.visauna.dto.PriceSlot;

import java.util.StringJoiner;

@Component
public class PriceCalculationService {


    public long calculatePricePerHourFor(Hall hall, int countOfVisitor) {
        return Math.multiplyExact(hall.getPriceFor(countOfVisitor, isWeekends()), countOfVisitor);
    }

    //    todo Определение не выходной ли
    private boolean isWeekends() {
        return false;
    }
}
