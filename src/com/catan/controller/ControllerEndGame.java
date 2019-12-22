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
import java.util.ResourceBundle;

public class ControllerEndGame{

    @FXML
    ResourceBundle resources;
    @FXML
    private JFXButton btnExit;
    @FXML
    private Label labelWon;

    private String playerName;
    private int victoryThreshold;

    public void initialize() {

        System.out.println(resources.getLocale());
        labelWon.setText(playerName+" "+resources.getString("endGameView_PlayerInfoPart1")+" " + victoryThreshold +" "+resources.getString("endGameView_PlayerInfoPart2"));
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
