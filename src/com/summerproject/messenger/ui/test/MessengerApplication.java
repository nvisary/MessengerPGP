package com.summerproject.messenger.ui.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MessengerApplication extends javafx.application.Application{

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("PGP Messenger");
        primaryStage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerApplication.class.getResource("ui/MainScreen.fxml"));
        Scene scene = new Scene(loader.load());
        MainScreenController controller = loader.getController();
        ObservableList<String> items = FXCollections.observableArrayList (
                "One", "Two", "Three", "Four");
        controller.setItemsToListView(items);
        controller.listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 ) {
                    System.out.println(controller.listView.getSelectionModel().getSelectedItem());
                }

            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

        PasswordScreenController passwordScreenController = new PasswordScreenController();
        passwordScreenController.display();



    }
}
