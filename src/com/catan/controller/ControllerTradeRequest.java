package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.GameLog;
import com.catan.modal.Settings;
import com.catan.modal.Trade;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerTradeRequest {

    // view components
    @FXML
    private ImageView imgOfferBrick;
    @FXML
    private ImageView imgOfferGrain;
    @FXML
    private ImageView imgOfferOre;
    @FXML
    private ImageView imgOfferWool;
    @FXML
    private ImageView imgOfferLumber;
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
    private Label labelTitleOfTradeRequest;
    @FXML
    private ImageView imgRequestBrick;
    @FXML
    private ImageView imgRequestGrain;
    @FXML
    private ImageView imgRequestOre;
    @FXML
    private ImageView imgRequestWool;
    @FXML
    private ImageView imgRequestLumber;
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
    private AnchorPane root;
    @FXML
    ResourceBundle resources;

    // properties
    private Trade trade;

    @FXML
    public void initialize() {
        root.setStyle("-fx-background-image: url("+ Constants.PATH_BG_INVENTION() +");\n" +
                "-fx-background-size: cover;\n");

        ArrayList<ImageView> imgOffers = new ArrayList<>(Arrays.asList(
                imgOfferOre, imgOfferBrick,
                imgOfferLumber, imgOfferGrain,
                imgOfferWool));
        ArrayList<ImageView> imgRequests = new ArrayList<>(Arrays.asList(
                imgRequestOre, imgRequestBrick,
                imgRequestLumber, imgRequestGrain,
                imgRequestWool));
        for (int i = 0; i < Constants.getResourcePaths().size(); i++) {
            String resourcePath = Constants.getResourcePaths().get(i);
            Image image = new Image(resourcePath);
            imgOffers.get(i).setImage(image);
            imgRequests.get(i).setImage(image);
        }
    }

    // methods

    @FXML
    void acceptTradeOffer(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language", new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
        trade.completeTrade();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
        GameLog gameLog = GameLog.getInstance();
        gameLog.addLog(StringUtils.capitalize(trade.getPlayerTrader().getName()) + " " + bundle.getString("gamelogs_hasTradedWith") + " " + StringUtils.capitalize(trade.getPlayerToBeTraded().getName()), trade.getPlayerTrader().getColor());
    }

    @FXML
    void declineOffer(ActionEvent actionEvent) {
        ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language", new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
        String errorMessage = resources.getString("tradeRequestView_RequestFrom") + trade.getPlayerTrader().getName() +
                resources.getString("tradeRequestView_DeniedBy") + trade.getPlayerToBeTraded().getName() + ".";
        trade.setErrorMessage(errorMessage);
        trade.printTradeDetails();
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
        GameLog gameLog = GameLog.getInstance();
        gameLog.addLog(StringUtils.capitalize(trade.getPlayerToBeTraded().getName()) + " " + bundle.getString("gamelogs_hasDeclinedTradedWith") + " " + StringUtils.capitalize(trade.getPlayerTrader().getName()), trade.getPlayerToBeTraded().getColor());
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
