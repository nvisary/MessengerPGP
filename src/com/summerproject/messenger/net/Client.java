package com.summerproject.messenger.net;

import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.pgp.PGPEncodedData;
import com.summerproject.messenger.pgp.rsa.PublicKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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


            System.out.println("Please, input your password for generate PGP keys...");
            StringBuilder sb = new StringBuilder(reader.readLine());
            String password = sb.toString();
            System.out.println("Please, input public key...");
            sb = new StringBuilder(reader.readLine());

            PGP pgp = new PGP();
            pgp.setPublicReceiverKey(new PublicKey(new BigInteger(sb.toString())));
            pgp.setUserPassword(password);
            System.out.println("Input your secret message...");
            sb = new StringBuilder(reader.readLine());
            PGPEncodedData pgpEncodedData = pgp.encode(sb.toString().getBytes());
            Data data = new Data(pgpEncodedData, sb.toString());
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
