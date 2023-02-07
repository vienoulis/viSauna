package ru.vienoulis.visauna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.visauna.service.KeyBoardService;

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
}
