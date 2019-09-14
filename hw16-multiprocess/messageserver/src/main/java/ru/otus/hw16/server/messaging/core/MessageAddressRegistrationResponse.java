package ru.otus.hw16.server.messaging.core;


import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.Message;

import java.io.Serializable;

public class MessageAddressRegistrationResponse extends Message {
    private final long time;
    private final Address address;

    public MessageAddressRegistrationResponse(Address address) {
        time = System.currentTimeMillis();
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessageAddressRegistrationResponse{");
        sb.append("address=").append(address).append(", ");
        sb.append("time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
