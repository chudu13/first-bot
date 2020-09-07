package ru.home.mydb_bot.botapi;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers){
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) throws NullPointerException{
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isFillingProfileState(currentState).equals("Show_Menu")){
            return messageHandlers.get(BotState.SHOW_MAIN_MENU);
        }
        if (isFillingProfileState(currentState).equals("Show_Profile")){
            return messageHandlers.get(BotState.SHOW_USER_PROFILE);
        }
        if (isFillingProfileState(currentState).equals("Show_Help")){
            return messageHandlers.get(BotState.SHOW_HELP_MENU);
        }
        if (isFillingProfileState(currentState).equals("Write_Data")){
            return messageHandlers.get(BotState.WRITE_PROFILE_DATA);
        }
        if (isFillingProfileState(currentState).equals("Get_Data")){
            return messageHandlers.get(BotState.GET_PROFILE_DATA);
        }
        return messageHandlers.get(currentState);
    }

    private String isFillingProfileState(BotState currentState) {
        switch (currentState){
            case SHOW_MAIN_MENU:
                return "Show_Menu";
            case SHOW_USER_PROFILE:
                return "Show_Profile";
            case SHOW_HELP_MENU:
                return "Show_Help";
            case ASK_NAME:
            case ASK_SURNAME:
            case ASK_GENDER:
            case ASK_AGE:
            case ASK_CITY:
            case ASK_CELLPHONE:
            case ASK_EMAIL:
            case ASK_MARRIED:
            case ASK_CHILD:
            case WRITE_PROFILE_DATA:
                return "Write_Data";
            case GET_BY_NAME:
            case GET_BY_SURNAME:
            case GET_BY_AGE:
            case GET_BY_CITY:
            case GET_DATA_READY:
            case GET_PROFILE_DATA:
                return "Get_Data";
            default:
                return "Error";
        }
    }
}