package ru.otus.hw16.frontend.messaging.messages;

import ru.otus.hw16.frontend.dbservice.DbService;
import ru.otus.hw16.frontend.entity.User;
import ru.otus.hw16.frontend.messaging.MessageToBackEnd;
import ru.otus.hw16.frontend.messaging.core.Address;

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
