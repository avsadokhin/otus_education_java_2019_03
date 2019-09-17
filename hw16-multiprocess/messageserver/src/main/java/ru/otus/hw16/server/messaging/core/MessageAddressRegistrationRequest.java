package ru.otus.hw16.server.messaging.core;


import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.Addressee;
import ru.otus.hw16.server.messaging.core.Message;

import java.io.Serializable;

public class MessageAddressRegistrationRequest extends Message {
    private final long time;
    private final String addressName;

    public MessageAddressRegistrationRequest(String addressName) {
        super(MessageAddressRegistrationRequest.class);

        time = System.currentTimeMillis();
        this.addressName = addressName;
    }

    public String getAddressName() {
        return addressName;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageAddressRegistrationRequest{");
        sb.append("addressName=").append(addressName).append(", ");
        sb.append("time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
