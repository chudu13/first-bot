package ru.home.mydb_bot.cache;

import ru.home.mydb_bot.botapi.BotState;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.botapi.handlers.QueryState;
import ru.home.mydb_bot.model.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    void setUsersCurrentKeyboardState(int userId, KeyboardState keyboardState);

    void setUsersCurrentQueryState(int userId, QueryState queryState);

    void setUsersCurrentCountState(int userId, int count);

    BotState getUsersCurrentBotState(int userId);

    KeyboardState getUsersCurrentKeyboardState(int userId);

    QueryState getUsersCurrentQueryState(int userId);

    UserProfileData getUserProfileData(int userId);

    Integer getUsersCurrentCountState(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);
}
