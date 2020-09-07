package ru.home.mydb_bot.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.mydb_bot.MyFbiTelegramBot;

@RestController
public class WebHookController {
    private final MyFbiTelegramBot myFbiTelegramBot;

    public WebHookController(MyFbiTelegramBot myFbiTelegramBot){
        this.myFbiTelegramBot = myFbiTelegramBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public SendMessage onUpdateReceived(@RequestBody Update update){
        return myFbiTelegramBot.onWebhookUpdateReceived(update);
    }
}
