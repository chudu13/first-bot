package ru.home.mydb_bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
    CITY(EmojiParser.parseToUnicode(":city_sunrise:")),
    DART(EmojiParser.parseToUnicode(":dart:")),
    EMAIL(EmojiParser.parseToUnicode(":e-mail:")),
    INPUT(EmojiParser.parseToUnicode(":man_technologist:")),
    HELP(EmojiParser.parseToUnicode(":rotating_light:")),
    WARNING(EmojiParser.parseToUnicode(":warning:")),
    MENU(EmojiParser.parseToUnicode(":bulb:")),
    SEARCH(EmojiParser.parseToUnicode(":mag:")),
    PHONE(EmojiParser.parseToUnicode(":iphone:")),
    GENDER(EmojiParser.parseToUnicode(":performing_arts:")),
    MARRIED(EmojiParser.parseToUnicode(":family:")),
    CHILDREN(EmojiParser.parseToUnicode(":baby:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
