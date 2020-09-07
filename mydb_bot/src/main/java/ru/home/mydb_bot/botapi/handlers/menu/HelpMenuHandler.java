package ru.home.mydb_bot.botapi.handlers.menu;

import com.vdurmont.emoji.Emoji;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.InputMessageHandler;
import ru.home.mydb_bot.service.MainMenuService;
import ru.home.mydb_bot.service.ReplyMessagesService;
import ru.home.mydb_bot.utils.Emojis;

@Component
public class HelpMenuHandler implements InputMessageHandler {

    private MainMenuService menuService;
    private ReplyMessagesService replyMessagesService;

    public HelpMenuHandler(MainMenuService menuService, ReplyMessagesService replyMessagesService){
        this.menuService = menuService;
        this.replyMessagesService = replyMessagesService;
    }
    @Override
    public SendMessage handle(Message message) {
        return menuService.getMainMenuMessage(message.getChatId(),
                replyMessagesService.getReplyText("reply.showHelpMenu", Emojis.HELP,Emojis.HELP));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_HELP_MENU;
    }
}
