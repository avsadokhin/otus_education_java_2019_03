package ru.otus.messaging;

import ru.otus.dbservice.DbService;
import ru.otus.entity.User;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.Addressee;
import ru.otus.messaging.core.Message;

import java.util.logging.Level;

public abstract class MessageToBackEnd extends Message {
    public MessageToBackEnd(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DbService) {
            exec((DbService<User>) addressee);
        } else {
            logger.log(Level.INFO, "Addressee type (type=" + addressee.getClass() + ", " +
                    "address=" + addressee.getAddress() + ") don't correspond message type");
        }
    }

    public abstract <T extends User> void exec(DbService<T> dbService);
}
