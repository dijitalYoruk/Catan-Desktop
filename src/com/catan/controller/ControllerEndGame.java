package com.catan.controller;

import com.catan.modal.Player;
import com.catan.modal.Settings;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerEndGame{

    @FXML
    private JFXButton btnExit;
    @FXML
    private Label labelWon;
    private String playerName;
    private int victoryThreshold;
    public void initialize() {
        labelWon.setText(playerName+" has reached the "+ victoryThreshold +" victory points.");

    }
    public void setProperties(String playerName,int victoryThreshold) {
        this.playerName = playerName;
        this.victoryThreshold = victoryThreshold;
        initialize();
    }
    public void returnMain(ActionEvent actionEvent){
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
