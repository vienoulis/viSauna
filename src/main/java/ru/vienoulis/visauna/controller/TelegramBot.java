package ru.vienoulis.visauna.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vienoulis.visauna.handlers.callback.CallbackQueryHandler;
import ru.vienoulis.visauna.model.ShortCallbackData;
import ru.vienoulis.visauna.service.KeyBoardService;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static ru.vienoulis.visauna.service.TextService.NO_COMMAND_DEFAULT_MST;

@Slf4j
@Getter
@Component
public class TelegramBot extends TelegramLongPollingCommandBot {
    private final SendMessage response = new SendMessage();

    private final String botUsername;
    private final String botToken;
    private final String defaultMessage;
    private final KeyBoardService kbService;
    private final Set<CallbackQueryHandler<?>> queryHandlers;
    private final Gson gson;

    public TelegramBot(
            TelegramBotsApi telegramBotsApi,
            @Value("${app.defaultMessage}") String defaultMessage,
            @Value("${telegram-bot.name}") String botUsername,
            @Value("${telegram-bot.token}") String botToken,
            IBotCommand[] handlersCommand,
            Set<CallbackQueryHandler<?>> queryHandlers,
            KeyBoardService kbService, Gson gson) throws TelegramApiException {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.defaultMessage = defaultMessage;
        this.kbService = kbService;
        this.queryHandlers = queryHandlers;
        this.gson = gson;
        telegramBotsApi.registerBot(this);
        registerAll(handlersCommand);
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        log.info("processNonCommandUpdate.enter; usrMessage: {}", update.getMessage());

        if (update.hasMessage()) {
            processLikeMessage(update);
            log.info("processNonCommandUpdate.exit; processLikeMessage");
            return;
        }

        if (update.hasCallbackQuery()) {
            log.info("processNonCommandUpdate.hasCallbackQuery;");
            if (isCanHandleCallback(update.getCallbackQuery())) {
                handleCallbackQuery(update);
                log.info("processNonCommandUpdate.exit; isCanHandleCallback");
                return;
            }

            log.info("processNonCommandUpdate; sendDefaultMsg");
            sendDefaultMsg(update.getCallbackQuery().getMessage());
        }
        log.info("processNonCommandUpdate.exit;");
    }

    private void processLikeMessage(Update update) throws TelegramApiException {
        log.info("processLikeMessage.enter;");
        sendDefaultMsg(update.getMessage());
        log.info("processLikeMessage.exit;");
    }

    private boolean isCanHandleCallback(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        try {
            var shortData = gson.fromJson(data, ShortCallbackData.class);
            return queryHandlers.stream()
                    .map(CallbackQueryHandler::getIdentifier)
                    .anyMatch(i -> Objects.equals(i, shortData.getAction()));
        } catch (JsonSyntaxException e) {
            log.warn("isCallbackQueryCmd.warn; cause by {}", e.getMessage());
            return false;
        }
    }

    private void handleCallbackQuery(Update update) {
        log.info("handleCallbackQuery.enter;");
        var callback = update.getCallbackQuery();
        queryHandlers.stream()
                .filter(c -> c.validate(callback))
                .forEach(c -> c.processMessage(this, callback));
        log.info("handleCallbackQuery.exit;");
    }

    private void sendDefaultMsg(Message callbackQuery) throws TelegramApiException {
        log.info("sendDefaultMsg.enter;");
        defaultMsg(SendMessage.builder()
                .chatId(callbackQuery.getChatId())
                .parseMode(ParseMode.HTML)
                .text(NO_COMMAND_DEFAULT_MST)
                .build());
        log.info("sendDefaultMsg.exit;");
    }

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     */
    private void defaultMsg(SendMessage response) throws TelegramApiException {
        log.info("defaultMsg.enter;");
        response.setReplyMarkup(kbService.getStartKB());
        execute(response);
        log.info("defaultMsg.exit;");
    }
}