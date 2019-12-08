package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import com.catan.modal.Trade;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

    // properties
    private HashMap<String, Integer> offeredResources;
    private HashMap<String, Integer> requestedResources;
    private HashMap<String, Integer> actualPlayerResources;
    private ArrayList<Player> allPlayers;
    private boolean isTradeWithChest = false;
    private Player actualPlayer;
    private Player playerToBeTraded;

    private ArrayList<String> resourceNames = new ArrayList<>(
            Arrays.asList(Constants.CARD_ORE, Constants.CARD_BRICK, Constants.CARD_LUMBER,
                    Constants.CARD_GRAIN, Constants.CARD_WOOL)
    );

    @FXML
    public void initialize() {
        requestedResources = new HashMap<>();
        actualPlayerResources = new HashMap<>();
        offeredResources = new HashMap<>();

        for (String resourceName: resourceNames) {
            offeredResources.put(resourceName, 0);
            requestedResources.put(resourceName, 0);
        }
        selectorTrade.setStyle("-fx-font: 20px \"Book " +
                "Antiqua\"; -fx-background-color: orange");
    }

    @FXML
    void clearTrade(ActionEvent event) {
        for (String resourceName: resourceNames) {
            offeredResources.put(resourceName, 0);
            requestedResources.put(resourceName, 0);
        }
        labelOfferedBrick.setText("x0");
        labelOfferedGrain.setText("x0");
        labelOfferedOre.setText("x0");
        labelOfferedWool.setText("x0");
        labelOfferedLumber.setText("x0");
        labelRequestedBrick.setText("x0");
        labelRequestedGrain.setText("x0");
        labelRequestedOre.setText("x0");
        labelRequestedWool.setText("x0");
        labelRequestedLumber.setText("x0");
        setActualPlayerAndLabels(actualPlayer);
    }

    public void setActualPlayerAndLabels(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
        for (String resourceName: resourceNames) {
            int resourceCount = actualPlayer.getSourceCards().get(resourceName).size();
            actualPlayerResources.put(resourceName, resourceCount);
        }
        labelActualPlayerLumber.setText("x" + actualPlayerResources.get(Constants.CARD_LUMBER));
        labelActualPlayerBrick.setText("x" + actualPlayerResources.get(Constants.CARD_BRICK));
        labelActualPlayerGrain.setText("x" + actualPlayerResources.get(Constants.CARD_GRAIN));
        labelActualPlayerOre.setText("x" + actualPlayerResources.get(Constants.CARD_ORE));
        labelActualPlayerWool.setText("x" + actualPlayerResources.get(Constants.CARD_WOOL));
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
            labelOutputOfTrade.setText("Trade is successful.");
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
            labelOfferedBrick.setText("x0");
            labelOfferedGrain.setText("x0");
            labelOfferedOre.setText("x0");
            labelOfferedWool.setText("x0");
            labelOfferedLumber.setText("x0");
            setActualPlayerAndLabels(actualPlayer);
            for (String resourceName: resourceNames) {
                offeredResources.put(resourceName, 0);
            }

            if (mouseEvent.getSource() == imgOfferBrick && actualPlayerResources.get(Constants.CARD_BRICK) >= 4){
                labelOfferedBrick.setText("x4");
                offeredResources.put(Constants.CARD_BRICK, 4);
                int playerBrickCount = actualPlayerResources.get(Constants.CARD_BRICK) - 4;
                actualPlayerResources.put(Constants.CARD_BRICK, playerBrickCount);
                labelActualPlayerBrick.setText("x" + playerBrickCount);
            }
            else if (mouseEvent.getSource() == imgOfferOre && actualPlayerResources.get(Constants.CARD_ORE) >= 4){
                labelOfferedOre.setText("x4");
                offeredResources.put(Constants.CARD_ORE, 4);
                int playerOreCount = actualPlayerResources.get(Constants.CARD_ORE) - 4;
                actualPlayerResources.put(Constants.CARD_ORE, playerOreCount);
                labelActualPlayerOre.setText("x" + playerOreCount);
            }
            else if (mouseEvent.getSource() == imgOfferGrain && actualPlayerResources.get(Constants.CARD_GRAIN) >= 4){
                labelOfferedGrain.setText("x4");
                offeredResources.put(Constants.CARD_GRAIN, 4);
                int playerGrainCount = actualPlayerResources.get(Constants.CARD_GRAIN) - 4;
                actualPlayerResources.put(Constants.CARD_GRAIN, playerGrainCount);
                labelActualPlayerGrain.setText("x" + playerGrainCount);
            }
            else if (mouseEvent.getSource() == imgOfferLumber && actualPlayerResources.get(Constants.CARD_LUMBER) >= 4){
                labelOfferedLumber.setText("x4");
                offeredResources.put(Constants.CARD_LUMBER, 4);
                int playerLumberCount = actualPlayerResources.get(Constants.CARD_LUMBER) - 4;
                actualPlayerResources.put(Constants.CARD_LUMBER, playerLumberCount);
                labelActualPlayerLumber.setText("x" + playerLumberCount);
            }
            else if (mouseEvent.getSource() == imgOfferWool && actualPlayerResources.get(Constants.CARD_WOOL) >= 4){
                labelOfferedWool.setText("x4");
                offeredResources.put(Constants.CARD_WOOL, 4);
                int playerWoolCount = actualPlayerResources.get(Constants.CARD_WOOL) - 4;
                actualPlayerResources.put(Constants.CARD_WOOL, playerWoolCount);
                labelActualPlayerWool.setText("x" + playerWoolCount);
            }

        } else {
            if (mouseEvent.getSource() == imgOfferBrick && actualPlayerResources.get(Constants.CARD_BRICK) > 0){
                int offeredBrickCount = offeredResources.get(Constants.CARD_BRICK) + 1;
                offeredResources.put(Constants.CARD_BRICK, offeredBrickCount);
                int playerBrickCount = actualPlayerResources.get(Constants.CARD_BRICK) - 1;
                actualPlayerResources.put(Constants.CARD_BRICK, playerBrickCount);
                labelOfferedBrick.setText("x" + offeredBrickCount);
                labelActualPlayerBrick.setText("x" + playerBrickCount);
            }
            else if (mouseEvent.getSource() == imgOfferOre && actualPlayerResources.get(Constants.CARD_ORE) > 0){
                int offeredOreCount = offeredResources.get(Constants.CARD_ORE) + 1;
                offeredResources.put(Constants.CARD_ORE, offeredOreCount);
                int playerOreCount = actualPlayerResources.get(Constants.CARD_ORE) - 1;
                actualPlayerResources.put(Constants.CARD_ORE, playerOreCount);
                labelOfferedOre.setText("x" + offeredOreCount);
                labelActualPlayerOre.setText("x" + playerOreCount);
            }
            else if (mouseEvent.getSource() == imgOfferGrain && actualPlayerResources.get(Constants.CARD_GRAIN) > 0){
                int offeredGrainCount = offeredResources.get(Constants.CARD_GRAIN) + 1;
                offeredResources.put(Constants.CARD_GRAIN, offeredGrainCount);
                int playerGrainCount = actualPlayerResources.get(Constants.CARD_GRAIN) - 1;
                actualPlayerResources.put(Constants.CARD_GRAIN, playerGrainCount);
                labelOfferedGrain.setText("x" + offeredGrainCount);
                labelActualPlayerGrain.setText("x" + playerGrainCount);
            }
            else if (mouseEvent.getSource() == imgOfferLumber && actualPlayerResources.get(Constants.CARD_LUMBER) > 0){
                int offeredLumberCount = offeredResources.get(Constants.CARD_LUMBER) + 1;
                offeredResources.put(Constants.CARD_LUMBER, offeredLumberCount);
                int playerLumberCount = actualPlayerResources.get(Constants.CARD_LUMBER) - 1;
                actualPlayerResources.put(Constants.CARD_LUMBER, playerLumberCount);
                labelOfferedLumber.setText("x" + offeredLumberCount);
                labelActualPlayerLumber.setText("x" + playerLumberCount);
            }
            else if (mouseEvent.getSource() == imgOfferWool && actualPlayerResources.get(Constants.CARD_WOOL) > 0){
                int offeredWoolCount = offeredResources.get(Constants.CARD_WOOL) + 1;
                offeredResources.put(Constants.CARD_WOOL, offeredWoolCount);
                int playerWoolCount = actualPlayerResources.get(Constants.CARD_WOOL) - 1;
                actualPlayerResources.put(Constants.CARD_WOOL, playerWoolCount);
                labelOfferedWool.setText("x" + offeredWoolCount);
                labelActualPlayerWool.setText("x" + playerWoolCount);
            }
        }
    }

    @FXML
    public void requestResource(MouseEvent mouseEvent) {
        if (isTradeWithChest) {
            labelRequestedBrick.setText("x0");
            labelRequestedGrain.setText("x0");
            labelRequestedOre.setText("x0");
            labelRequestedWool.setText("x0");
            labelRequestedLumber.setText("x0");
            for (String resourceName: resourceNames) {
                requestedResources.put(resourceName, 0);
            }
        }

        if (mouseEvent.getSource() == imgRequestBrick){
            int requestedBrickCount = requestedResources.get(Constants.CARD_BRICK) + 1;
            requestedResources.put(Constants.CARD_BRICK, requestedBrickCount);
            labelRequestedBrick.setText("x" + requestedBrickCount);
        }
        else if (mouseEvent.getSource() == imgRequestOre){
            int requestedOreCount = requestedResources.get(Constants.CARD_ORE) + 1;
            requestedResources.put(Constants.CARD_ORE, requestedOreCount);
            labelRequestedOre.setText("x" + requestedOreCount);
        }
        else if (mouseEvent.getSource() == imgRequestGrain){
            int requestedGrainCount = requestedResources.get(Constants.CARD_GRAIN) + 1;
            requestedResources.put(Constants.CARD_GRAIN, requestedGrainCount);
            labelRequestedGrain.setText("x" + requestedGrainCount);
        }
        else if (mouseEvent.getSource() == imgRequestLumber){
            int requestedLumberCount = requestedResources.get(Constants.CARD_LUMBER) + 1;
            requestedResources.put(Constants.CARD_LUMBER, requestedLumberCount);
            labelRequestedLumber.setText("x" + requestedLumberCount);
        }
        else if (mouseEvent.getSource() == imgRequestWool){
            int requestedWoolCount = requestedResources.get(Constants.CARD_WOOL) + 1;
            requestedResources.put(Constants.CARD_WOOL, requestedWoolCount);
            labelRequestedWool.setText("x" + requestedWoolCount);
        }
    }

    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        Stage window = (Stage) root.getScene().getWindow();
        window.close();
    }
}
