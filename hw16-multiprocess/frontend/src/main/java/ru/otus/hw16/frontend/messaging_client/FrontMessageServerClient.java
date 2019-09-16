package ru.otus.hw16.frontend.messaging_client;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;


import ru.otus.hw16.backend.entity.User;
import ru.otus.hw16.frontend.front.FrontService;
import ru.otus.hw16.server.client.MessageServerClientImpl;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.messaging.messages.MessageGetUserCollectionResponse;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontMessageServerClient extends MessageServerClientImpl {
    private static final Logger logger = Logger.getLogger(FrontMessageServerClient.class.getName());
    public static final Gson gson = new GsonBuilder().create();

    @Autowired
    private FrontService frontService;

    public FrontMessageServerClient(String host, int port, String from, String to) {
        super(host, port, from, to);

    }

    @Override
    public void startMessaging() {
        while (true) {
            try {
                final Message message = getSocketMessageWorker().take();
                logger.log(Level.INFO, "Получено сообщение: " + message);
                processMessageType(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void processMessageType(Message message) {
        if (message instanceof MessageGetUserCollectionResponse) {
            processMessageGetUserCollectionRequest((MessageGetUserCollectionResponse) message);}
         else {
            logger.log(Level.WARNING, "Получено неизвестного типа сообщение: " + message);
        }
    }

    private void processMessageGetUserCollectionRequest(MessageGetUserCollectionResponse request) {
        final Class<?> reqObjectClass;
        try {
            reqObjectClass = request.getClazz();
            final List<User> userList = (List<User>) gson.fromJson(request.getJsonMessage(), reqObjectClass);
            frontService.addUserList(userList);
       } catch (ClassCastException e) {
           logger.log(Level.WARNING, "Ошибка преобразования объект сообщения (" + request+"' к типу:" + request.getClazz());
       }

    }



}
