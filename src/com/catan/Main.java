package com.catan;

import com.catan.Util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/program.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, Constants.PANE_WIDTH, Constants.PANE_HEIGHT));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
