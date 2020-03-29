package pl.szotaa.brokrr.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BrokrrClient implements AutoCloseable {

    private static volatile BrokrrClient instance;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private BrokrrClient(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new BrokrrClientException("Failed to initialize client", e);
        }
    }

    public static void initialize(String host, int port) {
        if(instance != null) {
            throw new AssertionError("Client already initialized");
        }
        synchronized (BrokrrClient.class) {
            if(instance != null) {
                throw new AssertionError("Client already initialized in another thread");
            }
            instance = new BrokrrClient(host, port);
        }
    }

    public static BrokrrClient getInstance() {
        if(instance == null) {
            throw new AssertionError("Please initialize client before usage with BrokrrClient.initialize method");
        }
        return instance;
    }

    public Object readObject() {
        try {
            return inputStream.readUnshared();
        } catch (IOException | ClassNotFoundException e) {
            throw new BrokrrClientException("Failed to readObject", e);
        }
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }
}