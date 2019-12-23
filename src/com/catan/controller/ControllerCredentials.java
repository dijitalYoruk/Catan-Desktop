package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.Settings;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ControllerCredentials extends ControllerBase{
    @FXML
    AnchorPane root;

    @FXML
    public void initialize(){
        updateSoundImg();
        root.setStyle(
                "-fx-background-image: url("+ Constants.PATH_BG_CREDENTIALS() +");\n" +
                "-fx-background-size: cover;\n" +
                "-fx-pref-width: 1920;\n" +
                "-fx-pref-height: 1080;"
        );
    }

    @FXML
    public void returnToProgram(ActionEvent actionEvent) {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.PATH_VIEW_PROGRAM)), bundle);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
