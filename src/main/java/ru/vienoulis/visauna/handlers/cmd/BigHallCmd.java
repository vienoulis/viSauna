package ru.vienoulis.visauna.handlers.cmd;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.service.PriceCalculationService;
import ru.vienoulis.visauna.service.Repository;

import static ru.vienoulis.visauna.model.CallbackQueryHandlers.BIG_HALL;

@Controller
public class BigHallCmd implements IBotCommand {
    private final SendMessage messageToSend = new SendMessage();
    private final PriceCalculationService calculationService;
    private final Repository repository;

    @Autowired
    public BigHallCmd(PriceCalculationService calculationService, Repository repository) {
        this.calculationService = calculationService;
        this.repository = repository;
    }

    @Override
    public String getCommandIdentifier() {
        return BIG_HALL.name();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        messageToSend.setChatId(message.getChatId().toString());
        var bigHall = repository.getHall(BIG_HALL.name());
        messageToSend.setText(calculationService.getPriceList(bigHall));
        absSender.execute(messageToSend);
    }
}
