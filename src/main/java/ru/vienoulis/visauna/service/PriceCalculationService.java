package ru.vienoulis.visauna.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.vienoulis.visauna.dto.PriceSlot;
import ru.vienoulis.visauna.model.Prices;
import ru.vienoulis.visauna.model.callback.PriseSlotAndHoursCQD;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

@Slf4j
@Component
public class PriceCalculationService {
    @Value("${app.percentForClean}")
    private long percentForClean;

    public Prices calculatePricePerHourFor(PriseSlotAndHoursCQD priseSlotAndHour) {
        log.info("calculatePricePerHourFor.enter; priseSlotAndHour: {}", priseSlotAndHour);
        var priceForVisit = getPriceForVisit(priseSlotAndHour);
        var priceForClean = getPriceForClean(priceForVisit);
        var priceAndClean = priceForClean + priceForVisit;

        var result =  Prices.builder()
                .priceForVisit(priceForVisit)
                .priceForClean(priceForClean)
                .priceAndClean(priceAndClean)
                .build();
        log.info("calculatePricePerHourFor.exit; result: {}", result);
        return result;
    }

    private long getPriceForClean(long priceForVisit) {
        return priceForVisit / 100 * percentForClean;
    }

    private long getPriceForVisit(PriseSlotAndHoursCQD priseSlotAndHour) {
        var hours = priseSlotAndHour.getHours();
        var priseSlot = priseSlotAndHour.getPriceSlot();
        return priseSlot.getPricePerHours() * hours / 100;
    }
}
