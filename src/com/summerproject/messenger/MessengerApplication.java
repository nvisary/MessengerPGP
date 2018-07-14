package com.summerproject.messenger;


import com.summerproject.messenger.ui.InputPasswordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MessengerApplication extends Application{
    private Stage primaryStage;
    private static String propertiesFileName = "settings.prop";
    private String[] args;
    public static void main(String[] args) {
        MessengerApplication.launch(args);
    }

    public void startApplication() {
        this.primaryStage.setTitle("Input password");
        this.primaryStage.setResizable(false);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(InputPasswordController.class.getResource("InputPasswordScreen.fxml"));
            Pane pane = (Pane) loader.load();

            InputPasswordController controller = loader.getController();
            controller.setName("name");

            Scene scene = new Scene(pane);

            this.primaryStage.setScene(scene);
            this.primaryStage.show();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        checkPropFile();
    }

    private void checkPropFile()  {
        InputPasswordController passwordScreen = new InputPasswordController();
        try {
            //bobFrame.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        startApplication();


    }
}
