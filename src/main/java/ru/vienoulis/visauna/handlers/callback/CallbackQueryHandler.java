package ru.vienoulis.visauna.handlers.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.CallbackQueryData;
import ru.vienoulis.visauna.service.CallbackCompressorService;

public abstract class CallbackQueryHandler<T extends CallbackQueryData> {
    @Autowired
    private CallbackCompressorService compressorService;
    protected T data;

    public abstract Action getIdentifier();

    protected abstract void execute(AbsSender absSender, CallbackQuery callback);

    public void processMessage(AbsSender absSender, CallbackQuery callback) {
        data = (T) compressorService.deCompressData(callback.getData());
        execute(absSender, callback);
    }

    public boolean validate(CallbackQuery callbackQuery) {
        var action = compressorService.getActionFrom(callbackQuery.getData());
        return action == getIdentifier();
    }
}
