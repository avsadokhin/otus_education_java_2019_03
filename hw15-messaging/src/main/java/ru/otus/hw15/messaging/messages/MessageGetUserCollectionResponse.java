package ru.otus.hw15.messaging.messages;

import ru.otus.hw15.entity.User;
import ru.otus.hw15.front.FrontService;
import ru.otus.hw15.messaging.MessageToFrontEnd;
import ru.otus.hw15.messaging.core.Address;

import java.util.List;
import java.util.logging.Level;

public class MessageGetUserCollectionResponse<T extends User> extends MessageToFrontEnd {
    private final List<T> content;

    public MessageGetUserCollectionResponse(Address from, Address to, List<T> content) {
        super(from, to);
        this.content = content;
    }

    @Override
    public void exec(FrontService frontService) {
        logger.log(Level.INFO, "MessageToFrontEnd.MessageGetUserCollectionResponse: message response (content=" + content + ")");
        frontService.getMessagingTemplate().convertAndSend("/topic/usersResponse", content);
    }
}
