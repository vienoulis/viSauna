package ru.vienoulis.visauna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

import static ru.vienoulis.visauna.model.CallbackQueryHandlers.BIG_HALL;
import static ru.vienoulis.visauna.model.CallbackQueryHandlers.SMALL_HALL;

@Component
public class KeyBoardService {

    @Autowired
    public KeyBoardService() {
    }

    public InlineKeyboardMarkup getChooseHllKb() {
        var rowOne = new ArrayList<InlineKeyboardButton>();
        rowOne.add(InlineKeyboardButton.builder()
                .text("Большой")
                .callbackData(BIG_HALL.name())
                .build());
        rowOne.add(InlineKeyboardButton.builder()
                .text("Малый")
                .callbackData(SMALL_HALL.name())
                .build());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowOne)
                .build();
    }
}
