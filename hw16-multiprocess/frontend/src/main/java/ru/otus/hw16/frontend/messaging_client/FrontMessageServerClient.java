package ru.otus.hw16.frontend.messaging_client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.hw16.model.entity.User;
import ru.otus.hw16.frontend.front.FrontService;
import ru.otus.hw16.server.client.MessageServerClientImpl;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.messaging.messages.MessageCreateUserRequest;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionRequest;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontMessageServerClient extends MessageServerClientImpl implements FrontService {
    private static final Logger logger = Logger.getLogger(FrontMessageServerClient.class.getName());
    public static final Gson gson = new GsonBuilder().create();
   @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final List<User> userList = new ArrayList<>();

    public FrontMessageServerClient(String host, int port, String from, String to) {
        super(host, port, from, to);
    }

    @Override
    public void startMessaging() {
        while (true) {
            try {
                final Message message = getSocketMessageWorker().take();
                logger.log(Level.INFO, "Message receive: " + message);
                processMessageType(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void processMessageType(Message message) {
        if (message instanceof MessageGetUserCollectionResponse) {
            processMessageGetUserCollectionResponse((MessageGetUserCollectionResponse) message);}
         else {
            logger.log(Level.WARNING, "Message type unknown: " + message);
        }
    }

    private void processMessageGetUserCollectionResponse(MessageGetUserCollectionResponse request) {
            final List<User> userList = request.getUserList();
            addUserList(userList);
            messagingTemplate.convertAndSend("/topic/usersResponse", userList);
    }


    @Autowired
    public SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    @Override
    public <T extends User> void createUserRequest(T user) {
        Message message = new MessageCreateUserRequest(getAddressFrom()
                , getAddressTo(), user);
        getSocketMessageWorker().send(message);
    }

    @Override
    public <T extends User> void getUserCollectionRequest() {
        Message message = new MessageGetUserCollectionRequest(getAddressFrom()
                , getAddressTo());
        getSocketMessageWorker().send(message);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    @Override
    public <T extends User> void addUserList(List<T> userList) {
        System.out.println("userList"+userList.size());
        this.userList.addAll(userList);
    }



}
