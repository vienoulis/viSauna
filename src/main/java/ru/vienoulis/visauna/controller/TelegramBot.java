package ru.vienoulis.visauna.controller;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vienoulis.visauna.handlers.cmd.StartCmd;

import java.util.Objects;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;
    private final String defaultMessage;

    public TelegramBot(
            TelegramBotsApi telegramBotsApi,
            @Value("${app.defaultMessage}") String defaultMessage,
            @Value("${telegram-bot.name}") String botUsername,
            @Value("${telegram-bot.token}") String botToken,
            StartCmd startCmd) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.defaultMessage = defaultMessage;
        telegramBotsApi.registerBot(this);
        register(startCmd);
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        log.info("onUpdateReceived.enter; usrMessage: {}", update.getMessage());
        var message = update.getMessage();

        if (Objects.isNull(message))
            message = update.getCallbackQuery().getMessage();

        response.setChatId(message.getChatId().toString());
        defaultMsg(response, defaultMessage);
        log.info("onUpdateReceived.exit;");
    }

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     * @param msg      - сообщение
     */
    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        response.setText(msg);
        execute(response);
    }
}