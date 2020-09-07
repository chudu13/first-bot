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
public class CellphoneCommand implements Command, UpdateValidator {
    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;
    private KeyboardService keyboardService;

    public CellphoneCommand(ReplyMessagesService replyMessagesService, UserDataCache userDataCache) {
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
            replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askCellphoneRepeat", Emojis.WARNING);
        } else {
            try {
                profileData.setCellphone(Long.parseLong(usersAnswer));
                userDataCache.setUsersCurrentBotState(userId, BotState.ASK_MARRIED);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askMarried", Emojis.MARRIED);
                replyToUser.setReplyMarkup(keyboardService.getMyKeyboardMarkup(KeyboardState.USE_MARRIED,userId));
                cleanCacheCount(userId);
            }catch (NumberFormatException ex){
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askError",Emojis.WARNING);
            }
        }
        return replyToUser;
    }

    @Override
    public boolean updateValidator(String answer) {
        return answer.trim().matches("\\+?\\d+");
    }

    private void cleanCacheCount(int userId){
        userDataCache.setUsersCurrentCountState(userId,0);
    }
}