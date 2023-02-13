package ru.vienoulis.visauna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.vienoulis.visauna.dto.Hall;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.TestCQD;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.vienoulis.visauna.model.CallbackQueryTypes.BIG_HALL;
import static ru.vienoulis.visauna.model.CallbackQueryTypes.SMALL_HALL;

@Component
public class KeyBoardService {
    private final Repository repository;
    private final CallbackCompressorService compressorService;

    @Autowired
    public KeyBoardService(Repository repository, CallbackCompressorService compressorService) {
        this.repository = repository;
        this.compressorService = compressorService;
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
        var hall = repository.getHall(BIG_HALL.name());
        var kbRow = hall.getPriceList().stream()
                .map(p -> InlineKeyboardButton.builder()
                        .text(p.toString())
                        .callbackData(String.format("#test 1 two %s", BIG_HALL))
                        .build())
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(kbRow)
                .build();
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

    public InlineKeyboardMarkup getChooseVisitors(Hall hall) {
        var kbRow = hall.getPriceList().stream()
                .map(p -> {
                    var shortData = compressorService.compressData(Action.test, TestCQD.builder().number(666).someString(p.toString()).build());
                    return InlineKeyboardButton.builder()
                            .text(p.toString())
                            .callbackData(shortData)
                            .build();
                })
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder()
                .keyboardRow(kbRow)
                .build();
    }
}
