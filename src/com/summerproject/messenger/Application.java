package com.summerproject.messenger;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.pgp.idea.IDEA;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.ui.MainScreen;
import com.summerproject.messenger.ui.PasswordScreen;
import com.summerproject.messenger.ui.UsernameScreen;
import com.summerproject.messenger.util.FileWorker;

import java.io.File;
import java.io.IOException;

public class Application {
    private Model model;
    private String propertiesFileName = "prop.settings";
    private MainScreen mainScreen;
    private PasswordScreen passwordScreen;

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run() {
        model = new Model();
        mainScreen = new MainScreen("PGP messenger", model);
        mainScreen.display();
        model.setMainScreen(mainScreen);
        checkProperties();
        model.startServer();

        mainScreen.setTextToJTFPublicKey(model.getPgp().getPublicPGPKey().toString());
        encodeFile(propertiesFileName, model.getUserPassword());
    }

    private void checkProperties() {
        File file = new File(propertiesFileName);
        passwordScreen = new PasswordScreen(mainScreen, "Input password", model);
        passwordScreen.setVisible(true);
        if (file.exists()) {
            decodeFile(propertiesFileName, model.getUserPassword());
            FileWorker.readProperties(propertiesFileName);
            try {
                PrivateKey privateKey = new PrivateKey(FileWorker.privateKey);
                PublicKey publicKey = new PublicKey(FileWorker.publicKey);
                model.getPgp().setPrivatePGPkey(privateKey);
                model.getPgp().setPublicPGPkey(publicKey);
                model.setUsername(FileWorker.username);

            } catch (NullPointerException ex) {
                System.exit(-1);
            }

            System.out.println(model.getPgp().getPublicPGPKey());
            System.out.println(model.getPgp().getPrivatePGPKey());

        } else {
            if (model.getUserPassword() != null) {
                model.generatePgpKeys();
                UsernameScreen usernameScreen = new UsernameScreen(mainScreen, "Input username", model);
                usernameScreen.setVisible(true);
                PublicKey publicKey = model.getPgp().getPublicPGPKey();
                PrivateKey privateKey = model.getPgp().getPrivatePGPKey();
                FileWorker.writeProperties(propertiesFileName, publicKey, privateKey, model.getUsername());
            }
        }
    }

    private void encodeFile(String fileName, String password) {
        try {
            byte[] f = FileWorker.loadFile(fileName);
            IDEA idea = new IDEA();
            byte[] encoded = idea.encode(f, password);
            FileWorker.saveFile(fileName, encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decodeFile(String fileName, String password) {
        try {
            byte[] encodedFile = FileWorker.loadFile(fileName);
            IDEA idea = new IDEA();
            byte[] decodedFile = idea.decode(encodedFile, password);
            FileWorker.saveFile(fileName, decodedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
