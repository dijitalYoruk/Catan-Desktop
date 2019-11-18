package com.catan.controller;

import com.catan.modal.Player;
import com.catan.modal.Trade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.util.Set;

public class ControllerTrade<trade> {

    @FXML
    private Label givenBrick;

    @FXML
    private Label givenWheat;

    @FXML
    private Label givenWool;

    @FXML
    private Label givenLumber;

    @FXML
    private Label givenOre;

    @FXML
    private Label requestedBrick;

    @FXML
    private Label requestedWheat;

    @FXML
    private Label requestedWool;

    @FXML
    private Label requestedLumber;

    @FXML
    private Label requestedOre;

    @FXML
    private Button requestButton;

    @FXML
    private Button clearTradeButton;

    @FXML
    private Label playersBrick;

    @FXML
    private Label playersWheat;

    @FXML
    private Label playersOre;

    @FXML
    private Label playersWool;

    @FXML
    private Label playersLumber;

    @FXML
    private Label componentsBrick;

    @FXML
    private Label componentsWheat;

    @FXML
    private Label componentsOre;

    @FXML
    private Label componentsWool;

    @FXML
    private Label componentsLumber;

    public int givenLumberNo = 0;
    public int givenWoolNo = 0;
    public int givenOreNo = 0;
    public int givenWheatNo = 0;
    public int givenBrickNo = 0;
    public int requestedLumberNo = 0;
    public int requestedWoolNo = 0;
    public int requestedOreNo = 0;
    public int requestedWheatNo = 0;
    public int requestedBrickNo = 0;

    @FXML
    void initalizeTrade(Player player){

        givenLumberNo = 0;
        givenWoolNo = 0;
        givenOreNo = 0;
        givenWheatNo = 0;
        givenBrickNo = 0;
        requestedLumberNo = 0;
        requestedWoolNo = 0;
        requestedOreNo = 0;
        requestedWheatNo = 0;
        requestedBrickNo = 0;

        componentsLumber.setText("x?");
        componentsWool.setText("x?");
        componentsOre.setText("x?");
        componentsWheat.setText("x?");
        componentsBrick.setText("x?");

        givenLumber.setText(givenLumberNo+"");
        givenWool.setText(givenWoolNo+"");
        givenOre.setText(givenOreNo+"");
        givenWheat.setText(givenWheatNo+"");
        givenBrick.setText(givenBrickNo+"");

        requestedLumber.setText(requestedLumberNo+"");
        requestedWool.setText(requestedWoolNo+"");
        requestedOre.setText(requestedOreNo+"");
        requestedWheat.setText(requestedWheatNo+"");
        requestedBrick.setText(requestedBrickNo+"");

        playersLumber.setText(player.getSourceCards().get("lumber").size()+"");
        playersWool.setText(player.getSourceCards().get("wool").size()+"");
        playersOre.setText(player.getSourceCards().get("ore").size()+"");
        playersWheat.setText(player.getSourceCards().get("wheat").size()+"");
        playersBrick.setText(player.getSourceCards().get("brick").size()+"");

    }

    @FXML
    void clearTrade(ActionEvent event) {
        givenLumberNo = 0;
        givenWoolNo = 0;
        givenOreNo = 0;
        givenWheatNo = 0;
        givenBrickNo = 0;
        requestedLumberNo = 0;
        requestedWoolNo = 0;
        requestedOreNo = 0;
        requestedWheatNo = 0;
        requestedBrickNo = 0;

        givenLumber.setText("x"+givenLumberNo);
        givenWool.setText("x"+givenWoolNo);
        givenOre.setText("x"+givenOreNo);
        givenWheat.setText("x"+givenWheatNo);
        givenBrick.setText("x"+givenBrickNo);

        requestedLumber.setText("x"+requestedLumberNo);
        requestedWool.setText("x"+requestedWoolNo);
        requestedOre.setText("x"+requestedOreNo);
        requestedWheat.setText("x"+requestedWheatNo);
        requestedBrick.setText("x"+requestedBrickNo);
    }

    @FXML
    void requestTrade(ActionEvent event) {
        // resolve the trade
    }

    @FXML
    void tradeWith(ActionEvent event) {
       // this.setTradeWith(((MenuItem)actionEvent.getTarget()).getText());
       // themes.setText(settingTemp.getCurrentTheme());

        //Player tradeWith = new Player();
        //Trade trade = new Trade(player, tradeWith);
    }

    @FXML
    private void outputNotPossible() {
        System.out.println("Trade Not Possible");
    }

    @FXML
    void changeTradeWith(ActionEvent event) {

    }

}
