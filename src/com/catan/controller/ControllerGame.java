package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;
import com.catan.interfaces.InterfaceMakeTrade;
import com.catan.interfaces.*;
import com.catan.modal.*;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private Player currentPlayer;
    private int playerTurn = 0;
    private Road tempRoad;
    private Chest chest;

    @Override
    public void setDevelopmentCardInvention(final DevelopmentCard developmentCardInvention) {
        this.developmentCardInvention = developmentCardInvention;
    }

    @Override
    public void initialize() {
        super.initialize();
        developmentCardExchangeProfit = null;
        developmentCardDestroyRoad = null;
        developmentCardInvention = null;
        selectConstructionInitial(imgRoad);
        currentPlayer = getPlayers().get(0);
        activateAllVertices();
        chest = new Chest();
    }

    @FXML
    public void selectConstruction(final MouseEvent mouseEvent) {
        final Rectangle rectangle = (Rectangle) mouseEvent.getSource();
        if (isStepInitial) {
            selectConstructionInitial(rectangle);
        } else {
            selectConstructionActual(rectangle);
        }
    }

    @FXML
    public void unselectConstructions(final MouseEvent mouseEvent) {
        if ((isStepActual && constructionUnselect) || (isStepInitial && isRoadBuild && isConstructionBuild)) {
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
    public void blurVertex(final MouseEvent mouseEvent) {
        final Circle circle = (Circle) mouseEvent.getSource();
        if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS
                && circle.getFill() != Constants.COLOR_ROAD_SELECTION_VERTEX) {
            circle.setFill(Constants.COLOR_BLUR_VERTEX);
        }
    }

    @FXML
    public void blendVertex(final MouseEvent mouseEvent) {
        final Circle circle = (Circle) mouseEvent.getSource();
        final Vertex vertex = getCorrespondingVertex(circle);
        if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS && vertex.isActive()) {
            circle.setFill(Constants.COLOR_BLEND_VERTEX);
        }
    }

    @FXML
    void trade(final ActionEvent event) {
        if (isStepActual) {
            openDialog(Constants.PATH_VIEW_TRADE_OFFER, "Trade", null, null);
        }
    }

    @FXML
    public void playDevelopmentCard(final ActionEvent event) {
        if (!isStepActual)
            return;
        if (currentPlayer.getTotalDevelopmentCards() > 0) {
            openDialog(Constants.PATH_VIEW_PLAY_DEVELOPMENT_CARD, "Play Development Card", null, null);
        } else {
            outputNotPossible("No resource");
        }
    }

    @Override
    public void playDevelopmentCard(final DevelopmentCard developmentCard) {
        if (!isStepActual)
            return;

        if (developmentCard != null) {
            developmentCard.performDevelopmentCardFeatures(currentPlayer, getPlayers(), terrainHexes, this);
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
    public void destroyRoad(final MouseEvent mouseEvent) {
        if (developmentCardDestroyRoad == null)
            return;
        final Line line = (Line) mouseEvent.getSource();
        final Road road = getCorrespondingRoad(line);
        developmentCardDestroyRoad.destroyRoad(road, currentPlayer, getPlayers());
    }

    @FXML
    public void exchangeTurnProfit(final MouseEvent mouseEvent) {
        if (developmentCardExchangeProfit == null)
            return;
        final Circle circle = (Circle) mouseEvent.getSource();
        developmentCardExchangeProfit.exchangeTurnProfit(circle, currentPlayer);
        if (developmentCardExchangeProfit.isDevelopmentCardUsed()) {
            developmentCardExchangeProfit = null;
        }
    }

    @Override
    public void openDialog(final String viewPath, final String title, final DevelopmentCard developmentCard,
            final Trade trade) {
        try {
            final Dialog dialog = new Dialog<>();
            dialog.initOwner(root.getScene().getWindow());
            dialog.setTitle(title);
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(viewPath));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            // Monopoly development card dialog.
            if (viewPath.equals(Constants.PATH_VIEW_DEV_MONOPOL_CARD)) {
                final ControllerDevMonopol monopolController = fxmlLoader.getController();
                monopolController.setProperties(currentPlayer, developmentCard, getPlayers());
            }

            // Invention development card dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_DEV_INVENTION_CARD)) {
                final ControllerDevInvention inventionController = fxmlLoader.getController();
                inventionController.setProperties(currentPlayer, developmentCard);
            }

            // Trade offer dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_TRADE_OFFER)) {
                final ControllerTradeOffer tradeController = fxmlLoader.getController();
                // TODO actual player needs to be passed here afterwards.
                tradeController.setActualPlayerAndLabels(playerActual);
                tradeController.setAllPlayers(getPlayers());
            }

            // Incoming Trade Request dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_TRADE_REQUEST) && trade.isTradePossible()) {
                final ControllerTradeRequest tradeRequestController = fxmlLoader.getController();
                tradeRequestController.setProperties(trade);
            }

            // Thief punishment dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_PUNISHMENT)) {
                final ControllerThiefPunishment controller = fxmlLoader.getController();
                controller.setPlayer(playerActual);
            }

            // Play development card dialog.
            else if (viewPath.equals(Constants.PATH_VIEW_PLAY_DEVELOPMENT_CARD)) {
                final ControllerPlayDevelopmentCard controller = fxmlLoader.getController();
                controller.setProperties(currentPlayer, this);
            }

            dialog.showAndWait();
            if (developmentCardInvention != null) {
                final DevelopmentCard card = developmentCardInvention;
                developmentCardInvention = null;
                playDevelopmentCard(card);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    @Override
    public void buyDevelopmentCard(final ActionEvent event) {
        if (isStepActual) {
            final boolean hasEnoughResources = currentPlayer.hasEnoughResources(Constants.DEVELOPMENT_CARD);
            if (hasEnoughResources) {
                currentPlayer.buyDevelopmentCard(chest);
            } else {
                outputNotPossible("not enough resources");
            }
        }
    }

    private void rollDie() {
        if (isStepActual) {
            die.rollDie();
            final Image img = new Image("./com/catan/assets/die" + die.getDice1() + ".png");
            getImgDie1().setFill(new ImagePattern(img));
            final Image img2 = new Image("./com/catan/assets/die" + die.getDice2() + ".png");
            getImgDie2().setFill(new ImagePattern(img2));
        }
    }

    @FXML
    public void makeConstruction(final MouseEvent mouseEvent) {
        final Circle circle = (Circle) mouseEvent.getSource();
        if (isStepInitial) {
            makeConstructionInitial(circle);
        } else if (isStepActual) {
            makeConstructionActual(circle);
        }
    }

    @FXML
    public void endTurn(final ActionEvent actionEvent) throws IOException {
        if (isStepInitial) {
            initialTurn();
        } else if (isStepActual) {
            actualTurn();
        }
    }

    @Override
    public void outputNotPossible(final String warningType) {
        final Label warning = getWarningLabel();
        final FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.0), warning);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        switch (warningType) {
        case "Not possible":
            warning.setText("Not Possible");
            warning.setOpacity(1);
            break;
        case "Thief":
            warning.setText("You must move the thief");
            warning.setOpacity(1);
            break;
        case "ProfitExchange":
            warning.setText("Please exchange the profits of terrain hexes by selecting them.");
            warning.setOpacity(1);
            break;
        case "RoadDestruction":
            warning.setText("Please destroy a road.");
            warning.setOpacity(1);
            break;
        default:
            warning.setText("Not enough resource for this type construction");
            warning.setOpacity(1);
            break;
        }
        fadeTransition.play();
    }

    private Road getCorrespondingRoad(final Line line) {
        for (final Road road : getRoads()) {
            if (road.getRoad() == line) {
                return road;
            }
        }
        return null;
    }

    private boolean isVertexSuitableForConstruction(final Vertex vertex) {
        final ArrayList<Vertex> neighbors = vertex.getNeighbors();
        for (final Vertex v : neighbors) {
            if (v.hasConstruction()) {
                return false;
            }
        }
        return true;
    }

    private void constructRoad() {
        final Road road = getCorrespondingRoad(roadVertex1, roadVertex2);
        if (road != null && !road.getRoad().isVisible()) {
            // coloring road.
            final Color color = currentPlayer.getRoadColor();
            road.getRoad().setStroke(color);
            road.getRoad().setVisible(true);
            // adding road;
            currentPlayer.getRoads().add(road);
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
            if (currentPlayer instanceof PlayerActual)
                outputNotPossible(Constants.CONSTRUCTION_STRING);
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

    private Road getCorrespondingRoad(final Vertex vertex1, final Vertex vertex2) {
        for (final Road road : getRoads()) {
            if (road.containsRoad(vertex1, vertex2)) {
                return road;
            }
        }
        return null;
    }

    private Vertex getCorrespondingVertex(final Circle shape) {
        for (final Vertex vertex : getVertices()) {
            if (vertex.getShape() == shape) {
                return vertex;
            }
        }
        return null;
    }

    private void activateAllVertices() {
        for (final Vertex vertex : getVertices()) {
            if (!vertex.hasConstruction()) {
                vertex.setActive(true);
            }
        }
    }

    private void deActivateAllVertices() {
        for (final Vertex vertex : getVertices()) {
            vertex.setActive(false);
        }
    }

    private ArrayList<Vertex> getActivatedVertices() {
        final ArrayList<Vertex> activated = new ArrayList<>();
        for (final Vertex vertex : getVertices()) {
            if (vertex.isActive()) {
                activated.add(vertex);
            }
        }
        return activated;
    }

    private void activatePlayerVertices() {
        deActivateAllVertices();
        final ArrayList<Road> roads = currentPlayer.getRoads();
        for (final Road road : roads) {
            for (final Vertex vertex : road.getVertices()) {
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

    private void activateNeighbours(final Vertex vertex) {
        for (final Vertex v : vertex.getNeighbors()) {
            final Settlement settlement = v.getSettlement();
            if (settlement == null || settlement.getPlayer() == currentPlayer) {
                final Road road = getCorrespondingRoad(vertex, v);
                if (road != null && !road.getRoad().isVisible()) {
                    v.setActive(true);
                }
            }
        }
    }

    private void setSelectedConstruction(final String selectedConstruction) {
        this.selectedConstruction = selectedConstruction;
    }

    private boolean isSelectedSettlement() {
        return selectedConstruction.equals(Constants.VILLAGE) || selectedConstruction.equals(Constants.CITY)
                || selectedConstruction.equals(Constants.CIVILISATION);
    }

    // ------------ ACTUAL STEP METHODS ---------------- //

    private void actualTurn() {
        developmentCardExchangeProfit = null;
        developmentCardDestroyRoad = null;
        playerTurn = playerTurn % 4;
        currentPlayer = getPlayers().get(playerTurn++);
        rollDie();

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println(currentPlayer.getName() + " : " + currentPlayer.getColor() + " | Die Result: " + die.getDieSum());
        System.out.println("----------------------------------------------------------------------------------------------");

        //  doing thief operations.
        if (die.getDieSum() == 7) {
            thiefResourceCardPunishAI();
            thiefResourceCardPunishActual();
            playThief(currentPlayer);
        }

        getTurnProfit();

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
    public void makeTradeForAI(final boolean isTradeWithChest) {
        if (isTradeWithChest) { // trade with chest
            final HashMap<String, Integer> requestedRC = ((PlayerAI) currentPlayer).getRequestedResourceCardForChest();
            final HashMap<String, Integer> offeredRC = ((PlayerAI) currentPlayer)
                    .getOfferedResourceCardForChest(requestedRC);
            new Trade(currentPlayer, null, requestedRC, offeredRC, isTradeWithChest);
        }

        else { // trade between players
            final int idOfPlayerToBeTraded = (int) (Math.random() * 4);
            final Player playerToBeTraded = getPlayers().get(idOfPlayerToBeTraded);

            if (currentPlayer instanceof PlayerAI && playerToBeTraded != currentPlayer) {
                // setting trade materials
                final HashMap<String, Integer> requestedRC = ((PlayerAI) currentPlayer)
                        .getRequestedResourceCards(playerToBeTraded);
                final HashMap<String, Integer> offeredRC = ((PlayerAI) currentPlayer)
                        .getOfferedResourceCards(requestedRC);
                final Trade trade = new Trade(currentPlayer, playerToBeTraded, requestedRC, offeredRC,
                        isTradeWithChest);

                // trade request sent to actual player by playerAI
                if (playerToBeTraded == playerActual && trade.isTradePossible()) {
                    openDialog(Constants.PATH_VIEW_TRADE_REQUEST, "Incoming Trade Offer", null, trade);
                }
            }
        }
    }

    private void thiefResourceCardPunishActual() {
        if (playerActual.getTotalCards() > 7) {
            openDialog(Constants.PATH_VIEW_PUNISHMENT, "Thief will steal something", null, null);
        }
    }

    private void thiefResourceCardPunishAI() {
        for (int i = 0; i < getPlayers().size(); i++) {
            final Player player = getPlayers().get(i);
            if (player instanceof PlayerAI && player.getTotalCards() > 7) {
                ((PlayerAI) player).punish();
            }
        }
    }

    private void playThief(final Player currentPlayer) {
        if (currentPlayer instanceof PlayerAI) {
            final int randomHexIndex = (int) (Math.random() * 19);
            thief.setTerrainHex(terrainHexes.get(randomHexIndex));
            thief.punishUsersAroundHexByThief(currentPlayer);
        } else if (currentPlayer instanceof PlayerActual) {
            thief.setCanThiefMove(true);
            outputNotPossible(Constants.THIEF_STRING);
        }
    }

    @FXML
    public void moveThief(final MouseEvent mouseEvent) {
        if (thief.canThiefMove()) {
            thief.updateLocation(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        }
    }

    @FXML
    public void thiefMoved(final MouseEvent mouseEvent) {
        if (thief.canThiefMove()) {
            final TerrainHex newHex = getHexWithCoordinates(imgMovingThief);
            if (newHex != null) {
                thief.setCanThiefMove(false);
                thief.setTerrainHex(newHex);
                thief.punishUsersAroundHexByThief(currentPlayer);
            }
        }
    }

    private void getTurnProfit() {
        System.out.println(
                "----------------------------------------------------------------------------------------------");
        for (final Player player : getPlayers()) {
            player.getTurnProfit(die.getDieSum(), thief.getTerrainHex());
            player.showSourceCards();
        }
        System.out.println(
                "----------------------------------------------------------------------------------------------");
    }

    @Override
    public void makeConstructionActual(final Circle circle) {
        if (!currentPlayer.hasEnoughResources(selectedConstruction)) {
            if (currentPlayer instanceof PlayerActual)
                outputNotPossible(Constants.CONSTRUCTION_STRING);
            return;
        }
        if (isSelectedSettlement()) {
            final Vertex vertex = getCorrespondingVertex(circle);
            if (vertex != null && isVertexSuitableForConstruction(vertex) && vertex.isActive()) {
                Settlement settlement;
                String imagePath = "";
                switch (selectedConstruction) {
                case Constants.CITY:
                    imagePath = currentPlayer.getSettlementImagePath(Constants.CITY);
                    settlement = new City(imagePath, vertex, currentPlayer);
                    break;
                case Constants.VILLAGE:
                    imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                    settlement = new Village(imagePath, vertex, currentPlayer);
                    break;
                default:
                    imagePath = currentPlayer.getSettlementImagePath(Constants.CIVILISATION);
                    settlement = new Civilisation(imagePath, vertex, currentPlayer);
                    break;
                }
                final Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(Constants.CONSTRUCTION_RADIUS);
                getSettlements().add(settlement);
                currentPlayer.getSettlements().add(settlement);
                vertex.setSettlement(settlement);
                currentPlayer.subtractPriceOfConstruction(selectedConstruction);
                currentPlayer.showSourceCards();
                unselectConstructions(null);
                deActivateAllVertices();
            } else {
                if (currentPlayer instanceof PlayerActual)
                    outputNotPossible("Not possible");
            }
        } else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (currentPlayer instanceof PlayerActual && isStepInitial && !isRoadBuild) {
                    selectConstructionActual(imgRoad);
                }
                constructionUnselect = false;
                roadVertex1 = getCorrespondingVertex(circle);
                if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS) {
                    circle.setFill(Constants.COLOR_ROAD_SELECTION_VERTEX);
                }
            } else {
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
            final ArrayList<Road> roads = currentPlayer.getRoads();
            final ArrayList<Vertex> vertices = new ArrayList<>();
            for (final Road road : roads) {
                for (final Vertex vertex : road.getVertices()) {
                    if (!vertices.contains(vertex)) {
                        vertices.add(vertex);
                    }
                }
            }
            // setting first vertex of road;
            int index = (int) (Math.random() * vertices.size());
            final Vertex vertex1 = vertices.get(index);
            // setting second vertex of road;
            index = (int) (Math.random() * vertex1.getNeighbors().size());
            final Vertex vertex2 = vertex1.getNeighbors().get(index);
            final Road road = getCorrespondingRoad(vertex1, vertex2);

            if (road != null && !road.getRoad().isVisible()) {
                makeConstructionActual(vertex1.getShape());
                makeConstructionActual(vertex2.getShape());
                isRoadConstructed = true;
            }
        }
    }

    @Override
    public void makeVillageActualForAI() {
        final ArrayList<Vertex> vertices = getActivatedVertices();
        if (vertices.size() > 0) {
            final int index = (int) (Math.random() * vertices.size());
            final Circle circle = vertices.get(index).getShape();
            makeConstructionActual(circle);
        }
    }

    @Override
    public void selectActualConstructionForAI(final String type) {
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

    private void selectConstructionActual(final Rectangle rectangle) {
        // setting all of the pane as unselected
        imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);

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
            outputNotPossible(Constants.CONSTRUCTION_STRING);
            return;
        }

        activatePlayerVertices();

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }

    // ------------ INITIAL STEP METHODS ---------------- //

    private void initialTurn() throws IOException {
        while (true) {
            playerTurn = playerTurn % 4;
            final Player player = getPlayers().get(playerTurn);
            currentPlayer = player;
            activateAllVertices();

            // AI player
            if (player instanceof PlayerAI) {
                playAIInitialTurn();
                isRoadBuild = false;
                isConstructionBuild = false;
            }
            // Actual Player
            else if (player instanceof PlayerActual) {
                if ((isStepInitial && isRoadBuild && isConstructionBuild)) {
                    isRoadBuild = false;
                    isConstructionBuild = false;
                } else {
                    if (currentPlayer instanceof PlayerActual)
                        outputNotPossible("Not possible");
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

        while (tempSettlement == null || tempRoad == null) {
            if ((tempRoad != null)) {
                tempRoad.getRoad().setStroke(Color.BLACK);
                tempRoad.getRoad().setVisible(false);
                currentPlayer.getRoads().remove(tempRoad);
                tempRoad = null;
            }

            activateAllVertices();
            setSelectedConstruction(Constants.ROAD);

            // setting first vertex of road;
            int index = (int) (Math.random() * getActivatedVertices().size());
            Vertex vertex = getActivatedVertices().get(index);
            makeConstructionInitial(vertex.getShape());

            // setting second vertex of road;
            index = (int) (Math.random() * vertex.getNeighbors().size());
            vertex = vertex.getNeighbors().get(index);
            makeConstructionInitial(vertex.getShape());

            setSelectedConstruction(Constants.VILLAGE);
            if (tempRoad != null) {
                final ArrayList<Vertex> twoVertex = new ArrayList<>();
                twoVertex.add(tempRoad.getVertices().get(0));
                twoVertex.add(tempRoad.getVertices().get(1));

                if (isVertexSuitableForConstruction(twoVertex.get(0))) {
                    makeConstructionInitial(twoVertex.get(0).getShape());
                } else if (isVertexSuitableForConstruction(twoVertex.get(1))) {
                    makeConstructionInitial(twoVertex.get(0).getShape());
                }
            }
        }
        tempRoad = null;
        tempSettlement = null;
    }

    private void makeConstructionInitial(final Circle circle) {
        if (selectedConstruction.equals(Constants.CITY) || selectedConstruction.equals(Constants.VILLAGE)
                || selectedConstruction.equals(Constants.CIVILISATION)) {

            if (isRoadBuild && !isConstructionBuild) {
                selectConstructionInitial(imgVillage);
            }

            final Vertex vertex = getCorrespondingVertex(circle);
            if (vertex != null && isVertexSuitableForConstruction(vertex) && vertex.isActive()) {
                isConstructionBuild = true;
                final String imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                final Settlement settlement = new Village(imagePath, vertex, currentPlayer);

                // loading image
                final Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(Constants.CONSTRUCTION_RADIUS);

                // adding settlement
                getSettlements().add(settlement);
                currentPlayer.getSettlements().add(settlement);
                vertex.setSettlement(settlement);
                unselectConstructions(null);
                activatePlayerVertices();
                tempSettlement = settlement;

            } else {
                if (currentPlayer instanceof PlayerActual)
                    outputNotPossible("Not possible");
            }
        } else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (!isRoadBuild) {
                    selectConstructionInitial(imgRoad);
                }

                constructionUnselect = false;
                final Vertex vertex = getCorrespondingVertex(circle);
                if (vertex != null && !vertex.hasConstruction()) {
                    roadVertex1 = vertex;
                    final Color color = Constants.COLOR_ROAD_SELECTION_VERTEX;
                    roadVertex1.getShape().setFill(color);
                }
            } else {
                constructionUnselect = true;
                final Vertex vertex = getCorrespondingVertex(circle);
                if (vertex != null && !vertex.hasConstruction()) {
                    roadVertex2 = vertex;
                    constructRoad();
                } else {
                    refreshRoadSelectionVertices();
                    if(currentPlayer instanceof PlayerActual)
                        outputNotPossible("Not possible");
                }
            }
        }
    }

    private void selectConstructionInitial(Rectangle rectangle) {
        // setting all of the pane as unselected
        imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
        imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);

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

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }
}