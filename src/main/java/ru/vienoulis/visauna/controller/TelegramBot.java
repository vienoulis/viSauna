package ru.vienoulis.visauna.controller;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;
    private final String defaultMessage;


    public TelegramBot(
            TelegramBotsApi telegramBotsApi,
            @Value("${app.defaultMessage}") String defaultMessage,
            @Value("${telegram-bot.name}") String botUsername,
            @Value("${telegram-bot.token}") String botToken) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.defaultMessage = defaultMessage;
        telegramBotsApi.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update request) {
        log.info("onUpdateReceived.enter; usrMessage: {}", request.getMessage().getText());
        response.setChatId(request.getMessage().getChatId().toString());
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