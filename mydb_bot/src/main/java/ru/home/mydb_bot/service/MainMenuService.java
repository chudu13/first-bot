package ru.home.mydb_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Service
public class MainMenuService {

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage){
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard(){
        final ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

        replyKeyboard.setSelective(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow getData = new KeyboardRow();
        KeyboardRow writeData = new KeyboardRow();
        KeyboardRow loadData = new KeyboardRow();
        KeyboardRow help = new KeyboardRow();

        getData.add(new KeyboardButton("Получить данные"));
        writeData.add(new KeyboardButton("Ввести данные"));
        loadData.add(new KeyboardButton("Созданная анкета"));
        loadData.add(new KeyboardButton("Скачать анкету"));
        help.add(new KeyboardButton("Помощь"));

        keyboard.add(getData);
        keyboard.add(writeData);
        keyboard.add(loadData);
        keyboard.add(help);

        replyKeyboard.setKeyboard(keyboard);
        return replyKeyboard;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup){

        final SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(textMessage);

        if (replyKeyboardMarkup != null){
            message.setReplyMarkup(replyKeyboardMarkup);
        }
        return message;
    }
}