package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerTrade {

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
    private Label labelActualPlayerBrick;

    @FXML
    private Label labelActualPlayerGrain;

    @FXML
    private Label labelActualPlayerOre;

    @FXML
    private Label labelActualPlayerWool;

    @FXML
    private Label labelActualPlayerLumber;


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

    private int givenLumberNo = 0;
    private int givenWoolNo = 0;
    private int givenOreNo = 0;
    private int givenWheatNo = 0;
    private int givenBrickNo = 0;
    private int requestedLumberNo = 0;
    private int requestedWoolNo = 0;
    private int requestedOreNo = 0;
    private int requestedWheatNo = 0;
    private int requestedBrickNo = 0;
    private Player actualPlayer;

    @FXML
    void initialize(){
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

    public void setActualPlayerAndLabels(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
        labelActualPlayerBrick.setText("x" + actualPlayer.getSourceCards().get(Constants.CARD_LUMBER).size());
        labelActualPlayerWool.setText("x" + actualPlayer.getSourceCards().get(Constants.CARD_WOOL).size());
        labelActualPlayerOre.setText("x" + actualPlayer.getSourceCards().get(Constants.CARD_ORE).size());
        labelActualPlayerGrain.setText("x" + actualPlayer.getSourceCards().get(Constants.CARD_GRAIN).size());
        labelActualPlayerLumber.setText("x" + actualPlayer.getSourceCards().get(Constants.CARD_LUMBER).size());
    }

}
