package com.summerproject.messenger.net;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.pgp.PGP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private ObjectInputStream inputStream;
    private ServerSocket serverSocket;
    private Socket socket;
    private int serverPort = 7777;
    private PGP pgp = new PGP();
    private Model model;

    public Server(int serverPort, Model model) {
        this.serverPort = serverPort;
        this.model = model;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*Thread serverThread = new Thread(new Server(7777));
        serverThread.start();*/
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                Connection connection = new Connection(socket, pgp, model);
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public int getServerPort() {
        return serverPort;
    }
}
