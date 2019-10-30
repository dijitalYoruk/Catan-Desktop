package com.catan.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class ControllerSettings {
    @FXML
    JFXTextArea largestArmyTh;
    @FXML
    JFXTextArea longestRoadTh;
    @FXML
    JFXTextArea victoryPointTh;
    @FXML
    public void returnToProgram(MouseEvent actionEvent){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/program.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void returnToSettings(MouseEvent actionEvent){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToThresholds(ActionEvent actionEvent){
        System.out.println("c1");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/thresholds.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToThemes(ActionEvent actionEvent){
        System.out.println("c1");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/themes.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToMusic(ActionEvent actionEvent){
        System.out.println("c1");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/changeMusic.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitTheme(ActionEvent actionEvent){

    }
    @FXML
    public void submitMusic(ActionEvent actionEvent){

    }
    @FXML
    public void submitSettings(ActionEvent actionEvent)  {
        try {

            FileWriter writer = new FileWriter(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt", true);
            writer.write(victoryPointTh.getText() + "\n");
            writer.write(largestArmyTh.getText() + "\n");
            writer.write(longestRoadTh.getText() );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
