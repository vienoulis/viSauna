package ru.vienoulis.visauna.handlers.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.CallbackQueryData;

public abstract class CallbackQueryHandler<T extends CallbackQueryData> {
    public static final String CQ_PREFIX = "#";
    protected T data;

    protected abstract String getIdentifier();

    protected abstract void execute(AbsSender absSender, CallbackQuery callback);

    protected abstract T initData(String... args);

    public void processMessage(AbsSender absSender, CallbackQuery callback) {
        data = initData(callback.getData().split(" "));
        execute(absSender, callback);
    }

    public boolean validate(CallbackQuery callbackQuery) {
        var data = callbackQuery.getData();
        return data.startsWith(String.format("%s%s", CQ_PREFIX, getIdentifier()));
    }
}
