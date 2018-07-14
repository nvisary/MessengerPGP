package com.summerproject.messenger;

import com.summerproject.messenger.model.Model;
import com.summerproject.messenger.ui.MainScreen;
import com.summerproject.messenger.ui.PasswordScreen;

import java.io.*;

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
        if (file.exists()) {
            passwordScreen = new PasswordScreen( mainScreen, "Input password", model);
            passwordScreen.setVisible(true);
        } else {
            try {
                FileWriter writer = new FileWriter(propertiesFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
