package com.catan.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerProgram {

    @FXML
    public void gotoPlayGame(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void gotoInstructions(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/instructions.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
