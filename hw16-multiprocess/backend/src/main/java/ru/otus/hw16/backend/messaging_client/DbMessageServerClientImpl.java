package ru.otus.hw16.backend.messaging_client;

import ru.otus.hw16.backend.dbservice.DbService;
import ru.otus.hw16.model.entity.User;
import ru.otus.hw16.server.client.MessageServerClientImpl;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.messaging.messages.MessageCreateUserRequest;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionRequest;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbMessageServerClientImpl extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(DbMessageServerClientImpl.class.getName());
    private final DbService<User> dbService;
    public static final Gson gson = new GsonBuilder().create();

    public DbMessageServerClientImpl(String host, int port, String from, String to, DbService<User> dbService) {
        super(host, port, from, to);
        this.dbService = dbService;
    }

    @Override
    public void startMessaging() {
        while (true) {
            try {
                final Message message = getSocketMessageWorker().take();
                logger.log(Level.INFO, "Message received: " + message);
                processMessageType(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void processMessageType(Message message) {
        if (message instanceof MessageGetUserCollectionRequest) {
            processMessageGetUserCollectionRequest((MessageGetUserCollectionRequest) message);
        } else if (message instanceof MessageCreateUserRequest) {
            processMessageCreateUserRequest((MessageCreateUserRequest) message);
        } else {
            logger.log(Level.WARNING, "Message type unknown receipt: " + message);
        }
    }

    private void processMessageCreateUserRequest(MessageCreateUserRequest request) {
        final User user = request.getUser();
        dbService.create(user);
    }

    private void processMessageGetUserCollectionRequest(MessageGetUserCollectionRequest request) {

        final List<User> contentList = dbService.findAll();
        if (contentList == null) {
            logger.log(Level.INFO, "User collection is empty");
        } else {

            getSocketMessageWorker().send(new MessageGetUserCollectionResponse(
                    request.getTo(), request.getFrom(), contentList)
            );

        }
    }


}
