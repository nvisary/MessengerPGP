package com.summerproject.messenger.net;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.pgp.PGPEncodedData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Connection extends Thread {
    private ObjectInputStream inputStream;
    private PGP pgp;
    private Model model;

    public Connection(Socket socket, PGP pgp, Model model) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        this.model = model;
        //this.pgp = pgp;
    }

    @Override
    public void run() {
        try {
            Data data = (Data) inputStream.readObject();
            model.addMessage(data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
