package com.summerproject.messenger.ui.test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PasswordScreenController {
    @FXML
    Button btnAccept;
    @FXML
    TextField textField;

    public String display() throws IOException {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Input password");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerApplication.class.getResource("ui/PasswordScreen.fxml"));
        Scene scene = new Scene(loader.load());

        window.setScene(scene);
        window.showAndWait();
        btnAccept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });

        return "good";
    }


}
