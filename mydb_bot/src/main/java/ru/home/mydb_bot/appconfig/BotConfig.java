package ru.home.mydb_bot.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.home.mydb_bot.MyFbiTelegramBot;
import ru.home.mydb_bot.botapi.TelegramFacade;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    @Bean
    public MyFbiTelegramBot myTelegramBot(TelegramFacade telegramFacade){

        MyFbiTelegramBot myFbiTelegramBot = new MyFbiTelegramBot(telegramFacade);
        myFbiTelegramBot.setBotUserName(botUserName);
        myFbiTelegramBot.setBotToken(botToken);
        myFbiTelegramBot.setWebHookPath(webHookPath);

        return myFbiTelegramBot;
    }

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
