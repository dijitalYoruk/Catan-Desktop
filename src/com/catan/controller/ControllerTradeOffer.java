package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import com.catan.modal.Trade;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerTradeOffer {

    @FXML
    private ImageView imgOfferLumber;
    @FXML
    private ImageView imgOfferWool;
    @FXML
    private ImageView imgOfferOre;
    @FXML
    private ImageView imgOfferGrain;
    @FXML
    private ImageView imgOfferBrick;
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
    private ComboBox<String> selectorTrade;
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
    private Label labelRequestedGrain;
    @FXML
    private Label labelRequestedWool;
    @FXML
    private Label labelRequestedLumber;
    @FXML
    private Label labelRequestedOre;
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
    private Label labelOutputOfTrade;
    @FXML
    private AnchorPane root;
    @FXML
    ResourceBundle resources;
    // properties
    private HashMap<String, Integer> offeredResources;
    private HashMap<String, Integer> requestedResources;
    private HashMap<String, Integer> actualPlayerResources;
    private boolean isTradeWithChest = false;
    private ArrayList<Player> allPlayers;
    private ArrayList<Label> labelsRequests;
    private ArrayList<Label> labelsOffer;
    private ArrayList<Label> labelsActualPlayer;
    private ArrayList<ImageView> imgOffers;
    private ArrayList<ImageView> imgRequests;
    private Player actualPlayer;
    private Player playerToBeTraded;


    @FXML
    public void initialize() {
        requestedResources = new HashMap<>();
        actualPlayerResources = new HashMap<>();
        offeredResources = new HashMap<>();

        labelsRequests = new ArrayList<>(Arrays.asList(
                labelRequestedOre, labelRequestedBrick,
                labelRequestedLumber, labelRequestedGrain,
                labelRequestedWool));

        labelsOffer = new ArrayList<>(Arrays.asList(
                labelOfferedOre, labelOfferedBrick,
                labelOfferedLumber, labelOfferedGrain,
                labelOfferedWool));

        labelsActualPlayer = new ArrayList<>(Arrays.asList(
                labelActualPlayerOre, labelActualPlayerBrick,
                labelActualPlayerLumber, labelActualPlayerGrain,
                labelActualPlayerWool));

        imgOffers = new ArrayList<>(Arrays.asList(
                imgOfferOre, imgOfferBrick,
                imgOfferLumber, imgOfferGrain,
                imgOfferWool));

        imgRequests = new ArrayList<>(Arrays.asList(
                imgRequestOre, imgRequestBrick,
                imgRequestLumber, imgRequestGrain,
                imgRequestWool));

        for (String resourceName: Constants.resourceNames) {
            offeredResources.put(resourceName, 0);
            requestedResources.put(resourceName, 0);
        }
        selectorTrade.setStyle("-fx-font: 20px \"Book " +
                "Antiqua\"; -fx-background-color: orange");
    }

    @FXML
    void clearTrade(ActionEvent event) {
        for (String resourceName: Constants.resourceNames) {
            offeredResources.put(resourceName, 0);
            requestedResources.put(resourceName, 0);
        }
        for (Label label: labelsRequests) { label.setText("x0"); }
        for (Label label: labelsOffer)    { label.setText("x0"); }
        setActualPlayerAndLabels(actualPlayer);
    }

    public void setActualPlayerAndLabels(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
        for (int i = 0; i < Constants.resourceNames.size(); i++) {
            String resourceName = Constants.resourceNames.get(i);
            int resourceCount = actualPlayer.getSourceCards().get(resourceName).size();
            actualPlayerResources.put(resourceName, resourceCount);
            labelsActualPlayer.get(i).setText("x" + actualPlayerResources.get(resourceName));
        }
    }

    @FXML
    void requestTrade(ActionEvent event) {
        if (isTradeWithChest) {
            Trade trade = new Trade(actualPlayer, null, requestedResources, offeredResources, true);
            terminateTrade(trade);
        } else {
            Trade trade = new Trade(actualPlayer, playerToBeTraded, requestedResources, offeredResources, false);
            terminateTrade(trade);
        }
    }

    private void terminateTrade(Trade trade) {
        if (trade.isTradeCompleted()) {
            labelOutputOfTrade.setText(resources.getString("tradeOfferView_TradeSuccesful"));
            labelOutputOfTrade.setOpacity(1);
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    closeDialog(null);
                });
            }).start();
        } else {
            String errorMessage = trade.getErrorMessage();
            if (!errorMessage.isEmpty()) {
                labelOutputOfTrade.setText(errorMessage);
                labelOutputOfTrade.setOpacity(1);
            }
        }
    }

    @FXML
    void selectPlayerToBeTraded(ActionEvent event) {
        clearTrade(null);
        String nameToBeTraded = selectorTrade.getValue();
        playerToBeTraded = null;
        if (!nameToBeTraded.equals("Chest")) {
            for (Player player: allPlayers) {
                if (player.getName().equals(nameToBeTraded)) {
                    playerToBeTraded = player;
                }
            }
        } else {
            isTradeWithChest = true;
        }
    }

    public void setAllPlayers(ArrayList<Player> allPlayers){
        this.allPlayers = new ArrayList<>();
        for (Player player : allPlayers) {
            if (player != actualPlayer) {
                this.allPlayers.add(player);
                selectorTrade.getItems().add(player.getName());
            }
        }
        selectorTrade.getItems().add("Chest");
        selectorTrade.setPromptText(this.allPlayers.get(0).getName());
        playerToBeTraded = this.allPlayers.get(0);
    }

    @FXML
    public void offerResource(MouseEvent mouseEvent) {
        if (isTradeWithChest) {
            setActualPlayerAndLabels(actualPlayer);

            for (int i = 0; i < Constants.resourceNames.size(); i++) {
                String resourceName = Constants.resourceNames.get(i);
                labelsOffer.get(i).setText("x0");
                offeredResources.put(resourceName, 0);
            }

            for (int i = 0; i < Constants.resourceNames.size(); i++) {
                String resourceName = Constants.resourceNames.get(i);
                if (mouseEvent.getSource() == imgOffers.get(i) && actualPlayerResources.get(resourceName) >= 4){
                    labelsOffer.get(i).setText("x4");
                    offeredResources.put(resourceName, 4);
                    int playerResourceCount = actualPlayerResources.get(resourceName) - 4;
                    actualPlayerResources.put(resourceName, playerResourceCount);
                    labelsActualPlayer.get(i).setText("x" + playerResourceCount);
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < Constants.resourceNames.size(); i++) {
                String resourceName = Constants.resourceNames.get(i);
                if (mouseEvent.getSource() == imgOffers.get(i) && actualPlayerResources.get(resourceName) > 0){
                    int offeredResourceCount = offeredResources.get(resourceName) + 1;
                    offeredResources.put(resourceName, offeredResourceCount);
                    int playerResourceCount = actualPlayerResources.get(resourceName) - 1;
                    actualPlayerResources.put(resourceName, playerResourceCount);
                    labelsOffer.get(i).setText("x" + offeredResourceCount);
                    labelsActualPlayer.get(i).setText("x" + playerResourceCount);
                    break;
                }
            }
        }
    }

    @FXML
    public void requestResource(MouseEvent mouseEvent) {
        if (isTradeWithChest) {
            for (int i = 0; i < Constants.resourceNames.size(); i++) {
                String resourceName = Constants.resourceNames.get(i);
                labelsRequests.get(i).setText("x0");
                requestedResources.put(resourceName, 0);
            }
        }
        for (int i = 0; i < Constants.resourceNames.size(); i++) {
            String resourceName = Constants.resourceNames.get(i);
            if (mouseEvent.getSource() == imgRequests.get(i)){
                int requestedResourceCount = requestedResources.get(resourceName) + 1;
                requestedResources.put(resourceName, requestedResourceCount);
                labelsRequests.get(i).setText("x" + requestedResourceCount);
                break;
            }
        }
    }

    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        Stage window = (Stage) root.getScene().getWindow();
        window.close();
    }
}