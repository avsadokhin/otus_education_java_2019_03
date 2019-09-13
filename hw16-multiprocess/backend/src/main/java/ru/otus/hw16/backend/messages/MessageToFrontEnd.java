package ru.otus.hw16.backend.messages;


import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.Addressee;
import ru.otus.hw16.server.messaging.core.Message;

import java.util.logging.Level;

public abstract class MessageToFrontEnd extends Message {

    public MessageToFrontEnd(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof FrontService) {
            exec((FrontService) addressee);
        } else {
            logger.log(Level.INFO, "Addressee type (type=" + addressee.getClass() + ", " +
                    "address=" + addressee.getAddress() + ") don't correspond message type");
        }
    }

    public abstract void exec(FrontService frontService);
}
