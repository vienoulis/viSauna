package ru.vienoulis.visauna.handlers.callback;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.CalculateHallCQD;
import ru.vienoulis.visauna.service.KeyBoardService;
import ru.vienoulis.visauna.service.Repository;

import static ru.vienoulis.visauna.model.callback.Action.calculateHall;

@Slf4j
@Controller
public class CalculateHallHandler extends CallbackQueryHandler<CalculateHallCQD> {
    private final SendMessage messageToSend = new SendMessage();
    private final KeyBoardService keyBoardService;
    private final Repository repository;

    @Autowired
    public CalculateHallHandler(KeyBoardService keyBoardService, Repository repository) {
        this.keyBoardService = keyBoardService;
        this.repository = repository;
    }

    @Override
    @SneakyThrows
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");
        messageToSend.setChatId(callback.getMessage().getChatId());
        var hall = repository.getHall(data.getHallType().name());
        messageToSend.setText(hall.getPriceListStringValue());
        messageToSend.setReplyMarkup(keyBoardService.getChooseVisitors(hall));
        absSender.execute(messageToSend);
        log.info("execute.exit;");
    }

    @Override
    public Action getIdentifier() {
        return calculateHall;
    }
}
