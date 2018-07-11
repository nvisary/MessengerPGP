package com.summerproject.messenger.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Connection extends Thread {
    private ObjectInputStream inputStream;


    public Connection(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            Data data = (Data) inputStream.readObject();
            System.out.println(data.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
