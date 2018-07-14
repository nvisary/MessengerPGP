package com.summerproject.messenger.net;

import com.summerproject.messenger.pgp.PGP;

import java.io.*;
import java.net.*;

public class Server implements Runnable {
    private ObjectInputStream inputStream;
    private ServerSocket serverSocket;
    private Socket socket;
    private int serverPort = 7777;
    private PGP pgp = new PGP();

    public Server(int serverPort) {
        this.serverPort = serverPort;
        try {
            serverSocket = new ServerSocket(serverPort);
            serverSocket.setSoTimeout(100000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        pgp.generatePGPKeys();
        System.out.println(pgp.getPublicPGPKey());
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(100000);
                Connection connection = new Connection(socket, pgp);
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Thread serverThread = new Thread(new Server(7777));
        serverThread.start();
    }
}
