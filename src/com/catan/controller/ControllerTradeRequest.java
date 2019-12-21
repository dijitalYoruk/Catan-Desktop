package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Trade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class ControllerTradeRequest {

    // view components
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
    ResourceBundle resources;

    // properties
    private Trade trade;

    // methods

    @FXML
    void acceptTradeOffer(ActionEvent actionEvent) {
        trade.completeTrade();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void declineOffer(ActionEvent actionEvent) {
        String errorMessage = resources.getString("tradeRequestView_RequestFrom") + trade.getPlayerTrader().getName() +
                resources.getString("tradeRequestView_DeniedBy") + trade.getPlayerToBeTraded().getName() + ".";
        trade.setErrorMessage(errorMessage);
        trade.printTradeDetails();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void setProperties(Trade trade) {
        this.trade = trade;
        // Display invitor's name
        labelTitleOfTradeRequest.setText(trade.getPlayerTrader().getName() +" " + resources.getString("tradeRequestView_TradeExplanation"));

        // Display offered Resource Cards
        labelOfferedBrick.setText(  "x" + trade.getOfferedResourceCards().get(Constants.CARD_BRICK  ));
        labelOfferedLumber.setText( "x" + trade.getOfferedResourceCards().get(Constants.CARD_LUMBER ));
        labelOfferedOre.setText(    "x" + trade.getOfferedResourceCards().get(Constants.CARD_ORE    ));
        labelOfferedGrain.setText(  "x" + trade.getOfferedResourceCards().get(Constants.CARD_GRAIN  ));
        labelOfferedWool.setText(   "x" + trade.getOfferedResourceCards().get(Constants.CARD_WOOL   ));

        // Display requested Resource Cards
        labelRequestedBrick.setText( "x" + trade.getRequestedResourceCards().get(Constants.CARD_BRICK  ));
        labelRequestedLumber.setText("x" + trade.getRequestedResourceCards().get(Constants.CARD_LUMBER ));
        labelRequestedOre.setText(   "x" + trade.getRequestedResourceCards().get(Constants.CARD_ORE    ));
        labelRequestedWheat.setText( "x" + trade.getRequestedResourceCards().get(Constants.CARD_GRAIN  ));
        labelRequestedWool.setText(  "x" + trade.getRequestedResourceCards().get(Constants.CARD_WOOL   ));
    }
}
