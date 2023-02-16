package ru.vienoulis.visauna.handlers.callback;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.PriseSlotAndHoursCQD;
import ru.vienoulis.visauna.model.callback.TestCQD;

@Slf4j
@Component
public class PriseSlotAndHoursHandler extends CallbackQueryHandler<PriseSlotAndHoursCQD> {
    @Override
    public Action getIdentifier() {
        return Action.priceSlotWithHour;
    }

    @Override
    @SneakyThrows
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");
        var priseSlot = data.getPriceSlot();
        var hours = data.getHours();
        var priceForVisit = priseSlot.getPrice() * hours / 100;
        var priceForClean = priceForVisit / 10;
        var priceAndClean = priceForClean + priceForVisit;

        absSender.execute(SendMessage.builder()
                .text(String.format("""
                                Посещение в количестве %s, на %d ч.
                                стоит %d.00 руб. плюс %d.00 руб. за уборку.
                                Итого: %d.00 руб""",
                        priseSlot, hours, priceForVisit, priceForClean, priceAndClean))
                .chatId(callback.getMessage().getChatId())
                .build());
        log.info("execute.exit;");
    }
}
