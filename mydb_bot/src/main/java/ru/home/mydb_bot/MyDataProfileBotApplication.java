package ru.home.mydb_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class MyDataProfileBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(MyDataProfileBotApplication.class, args);
    }
}
