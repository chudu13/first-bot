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
public class AgeCommand implements Command, UpdateValidator {
    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;

    public AgeCommand(ReplyMessagesService replyMessagesService, UserDataCache userDataCache) {
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
        SendMessage replyToUser;

        if (count != 0){
            if (!updateValidator(usersAnswer)) {
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askAgeRepeat", Emojis.WARNING);
            } else {
                try{
                    profileData.setAge(Integer.parseInt(usersAnswer));
                    userDataCache.setUsersCurrentBotState(userId, BotState.ASK_EMAIL);
                    replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askEmail", Emojis.EMAIL);
                }catch (NumberFormatException ex){
                    replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askError", Emojis.WARNING);
                }
            }
        }else {
            replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askAge", Emojis.INPUT);
        }
        userDataCache.setUsersCurrentCountState(userId,++count);

        return replyToUser;
    }

    @Override
    public boolean updateValidator(String answer) {
        return answer.trim().matches("[^.]\\d+");
    }
}