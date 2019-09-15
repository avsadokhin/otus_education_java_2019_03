package ru.otus.hw16.server.client;

import ru.otus.hw16.server.messaging.core.*;
import ru.otus.hw16.server.workers.SocketMessageWorker;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MessageServerClientImpl implements MessageServerClient {
    private static final Logger logger = Logger.getLogger(MessageServerClientImpl.class.getName());

    private static final long PAUSE_MS = 1000;

    private SocketMessageWorker socketMessageWorker;
    private final Address addressTo;
    private final Address addressFrom;

    public MessageServerClientImpl(String host, int port, String from, String to) {
        try {
            Socket socket = new Socket(host, port);
            this.socketMessageWorker = new SocketMessageWorker(socket);
            socketMessageWorker.init();
        } catch (IOException e) {
            logger.log(Level.INFO, "Ошибка соединения host:" + host + ", port:" + port);
            throw new RuntimeException(e);
        }

        this.addressFrom = registerFromRequest(from);
        this.addressTo = registerToRequest(to);
    }


    public SocketMessageWorker getSocketMessageWorker() {
        return socketMessageWorker;
    }


    public Address registerFromRequest(String addressName) {
        socketMessageWorker.send(new MessageAddressRegistrationRequest(addressName));
        Address address = null;
        try {
            MessageAddressRegistrationResponse addressRegistrationResponse = (MessageAddressRegistrationResponse) socketMessageWorker.take();
            address = addressRegistrationResponse.getAddress();
            logger.log(Level.INFO, "Регистрация адреса '" + addressName + "'. Получен адрес от MessageServer: " + address);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return address;
    }


    public Address registerToRequest(String addressName) {
        Address address = null;
        while (address == null) {
            socketMessageWorker.send(new MessageGetAddressRegistrationRequest(addressName));
            try {
                MessageGetAddressRegistrationResponse getAddressRegistrationResponse = (MessageGetAddressRegistrationResponse) socketMessageWorker.take();
                address = getAddressRegistrationResponse.getAddress();
                if (address == null) Thread.sleep(PAUSE_MS); //Подождем пока зарегистрируется контрагент
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.INFO, "Запрос адреса стороны '" + addressName + "'. Получен  адрес от MessageServer: " + address);
        return address;
    }

    public void startMessaging() {

    }


    public Address getAddressTo() {
        return addressTo;
    }


    public Address getAddressFrom() {
        return addressFrom;
    }

    public void close() throws IOException {
        socketMessageWorker.close();
    }
}
