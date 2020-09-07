package ru.home.mydb_bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.mydb_bot.botapi.KeyboardState;
import ru.home.mydb_bot.cache.UserDataCache;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    private KeyboardState keyboardState;
    private UserDataCache userDataCache;

    public KeyboardService(UserDataCache userDataCache){
        this.userDataCache = userDataCache;
    }

    public InlineKeyboardMarkup getMyKeyboardMarkup(KeyboardState keyboardState, int userId) {
        this.keyboardState = keyboardState;
        userDataCache.setUsersCurrentKeyboardState(userId, this.keyboardState);

        InlineKeyboardMarkup inlineKeyboardMarkup = null;

        if (keyboardState.equals(KeyboardState.USE_GENDER)){
            inlineKeyboardMarkup = createKeyboardGender();
        }
        if (keyboardState.equals(KeyboardState.USE_MARRIED)){
           inlineKeyboardMarkup = createKeyboardMarried();
        }
        if (keyboardState.equals(KeyboardState.USE_CHILDREN)){
           inlineKeyboardMarkup = createKeyboardChildren();
        }
        if (keyboardState.equals(KeyboardState.USE_SEARCH)){
            inlineKeyboardMarkup = createKeyboardSearch();
        }

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createKeyboardGender(){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonGenderMan = new InlineKeyboardButton().setText("М");
        InlineKeyboardButton buttonGenderWoman = new InlineKeyboardButton().setText("Ж");

        buttonGenderMan.setCallbackData("buttonMan");
        buttonGenderWoman.setCallbackData("buttonWoman");

        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        keyboardButtonRow.add(buttonGenderMan);
        keyboardButtonRow.add(buttonGenderWoman);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createKeyboardMarried(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonManMarried = new InlineKeyboardButton().setText("Женат");
        InlineKeyboardButton buttonManNotMarried = new InlineKeyboardButton().setText("Не женат");
        InlineKeyboardButton buttonManDivorced = new InlineKeyboardButton().setText("Разведен");
        InlineKeyboardButton buttonWomanMarried = new InlineKeyboardButton().setText("Замужем");
        InlineKeyboardButton buttonWomanNotMarried = new InlineKeyboardButton().setText("Не замужем");
        InlineKeyboardButton buttonWomanDivorced = new InlineKeyboardButton().setText("Разведена");

        buttonManMarried.setCallbackData("buttonManMarried");
        buttonManNotMarried.setCallbackData("buttonManNotMarried");
        buttonManDivorced.setCallbackData("buttonManDivorced");
        buttonWomanMarried.setCallbackData("buttonWomanMarried");
        buttonWomanNotMarried.setCallbackData("buttonWomanNotMarried");
        buttonWomanDivorced.setCallbackData("buttonWomanDivorced");

        List<InlineKeyboardButton> keyboardButtonRow1 = new ArrayList<>();
        keyboardButtonRow1.add(buttonManMarried);
        keyboardButtonRow1.add(buttonManNotMarried);
        keyboardButtonRow1.add(buttonManDivorced);

        List<InlineKeyboardButton> keyboardButtonRow2 = new ArrayList<>();
        keyboardButtonRow2.add(buttonWomanMarried);
        keyboardButtonRow2.add(buttonWomanNotMarried);
        keyboardButtonRow2.add(buttonWomanDivorced);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow1);
        rowList.add(keyboardButtonRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createKeyboardChildren(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonChildYes = new InlineKeyboardButton().setText("Да");
        InlineKeyboardButton buttonChildNo = new InlineKeyboardButton().setText("Нет");

        buttonChildYes.setCallbackData("buttonYes");
        buttonChildNo.setCallbackData("buttonNo");

        List<InlineKeyboardButton> keyboardButtonRow = new ArrayList<>();
        keyboardButtonRow.add(buttonChildYes);
        keyboardButtonRow.add(buttonChildNo);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
    private InlineKeyboardMarkup createKeyboardSearch(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton searchByNameAndSurname = new InlineKeyboardButton().setText("Имя и фамилия");
        InlineKeyboardButton searchByName = new InlineKeyboardButton().setText("Имя");
        InlineKeyboardButton searchBySurname = new InlineKeyboardButton().setText("Фамилия");
        InlineKeyboardButton searchByCity = new InlineKeyboardButton().setText("Город");
        InlineKeyboardButton searchByAge = new InlineKeyboardButton().setText("Возраст");

        searchByNameAndSurname.setCallbackData("bNameAndSurname");
        searchByName.setCallbackData("bName");
        searchBySurname.setCallbackData("bSurname");
        searchByCity.setCallbackData("bCity");
        searchByAge.setCallbackData("bAge");

        List<InlineKeyboardButton> keyboardButtons1 = new ArrayList<>();
        keyboardButtons1.add(searchByNameAndSurname);
        keyboardButtons1.add(searchByCity);

        List<InlineKeyboardButton> keyboardButtons2 = new ArrayList<>();
        keyboardButtons2.add(searchByName);
        keyboardButtons2.add(searchBySurname);
        keyboardButtons2.add(searchByAge);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtons1);
        rowList.add(keyboardButtons2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public KeyboardState getKeyboardState() {
        return keyboardState;
    }
}