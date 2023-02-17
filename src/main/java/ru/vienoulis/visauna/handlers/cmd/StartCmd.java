package ru.vienoulis.visauna.handlers.cmd;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.service.KeyBoardService;

@Component
public class StartCmd implements IBotCommand {
    private final KeyBoardService kbService;

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
        message.enableHtml(true);
        message.setText("""
            <b><strong>Здравствуйте!</strong></b>
        Я могу для Вас рассчитать стоимость
        посещения нашей сауны.
        <strong>Для начала выберите зал:</strong>""");
        message.setReplyMarkup(kbService.getChooseHllKb());

        absSender.execute(message);
    }
}
