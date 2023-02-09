package ru.vienoulis.visauna.handlers.cmd;

import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static ru.vienoulis.visauna.model.CallbackQueryHandlers.BIG_HALL;
import static ru.vienoulis.visauna.service.TextService.BIG_HALL_PRICE;

@Controller
public class BigHallCmd implements IBotCommand {
    private final SendMessage messageToSend = new SendMessage();

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
        messageToSend.setText(BIG_HALL_PRICE);
        absSender.execute(messageToSend);
    }
}
