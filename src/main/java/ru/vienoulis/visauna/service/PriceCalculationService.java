package ru.vienoulis.visauna.service;

import org.jvnet.hk2.annotations.Service;
import ru.vienoulis.visauna.dto.Hall;
import ru.vienoulis.visauna.dto.PriceSlot;

import java.util.StringJoiner;

@Service
public class PriceCalculationService {

    public long calculatePriceFor(Hall hall, int countOfVisitor) {
        return Math.multiplyExact(hall.getPriceFor(countOfVisitor, isWeekends()), countOfVisitor);
    }

//    todo Определение не выходной ли
    private boolean isWeekends() {
        return false;
    }

    public String getPriceList(Hall hall) {
        StringJoiner sj = new StringJoiner("\n");
        hall.getPriceList()
                .stream()
                .map(PriceSlot::getStringValue)
                .forEach(sj::add);
        return sj.toString();
    }
}
