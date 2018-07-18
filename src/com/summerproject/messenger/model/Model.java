package com.summerproject.messenger.model;

import com.summerproject.messenger.net.Data;
import com.summerproject.messenger.net.Server;
import com.summerproject.messenger.pgp.PGP;
import com.summerproject.messenger.ui.MainScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private String userPassword;
    private PGP pgp;
    private Server server;
    private int serverPort;
    private MainScreen mainScreen;
    private String username;
    //private HashMap<String, <ArrayList<Message>> dialogs = new HashMap;
    private HashMap<String, ArrayList<Message>> dialogs = new HashMap<>();

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
        try {
            byte[] message = pgp.decode(data.getPgpEncodedData());
            System.out.println("Message: " + new String(message));
            mainScreen.addToList(data.getUsername(), new String(message));
            if (dialogs.containsKey(data.getUsername())) {
                dialogs.get(data.getUsername()).add(new Message(data.getUsername(), new String(message)));
            } else {
                ArrayList<Message> tmp = new ArrayList<>();
                tmp.add(new Message(data.getUsername(), new String(message)));
                dialogs.put(data.getUsername(), tmp);
            }
            System.out.println(new String(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mainScreen.addToList(data.getUsername(), data.getMessage());

    }

    public synchronized void addMessage(String you, String username, String message) {
        if (dialogs.containsKey(username) && you.equals("You")) {
            dialogs.get(username).add(new Message("You", message));
        }
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
