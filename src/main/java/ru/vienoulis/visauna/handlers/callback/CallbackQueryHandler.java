package ru.vienoulis.visauna.handlers.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.CallbackQueryData;

public abstract class CallbackQueryHandler<T extends CallbackQueryData> {
    public static final String CQ_PREFIX = "#";
    protected T data;

    public abstract Action getIdentifier();

    protected abstract void execute(AbsSender absSender, CallbackQuery callback);

    public void processMessage(AbsSender absSender, CallbackQuery callback) {
        execute(absSender, callback);
    }

    public boolean validate(CallbackQuery callbackQuery) {
        return true;
    }
}
