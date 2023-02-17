package ru.vienoulis.visauna.handlers.cmd;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.service.KeyBoardService;

@Slf4j
@Component
public class TestCmd implements IBotCommand {
    private final KeyBoardService kbService;

    public TestCmd(KeyBoardService kbService) {
        this.kbService = kbService;
    }

    @Override
    public String getCommandIdentifier() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Тестовая команда";
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message msg, String[] arguments) {
        log.info("processMessage.enter;");
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId().toString());
        message.setReplyMarkup(kbService.test());
        message.setText("Клавиатура после команды test");

        absSender.execute(message);
        log.info("processMessage.exit;");
    }
}
