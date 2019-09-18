package ru.otus.hw16.server.messaging.core;


public class MessageGetAddressRegistrationResponse extends Message {
    private final long time;
    private final Address address;

    public MessageGetAddressRegistrationResponse(Address address) {
        super(MessageGetAddressRegistrationResponse.class);
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
