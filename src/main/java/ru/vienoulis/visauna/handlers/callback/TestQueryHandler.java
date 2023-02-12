package ru.vienoulis.visauna.handlers.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.vienoulis.visauna.model.CallbackQueryTypes;
import ru.vienoulis.visauna.model.callback.TestCQD;

@Slf4j
@Component
public class TestQueryHandler extends CallbackQueryHandler<TestCQD> {
    @Override
    protected String getIdentifier() {
        return "test";
    }

    @Override
    protected TestCQD initData(String... args) {
        return new TestCQD(Integer.parseInt(args[1]), args[2], CallbackQueryTypes.valueOf(args[3]));
    }

    @Override
    protected void execute(AbsSender absSender, CallbackQuery callback) {
        log.info("execute.enter;");
        log.info("execute.exit;");
    }
}
