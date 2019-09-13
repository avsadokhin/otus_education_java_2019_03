package ru.otus.hw16.server.messaging.messages;


import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.Addressee;
import ru.otus.hw16.server.messaging.core.Message;

public class PingMessage extends Message {
    private final long time;

    public PingMessage(Address from, Address to) {
        super(from, to);
        time = System.currentTimeMillis();
    }

    @Override
    public void exec(Addressee addressee) {

    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PingMessage{");
        sb.append("time=").append(time);
        sb.append('}');
        return sb.toString();
    }
}
