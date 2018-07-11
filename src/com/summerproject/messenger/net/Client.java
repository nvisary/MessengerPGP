package com.summerproject.messenger.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private int serverPort;
    private String serverIp;
    private ObjectOutputStream objectOutputStream;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Client(String serverIp, int serverPort) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void start() {
        try {
            socket = new Socket(serverIp, serverPort);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


            StringBuilder sb = new StringBuilder(reader.readLine());
            Data data = new Data(null, sb.toString());
            objectOutputStream.writeObject(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 7777);
        client.start();
    }
}
