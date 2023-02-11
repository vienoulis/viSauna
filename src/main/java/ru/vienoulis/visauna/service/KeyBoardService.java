package ru.vienoulis.visauna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

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

    public ReplyKeyboard test() {
        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                .build();
        KeyboardButton testBtn = new KeyboardButton("testBtn");
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(testBtn))));
        return keyboardMarkup;
    }

    public ReplyKeyboard getStartKB() {
        KeyboardButton startBtn = new KeyboardButton("/start");
        KeyboardButton testBtn = KeyboardButton.builder()
                .text("/test")
                .build();

        return ReplyKeyboardMarkup.builder()
                .keyboardRow(new KeyboardRow(List.of(startBtn, testBtn)))
                .resizeKeyboard(true)
                .selective(true)
                .build();
    }
}
