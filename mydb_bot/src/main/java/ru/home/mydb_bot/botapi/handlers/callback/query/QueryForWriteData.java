package ru.home.mydb_bot.botapi.handlers.callback.query;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.service.KeyboardService;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.service.UsersProfileDataService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class QueryForWriteData {

    private ReplyMessagesService replyMessagesService;
    private UserDataCache userDataCache;
    private KeyboardService keyboardService;
    private UsersProfileDataService usersProfileDataService;

    public QueryForWriteData(ReplyMessagesService replyMessagesService, UserDataCache userDataCache,
                             KeyboardService keyboardService, UsersProfileDataService usersProfileDataService) {
        this.replyMessagesService = replyMessagesService;
        this.userDataCache = userDataCache;
        this.keyboardService = keyboardService;
        this.usersProfileDataService = usersProfileDataService;
    }

    public SendMessage processGender(long chatId, int userId,
                                      CallbackQuery buttonQuery, SendMessage callBackAnswer){

        if (buttonQuery.getData().equals("buttonMan")) {
            callBackAnswer = keepGender(chatId, userId, "М");
        } else if (buttonQuery.getData().equals("buttonWoman")) {
            callBackAnswer = keepGender(chatId, userId, "Ж");
        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }
        return callBackAnswer;
    }

    public SendMessage processChildren(long chatId, int userId,
                                        CallbackQuery buttonQuery, SendMessage callBackAnswer){

        if (buttonQuery.getData().equals("buttonYes")){
            callBackAnswer = keepChild(chatId, userId, "Да");
        } else if (buttonQuery.getData().equals("buttonNo")) {
            callBackAnswer = keepChild(chatId, userId, "Нет");
        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }
        return callBackAnswer;
    }

    public SendMessage processMarried(long chatId, int userId,
                                       CallbackQuery buttonQuery, SendMessage callBackAnswer){

        if (buttonQuery.getData().equals("buttonManMarried")) {
            callBackAnswer = keepMarried(chatId,userId,"Женат");
        } else if (buttonQuery.getData().equals("buttonManNotMarried")) {
            callBackAnswer = keepMarried(chatId,userId,"Не женат");
        } else if (buttonQuery.getData().equals("buttonManDivorced")) {
            callBackAnswer = keepMarried(chatId,userId,"Разведен");
        } else if (buttonQuery.getData().equals("buttonWomanMarried")) {
            callBackAnswer = keepMarried(chatId,userId,"Замужем");
        } else if (buttonQuery.getData().equals("buttonWomanNotMarried")) {
            callBackAnswer = keepMarried(chatId,userId,"Не замужем");
        } else if (buttonQuery.getData().equals("buttonWomanDivorced")) {
            callBackAnswer = keepMarried(chatId,userId,"Разведена");
        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }
        return callBackAnswer;
    }

    private SendMessage keepGender(long chatId, int userId, String button) {
        SendMessage callBackAnswer;
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        userProfileData.setGender(button);
        userDataCache.saveUserProfileData(userId, userProfileData);
        userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        callBackAnswer = replyMessagesService.getReplyMessage(chatId, "reply.askAge", Emojis.INPUT);
        return callBackAnswer;
    }

    private SendMessage keepChild(long chatId, int userId, String button) {
        SendMessage callBackAnswer;
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        userProfileData.setHasChild(button);
        userProfileData.setChatId(chatId);
        userDataCache.saveUserProfileData(userId,userProfileData);
        userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        usersProfileDataService.saveUserSubscription(userProfileData);
        callBackAnswer = replyMessagesService.getReplyMessage(chatId, "reply.profileFilled",Emojis.DART);
        return callBackAnswer;
    }

    private SendMessage keepMarried(long chatId, int userId, String button){
        SendMessage callBackAnswer;
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        userProfileData.setMarried(button);
        userDataCache.saveUserProfileData(userId, userProfileData);
        callBackAnswer = replyMessagesService.getReplyMessage(chatId, "reply.askHasChild",Emojis.CHILDREN);
        callBackAnswer.setReplyMarkup(keyboardService.getMyKeyboardMarkup(KeyboardState.USE_CHILDREN,userId));
        return callBackAnswer;
    }
}