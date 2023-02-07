package ru.vienoulis.visauna.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;

@Service
public class KeyBoardService {

    @Autowired
    public KeyBoardService() {
    }

    public InlineKeyboardMarkup getChooseHllKb() {
        var rowOne = new ArrayList<InlineKeyboardButton>();
        rowOne.add(InlineKeyboardButton.builder()
                .text("Большой")
                .callbackData("/big")
                .build());
        rowOne.add(InlineKeyboardButton.builder()
                .text("Малый")
                .callbackData("/big")
                .build());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(rowOne)
                .build();
    }
}
