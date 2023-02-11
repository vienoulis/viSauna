package ru.vienoulis.visauna.config;

import jakarta.inject.Singleton;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vienoulis.visauna.dto.Hall;
import ru.vienoulis.visauna.dto.PriceSlot;
import ru.vienoulis.visauna.handlers.cmd.BigHallCmd;
import ru.vienoulis.visauna.handlers.cmd.SmallHallCmd;
import ru.vienoulis.visauna.handlers.cmd.StartCmd;
import ru.vienoulis.visauna.service.Repository;

import java.util.HashSet;
import java.util.Set;

import static ru.vienoulis.visauna.model.CallbackQueryHandlers.BIG_HALL;
import static ru.vienoulis.visauna.model.CallbackQueryHandlers.SMALL_HALL;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:application.yaml")
public class AppConf {

    @Bean
    TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
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

    @Bean
    @Singleton
    Repository getRepository() {
        var repo = new Repository();
        var bigHall = new Hall(16, "Большой зал");
        bigHall.setPriceFor(new PriceSlot(1, 4, 140000));
        bigHall.setPriceFor(new PriceSlot(5, 8, 180000));
        bigHall.setPriceFor(new PriceSlot(9, 16, 220000));
        repo.saveHall(BIG_HALL.name(), bigHall);

        var smallHall = new Hall(10, "Малый зал");
        smallHall.setPriceFor(new PriceSlot(1, 2, 100000));
        smallHall.setPriceFor(new PriceSlot(3, 4, 120000));
        smallHall.setPriceFor(new PriceSlot(5, 10, 140000));

        repo.saveHall(SMALL_HALL.name(), smallHall);

        return repo;
    }
}
