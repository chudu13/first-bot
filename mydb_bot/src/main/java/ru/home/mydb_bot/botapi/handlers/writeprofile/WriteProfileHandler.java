package ru.home.mydb_bot.botapi.handlers.writeprofile;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.InputMessageHandler;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.service.ReplyMessagesService;

@Component
public class WriteProfileHandler implements InputMessageHandler {

    private UserDataCache userDataCache;
    private ReplyMessagesService replyMessagesService;

    public WriteProfileHandler(UserDataCache userDataCache,
                               ReplyMessagesService replyMessagesService){
        this.userDataCache = userDataCache;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.WRITE_PROFILE_DATA)){
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_NAME);
        }
        return apply(message,userDataCache.getUsersCurrentBotState(message.getFrom().getId()));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WRITE_PROFILE_DATA;
    }

    private SendMessage apply(Message message, BotState botState){
        int userId = message.getFrom().getId();
        UserProfileData profileData = userDataCache.getUserProfileData(userId);

        SendMessage sendMessage = botState.getCommand(replyMessagesService, userDataCache, message);
        userDataCache.saveUserProfileData(userId, profileData);
        return sendMessage;
    }
}