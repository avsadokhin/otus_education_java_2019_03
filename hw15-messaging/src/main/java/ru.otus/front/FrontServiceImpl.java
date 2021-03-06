package ru.otus.front;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import ru.otus.entity.User;
import ru.otus.messaging.MessageSystemContext;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.Message;
import ru.otus.messaging.core.MessageSystem;
import ru.otus.messaging.messages.MessageCreateUserRequest;
import ru.otus.messaging.messages.MessageGetUserCollectionRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class FrontServiceImpl implements FrontService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageSystemContext context;
    private final Address address;
    private final List<User> userList = new ArrayList<>();

    public FrontServiceImpl(MessageSystemContext context, Address address, SimpMessagingTemplate messagingTemplate) {
        this.context = context;
        this.address = address;
        this.messagingTemplate = messagingTemplate;

        context.setFrontAddress(address);
        context.getMessageSystem().registerAddressee(this);
    }

    @Override
    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    @Override
    public <T extends User> void createUserRequest(T user) {
        Message message = new MessageCreateUserRequest(getAddress(), context.getDbAddress(), user);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public <T extends User> void getUserCollectionRequest() {
        System.out.println("getUserCollectionRequest");
        Message message = new MessageGetUserCollectionRequest(getAddress(), context.getDbAddress());
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public <T extends User> void addUserList(List<T> userList) {
        this.userList.addAll(userList);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
