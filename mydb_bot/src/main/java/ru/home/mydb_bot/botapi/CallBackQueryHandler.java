package ru.home.mydb_bot.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallBackQueryHandler {
     SendMessage handleUpdate(Update update);
}