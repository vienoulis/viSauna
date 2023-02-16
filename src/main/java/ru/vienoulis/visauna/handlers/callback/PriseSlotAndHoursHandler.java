package ru.vienoulis.visauna.handlers.callback;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.Prices;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.PriseSlotAndHoursCQD;
import ru.vienoulis.visauna.service.PriceCalculationService;

@Slf4j
@Component
public class PriseSlotAndHoursHandler extends CallbackQueryHandler<PriseSlotAndHoursCQD> {
    private final PriceCalculationService calculationService;

    @Autowired
    public PriseSlotAndHoursHandler(PriceCalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @Override
    public Action getIdentifier() {
        return Action.priceSlotWithHour;
    }

    @Override
    @SneakyThrows
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");

        var prices = calculationService.calculatePricePerHourFor(data);

        absSender.execute(SendMessage.builder()
                .text(getTotoalSumString(prices))
                .chatId(callback.getMessage().getChatId())
                .build());

        log.info("execute.exit;");
    }

    private String getTotoalSumString(Prices prices) {
        return String.format("""
                        Посещение в количестве %s, на %d ч.
                        стоит %d.00 руб. плюс %d.00 руб. за уборку.
                        Итого: %d.00 руб""",
                data.getPriceSlot(), data.getHours(), prices.getPriceForVisit(),
                prices.getPriceForClean(), prices.getPriceAndClean());
    }
}
