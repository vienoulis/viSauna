package ru.vienoulis.visauna.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vienoulis.visauna.handlers.callback.CallbackQueryHandler;
import ru.vienoulis.visauna.model.ShortCallbackData;
import ru.vienoulis.visauna.service.KeyBoardService;

import java.util.Objects;
import java.util.Set;

import static ru.vienoulis.visauna.handlers.callback.CallbackQueryHandler.CQ_PREFIX;
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
        if (update.hasCallbackQuery()) {
            log.info("hasCallbackQuery");
            if (isCallbackQueryCmd(update.getCallbackQuery())) {
                var callback = update.getCallbackQuery();
                processCallbackCmd(callback);
            } else {
                var callbackQuery = update.getCallbackQuery();
                var availableCmdList = getRegisteredCommands().stream()
                        .filter(c -> Objects.equals(c.getCommandIdentifier(), callbackQuery.getData()))
                        .toList();
                if (availableCmdList.isEmpty()) {
                    defaultMsg(new SendMessage(callbackQuery.getMessage().getChatId().toString(), NO_COMMAND_DEFAULT_MST));
                } else {
                    availableCmdList.forEach(c -> c.processMessage(this, callbackQuery.getMessage(), null));
                }
            }
        }

        if (update.hasMessage()) {
            defaultMsg(new SendMessage(update.getMessage().getChatId().toString(), NO_COMMAND_DEFAULT_MST));
        }
        log.info("processNonCommandUpdate.exit;");
    }

    private void processCallbackCmd(CallbackQuery callback) {
        queryHandlers.stream()
                .filter(c -> c.validate(callback))
                .forEach(c -> c.processMessage(this, callback));
    }

    private boolean isCallbackQueryCmd(CallbackQuery callbackQuery) {
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

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     */
    private void defaultMsg(SendMessage response) throws TelegramApiException {
        response.setReplyMarkup(kbService.getStartKB());
        execute(response);
    }
}