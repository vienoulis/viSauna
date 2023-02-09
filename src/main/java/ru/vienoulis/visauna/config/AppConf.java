package ru.vienoulis.visauna.config;

import jakarta.inject.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.visauna.handlers.cmd.BigHallCmd;
import ru.vienoulis.visauna.handlers.cmd.SmallHallCmd;
import ru.vienoulis.visauna.handlers.cmd.StartCmd;
import ru.vienoulis.visauna.service.KeyBoardService;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:application.yaml")
public class AppConf {

    @Bean
    TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    KeyBoardService getKeyBoardService() {
        return new KeyBoardService();
    }

    @Bean
    @Singleton
    IBotCommand[] getHandlersCommand(StartCmd startCmd, BigHallCmd bigHallCmd, SmallHallCmd smallHallCmd) {
        Set<IBotCommand> result = new HashSet<>();
        result.add(startCmd);
        result.add(bigHallCmd);
        result.add(smallHallCmd);
        return result.toArray(new IBotCommand[]{});
    }
}
