package ru.otus.messaging.messages;

import ru.otus.dbservice.DbService;
import ru.otus.entity.User;
import ru.otus.messaging.MessageToBackEnd;
import ru.otus.messaging.core.Address;

import java.util.logging.Level;

public class MessageCreateUserRequest extends MessageToBackEnd {
    private final User userData;

    public MessageCreateUserRequest(Address from, Address to, User userData) {
        super(from, to);
        this.userData = userData;
    }

    @Override
    public <T extends User> void exec(DbService<T> dbService) {
        logger.log(Level.INFO, "MessageToBackEnd.MessageCreateUserRequest: Create user object(" + userData + ")");
        dbService.create((T) userData);
    }
}
