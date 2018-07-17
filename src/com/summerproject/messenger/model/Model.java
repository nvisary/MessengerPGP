package com.summerproject.messenger.model;

import com.summerproject.messenger.net.Client;
import com.summerproject.messenger.net.Server;
import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.ui.MainScreen;

import javax.jws.WebParam;
import javax.swing.*;
import java.util.ArrayList;

public class Model {
    private String userPassword;
    private PGP pgp;
    private Server server;
    private int serverPort;
    private Client client;
    private String lastMessage;
    private Boolean isLastMessage = false;
    private MainScreen mainScreen;
    public Model() {
        pgp = new PGP();
    }

    public void startServer(int port) {
        serverPort = port;
        server = new Server(port, this);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public synchronized void addMessage(String message) {
        lastMessage = message;
        mainScreen.addToList("vasya", message);
        isLastMessage = true;
    }

    public boolean isLastMessage() {
        return isLastMessage;
    }

    public String getLastMessage() {
        isLastMessage = false;
        return lastMessage;
    }

    public void generatePgpKeys() {
        pgp.setUserPassword(userPassword);
        pgp.generatePGPKeys();
    }

    public Server getServer() {
        return server;
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

    public int getServerPort() {
        return serverPort;
    }
}