package com.catan;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.MusicPlayer;
import com.catan.modal.Settings;
import javafx.application.Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language", new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
        //Parent root = fxmlLoader.load(getClass().getResource("view/program.fxml"), bundle);
        Parent root = fxmlLoader.load(getClass().getResource("view/intro.fxml"), bundle);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, Constants.PANE_WIDTH, Constants.PANE_HEIGHT));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
