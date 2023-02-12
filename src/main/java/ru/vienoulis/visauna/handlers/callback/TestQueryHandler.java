package ru.vienoulis.visauna.handlers.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class TestQueryHandler extends CallbackQueryHandler {
    @Override
    public String getIdentifier() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Test description";
    }

    @Override
    public void processMessage(AbsSender absSender, CallbackQuery callback) {
        log.info("processMessage.enter;");
        log.info("processMessage.exit;");
    }
}
