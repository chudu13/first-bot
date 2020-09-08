package ru.home.mydb_bot.botapi.handlers.getprofile;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.InputMessageHandler;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.botapi.handlers.QueryState;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.repository.UserProfileMongoRepository;
import ru.home.mydb_bot.service.KeyboardService;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.service.UsersProfileDataService;
import ru.home.mydb_bot.utils.Emojis;

import java.util.List;

@Component
public class GetProfileHandler implements InputMessageHandler {

    private ReplyMessagesService replyMessagesService;
    private KeyboardService keyboardService;
    private UserDataCache userDataCache;
    private UserProfileMongoRepository mongoRepository;
    private UsersProfileDataService usersProfileDataService;

    public GetProfileHandler(ReplyMessagesService replyMessagesService, KeyboardService keyboardService,
                             UserDataCache userDataCache, UserProfileMongoRepository mongoRepository,
                             UsersProfileDataService usersProfileDataService) {
        this.replyMessagesService = replyMessagesService;
        this.keyboardService = keyboardService;
        this.usersProfileDataService = usersProfileDataService;
        this.userDataCache = userDataCache;
        this.mongoRepository = mongoRepository;
    }

    @Override
    public SendMessage handle(Message message) {

        int userId = message.getFrom().getId();
        long chatId = message.getChatId();
        String usersAnswer = message.getText();
        BotState botState = userDataCache.getUsersCurrentBotState(userId);
        SendMessage replyMessage;

        if (botState.equals(BotState.GET_PROFILE_DATA)){
            replyMessage = replyMessagesService.getReplyMessage(chatId, "reply.selectCriterion", Emojis.SEARCH);
            replyMessage.setReplyMarkup(keyboardService.getMyKeyboardMarkup(KeyboardState.USE_SEARCH,userId));
            return replyMessage;
        } else {
            return processUsersInput(userId,botState,chatId,usersAnswer);
        }
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_PROFILE_DATA;
    }

    private SendMessage processUsersInput(int userId, BotState botState, long chatId, String usersAnswer){
        SendMessage replyMessage = null;

        if (userDataCache.getUsersCurrentQueryState(userId).equals(QueryState.QUERY_GET_NAME_SURNAME)){
            replyMessage = getSendMessageNameAndSurname(chatId,userId,botState,usersAnswer);
        }
        if (userDataCache.getUsersCurrentQueryState(userId).equals(QueryState.QUERY_GET_NAME)){
            replyMessage = getSendMessageName(chatId,userId,botState,usersAnswer);
        }
        if (userDataCache.getUsersCurrentQueryState(userId).equals(QueryState.QUERY_GET_SURNAME)){
            replyMessage = getSendMessageSurname(chatId,userId,botState,usersAnswer);
        }
        if (userDataCache.getUsersCurrentQueryState(userId).equals(QueryState.QUERY_GET_CITY)){
            replyMessage = getSendMessageCity(chatId,userId,botState,usersAnswer);
        }
        if (userDataCache.getUsersCurrentQueryState(userId).equals(QueryState.QUERY_GET_AGE)){
            replyMessage = getSendMessageAge(chatId,userId,botState,usersAnswer);
        }
        return replyMessage;
    }

    private SendMessage getSendMessageName(long chatId, int userId,
                                           BotState botState, String usersAnswer) {
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser = null;

        switch(botState){
            case GET_DATA_READY:
                profileData.setName(usersAnswer);
                replyToUser =  new SendMessage(chatId,
                        String.format("%s%n --------------------------------------------------------------- %n%s",
                                "Данные по запросу:", toGetListData(mongoRepository.findByName(profileData.getName()))));
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        }
        return replyToUser;
    }

    private SendMessage getSendMessageAge(long chatId, int userId,
                                           BotState botState, String usersAnswer) {
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser = null;

        switch(botState){
            case GET_DATA_READY:
                profileData.setAge(Integer.parseInt(usersAnswer));
                replyToUser =  new SendMessage(chatId,
                        String.format("%s%n --------------------------------------------------------------- %n%s",
                                "Данные по запросу:", toGetListData(mongoRepository.findByAge(profileData.getAge()))));
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        }
        return replyToUser;
    }

    private SendMessage getSendMessageCity(long chatId, int userId,
                                           BotState botState, String usersAnswer) {
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser = null;

        switch(botState){
            case GET_DATA_READY:
                profileData.setCity(usersAnswer);
                replyToUser =  new SendMessage(chatId,
                        String.format("%s%n --------------------------------------------------------------- %n%s",
                                "Данные по запросу:",toGetListData(mongoRepository.findByCity(profileData.getCity()))));
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        }
        return replyToUser;
    }

    private SendMessage getSendMessageSurname(long chatId, int userId,
                                           BotState botState, String usersAnswer) {
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser = null;

        switch(botState){
            case GET_DATA_READY:
                profileData.setSurname(usersAnswer);
                replyToUser =  new SendMessage(chatId,
                        String.format("%s%n --------------------------------------------------------------- %n%s",
                                "Данные по запросу:",toGetListData(mongoRepository.findBySurname(profileData.getSurname()))));
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        }
        return replyToUser;
    }

    private SendMessage getSendMessageNameAndSurname(long chatId,int userId,
                                                     BotState botState, String usersAnswer) {
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser = null;

        switch(botState){
            case GET_BY_SURNAME:
                profileData.setName(usersAnswer);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askSurname",Emojis.INPUT);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_DATA_READY);
                break;
            case GET_DATA_READY:
                profileData.setSurname(usersAnswer);
                replyToUser =  new SendMessage(chatId,
                        String.format("%s%n --------------------------------------------------------------- %n%s",
                                "Данные по запросу:",toGetListData(usersProfileDataService
                                        .getUserProfileData(profileData.getName(),profileData.getSurname()))));
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_MAIN_MENU);
        }
        return replyToUser;
    }


    private StringBuilder toGetListData(List<UserProfileData> profile){
        StringBuilder builder = new StringBuilder();
        profile.forEach(element -> builder.append(element)
                .append(String.format("---------------------------------------------------------------%n")));

        if (builder.length()==0){
            builder.append("Такого профиля в базе данных не существует");
        }
        return builder;
    }
}