package ru.otus.messaging;

import ru.otus.front.FrontService;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.Addressee;
import ru.otus.messaging.core.Message;

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
