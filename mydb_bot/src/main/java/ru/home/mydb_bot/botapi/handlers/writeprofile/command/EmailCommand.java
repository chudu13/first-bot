package ru.home.mydb_bot.botapi.handlers.writeprofile.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.handlers.UpdateValidator;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class EmailCommand implements Command, UpdateValidator {

    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;

    public EmailCommand(ReplyMessagesService replyMessagesService, UserDataCache userDataCache) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage execute(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser;

        if (!updateValidator(usersAnswer)) {
            replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askEmailRepeat", Emojis.WARNING);
        } else {
            profileData.setEmail(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CITY);
            replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askCity",Emojis.CITY);
        }

        return replyToUser;
    }

    @Override
    public boolean updateValidator(String answer) {
        return answer.trim().matches("[a-z0-9.]{2,}@(mail|gmail){1}\\.(com|ru)");
    }
}