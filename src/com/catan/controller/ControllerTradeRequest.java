package com.catan.controller;

        import com.catan.Util.Constants;
        import com.catan.modal.Player;
        import com.catan.modal.Trade;
        import com.jfoenix.controls.JFXButton;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Label;

        import java.util.Map;

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

    private Player playerInvitedToTrade;
    private Player playerInvitor;
    private Map<String, Integer> requestedRCFromPlayer;
    private Map<String, Integer> offeredRCFromPlayer;

    @FXML
    void acceptTradeOffer(ActionEvent event) {
        Trade tradeInvitationAcception = new Trade(playerInvitor, playerInvitedToTrade, requestedRCFromPlayer,
                                offeredRCFromPlayer, false);
        tradeInvitationAcception.requestTrade();
        outputOfTradeOffer.setText("Trade Offer Accepted!");
        outputOfTradeOffer.setOpacity(1);
    }

    @FXML
    void declineOffer(ActionEvent event) {
        outputOfTradeOffer.setText("Trade Offer Denied!");
        outputOfTradeOffer.setOpacity(1);
    }

    public void setActualPlayerAndLabels(Player playerInvited, Player playerAI, Map<String, Integer> requestedResourceCards, Map<String, Integer> offeredResourceCards) {
        playerInvitedToTrade = playerInvited;
        playerInvitor = playerAI;
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
