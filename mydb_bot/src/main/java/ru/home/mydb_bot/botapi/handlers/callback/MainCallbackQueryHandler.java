package ru.home.mydb_bot.botapi.handlers.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.mydb_bot.botapi.CallBackQueryHandler;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.botapi.handlers.callback.query.QueryForGetData;
import ru.home.mydb_bot.botapi.handlers.callback.query.QueryForWriteData;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.service.MainMenuService;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Slf4j
@Component
public class MainCallbackQueryHandler implements CallBackQueryHandler {

    private MainMenuService mainMenuService;
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;
    private QueryForWriteData writeData;
    private QueryForGetData getData;

    public MainCallbackQueryHandler(MainMenuService mainMenuService, UserDataCache userDataCache,
                                    QueryForWriteData writeData, QueryForGetData getData,
                                    ReplyMessagesService messagesService) {
        this.mainMenuService = mainMenuService;
        this.messagesService = messagesService;
        this.userDataCache = userDataCache;
        this.writeData = writeData;
        this.getData = getData;
    }

    @Override
    public SendMessage handleUpdate(Update update) {

        CallbackQuery callbackQuery = update.getCallbackQuery();
        Message message = update.getMessage();

            log.info("New callbackQuery from User: {}, userId: {}, with data: {}",
                    update.getCallbackQuery().getFrom().getUserName(), callbackQuery.getFrom().getId(),
                    update.getCallbackQuery().getData());

        return processCallbackQuery(callbackQuery, message);
    }

    private SendMessage processCallbackQuery(CallbackQuery buttonQuery, Message message) {

        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        SendMessage callBackAnswer = mainMenuService.getMainMenuMessage(chatId,
                messagesService.getReplyText("reply.showMainMenu", Emojis.MENU));

        KeyboardState state = userDataCache.getUsersCurrentKeyboardState(userId);

        switch (state){
            case USE_GENDER:  //From Gender choose buttons
                callBackAnswer = writeData.processGender(chatId,userId,buttonQuery,callBackAnswer);
                break;
            case USE_MARRIED:
                callBackAnswer = writeData.processMarried(chatId,userId,buttonQuery,callBackAnswer);
                break;
            case USE_CHILDREN:
                callBackAnswer = writeData.processChildren(chatId,userId,buttonQuery,callBackAnswer);
                break;
            case USE_SEARCH:
                callBackAnswer = getData.processSearch(chatId,userId,buttonQuery);
        }

        return callBackAnswer;
    }
}