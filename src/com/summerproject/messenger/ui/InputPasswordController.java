package com.summerproject.messenger.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class InputPasswordController {
    private String name;
    public TextField passwordTextField;
    public Button btnOk;


    public void setName(String name) {
        this.name = name;
    }

    public void btnClick(ActionEvent actionEvent) {
       passwordTextField.setText(name);
    }
}
