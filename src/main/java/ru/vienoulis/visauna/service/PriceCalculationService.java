package ru.vienoulis.visauna.service;

import lombok.SneakyThrows;
import org.jvnet.hk2.annotations.Service;
import ru.vienoulis.visauna.dto.Hall;

@Service
public class PriceCalculationService {

    @SneakyThrows
    public long calculatePriceFor(Hall hall, int countOfVisitor) {
        return Math.multiplyExact(hall.getPriceFor(countOfVisitor), countOfVisitor);
    }
}
