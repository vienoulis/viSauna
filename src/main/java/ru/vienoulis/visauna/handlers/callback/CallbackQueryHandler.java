package ru.vienoulis.visauna.handlers.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class CallbackQueryHandler {
    public static final String CQ_PREFIX = "#";

    public abstract String getIdentifier();

    public abstract String getDescription();

    public void processMessage(AbsSender absSender, CallbackQuery callback) {

    }

    public boolean validate(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        return data.startsWith(String.format("%s%s", CQ_PREFIX, getIdentifier()));
    }
}
