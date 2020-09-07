package ru.home.mydb_bot.botapi.handlers.writeprofile.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.botapi.handlers.UpdateValidator;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.service.KeyboardService;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class SurnameCommand implements Command, UpdateValidator {
    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;
    private KeyboardService keyboardService;

    public SurnameCommand(ReplyMessagesService replyMessagesService, UserDataCache userDataCache) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage execute(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();

        keyboardService = new KeyboardService(userDataCache);
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser;

            if (!updateValidator(usersAnswer)) {
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askSurnameRepeat", Emojis.WARNING);
            } else {
                profileData.setSurname(usersAnswer);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_GENDER);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askGender", Emojis.GENDER);
                replyToUser.setReplyMarkup(keyboardService.getMyKeyboardMarkup(KeyboardState.USE_GENDER,userId));
            }

        return replyToUser;
    }

    @Override
    public boolean updateValidator(String answer){
        return answer.trim().matches("[а-яА-Я]{2,}");
    }
}