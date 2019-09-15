package ru.otus.hw16.server.workers;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.messaging.core.MessageAddressRegistrationRequest;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketMessageWorker implements MessageWorker {
    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    public SocketMessageWorker(Socket socket) {
        this.socket = socket;
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
    }

    public void init() {
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }

    @Override
    public Message pool() {
        return input.poll();
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }


    private void sendMessage() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            while (socket.isConnected()) {
                Message message = output.take();
            /*    String json = new Gson().toJson(message);
                out.println(json);
                out.println();*/
                out.writeObject(message);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() {
       /* try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()){
                    String json = stringBuilder.toString();
                    Message message = getMessageFromGson(json);
                    input.add(message);
                    stringBuilder = new StringBuilder();
                }
            }
        }  catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            while (socket.isConnected()) {
                final Message message = (Message) in.readObject();
                input.add(message);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private Message getMessageFromGson(String json) throws ClassNotFoundException {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(json);
        //String className = String.valueOf(jsonObject.get(Message.CLASS_NAME_VARIABLE));
        Class<?> messageClass = MessageAddressRegistrationRequest.class;

        return (Message) new Gson().fromJson(json, messageClass);
    }
}
