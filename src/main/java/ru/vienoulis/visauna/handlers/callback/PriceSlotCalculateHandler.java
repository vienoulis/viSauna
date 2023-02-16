package ru.vienoulis.visauna.handlers.callback;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.PriceSlotCQD;
import ru.vienoulis.visauna.service.KeyBoardService;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
public class PriceSlotCalculateHandler extends CallbackQueryHandler<PriceSlotCQD> {
    private final KeyBoardService keyBoardService;

    public PriceSlotCalculateHandler(KeyBoardService keyBoardService) {
        this.keyBoardService = keyBoardService;
    }

    @Override
    public Action getIdentifier() {
        return Action.priceSlot;
    }

    @Override
    @SneakyThrows
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");

        SendMessage sendMessage = SendMessage.builder()
                .text("На сколько часов?")
                .chatId(callback.getMessage().getChatId())
                .replyMarkup(keyBoardService.inputHours(data.getPriceSlot()))
                .build();

        absSender.execute(sendMessage);
        log.info("execute.exit;");
    }
}
