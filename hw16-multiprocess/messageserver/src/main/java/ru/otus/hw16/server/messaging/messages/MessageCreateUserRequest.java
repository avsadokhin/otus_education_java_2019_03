package ru.otus.hw16.server.messaging.messages;


import ru.otus.hw16.model.entity.User;
import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.AddressMessage;
import ru.otus.hw16.server.messaging.core.Addressee;

public class MessageCreateUserRequest extends AddressMessage {
    User user;
    public MessageCreateUserRequest(Address from, Address to, User user) {
        super(from, to, MessageCreateUserRequest.class);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void exec(Addressee addressee) {

    }
}
