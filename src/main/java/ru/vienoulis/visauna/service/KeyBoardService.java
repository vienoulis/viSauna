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
import ru.vienoulis.visauna.dto.PriceSlot;
import ru.vienoulis.visauna.model.CallbackQueryTypes;
import ru.vienoulis.visauna.model.callback.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.vienoulis.visauna.model.CallbackQueryTypes.BIG_HALL;

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
        var row = getHallsBtnRow();
        var shortData = compressorService.compressData(Action.calendar, CalendarCQD.builder().build());
        var bookBtn = InlineKeyboardButton.builder()
                .text("Календарь")
                .callbackData(shortData)
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(row, List.of(bookBtn)))
                .build();
    }

    public ReplyKeyboard test() {
        var hall = repository.getHall(BIG_HALL.name());
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
                .keyboard(List.of(kbRow))
                .build();
    }

    public ReplyKeyboard getStartKB() {
        KeyboardButton startBtn = new KeyboardButton("/start");
        KeyboardButton testBtn = KeyboardButton.builder()
                .text("/bug")
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
                    var shortData = compressorService.compressData(Action.priceSlot,
                            PriceSlotCQD.builder().priceSlot(p).build());
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

    public InlineKeyboardMarkup inputHours(PriceSlot priceSlot) {
        var kbRow1 = getHoursBtnRow(priceSlot, PriceSlot.MIN_HOURS, PriceSlot.MAX_POW);
        var kbRow2 = getHoursBtnRow(priceSlot, PriceSlot.MAX_POW + 1, PriceSlot.MAX_HOURS);

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(kbRow1, kbRow2))
                .build();
    }

    private List<InlineKeyboardButton> getHoursBtnRow(PriceSlot priceSlot, int minHours, int maxPow) {
        return IntStream.rangeClosed(minHours, maxPow)
                .mapToObj(i -> {
                    var shortData = compressorService.compressData(Action.priceSlotWithHour,
                            PriseSlotAndHoursCQD.builder().priceSlot(priceSlot).hours(i).build());
                    return InlineKeyboardButton.builder()
                            .text(String.valueOf(i))
                            .callbackData(shortData)
                            .build();
                }).toList();
    }

    private List<InlineKeyboardButton> getHallsBtnRow() {
        return Arrays.stream(CallbackQueryTypes.values())
                .map(c -> {
                    var shortData = compressorService.compressData(Action.calculateHall,
                            CalculateHallCQD.builder().hallType(c).build());
                    return InlineKeyboardButton.builder()
                            .text(c.getHallBtnName())
                            .callbackData(shortData)
                            .build();
                }).toList();
    }
}
