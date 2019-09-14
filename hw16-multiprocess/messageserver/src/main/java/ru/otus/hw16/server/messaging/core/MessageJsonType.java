package ru.otus.hw16.server.messaging.core;

public class MessageJsonType extends AddressMessage {
    private final String jsonMessage;
    private final Class<?> clazz;

    public MessageJsonType(Address from, Address to, Class<?> clazz, String jsonMessage) {
        super(from, to);

        this.clazz = clazz;
        this.jsonMessage = jsonMessage;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getJsonMessage() {
        return jsonMessage;
    }


    @Override
    public void exec(Addressee addressee) {

    }

    @Override
    public String toString() {
        return "MessageJsonType{" +
                "jsonMessage='" + jsonMessage + '\'' +
                ", clazz=" + clazz +
                '}';
    }
}
