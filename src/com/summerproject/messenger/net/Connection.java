package com.summerproject.messenger.net;

import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.pgp.PGPEncodedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Connection extends Thread {
    private ObjectInputStream inputStream;
    private PGP pgp;


    public Connection(Socket socket, PGP pgp) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        this.pgp = pgp;
    }

    @Override
    public void run() {
        try {
            Data data = (Data) inputStream.readObject();
            PGPEncodedData pgpEncodedData = data.getPgpEncodedData();
            byte[] arr = pgp.decode(pgpEncodedData);
            System.out.println(new String(arr));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
