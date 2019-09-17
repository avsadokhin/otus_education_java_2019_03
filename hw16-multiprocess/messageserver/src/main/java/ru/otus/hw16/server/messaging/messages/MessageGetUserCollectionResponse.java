package ru.otus.hw16.server.messaging.messages;

import ru.otus.hw16.model.entity.User;
import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.AddressMessage;
import ru.otus.hw16.server.messaging.core.Addressee;

import java.util.List;

public class MessageGetUserCollectionResponse extends AddressMessage {
   List<User> userList;
    public MessageGetUserCollectionResponse(Address from, Address to, List<User> userList) {
        super(from, to, MessageGetUserCollectionResponse.class);
        this.userList = userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public void exec(Addressee addressee) {

    }
}
