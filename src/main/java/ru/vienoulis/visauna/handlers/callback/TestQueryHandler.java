package ru.vienoulis.visauna.handlers.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.callback.Action;
import ru.vienoulis.visauna.model.callback.TestCQD;

@Slf4j
@Component
public class TestQueryHandler extends CallbackQueryHandler<TestCQD> {
    @Override
    public Action getIdentifier() {
        return Action.test;
    }

    @Override
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");
        log.info("execute.exit;");
    }
}
