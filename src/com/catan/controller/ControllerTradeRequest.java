package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import com.catan.modal.Trade;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;

public class ControllerTradeRequest {

    @FXML
    private Label labelTitleOfTradeRequest;
    @FXML
    private Label labelOfferedBrick;
    @FXML
    private Label labelOfferedGrain;
    @FXML
    private Label labelOfferedWool;
    @FXML
    private Label labelOfferedLumber;
    @FXML
    private Label labelOfferedOre;
    @FXML
    private Label labelRequestedBrick;
    @FXML
    private Label labelRequestedWheat;
    @FXML
    private Label labelRequestedWool;
    @FXML
    private Label labelRequestedLumber;
    @FXML
    private Label labelRequestedOre;
    @FXML
    private JFXButton acceptOfferButton;
    @FXML
    private JFXButton declineOfferButton;
    @FXML
    private Label outputOfTradeOffer;

    private Player playerActual;
    private Player playerAI;
    private HashMap<String, Integer> requestedRCFromPlayer;
    private HashMap<String, Integer> offeredRCFromPlayer;

    @FXML
    void acceptTradeOffer(ActionEvent event) {
//        if (playerInvitedToTrade.getSourceCards().get(Constants.CARD_WOOL).size() < requestedRCFromPlayer.get(Constants.CARD_WOOL) ||
//                playerInvitedToTrade.getSourceCards().get(Constants.CARD_GRAIN).size() < requestedRCFromPlayer.get(Constants.CARD_GRAIN) ||
//                playerInvitedToTrade.getSourceCards().get(Constants.CARD_LUMBER).size() < requestedRCFromPlayer.get(Constants.CARD_LUMBER) ||
//                playerInvitedToTrade.getSourceCards().get(Constants.CARD_BRICK).size() < requestedRCFromPlayer.get(Constants.CARD_BRICK) ||
//                playerInvitedToTrade.getSourceCards().get(Constants.CARD_ORE).size() < requestedRCFromPlayer.get(Constants.CARD_ORE)) {
//            outputOfTradeOffer.setText("You don't have enough Resource Cards!");
//            outputOfTradeOffer.setOpacity(1);
//        }
//        else {
//            Trade tradeInvitationAcception = new Trade(playerInvitor, playerInvitedToTrade, requestedRCFromPlayer,
//                    offeredRCFromPlayer, false);
////            tradeInvitationAcception.requestTrade();
//            outputOfTradeOffer.setText("Trade Offer Accepted!");
//            outputOfTradeOffer.setOpacity(1);
//        }
    }

    @FXML
    void declineOffer(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void setTradeOfferProperties(Player playerActual, Player playerAI, HashMap<String, Integer> requestedResourceCards, HashMap<String, Integer> offeredResourceCards) {
        this.playerActual = playerActual;
        this.playerAI = playerAI;
        requestedRCFromPlayer = requestedResourceCards;
        offeredRCFromPlayer = offeredResourceCards;

        //Display invitor's name
        labelTitleOfTradeRequest.setText(playerAI.getName() + " offers you a Trade!");

        // Display offered Resource Cards
        labelOfferedBrick.setText("x" + offeredResourceCards.get(Constants.CARD_BRICK));
        labelOfferedLumber.setText("x" + offeredResourceCards.get(Constants.CARD_LUMBER));
        labelOfferedOre.setText("x" + offeredResourceCards.get(Constants.CARD_ORE));
        labelOfferedGrain.setText("x" + offeredResourceCards.get(Constants.CARD_GRAIN));
        labelOfferedWool.setText("x" + offeredResourceCards.get(Constants.CARD_WOOL));

        // Display requested Resource Cards
        labelRequestedBrick.setText("x" + requestedResourceCards.get(Constants.CARD_BRICK));
        labelRequestedLumber.setText("x" + requestedResourceCards.get(Constants.CARD_LUMBER));
        labelRequestedOre.setText("x" + requestedResourceCards.get(Constants.CARD_ORE));
        labelRequestedWheat.setText("x" + requestedResourceCards.get(Constants.CARD_GRAIN));
        labelRequestedWool.setText("x" + requestedResourceCards.get(Constants.CARD_WOOL));
    }
}
