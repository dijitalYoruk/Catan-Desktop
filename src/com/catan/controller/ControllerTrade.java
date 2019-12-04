package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Chest;
import com.catan.modal.Player;
import com.catan.modal.SourceCard;
import com.catan.modal.Trade;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ControllerTrade {

    @FXML
    private ImageView p1lumber;

    @FXML
    private ImageView p1wool;

    @FXML
    private ImageView p1ore;

    @FXML
    private ImageView p1grain;

    @FXML
    private ImageView p1brick;

    @FXML
    private ImageView oppBrick;

    @FXML
    private ImageView oppGrain;

    @FXML
    private ImageView oppOre;

    @FXML
    private ImageView oppWool;

    @FXML
    private ImageView oppLumber;

    @FXML
    private MenuButton tradingWith;

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

    @FXML
    private Label labelOutputOfTrade;

    private boolean isPlayerToTradeWithSelected = false;
    private boolean isTradeWithChest = false;
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

    private int playersBrick;
    private int playersGrain;
    private int playersOre;
    private int playersWool;
    private int playersLumber;

    private Player actualPlayer;
    private Player playerToTradeWith;
    private ArrayList<Player> playersList;
    private Trade trade;

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

        labelOutputOfTrade.setOpacity(0);
        setActualPlayerAndLabels(actualPlayer);
    }

    @FXML
    void requestTrade(ActionEvent event) {

        //generate a Trade object
        if (isPlayerToTradeWithSelected)
        {
            Map<String, Integer> requestedResourceCards = new HashMap<String, Integer>();
            Map<String, Integer> offeredResourceCards = new HashMap<String, Integer>();

            requestedResourceCards.put(Constants.CARD_ORE, requestedOreNo);
            requestedResourceCards.put(Constants.CARD_LUMBER, requestedLumberNo);
            requestedResourceCards.put(Constants.CARD_BRICK, requestedBrickNo);
            requestedResourceCards.put(Constants.CARD_GRAIN, requestedWheatNo);
            requestedResourceCards.put(Constants.CARD_WOOL, requestedWoolNo);

            offeredResourceCards.put(Constants.CARD_ORE, givenOreNo);
            offeredResourceCards.put(Constants.CARD_LUMBER, givenLumberNo);
            offeredResourceCards.put(Constants.CARD_BRICK, givenBrickNo);
            offeredResourceCards.put(Constants.CARD_GRAIN, givenWheatNo);
            offeredResourceCards.put(Constants.CARD_WOOL, givenWoolNo);

            trade = new Trade(actualPlayer, playerToTradeWith, requestedResourceCards, offeredResourceCards, isTradeWithChest);
//            trade.requestTrade();
        }
        else {
            outputNotPossible();
        }
        //display outcome of trade request
        if (trade.isTradePossible()) {
            labelOutputOfTrade.setText("Trade is Successful!");
            labelOutputOfTrade.setOpacity(1);
        }
        else {
            labelOutputOfTrade.setText("Trade Didn't Happen!");
            labelOutputOfTrade.setOpacity(1);
        }

    }

    @FXML
    void selectTradeWith(ActionEvent event) {

        String traderStr = ((MenuItem)event.getTarget()).getText();
        System.out.println(traderStr);
        tradingWith.setText(traderStr);
        if (traderStr.equals("Player 2")){
            playerToTradeWith = playersList.get(1);
            isPlayerToTradeWithSelected = true;
            isTradeWithChest = false;
        }
        else if (traderStr.equals("Player 3")){
            playerToTradeWith = playersList.get(2);
            isPlayerToTradeWithSelected = true;
            isTradeWithChest = false;
        }
        else if(traderStr.equals("Player 4")){
            playerToTradeWith = playersList.get(3);
            isPlayerToTradeWithSelected = true;
            isTradeWithChest = false;
        }
        else if (traderStr.equals("Chest")){
            isTradeWithChest = true;
            isPlayerToTradeWithSelected = true;
        }
    }

    @FXML
    private void outputNotPossible() {
        System.out.println("Trade Not Possible");
    }

    @FXML
    void changeTradeWith(ActionEvent event) {

    }

    public void setActualPlayerAndLabels(Player actualPlayer) {
        playersBrick = actualPlayer.getSourceCards().get(Constants.CARD_BRICK).size();
        playersGrain = actualPlayer.getSourceCards().get(Constants.CARD_GRAIN).size();
        playersOre = actualPlayer.getSourceCards().get(Constants.CARD_ORE).size();
        playersWool = actualPlayer.getSourceCards().get(Constants.CARD_WOOL).size();
        playersLumber = actualPlayer.getSourceCards().get(Constants.CARD_LUMBER).size();

        this.actualPlayer = actualPlayer;
        labelActualPlayerBrick.setText("x" + playersBrick);
        labelActualPlayerWool.setText("x" + playersWool);
        labelActualPlayerOre.setText("x" + playersOre);
        labelActualPlayerGrain.setText("x" + playersGrain);
        labelActualPlayerLumber.setText("x" + playersLumber);

    }

    public void passPlayersAL(ArrayList<Player> players){
        playersList = players;
    }


    public void selectResourcesToOffer(javafx.scene.input.MouseEvent mouseEvent) {
        if (((ImageView) mouseEvent.getSource()).getId().equals("p1brick") && playersBrick > 0) {
            playersBrick--;
            givenBrickNo++;
            labelActualPlayerBrick.setText("x" + playersBrick);
            givenBrick.setText("x" + givenBrickNo);
        } else if (((ImageView) mouseEvent.getSource()).getId().equals("p1ore") && playersOre > 0) {
            playersOre--;
            givenOreNo++;
            labelActualPlayerOre.setText("x" + playersOre);
            givenOre.setText("x" + givenOreNo);
        } else if (((ImageView) mouseEvent.getSource()).getId().equals("p1grain") && playersGrain > 0) {
            playersGrain--;
            givenWheatNo++;
            labelActualPlayerGrain.setText("x" + playersGrain);
            givenWheat.setText("x" + givenWheatNo);
        } else if (((ImageView) mouseEvent.getSource()).getId().equals("p1wool") && playersWool > 0) {
            playersWool--;
            givenWoolNo++;
            labelActualPlayerWool.setText("x" + playersWool);
            givenWool.setText("x" + givenWoolNo);
        } else if (((ImageView) mouseEvent.getSource()).getId().equals("p1lumber") && playersLumber > 0) {
            playersLumber--;
            givenLumberNo++;
            labelActualPlayerLumber.setText("x" + playersLumber);
            givenLumber.setText("x" + givenLumberNo);
        }
    }

    public void selectResourcesToRequest(MouseEvent mouseEvent) {
        if (((ImageView)mouseEvent.getSource()).getId().equals("oppBrick")){
            requestedBrickNo++;
            requestedBrick.setText("x" + requestedBrickNo);
        }else if (((ImageView)mouseEvent.getSource()).getId().equals("oppOre")){
            requestedOreNo++;
            requestedOre.setText("x" + requestedOreNo);
        }else if (((ImageView)mouseEvent.getSource()).getId().equals("oppGrain")){
            requestedWheatNo++;
            requestedWheat.setText("x" + requestedWheatNo);
        }else if (((ImageView)mouseEvent.getSource()).getId().equals("oppWool")){
            requestedWoolNo++;
            requestedWool.setText("x" + requestedWoolNo);
        }else if (((ImageView)mouseEvent.getSource()).getId().equals("oppLumber")){
            requestedLumberNo++;
            requestedLumber.setText("x" + requestedLumberNo);
        }
    }
}
