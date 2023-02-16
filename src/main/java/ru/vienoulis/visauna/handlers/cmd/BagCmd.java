package ru.vienoulis.visauna.handlers.cmd;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
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
public class BagCmd implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "bug";
    }

    @Override
    public String getDescription() {
        return "Оповещение об обнаружении бага";
    }

    @Override
    @SneakyThrows
    public void processMessage(AbsSender absSender, Message msg, String[] arguments) {
        log.info("processMessage.enter; msg: {}", msg);
        SendMessage message = new SendMessage();
        message.setChatId(msg.getChatId().toString());
        message.enableHtml(true);
        message.setText(String.format("""
                        %s
                        Сообщите этот код  <a href="tg://user?id=309427072">мне.</a>""",
                NanoIdUtils.randomNanoId()));
        absSender.execute(message);
        log.info("processMessage.exit;");
    }
}
