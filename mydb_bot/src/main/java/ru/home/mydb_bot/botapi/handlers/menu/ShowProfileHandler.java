package ru.home.mydb_bot.botapi.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.InputMessageHandler;
import ru.home.mydb_bot.model.UserProfileData;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class ShowProfileHandler implements InputMessageHandler {

    private final UserDataCache cache;
    private final ReplyMessagesService replyMessagesService;

    public ShowProfileHandler(UserDataCache cache, ReplyMessagesService replyMessagesService) {
        this.cache = cache;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        SendMessage userReply;

        final long chatId = message.getChatId();
        final int userId = message.getFrom().getId();
        final UserProfileData profile = cache.getUserProfileData(userId);
        cache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);

        if (profile.getHasChild() != null){
            userReply = new SendMessage(message.getChatId(), String.format("%s%n --------------------------------------------------------------- %nИмя: %s%n"
                            + "Фамилия: %s%nПол: %s%nПочта: %s%nВозраст: %d%nГород: %s%nНомер телефона: %d%nСемейное положение: %s%nНаличие детей: %s",
                    "Данные по созданному профилю:", profile.getName(), profile.getSurname(),profile.getGender(),profile.getEmail(),profile.getAge(),
                    profile.getCity(),profile.getCellphone(),profile.getMarried(),profile.getHasChild()));
            return userReply;
        } else {
            userReply = replyMessagesService.getReplyMessage(chatId,"reply.DoNotInput", Emojis.INPUT);
            return userReply;
        }
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}