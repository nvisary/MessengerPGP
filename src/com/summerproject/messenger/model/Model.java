package com.summerproject.messenger.model;

import com.summerproject.messenger.net.Data;
import com.summerproject.messenger.net.Server;
import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.ui.MainScreen;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private String userPassword;
    private PGP pgp;
    private Server server;
    private int serverPort;
    private MainScreen mainScreen;
    private String username;
    private Map<String, Data> messages = new HashMap<>();


    public Model() {
        pgp = new PGP();
    }

    public void startServer() {
        server = new Server(serverPort, this);
        Thread serverThread = new Thread(server);
        serverThread.start();
        if (mainScreen != null) {
            mainScreen.setPort(serverPort);
        }
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public synchronized void addMessage(Data data) {
        mainScreen.addToList(data.getUsername(), data.getMessage());
        messages.put(data.getUsername(), data);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setServerPort(String port) {
        serverPort = Integer.parseInt(port);
    }
}
