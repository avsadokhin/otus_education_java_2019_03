package ru.otus.hw15.messaging.messages;

import ru.otus.hw15.dbservice.DbService;
import ru.otus.hw15.entity.User;
import ru.otus.hw15.messaging.core.Address;
import ru.otus.hw15.messaging.MessageToBackEnd;

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
