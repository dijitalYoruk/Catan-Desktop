package com.catan.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ControllerEndGame{

    @FXML
    private AnchorPane root;
    @FXML
    ResourceBundle resources;
    @FXML
    private JFXButton btnExit;
    @FXML
    private Label labelWon;

    // properties
    private String playerName;
    private int victoryThreshold;

    public void initialize() {
        labelWon.setText(playerName+" "+resources.getString("endGameView_PlayerInfoPart1")+" " +
                victoryThreshold +" "+resources.getString("endGameView_PlayerInfoPart2"));
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
