package com.summerproject.messenger;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.pgp.idea.IDEA;
import com.summerproject.messenger.pgp.rsa.PrivateKey;
import com.summerproject.messenger.pgp.rsa.PublicKey;
import com.summerproject.messenger.ui.MainScreen;
import com.summerproject.messenger.ui.PasswordScreen;
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
        checkProperties();
    }

    private void checkProperties() {
        File file = new File(propertiesFileName);
        passwordScreen = new PasswordScreen(mainScreen, "Input password", model);
        passwordScreen.setVisible(true);
        if (file.exists()) {
            try {
                byte[] encodedFile = FileWorker.loadFile(propertiesFileName);
                IDEA idea = new IDEA();
                byte[] decodedFile = idea.decode(encodedFile, model.getUserPassword());
                FileWorker.saveFile(propertiesFileName, decodedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


            FileWorker.readProperties(propertiesFileName);
            try {
                PrivateKey privateKey = new PrivateKey(FileWorker.privateKey);
                PublicKey publicKey = new PublicKey(FileWorker.publicKey);
                model.getPgp().setPrivatePGPkey(privateKey);
                model.getPgp().setPublicPGPkey(publicKey);

            } catch (NullPointerException ex) {
                System.exit(-1);
            }

            System.out.println(model.getPgp().getPublicPGPKey());
            System.out.println(model.getPgp().getPrivatePGPKey());

        } else {
            if (model.getUserPassword() != null) {
                model.generatePgpKeys();
                PublicKey publicKey = model.getPgp().getPublicPGPKey();
                PrivateKey privateKey = model.getPgp().getPrivatePGPKey();
                FileWorker.writeProperties(propertiesFileName, publicKey, privateKey);
                try {
                    byte[] f = FileWorker.loadFile(propertiesFileName);
                    IDEA idea = new IDEA();
                    byte[] encoded = idea.encode(f, model.getUserPassword());
                    FileWorker.saveFile(propertiesFileName, encoded);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
