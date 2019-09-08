package ru.otus.hw15.messaging;

import ru.otus.hw15.dbservice.DbService;
import ru.otus.hw15.entity.User;
import ru.otus.hw15.messaging.core.Address;
import ru.otus.hw15.messaging.core.Addressee;
import ru.otus.hw15.messaging.core.Message;

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
