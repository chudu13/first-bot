package ru.home.mydb_bot.botapi;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.mydb_bot.MyFbiTelegramBot;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.model.UserProfileData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Slf4j
@Component
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private CallBackQueryHandler callBackQueryHandler;
    private MyFbiTelegramBot myFbiTelegramBot;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache,
                          CallBackQueryHandler callBackQueryHandler,@Lazy MyFbiTelegramBot myFbiTelegramBot){
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.callBackQueryHandler = callBackQueryHandler;
        this.myFbiTelegramBot = myFbiTelegramBot;
    }

    public SendMessage handleUpdate(Update update){
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()){
            return callBackQueryHandler.handleUpdate(update);
        }

        Message message = update.getMessage();

        if (message != null && message.hasText()){
            log.info("New message from User:{}, chatId:{}, with text:{}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message){
        String inputMessage = message.getText();
        long chatId = message.getChatId();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        switch (inputMessage){
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Получить данные":
                botState = BotState.GET_PROFILE_DATA;
                break;
            case "Ввести данные":
                botState = BotState.WRITE_PROFILE_DATA;
                break;
            case "Созданная анкета":
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Скачать анкету":
                if (userDataCache.getUserProfileData(userId).getHasChild() != null){
                    myFbiTelegramBot.sendDocument(chatId,"Созданная анкета",getUsersProfile(userId));
                }
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case "Помощь":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);
        replyMessage = botStateContext.processInputMessage(botState, message);
        return replyMessage;
    }

    @SneakyThrows
    public File getUsersProfile(int userId) {
        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        File profileFile = ResourceUtils.getFile("classpath:static/docs/text_load.txt");

        try (FileWriter fw = new FileWriter(profileFile.getAbsoluteFile());
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(userProfileData.toString());
        }

        return profileFile;
    }
}