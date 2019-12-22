package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.*;
import com.catan.modal.*;
import com.catan.Util.UTF8Control;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Locale;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerGame extends ControllerBaseGame implements InterfaceMakeConstruction, InterfaceDevelopmentCard, InterfaceMakeTrade {

    // properties
    private DevelopmentCard developmentCardExchangeProfit;
    private DevelopmentCard developmentCardDestroyRoad;
    private DevelopmentCard developmentCardInvention;
    private boolean constructionUnselect = true;
    private String selectedConstruction = "";
    private Vertex roadVertex1 = null;
    private Vertex roadVertex2 = null;
    private boolean isStepInitial = true;
    private boolean isStepActual = false;
    private boolean isConstructionBuild = false;
    private boolean isRoadBuild = false;
    private int initialStepCount = 0;
    private Settlement tempSettlement;
    private int playerTurn = 0;
    private GameLog gameLog;
    private int gameLogIterator = 0;
    private int noOfRound = 1;
    private FlowPane gameLogsFlowPane;
    private Road tempRoad;
    private Chest chest;

    //Language Resource
    @FXML
    private ResourceBundle resources;

    @Override
    public void setDevelopmentCardInvention(DevelopmentCard developmentCardInvention) {
        this.developmentCardInvention = developmentCardInvention;
    }

    public void init(ArrayList<Player> players) {
        updateSoundImg();
        setPlayers(players);
        developmentCardExchangeProfit = null;
        developmentCardDestroyRoad = null;
        developmentCardInvention = null;
        selectConstructionInitial(imgRoad);
        currentPlayer = getPlayers().get(0);
        activateAllVertices();
        gameLogsFlowPane = (FlowPane)gameLogsScrollPane.getContent();
        gameLog = GameLog.getInstance();
        chest = new Chest(getPlayers(), getSettings());

        for (Player player: getPlayers()) {
            if (player instanceof PlayerActual) {
                playerActual = player;
            }
        }
        endTurn(null);
    }

    @FXML
    public void selectConstruction(MouseEvent mouseEvent) {
        Circle rectangle = (Circle) mouseEvent.getSource();
        if (isStepInitial) {
            selectConstructionInitial(rectangle);
        } else {
            selectConstructionActual(rectangle);
        }
    }

    @FXML
    public void unselectConstructions(MouseEvent mouseEvent) {
        if ((isStepActual && constructionUnselect) ||
            (isStepInitial && isRoadBuild && isConstructionBuild)) {
            imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            selectedConstruction = "";
            deActivateAllVertices();
        }
        if (constructionUnselect) {
            refreshRoadSelectionVertices();
        }
        constructionUnselect = true;
    }

    @FXML
    public void blurVertex(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS && circle.getFill() != Constants.COLOR_ROAD_SELECTION_VERTEX) {
            circle.setFill(Constants.COLOR_BLUR_VERTEX);
        }
    }

    @FXML
    public void blendVertex(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        Vertex vertex = getCorrespondingVertex(circle);
        if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS && vertex.isActive()) {
            circle.setFill(Constants.COLOR_BLEND_VERTEX);
        }
    }

    @FXML
    void trade(ActionEvent event) {
        if (isStepActual) {
            openDialog(Constants.PATH_VIEW_TRADE_OFFER, "Trade" , null, null);
            updateCardsOfActualPlayerInView();
            updateGameLogsInView();
        }
    }

    @FXML
    public void playDevelopmentCard(ActionEvent event) {
        if (!isStepActual) return;
        if (currentPlayer.getTotalDevelopmentCards() > 0) {
            openDialog(Constants.PATH_VIEW_PLAY_DEVELOPMENT_CARD,
                    "Play Development Card", null, null);
        } else {
            displayWarning("No Dev Card");
        }
        updateCardsOfActualPlayerInView();
        updateGameLogsInView();
    }
    

    @Override
    public void playDevelopmentCard(DevelopmentCard developmentCard) {
        if (!isStepActual) {
            displayWarning("Wrong time for dev card");
            return;
        }

        if (developmentCard != null) {
            developmentCard.performDevelopmentCardFeatures(currentPlayer,
                    getPlayers(), terrainHexes, this);
            if (currentPlayer == playerActual) {
                if (developmentCard.getName().equals(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE)) {
                    developmentCardExchangeProfit = developmentCard;
                } else if (developmentCard.getName().equals(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION)) {
                    developmentCardDestroyRoad = developmentCard;
                }
            }
        }
    }

    @FXML
    public void destroyRoad(MouseEvent mouseEvent) {
        if (developmentCardDestroyRoad == null) return;
        Line line = (Line)mouseEvent.getSource();
        Road road = getCorrespondingRoad(line);
        developmentCardDestroyRoad.destroyRoad(road,
                currentPlayer, getPlayers());
    }

    @FXML
    public void exchangeTurnProfit(MouseEvent mouseEvent) {
        if (developmentCardExchangeProfit == null) return;
        Circle circle = (Circle) mouseEvent.getSource();
        developmentCardExchangeProfit.exchangeTurnProfit(circle, currentPlayer);
        if (developmentCardExchangeProfit.isDevelopmentCardUsed()) {
            developmentCardExchangeProfit = null;
        }
    }

    @Override
    public void openDialog(String viewPath, String title, DevelopmentCard developmentCard, Trade trade) {
        try {
            Dialog dialog = new Dialog<>();
            dialog.initOwner(root.getScene().getWindow());
            dialog.setTitle(title);
            FXMLLoader fxmlLoader = new FXMLLoader();
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(viewPath));
            fxmlLoader.setResources(bundle);
            dialog.getDialogPane().setContent(fxmlLoader.load());
            
            // Monopoly development card dialog.
            if (viewPath.equals(Constants.PATH_VIEW_DEV_MONOPOL_CARD)) {
                ControllerDevMonopol monopolController = fxmlLoader.getController();
                monopolController.setProperties(currentPlayer, developmentCard, getPlayers());
            }

            // Invention development card dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_DEV_INVENTION_CARD)) {
                ControllerDevInvention inventionController = fxmlLoader.getController();
                inventionController.setProperties(currentPlayer, developmentCard);
            }

            // Trade offer dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_TRADE_OFFER)) {
                ControllerTradeOffer tradeController = fxmlLoader.getController();
                tradeController.setActualPlayerAndLabels(playerActual);
                tradeController.setAllPlayers(getPlayers());
            }

            // Incoming Trade Request dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_TRADE_REQUEST) && trade.isTradePossible()) {
                ControllerTradeRequest tradeRequestController = fxmlLoader.getController();
                tradeRequestController.setProperties(trade);
            }

            // Thief punishment dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_PUNISHMENT)) {
                ControllerThiefPunishment controller = fxmlLoader.getController();
                controller.setPlayer(playerActual);
            }

            // Play development card dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_PLAY_DEVELOPMENT_CARD)) {
                ControllerPlayDevelopmentCard controller = fxmlLoader.getController();
                controller.setProperties(currentPlayer, this);
            }

            else if (viewPath.equals(Constants.PATH_VIEW_ENDGAME)) {
                ControllerEndGame controller = fxmlLoader.getController();
                controller.setProperties(currentPlayer.getName(),getSettings().getVictoryThreshold());
            }

            dialog.showAndWait();
            if (developmentCardInvention != null) {
                DevelopmentCard card = developmentCardInvention;
                developmentCardInvention = null;
                playDevelopmentCard(card);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Override
    public void buyDevelopmentCard(ActionEvent event) {
        if (isStepActual) {
            boolean hasEnoughResources = currentPlayer.hasEnoughResources(Constants.DEVELOPMENT_CARD);
            if (hasEnoughResources) {
                currentPlayer.buyDevelopmentCard(chest);
                updateGameLogsInView();
                updateCardsOfActualPlayerInView();
                if (currentPlayer instanceof PlayerActual) {
                    displaySuccess("bought dev card");
                }
            } else {
                displayWarning("Poor to buy dev card");
            }
        }
    }

    private void rollDie() {
        if (isStepActual) {
            die.rollDie();
            Image img = new Image(Constants.PATH_DIES().get(die.getDice1()));
            getImgDie1().setFill(new ImagePattern(img));
            Image img2 = new Image(Constants.PATH_DIES().get(die.getDice2()));
            getImgDie2().setFill(new ImagePattern(img2));
        }
    }

    @FXML
    public void makeConstruction(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        if (isStepInitial) {
            makeConstructionInitial(getCorrespondingVertex(circle));
        } else if (isStepActual) {
            makeConstructionActual(circle);
        }
    }

    @FXML
    public void endTurn(ActionEvent actionEvent) {
        if(isStepInitial) {
            initialTurn();
        } else if (isStepActual) {
            actualTurn();
            gameLog.addLog("Round "  + noOfRound + " has ended.", "gray");
            noOfRound++;
        }
        updateGameLogsInView();
        updateCardsOfActualPlayerInView();
    }

    private void updateCardsOfActualPlayerInView() {
        updateNumbersOfCardsInPanes();
        updateOrderOfCardPanes();
        Player longestRoadOwner = chest.getLongestRoadOwnerPlayer();
        Player strongestArmyOwner = chest.getStrongestArmyOwnerPlayer();
        if (longestRoadOwner != null) {
            longestRoadOwnerLabel.setText("  " + StringUtils.capitalize(longestRoadOwner.getName()) + "  ");
            longestRoadOwnerLabel.setStyle("-fx-background-color:" + longestRoadOwner.getColor() + ";" +
                    "-fx-text-fill: white;" + "-fx-font-size: 18px;");
        }
        if (strongestArmyOwner != null) {
            strongestArmyOwnerLabel.setText("  " + StringUtils.capitalize(strongestArmyOwner.getName()) + "  ");
            strongestArmyOwnerLabel.setStyle("-fx-background-color:" + strongestArmyOwner.getColor() + ";" +
                    "-fx-text-fill: white;" + "-fx-font-size: 18px;");
        }
    }

    // updates the card numbers and the number of card images on the cards pane
    private void updateNumbersOfCardsInPanes() {
        int spaceBetweenImages = 6;
        // reason of this map is to update all card panes in a loop
        HashMap<String, Object[]> resourceCardsMap = new HashMap<>();

        resourceCardsMap.put(Constants.CARD_WOOL, new Object[] {woolLabel, woolImages, paneWools, Constants.PATH_RESOURCE_WOOL()});
        resourceCardsMap.put(Constants.CARD_ORE, new Object[] {oreLabel, oreImages, paneOres, Constants.PATH_RESOURCE_ORE()});
        resourceCardsMap.put(Constants.CARD_LUMBER, new Object[] {lumberLabel, lumberImages, paneLumbers, Constants.PATH_RESOURCE_LUMBER()});
        resourceCardsMap.put(Constants.CARD_BRICK, new Object[] {brickLabel, brickImages, paneBricks, Constants.PATH_RESOURCE_BRICK()});
        resourceCardsMap.put(Constants.CARD_GRAIN, new Object[] {grainLabel, grainImages, paneGrains, Constants.PATH_RESOURCE_GRAIN()});

        HashMap<String, ArrayList<SourceCard>> sourceCards = playerActual.getSourceCards();
        Set<String> keys = sourceCards.keySet();

        for (String key: keys) {
            // this object array contains all card related objects in every iteration
            // ex:
            Object[] currentCardRelated = resourceCardsMap.get(key);
            String noOfCards = sourceCards.get(key).size() == 0 ? "" : sourceCards.get(key).size() + "";

            // updates the textual representation of the number of cards
            ((Label)currentCardRelated[0]).setText(noOfCards);
            // if the number of source cards it has is more than what is displayed on the screen,
            // add more card images to the display
            while (sourceCards.get(key).size() > ((ArrayList<ImageView>)currentCardRelated[1]).size() - 1) {
                ImageView imgToAdd = new ImageView(((String)currentCardRelated[3]));
                // puts the image right next to its predecessor
                if (((ArrayList<ImageView>)currentCardRelated[1]).size() == 1) {
                    imgToAdd.setLayoutX(((ArrayList<ImageView>) currentCardRelated[1]).get(((ArrayList<ImageView>) currentCardRelated[1]).size() - 1).getLayoutX());
                } else {
                    imgToAdd.setLayoutX(((ArrayList<ImageView>) currentCardRelated[1]).get(((ArrayList<ImageView>) currentCardRelated[1]).size() - 1).getLayoutX() + spaceBetweenImages);
                }
                imgToAdd.setLayoutY(((ArrayList<ImageView>)currentCardRelated[1]).get(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1).getLayoutY());
                imgToAdd.setFitHeight(((ArrayList<ImageView>)currentCardRelated[1]).get(0).getFitHeight());
                imgToAdd.setFitWidth(((ArrayList<ImageView>)currentCardRelated[1]).get(0).getFitWidth());
                // puts the image into its corresponding list. ex: grainImage -> grainImages
                ((ArrayList<ImageView>)currentCardRelated[1]).add(imgToAdd);
                // puts image into its corresponding pane. ex : grainImage -> grainsPane
                ((Pane)currentCardRelated[2]).getChildren().add(imgToAdd);
            }
            // if number of source cards it has is less than what is displayed on the screen,
            // remove the displayed card images
            while (sourceCards.get(key).size() < ((ArrayList<ImageView>)currentCardRelated[1]).size() - 1) {
                // removes the image from its corresponding pane
                ((Pane)currentCardRelated[2]).getChildren().remove(((ArrayList<ImageView>)currentCardRelated[1]).get(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1));
                // removes the image from its corresponding list
                ((ArrayList<ImageView>)currentCardRelated[1]).remove(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1);
            }

            if (((ArrayList<ImageView>)currentCardRelated[1]).size() == 1) {
                ((ArrayList<ImageView>)currentCardRelated[1]).get(0).setVisible(true);
            } else if (((ArrayList<ImageView>)currentCardRelated[1]).size() > 1) {
                ((ArrayList<ImageView>)currentCardRelated[1]).get(0).setVisible(false);
            }
        }

        HashMap<String, Object[]> developmentCardsMap = new HashMap<>();

        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_INVENTION, new Object[] {inventionLabel, inventionImages, paneInvention, Constants.PATH_DEVELOPMENT_CARD_INVENTION()});
        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_KNIGHT, new Object[] {knightLabel, knightImages, paneKnight, Constants.PATH_DEVELOPMENT_CARD_KNIGHT()});
        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_MONOPOL, new Object[] {monopolyLabel, monopolyImages, paneMonopoly, Constants.PATH_DEVELOPMENT_CARD_MONOPOL()});
        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE, new Object[] {profitLabel, profitImages, paneProfit, Constants.PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE()});
        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION, new Object[] {roadDestructionLabel, roadDestructionImages, paneRoadDestruction, Constants.PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION()});
        developmentCardsMap.put(Constants.DEVELOPMENT_CARD_VICTORY_POINT, new Object[] {victoryLabel, victoryImages, paneVictory, Constants.PATH_DEVELOPMENT_CARD_VICTORY_POINT()});

        HashMap<String, Integer> developmentCards = playerActual.getDevelopmentCards();
        Set<String> developmentKeys = developmentCards.keySet();
        for (String key: developmentKeys) {
            // this object array contains all card related objects in every iteration
            // ex:
            Object[] currentCardRelated = developmentCardsMap.get(key);
            String noOfCards = developmentCards.get(key) == 0 ? "" : developmentCards.get(key) + "";

            // updates the textual representation of the number of cards
            ((Label)currentCardRelated[0]).setText(noOfCards);
            // if the number of source cards it has is more than what is displayed on the screen,
            // add more card images to the display
            while (developmentCards.get(key) > ((ArrayList<ImageView>)currentCardRelated[1]).size() - 1) {
                ImageView imgToAdd = new ImageView(((String)currentCardRelated[3]));
                // puts the image right next to its predecessor
                if (((ArrayList<ImageView>)currentCardRelated[1]).size() == 1) {
                    imgToAdd.setLayoutX(((ArrayList<ImageView>) currentCardRelated[1]).get(((ArrayList<ImageView>) currentCardRelated[1]).size() - 1).getLayoutX());
                } else {
                    imgToAdd.setLayoutX(((ArrayList<ImageView>) currentCardRelated[1]).get(((ArrayList<ImageView>) currentCardRelated[1]).size() - 1).getLayoutX() + spaceBetweenImages);
                }
                imgToAdd.setLayoutY(((ArrayList<ImageView>)currentCardRelated[1]).get(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1).getLayoutY());
                imgToAdd.setFitHeight(((ArrayList<ImageView>)currentCardRelated[1]).get(0).getFitHeight());
                imgToAdd.setFitWidth(((ArrayList<ImageView>)currentCardRelated[1]).get(0).getFitWidth());
                // puts the image into its corresponding list. ex: grainImage -> grainImages
                ((ArrayList<ImageView>)currentCardRelated[1]).add(imgToAdd);
                // puts image into its corresponding pane. ex : grainImage -> grainsPane
                ((Pane)currentCardRelated[2]).getChildren().add(imgToAdd);
            }
            // if number of source cards it has is less than what is displayed on the screen,
            // remove the displayed card images
            while (developmentCards.get(key) < ((ArrayList<ImageView>)currentCardRelated[1]).size() - 1) {
                // removes the image from its corresponding pane
                ((Pane)currentCardRelated[2]).getChildren().remove(((ArrayList<ImageView>)currentCardRelated[1]).get(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1));
                // removes the image from its corresponding list
                ((ArrayList<ImageView>)currentCardRelated[1]).remove(((ArrayList<ImageView>)currentCardRelated[1]).size() - 1);
            }

            if (((ArrayList<ImageView>)currentCardRelated[1]).size() == 1) {
                ((ArrayList<ImageView>)currentCardRelated[1]).get(0).setVisible(true);
            } else if (((ArrayList<ImageView>)currentCardRelated[1]).size() > 1) {
                ((ArrayList<ImageView>)currentCardRelated[1]).get(0).setVisible(false);
            }
        }
    }

    // makes the card panes sorted in descending order
    // ex: 4 lumbers will be presented before 3 wools
    private void updateOrderOfCardPanes() {
        Arrays.sort(resourceCardPanes, (a, b) -> b.getChildren().size() - a.getChildren().size());
        for (int i = 0; i < resourceCardPanes.length; i++) {
            resourceCardPanes[i].setLayoutX(resourceCardsPaneLocations[i][0]);
            resourceCardPanes[i].setLayoutY(resourceCardsPaneLocations[i][1]);
        }

        Arrays.sort(developmentCardPanes, (a, b) -> b.getChildren().size() - a.getChildren().size());
        for (int i = 0; i < developmentCardPanes.length; i++) {
            developmentCardPanes[i].setLayoutX(developmentCardsPaneLocations[i][0]);
            developmentCardPanes[i].setLayoutY(developmentCardsPaneLocations[i][1]);
        }
    }

    private void updateGameLogsInView() {
        for (; gameLogIterator < gameLog.size(); gameLogIterator++) {
            String[] log = gameLog.getLog(gameLogIterator);
            String logText = log[0];
            String logColor = log[1];
            Label logLabel = new Label("  " + logText);
            logLabel.setMinWidth(400);
            logLabel.setMinHeight(35);
            logLabel.setCursor(Cursor.DEFAULT);
            String marginProperty = "-fx-padding: 5px 2px 5px 2px;" + "-fx-border-insets: 2px;" + "-fx-background-insets: 2px;";
            logLabel.setStyle("-fx-background-color:" + logColor + ";" + "-fx-text-fill: white;" + marginProperty);
            gameLogsFlowPane.getChildren().add(0, logLabel);
        }
    }

    @Override
    public void displayWarning(String warningType) {
        Label warning = getWarningLabel();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4.0), warning);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        switch (warningType) {
            case "Road Error":
                warning.setText(resources.getString("warning_roadError"));
                warning.setOpacity(1);
                break;
            case "No Dev Card":
                warning.setText(resources.getString("warning_noDevCardWarning"));
                warning.setOpacity(1);
                break;
            case "Poor to buy dev card":
                warning.setText(resources.getString("warning_noResourcesForDevCard"));
                warning.setOpacity(1);
                break;
            case "Wrong time for dev card":
                warning.setText(resources.getString("warning_wrongTimeForDevCard"));
                warning.setOpacity(1);
                break;
            case "Not possible":
                warning.setText(resources.getString("warning_notPossible"));
                warning.setOpacity(1);
                break;
            case "Thief":
                warning.setText(resources.getString("warning_playingThiefWarning"));
                warning.setOpacity(1);
                break;
            case "ProfitExchange":
                warning.setText(resources.getString("warning_profitExchangeWarning"));
                warning.setOpacity(1);
                break;
            case "RoadDestruction":
                warning.setText(resources.getString("warning_roadDestructionWarning"));
                warning.setOpacity(1);
                break;
            case "Construction Overlap":
                warning.setText(resources.getString("warning_constructionOverLap"));
                warning.setOpacity(1);
                break;
            default:
                warning.setText(resources.getString("warning_defaultErrorMessage"));
                warning.setOpacity(1);
                break;
        }
        fadeTransition.play();
    }

    @Override
    public void displaySuccess(String successType) {
        Label success = getSuccessLabel();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4.0), success);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        switch (successType) {
            case "bought dev card":
                success.setText(resources.getString("success_boughtDevCard"));
                success.setOpacity(1);
                break;
            default:
                success.setText(resources.getString("success_defaultMessage"));
                success.setOpacity(1);
                break;
        }
        fadeTransition.play();
    }

    private Road getCorrespondingRoad(Line line) {
        for (Road road: getRoads()) {
            if (road.getRoad() == line) { return road; }
        }
        return null;
    }

    private boolean isVertexSuitableForConstruction(Vertex vertex) {
        ArrayList<Vertex> neighbors = vertex.getNeighbors();
        for (Vertex v: neighbors) {
            if (v.hasConstruction()) {
                displayWarning("Construction Overlap");
                return false;
            }
        }
        return true;
    }

    private void constructRoad() {
        Road road = getCorrespondingRoad(roadVertex1, roadVertex2);
        if (road != null && !road.getRoad().isVisible()) {
            // coloring road.
            Color color = currentPlayer.getRoadColor();
            road.getRoad().setStroke(color);
            road.getRoad().setVisible(true);
            // adding road;
            currentPlayer.addRoad(road);
            activatePlayerVertices();
            isRoadBuild = true;

            if (isStepActual) {
                currentPlayer.subtractPriceOfConstruction(selectedConstruction);
            }
            if (isStepInitial && currentPlayer instanceof PlayerActual) {
                selectConstructionInitial(imgVillage);
            }
            if (isStepInitial && currentPlayer instanceof PlayerAI) {
                tempRoad = road;
            }
        } else {
            currentPlayer = getPlayers().get(playerTurn);
            if(currentPlayer instanceof PlayerActual)
                displayWarning("Road Error");
        }
        unselectConstructions(null);
    }

    private void refreshRoadSelectionVertices() {
        if (roadVertex1 != null && !roadVertex1.hasConstruction()) {
            roadVertex1.getShape().setFill(Constants.COLOR_BLUR_VERTEX);
        }
        if (roadVertex2 != null && !roadVertex2.hasConstruction()) {
            roadVertex2.getShape().setFill(Constants.COLOR_BLUR_VERTEX);
        }
        roadVertex1 = null;
        roadVertex2 = null;
    }

    private Road getCorrespondingRoad(Vertex vertex1, Vertex vertex2) {
        for (Road road: getRoads()) {
            if (road.containsRoad(vertex1, vertex2)) { return road; }
        }
        return null;
    }

    private Vertex getCorrespondingVertex(Circle shape) {
        for (Vertex vertex: getVertices()) {
            if (vertex.getShape() == shape) { return vertex; }
        }
        return null;
    }

    private void activateAllVertices() {
        for (Vertex vertex: getVertices()) {
            if (!vertex.hasConstruction()) { vertex.setActive(true); }
        }
    }

    private void deActivateAllVertices() {
        for (Vertex vertex: getVertices()) {
            vertex.setActive(false);
        }
    }

    private ArrayList<Vertex> getActivatedVertices() {
        ArrayList<Vertex> activated = new ArrayList<>();
        for(Vertex vertex: getVertices()) {
            if (vertex.isActive()) { activated.add(vertex); }
        }
        return activated;
    }

    private void activatePlayerVertices() {
        deActivateAllVertices();
        ArrayList<Road> roads = currentPlayer.getRoads();
        for (Road road: roads) {
            for(Vertex vertex: road.getVertices()) {
                switch (selectedConstruction) {
                    case Constants.ROAD:
                        vertex.setActive(true);
                        activateNeighbours(vertex);
                        break;
                    case Constants.VILLAGE:
                        if (!vertex.hasConstruction() && isVertexSuitableForConstruction(vertex)) {
                            vertex.setActive(true);
                        }
                        break;
                    case Constants.CITY:
                        if (vertex.hasConstruction() && vertex.getSettlement() instanceof Village) {
                            vertex.setActive(true);
                        }
                        break;
                    case Constants.CIVILISATION:
                        if (vertex.hasConstruction() && vertex.getSettlement() instanceof City) {
                            vertex.setActive(true);
                        }
                        break;
                }
            }
        }
    }

    private void  activateNeighbours(Vertex vertex) {
        for (Vertex v: vertex.getNeighbors()) {
            Settlement settlement = v.getSettlement();
            if (settlement == null || settlement.getPlayer() == currentPlayer) {
                Road road = getCorrespondingRoad(vertex, v);
                if (road != null && !road.getRoad().isVisible()) { v.setActive(true); }
            }
        }
    }

    private void setSelectedConstruction(String selectedConstruction) {
        this.selectedConstruction = selectedConstruction;
    }

    private boolean isSelectedSettlement() {
        return selectedConstruction.equals(Constants.VILLAGE)||
               selectedConstruction.equals(Constants.CITY)   ||
               selectedConstruction.equals(Constants.CIVILISATION);
    }

    // ------------ ACTUAL STEP METHODS ---------------- //

    private void actualTurn() {
        developmentCardExchangeProfit = null;
        developmentCardDestroyRoad = null;
        playerTurn = playerTurn % 4;
        currentPlayer = getPlayers().get(playerTurn);
        rollDie();

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println(currentPlayer.getName() + " : " + currentPlayer.getColor() + " | Die Result: " +
                die.getDieSum() + " | Victory Points: " + currentPlayer.getVictoryPoints());
        System.out.println("----------------------------------------------------------------------------------------------");
        gameLog.addLog(StringUtils.capitalize(currentPlayer.getName()) + " has rolled " + die.getDieSum() + ".", currentPlayer.getColor());
        playerTurn++;

        chest.refreshStrongestArmyOwner();
        if(chest.getStrongestArmyOwner().equals(currentPlayer.getName()))
            currentPlayer.setStrongestArmyCard(chest.getStrongestArmyCard());

        chest.refreshLongestRoadOwner();
        if(chest.getLongestRoadOwner().equals(currentPlayer.getName()))
            currentPlayer.setLongestArmyCard(chest.getLongestRoad());

        currentPlayer.refreshVictoryPoints();

        if(currentPlayer.getVictoryPoints() >= getSettings().getVictoryThreshold()) {
            openDialog(Constants.PATH_VIEW_ENDGAME, "The game end.", null, null);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                        new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
                Parent root = fxmlLoader.load(getClass().getResource("../view/program.fxml"),bundle);
                Stage window = (Stage) getImgDie1().getScene().getWindow();
                window.getScene().setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //  doing thief operations.
        if (die.getDieSum() == 7) {
            thiefResourceCardPunishAI();
            thiefResourceCardPunishActual();
            playThief(currentPlayer);
        }

        getTurnProfit();
        updateGameLogsInView();
        updateCardsOfActualPlayerInView();
        
        // AI player
        if (currentPlayer instanceof PlayerAI) {
            playAIActualTurn();
            actualTurn();
        }

        // Actual Player
        else if (currentPlayer instanceof PlayerActual) {
            deActivateAllVertices();
            refreshRoadSelectionVertices();
            constructionUnselect = true;
            unselectConstructions(null);
        }

    }

    private void playAIActualTurn() {
        ((PlayerAI) currentPlayer).decideToMakeTrade(this);
        ((PlayerAI) currentPlayer).decideToMakeConstruction(this);
        ((PlayerAI) currentPlayer).decideToBuyDevelopmentCard(this);
        ((PlayerAI) currentPlayer).decideToPlayDevelopmentCard(this);
    }

    @Override
    public void makeTradeForAI(boolean isTradeWithChest) {
        if (isTradeWithChest) { // trade with chest
            HashMap<String, Integer> requestedRC = ((PlayerAI)currentPlayer).getRequestedResourceCardForChest();
            HashMap<String, Integer> offeredRC   = ((PlayerAI)currentPlayer).getOfferedResourceCardForChest(requestedRC);
            new Trade(currentPlayer, null, requestedRC, offeredRC, isTradeWithChest);
        }

        else { // trade between players
            int idOfPlayerToBeTraded = (int)(Math.random() * 4);
            Player playerToBeTraded = getPlayers().get(idOfPlayerToBeTraded);

            if (currentPlayer instanceof PlayerAI && playerToBeTraded != currentPlayer) {
                // setting trade materials
                HashMap<String, Integer> requestedRC = ((PlayerAI)currentPlayer).getRequestedResourceCards(playerToBeTraded);
                HashMap<String, Integer> offeredRC = ((PlayerAI)currentPlayer).getOfferedResourceCards(requestedRC);
                Trade trade = new Trade(currentPlayer, playerToBeTraded, requestedRC, offeredRC, isTradeWithChest);

                // trade request sent to actual player by playerAI
                if (playerToBeTraded == playerActual && trade.isTradePossible()) {
                    openDialog(Constants.PATH_VIEW_TRADE_REQUEST, "Incoming Trade Offer", null, trade);
                }
            }
        }
    }

    private void thiefResourceCardPunishActual() {
        if(playerActual.getTotalCards() > 7){
            openDialog(Constants.PATH_VIEW_PUNISHMENT, "Thief will steal something", null, null);
            updateCardsOfActualPlayerInView();
        }
    }

    private void thiefResourceCardPunishAI(){
        for (int i = 0; i < getPlayers().size(); i++) {
            Player player = getPlayers().get(i);
            if (player instanceof PlayerAI && player.getTotalCards() > 7) {
                ((PlayerAI)player).punish();
            }
        }
    }

    private void playThief(Player currentPlayer){
        if (currentPlayer instanceof PlayerAI) {
            int randomHexIndex = (int)(Math.random() * 19);
            thief.setTerrainHex(terrainHexes.get(randomHexIndex));
            thief.punishUsersAroundHexByThief(currentPlayer);
        }
        else if (currentPlayer instanceof PlayerActual) {
            thief.setCanThiefMove(true);
            displayWarning(Constants.THIEF_STRING);
        }
    }

    @FXML
    public void moveThief(MouseEvent mouseEvent){
        if (thief.canThiefMove()) {
            thief.updateLocation(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    @FXML
    public void thiefMoved(MouseEvent mouseEvent){
        if (thief.canThiefMove()) {
            TerrainHex newHex = getHexWithCoordinates(imgMovingThief);
            if (newHex != null) {
                thief.setCanThiefMove(false);
                thief.setTerrainHex(newHex);
                thief.punishUsersAroundHexByThief(currentPlayer);
            }
        }
    }

    private void getTurnProfit() {
        System.out.println("----------------------------------------------------------------------------------------------");
        for (Player player: getPlayers()) {
            player.getTurnProfit(die.getDieSum(), thief.getTerrainHex());
            player.showSourceCards();
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    @Override
    public void makeConstructionActual(Circle circle) {
        if (selectedConstruction == null) return;

        if (!currentPlayer.hasEnoughResources(selectedConstruction)) {
            if(currentPlayer instanceof PlayerActual)
                displayWarning(Constants.CONSTRUCTION_STRING);
            return;
        }
        if (isSelectedSettlement()) {
            Vertex vertex = getCorrespondingVertex(circle);
            if (vertex != null && isVertexSuitableForConstruction(vertex) && vertex.isActive()) {
                Settlement settlement;
                String imagePath = "";
                switch (selectedConstruction) {
                    case Constants.CITY:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.CITY);
                        settlement = new City(imagePath, vertex, currentPlayer);
                        gameLog.addLog(StringUtils.capitalize(currentPlayer.getName()) +" has built a city.",
                                getPlayers().get(playerTurn % 4).getColor());
                        break;
                    case Constants.VILLAGE:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                        settlement = new Village(imagePath, vertex, currentPlayer);
                        gameLog.addLog(StringUtils.capitalize(currentPlayer.getName()) + " has built a village.",
                                getPlayers().get(playerTurn % 4).getColor());
                        break;
                    default:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.CIVILISATION);
                        settlement = new Civilisation(imagePath, vertex, currentPlayer);
                        gameLog.addLog(StringUtils.capitalize(currentPlayer.getName()) + " has built civilisation.",
                                getPlayers().get(playerTurn % 4).getColor());
                        break;
                }
                Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(Constants.CONSTRUCTION_RADIUS);
                getSettlements().add(settlement);
                currentPlayer.getSettlements().add(settlement);
                vertex.setSettlement(settlement);
                //harbour
                String vertexID = vertex.getShape().getId();
                addHarboursToPlayer(circle);
                //
                currentPlayer.subtractPriceOfConstruction(selectedConstruction);
                currentPlayer.showSourceCards();
                unselectConstructions(null);
                deActivateAllVertices();
            } else {
                if(currentPlayer instanceof PlayerActual)
                    displayWarning("Not possible");
            }
        }
        else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (currentPlayer instanceof PlayerActual && isStepInitial && !isRoadBuild) {
                    selectConstructionActual(imgRoad);
                }
                constructionUnselect = false;
                roadVertex1 = getCorrespondingVertex(circle);
                if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS) {
                    circle.setFill(Constants.COLOR_ROAD_SELECTION_VERTEX);
                }
            }
            else {
                constructionUnselect = true;
                roadVertex2 = getCorrespondingVertex(circle);
                constructRoad();
            }
        }
    }

    @Override
    public void makeRoadActualForAI() {
        boolean isRoadConstructed = false;
        while (!isRoadConstructed) {
            ArrayList<Road> roads = currentPlayer.getRoads();
            ArrayList<Vertex> vertices = new ArrayList<>();
            for (Road road: roads) {
                for (Vertex vertex: road.getVertices()) {
                    if (!vertices.contains(vertex)) {
                        vertices.add(vertex);
                    }
                }
            }
            // setting first vertex of road;
            int index = (int) (Math.random() * vertices.size());
            Vertex vertex1 = vertices.get(index);
            // setting second vertex of road;

            ArrayList<Vertex> neighbours = new ArrayList<>();
            for (Vertex vertex: vertex1.getNeighbors()) {
                if (!vertex.hasConstruction()) {
                    neighbours.add(vertex);
                }
            }

            index = (int) (Math.random() * neighbours.size());
            Vertex vertex2 = vertex1.getNeighbors().get(index);
            Road road = getCorrespondingRoad(vertex1, vertex2);

            if (road != null && !road.getRoad().isVisible()) {
                makeConstructionActual(vertex1.getShape());
                makeConstructionActual(vertex2.getShape());
                isRoadConstructed = true;
            }
        }
    }

    @Override
    public void makeVillageActualForAI() {
        ArrayList<Vertex> vertices = getActivatedVertices();
        if (vertices.size() > 0) {
            int index = (int) (Math.random() * vertices.size());
            Circle circle = vertices.get(index).getShape();
            makeConstructionActual(circle);
        }
    }

    @Override
    public void selectActualConstructionForAI(String type) {
        switch (type) {
            case Constants.VILLAGE:
                selectConstructionActual(imgVillage);
                break;
            case Constants.CITY:
                selectConstructionActual(imgCity);
                break;
            case Constants.CIVILISATION:
                selectConstructionActual(imgCivilisation);
                break;
            case Constants.ROAD:
                selectConstructionActual(imgRoad);
                break;
        }
    }

    private void selectConstructionActual(Circle rectangle) {
        // setting all of the pane as unselected
        imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgRoad.setStrokeWidth(5);
        imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCity.setStrokeWidth(5);
        imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgVillage.setStrokeWidth(5);
        imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCivilisation.setStrokeWidth(5);

        // setting selected construction
        if (rectangle == imgRoad) {
            selectedConstruction = Constants.ROAD;
        } else if (rectangle == imgCity) {
            selectedConstruction = Constants.CITY;
        } else if (rectangle == imgVillage) {
            selectedConstruction = Constants.VILLAGE;
        } else if (rectangle == imgCivilisation) {
            selectedConstruction = Constants.CIVILISATION;
        }

        if (!currentPlayer.hasEnoughResources(selectedConstruction)) {
            selectedConstruction = "";
            displayWarning(Constants.CONSTRUCTION_STRING);
            return;
        }

        activatePlayerVertices();

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);
        rectangle.setStrokeWidth(5);

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }

    // ------------ INITIAL STEP METHODS ---------------- //

    private void initialTurn()  {
        while (true) {
            playerTurn = playerTurn % 4;
            Player player = getPlayers().get(playerTurn);
            currentPlayer = player;
            activateAllVertices();

            // AI player
            if (player instanceof PlayerAI) {
                playAIInitialTurn();
                isRoadBuild = false;
                isConstructionBuild = false;
            }
            // Actual Player
            else if (player instanceof  PlayerActual) {
                if ((isStepInitial && isRoadBuild && isConstructionBuild)) {
                    isRoadBuild = false;
                    isConstructionBuild = false;
                } else {
                    if(currentPlayer instanceof PlayerActual)
                        displayWarning("Not possible");
                    selectConstructionInitial(imgRoad);
                    return;
                }
            }
            playerTurn++;
            initialStepCount++;

            // terminating initial step
            if (initialStepCount == 8) {
                isStepActual = true;
                isStepInitial = false;
                actualTurn();
                return;
            }
        }
    }

    private void playAIInitialTurn() {
        tempRoad = null;
        tempSettlement = null;
        int count = 0;
        while ((tempSettlement == null || tempRoad == null)&count<400) {
            count++;
            if ((tempRoad != null)) {
                tempRoad.getRoad().setStroke(Color.BLACK);
                tempRoad.getRoad().setVisible(false);
                currentPlayer.getRoads().remove(tempRoad);
                tempRoad = null;
            }

            ArrayList<Vertex> outerVertices = new ArrayList<>();
            for (int i = 0; i < vertices.size(); i++)
                if (vertices.get(i).getFields().size() < 3)
                    outerVertices.add(vertices.get(i));

            ArrayList<Vertex> thiefVertices = thief.getTerrainHex().getVertices();
            Set<Vertex> set = new HashSet<>(outerVertices);
            if(count < 180)
                set.addAll(thiefVertices);
            ArrayList<Vertex> undesiredVertices = new ArrayList<>(set);
            ArrayList<Vertex> desiredVertices = new ArrayList<>();


            for (int i = 0; i < terrainHexes.size(); i++) {
                int number = terrainHexes.get(i).getNumberOnHex();
                if (number == 5 || number == 6 || number == 7 || number == 8) {
                    desiredVertices.addAll(terrainHexes.get(i).getVertices());
                }
                if(count > 110) {
                    desiredVertices.addAll(terrainHexes.get(i).getVertices());
                }

            }

            Set<Vertex> vertexSet = new HashSet<>(desiredVertices);
            desiredVertices.clear();
            desiredVertices.addAll(vertexSet);
            desiredVertices.removeAll(new HashSet<>(undesiredVertices));

            for (int i = 0; i < vertices.size(); i++){
                for (int j = 0; j < desiredVertices.size(); j++) {
                    if (vertices.get(i).hasConstruction() & vertices.get(i).getShape() == desiredVertices.get(j).getShape())
                        desiredVertices.remove(desiredVertices.get(j));
                }
            }
            activateAllVertices();
            setSelectedConstruction(Constants.ROAD);
            // setting first vertex of road;
            //int index = (int) (Math.random() * getActivatedVertices().size());
            Vertex vertex = desiredVertices.get ((int)(Math.random() * desiredVertices.size()));
            if(isVertexSuitableForConstruction(vertex))
                desiredVertices.remove(vertex);


            // setting second vertex of road;
            Vertex vertex2 = desiredVertices.get ((int)(Math.random() * desiredVertices.size()));
            if(isVertexSuitableForConstruction(vertex2))
                desiredVertices.remove(vertex2);

            if(isVertexSuitableForConstruction(vertex) || isVertexSuitableForConstruction(vertex2)) {
                makeConstructionInitial(vertex);
                makeConstructionInitial(vertex2);
                if(!isVertexSuitableForConstruction(vertex))
                    desiredVertices.remove(vertex);
                if(!isVertexSuitableForConstruction(vertex2))
                    desiredVertices.remove(vertex2);
            }

            setSelectedConstruction(Constants.VILLAGE);
            if (tempRoad != null) {
                ArrayList<Vertex> twoVertex = new ArrayList<>();
                twoVertex.add(tempRoad.getVertices().get(0));
                twoVertex.add(tempRoad.getVertices().get(1));

                if (isVertexSuitableForConstruction(twoVertex.get(0)) & !undesiredVertices.contains(twoVertex.get(0))) {
                    makeConstructionInitial(twoVertex.get(0));
                }
                else if (isVertexSuitableForConstruction(twoVertex.get(1)) & !undesiredVertices.contains(twoVertex.get(1))){
                    makeConstructionInitial(twoVertex.get(1));
                }

            }
        }
        tempRoad = null;
        tempSettlement = null;
    }

    private void makeConstructionInitial(Vertex vertex) {
        if (selectedConstruction.equals(Constants.CITY)   ||
            selectedConstruction.equals(Constants.VILLAGE)||
            selectedConstruction.equals(Constants.CIVILISATION)) {

            if (isRoadBuild && !isConstructionBuild) {
                selectConstructionInitial(imgVillage);
            }

            //Vertex vertex = getCorrespondingVertex(circle);
            if (vertex != null && isVertexSuitableForConstruction(vertex) && vertex.isActive()) {
                isConstructionBuild = true;
                String imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                Settlement settlement = new Village(imagePath, vertex, currentPlayer);

                // loading image
                Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(Constants.CONSTRUCTION_RADIUS);

                // adding settlement
                getSettlements().add(settlement);
                currentPlayer.getSettlements().add(settlement);
                vertex.setSettlement(settlement);
                //harbour
                String vertexID = vertex.getShape().getId();
                addHarboursToPlayer(vertex.getShape());

                unselectConstructions(null);
                activatePlayerVertices();
                tempSettlement = settlement;

            } else {
                if(currentPlayer instanceof PlayerActual)
                    displayWarning("Not possible");
            }
        }
        else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (!isRoadBuild) {
                    selectConstructionInitial(imgRoad);
                }

                constructionUnselect = false;
                //Vertex vertex = getCorrespondingVertex(circle);
                if (vertex != null && !vertex.hasConstruction()) {
                    roadVertex1 = vertex;
                    Color color = Constants.COLOR_ROAD_SELECTION_VERTEX;
                    roadVertex1.getShape().setFill(color);
                }
            }
            else {
                constructionUnselect = true;
                //Vertex vertex = getCorrespondingVertex(circle);
                if (vertex != null && !vertex.hasConstruction()) {
                    roadVertex2 = vertex;
                    constructRoad();
                } else {
                    refreshRoadSelectionVertices();
                    if(currentPlayer instanceof PlayerActual)
                        displayWarning("Not possible");
                }
            }
        }
    }

    private void selectConstructionInitial(Circle rectangle) {
        // setting all of the pane as unselected
        imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgRoad.setStrokeWidth(5);
        imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCity.setStrokeWidth(5);
        imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgVillage.setStrokeWidth(5);
        imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCivilisation.setStrokeWidth(5);

        // setting selected construction
        if (rectangle == imgRoad) {
            selectedConstruction = Constants.ROAD;
        } else if (rectangle == imgVillage) {
            selectedConstruction = Constants.VILLAGE;
        }

        //if (currentPlayer instanceof PlayerActual) {
        if (isStepInitial && !isRoadBuild) {
            selectedConstruction = Constants.ROAD;
            rectangle = imgRoad;
        }
        else if (isStepInitial && !isConstructionBuild) {
            selectedConstruction = Constants.VILLAGE;
            rectangle = imgVillage;
        }
        else if (isStepInitial) {
            selectedConstruction = "";
            return;
        }

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);
        rectangle.setStrokeWidth(5);
        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }
}