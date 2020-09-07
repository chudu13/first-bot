package ru.home.mydb_bot.botapi;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.mydb_bot.botapi.handlers.writeprofile.command.*;
import ru.home.mydb_bot.cache.UserDataCache;
import ru.home.mydb_bot.service.ReplyMessagesService;

public enum BotState {
    ASK_NAME{
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new NameCommand(service,cache).execute(message);
        }
    },
    GET_BY_NAME {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    GET_BY_SURNAME {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    GET_BY_CITY {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    GET_BY_AGE {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    GET_DATA_READY {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },

    ASK_SURNAME {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new SurnameCommand(service,cache).execute(message);
        }
    },
    ASK_GENDER {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    ASK_EMAIL {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new EmailCommand(service,cache).execute(message);
        }
    },
    ASK_AGE {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new AgeCommand(service,cache).execute(message);
        }
    },
    ASK_CITY {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new CityCommand(service,cache).execute(message);
        }
    },
    ASK_CELLPHONE {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return new CellphoneCommand(service,cache).execute(message);
        }
    },
    ASK_MARRIED {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    ASK_CHILD {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    GET_PROFILE_DATA {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    WRITE_PROFILE_DATA {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    SHOW_MAIN_MENU {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    SHOW_HELP_MENU {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    },
    SHOW_USER_PROFILE {
        @Override
        public SendMessage getCommand(ReplyMessagesService service, UserDataCache cache, Message message) {
            return null;
        }
    };

    public abstract SendMessage getCommand(ReplyMessagesService service, UserDataCache cache,Message message);
}
