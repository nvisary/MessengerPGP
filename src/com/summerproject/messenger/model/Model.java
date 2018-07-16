package com.summerproject.messenger.model;

import com.summerproject.messenger.net.Client;
import com.summerproject.messenger.net.Server;
import com.summerproject.messenger.pgp.PGP;

import javax.jws.WebParam;

public class Model {
    private String userPassword;
    private PGP pgp;
    private Server server;
    private Client client;

    public Model() {
        pgp = new PGP();
        //Thread serverThread = new Thread(new Server(7070));
        //serverThread.start();
    }

    public void generatePgpKeys() {
        pgp.setUserPassword(userPassword);
        pgp.generatePGPKeys();
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public PGP getPgp() {
        return pgp;
    }
}
