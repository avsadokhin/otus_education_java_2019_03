package ru.otus.messaging.messages;

import ru.otus.dbservice.DbService;
import ru.otus.entity.User;
import ru.otus.messaging.MessageToBackEnd;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.Message;

import java.util.List;
import java.util.logging.Level;

public class MessageGetUserCollectionRequest extends MessageToBackEnd {
    public MessageGetUserCollectionRequest(Address from, Address to) {
        super(from, to);
    }

    @Override
    public <T extends User> void exec(DbService<T> dbService) {
        logger.log(Level.INFO, "MessageToBackEnd.MessageGetUserCollectionRequest");
        final List<T> contentList = dbService.findAll();
        if (contentList == null) {
            logger.log(Level.INFO, "contentList is null");
        } else {
            dbService.getMS().sendMessage(new MessageGetUserCollectionResponse<>(getTo(), getFrom(), contentList));
        }

    }


}
