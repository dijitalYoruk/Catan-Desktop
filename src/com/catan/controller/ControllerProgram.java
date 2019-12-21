package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerProgram extends ControllerBase {

    @FXML
    private AnchorPane root;

    @FXML
    public void initialize() {
        super.initialize();
        root.setStyle(
                "-fx-background-image: url("+ Constants.PATH_BG_PROGRAM() +");\n" +
                "-fx-background-size: cover;\n" +
                "-fx-pref-width: 1920;\n" +
                "-fx-pref-height: 1080;"
        );
    }

    @FXML
    public void gotoPlayGame(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = fxmlLoader.load(getClass().getResource("../view/gameEntrance.fxml"), bundle);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSettings(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = fxmlLoader.load(getClass().getResource("../view/settings.fxml"), bundle);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void gotoInstructions(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = fxmlLoader.load(getClass().getResource("../view/instructions.fxml"), bundle);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exitGame(){
        System.exit(0);
    }
}
