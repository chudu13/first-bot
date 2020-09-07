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
public class NameCommand implements Command, UpdateValidator {
    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;

    public NameCommand(ReplyMessagesService replyMessagesService, UserDataCache userDataCache) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage execute(Message message) {
        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        int count = userDataCache.getUsersCurrentCountState(userId);
        SendMessage replyToUser = null;

        if (count != 0){
            if (!updateValidator(usersAnswer)) {
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askNameRepeat",Emojis.WARNING);
            } else {
                profileData.setName(usersAnswer);
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_SURNAME);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askSurname",Emojis.INPUT);
            }
        }else {
            replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askName", Emojis.INPUT);
        }
        userDataCache.setUsersCurrentCountState(userId,++count);

        return replyToUser;
    }

    @Override
    public boolean updateValidator(String answer){
        return answer.trim().matches("[а-яА-Я]{2,}");
    }
}