package ru.otus.hw16.frontend.front;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.otus.hw16.backend.entity.User;
import ru.otus.hw16.frontend.messaging_client.FrontMessageServerClient;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.messaging.messages.MessageCreateUserRequest;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class FrontServiceImpl implements FrontService {
    private final SimpMessagingTemplate messagingTemplate;
    private static final Gson gson = new GsonBuilder().create();
    private FrontMessageServerClient frontMessageServerClient;

    private final List<User> userList = new ArrayList<>();

    public FrontServiceImpl(FrontMessageServerClient frontMessageServerClient, SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        this.frontMessageServerClient = frontMessageServerClient;
    }

    @Override
    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    @Override
    public <T extends User> void createUserRequest(T user) {
        Message message = new MessageCreateUserRequest(frontMessageServerClient.getAddressFrom()
                , frontMessageServerClient.getAddressTo(), gson.toJson(user, user.getClass()), user.getClass());
        frontMessageServerClient.getSocketMessageWorker().send(message);
    }

    @Override
    public <T extends User> void getUserCollectionRequest() {
        Message message = new MessageGetUserCollectionRequest(frontMessageServerClient.getAddressFrom()
                , frontMessageServerClient.getAddressTo());
        frontMessageServerClient.getSocketMessageWorker().send(message);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public <T extends User> void addUserList(List<T> userList) {
        this.userList.addAll(userList);
    }


}
