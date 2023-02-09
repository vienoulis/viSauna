package ru.vienoulis.visauna.handlers.cmd;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.service.KeyBoardService;

@Controller
public class StartCmd implements IBotCommand {
    private final KeyBoardService kbService;

    @Autowired
    public StartCmd(KeyBoardService kbService) {
        this.kbService = kbService;
    }

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Start discription";
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message msg, String[] arguments) {
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId().toString());
        message.setReplyMarkup(kbService.getChooseHllKb());
        message.setText("Клавиатура после команды старт");

        absSender.execute(message);
    }
}
