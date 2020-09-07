package ru.home.mydb_bot.cache;

import org.springframework.stereotype.Component;
import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.botapi.handlers.QueryState;
import ru.home.mydb_bot.model.UserProfileData;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {

    private Map<Integer,BotState> usersBotStates = new HashMap<>();
    private Map<Integer,UserProfileData> usersProfileData = new HashMap<>();
    private Map<Integer,KeyboardState> usersKeyboardState = new HashMap<>();
    private Map<Integer,QueryState> usersQueryState = new HashMap<>();
    private Map<Integer,Integer> usersCountState = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public void setUsersCurrentKeyboardState(int userId, KeyboardState keyboardState) {
        usersKeyboardState.put(userId,keyboardState);
    }

    @Override
    public void setUsersCurrentQueryState(int userId, QueryState queryState) {
        usersQueryState.put(userId, queryState);
    }

    @Override
    public void setUsersCurrentCountState(int userId, int count) {
        usersCountState.put(userId,count);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        BotState botState = usersBotStates.get(userId);

        if (botState == null){
            botState = BotState.SHOW_MAIN_MENU;
        }
        return botState;
    }

    @Override
    public KeyboardState getUsersCurrentKeyboardState(int userId) {
        KeyboardState keyboardState = usersKeyboardState.get(userId);
        return keyboardState;
    }

    @Override
    public QueryState getUsersCurrentQueryState(int userId) {
        QueryState queryState = usersQueryState.get(userId);
        return queryState;
    }


    @Override
    public UserProfileData getUserProfileData(int userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);

        if (userProfileData == null){
            userProfileData = new UserProfileData();
        }

        return userProfileData;
    }

    @Override
    public Integer getUsersCurrentCountState(int userId) {
        try {
            int val = usersCountState.get(userId);
        }catch (NullPointerException ex){
            setUsersCurrentCountState(userId,0);
        }
        return usersCountState.get(userId);
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
}