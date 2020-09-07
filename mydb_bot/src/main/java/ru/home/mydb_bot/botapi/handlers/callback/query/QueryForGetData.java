package ru.home.mydb_bot.botapi.handlers.callback.query;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.handlers.QueryState;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class QueryForGetData {

    private UserDataCache userDataCache;
    private ReplyMessagesService replyMessagesService;

    public QueryForGetData(UserDataCache userDataCache,ReplyMessagesService replyMessagesService) {
        this.userDataCache = userDataCache;
        this.replyMessagesService = replyMessagesService;
    }

    public SendMessage processSearch(long chatId,int userId,CallbackQuery buttonQuery){
        String query = buttonQuery.getData();
        SendMessage replyToUser = null;

        switch (query){
            case "bNameAndSurname":
                userDataCache.setUsersCurrentQueryState(userId, QueryState.QUERY_GET_NAME_SURNAME);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_BY_SURNAME);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askName", Emojis.INPUT);
                break;
            case "bName":
                userDataCache.setUsersCurrentQueryState(userId,QueryState.QUERY_GET_NAME);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_DATA_READY);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askName", Emojis.INPUT);
                break;
            case "bSurname":
                userDataCache.setUsersCurrentQueryState(userId,QueryState.QUERY_GET_SURNAME);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_DATA_READY);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askSurname", Emojis.INPUT);
                break;
            case "bCity":
                userDataCache.setUsersCurrentQueryState(userId,QueryState.QUERY_GET_CITY);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_DATA_READY);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askCity", Emojis.CITY);
                break;
            case "bAge":
                userDataCache.setUsersCurrentQueryState(userId,QueryState.QUERY_GET_AGE);
                userDataCache.setUsersCurrentBotState(userId,BotState.GET_DATA_READY);
                replyToUser = replyMessagesService.getReplyMessage(chatId, "reply.askAge",Emojis.INPUT);
                break;
            default:
                userDataCache.setUsersCurrentBotState(userId,BotState.SHOW_HELP_MENU);
        }
        return replyToUser;
    }
}