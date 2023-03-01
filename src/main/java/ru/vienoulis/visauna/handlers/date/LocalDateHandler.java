package ru.vienoulis.visauna.handlers.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.vienoulis.visauna.model.BookingSlot;
import ru.vienoulis.visauna.service.KeyBoardService;
import ru.vienoulis.visauna.service.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class LocalDateHandler {
    private static final String REG_EXP = "^\\d{4}-\\d{2}-\\d{2}$";
    private final Repository repository;
    private final KeyBoardService keyBoardService;

    @Autowired
    public LocalDateHandler(Repository repository, KeyBoardService keyBoardService) {
        this.repository = repository;
        this.keyBoardService = keyBoardService;
    }

    public boolean isLocalData(CallbackQuery callbackQuery) {
        return Pattern.matches(REG_EXP, callbackQuery.getData());
    }

    public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
        log.info("handleCallbackQuery.enter;");
        repository.addBookingSlot(LocalDate.parse(callbackQuery.getData()), BookingSlot.builder().slotName("asd;fojkh]asrdg'lkhn'").build());
        var currentSlots = repository.getBookingSlotAt(LocalDate.parse(callbackQuery.getData()));
        var markup = InlineKeyboardMarkup.builder()
                .keyboard(List.of(keyBoardService.generateSlotsBtn(currentSlots)))
                .build();
        log.info("handleCallbackQuery.exit;");
        return SendMessage.builder()
                .text("Hello")
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(markup)
                .build();
    }
}
