package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;
import com.catan.interfaces.InterfaceUpdateGameAfterPopUp;
import com.catan.modal.*;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.security.SelectableSecurityManager;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.IIOParam;
import javax.naming.ldap.Control;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ControllerGame extends ControllerBaseGame implements InterfaceMakeConstruction, InterfaceUpdateGameAfterPopUp {

    // properties
    private boolean constructionUnselect = true;
    private String selectedConstruction = "";
    private Vertex roadVertex1 = null;
    private Vertex roadVertex2 = null;
    private boolean isStepInitial = true;
    private boolean isStepActual = false;
    private boolean isConstructionBuild = false;
    private boolean isRoadBuild = false;
    private int initialStepCount = 0;
    private Player currentPlayer;
    private int playerTurn = 0;
    private boolean thiefCanMove = false;
    private boolean initialThief = true;
    private List<String[]> gameLog = new ArrayList<>();
    int gameLogIterator = 0;
    private int noOfRound = 1;
    FlowPane gameLogsFlowPane;
    private ArrayList<ImageView> lumberImages = new ArrayList<>();
    private ArrayList<ImageView> brickImages = new ArrayList<>();
    private ArrayList<ImageView> grainImages = new ArrayList<>();
    private ArrayList<ImageView> oreImages = new ArrayList<>();
    private ArrayList<ImageView> woolImages = new ArrayList<>();
    private Pane[] cardPanes = new Pane[5];
    double[][] cardsPaneLocations = new double[5][2];

    @Override
    public void initialize() {
        super.initialize();
        selectConstructionInitial(imgRoad);
        currentPlayer = getPlayers().get(0);
        activateAllVertices();
        gameLogsFlowPane = (FlowPane)gameLogsScrollPane.getContent();

        initializeActualPlayerCardsPaneRelatedComponents();
    }

    private void initializeActualPlayerCardsPaneRelatedComponents() {
        dummyLumberImageView.setVisible(false);
        lumberImages.add(dummyLumberImageView);
        dummyBrickImageView.setVisible(false);
        brickImages.add(dummyBrickImageView);
        dummyGrainImageView.setVisible(false);
        grainImages.add(dummyGrainImageView);
        dummyOreImageView.setVisible(false);
        oreImages.add(dummyOreImageView);
        dummyWoolImageView.setVisible(false);
        woolImages.add(dummyWoolImageView);

        cardPanes[0] = lumbersPane;
        cardPanes[1] = woolsPane;
        cardPanes[2] = oresPane;
        cardPanes[3] = bricksPane;
        cardPanes[4] = grainsPane;

        cardsPaneLocations[0] = new double[] {lumbersPane.getLayoutX(), lumbersPane.getLayoutY()};
        cardsPaneLocations[1] = new double[] {woolsPane.getLayoutX(), woolsPane.getLayoutY()};
        cardsPaneLocations[2] = new double[] {bricksPane.getLayoutX(), bricksPane.getLayoutY()};
        cardsPaneLocations[3] = new double[] {oresPane.getLayoutX(), oresPane.getLayoutY()};
        cardsPaneLocations[4] = new double[] {grainsPane.getLayoutX(), grainsPane.getLayoutY()};
    }

    @FXML
    public void selectConstruction(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) mouseEvent.getSource();
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
    void trade(ActionEvent event) throws IOException {
        if (isStepActual) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(root.getScene().getWindow());

            FXMLLoader fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getClassLoader().getResource("com/catan/view/trade.fxml"));

            dialog.setTitle("Trade");
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            ControllerTrade tradeController = fxmlLoader.getController();
            tradeController.setActualPlayerAndLabels(getPlayers().get(0)); // actual player
            tradeController.passPlayersAL(getPlayers());
            Optional<ButtonType> inputOfUser = dialog.showAndWait();
        }
    }

    @FXML
    void playDevelopmentCard(ActionEvent event) {
        if (isStepActual) {
            // TODO playDevelopmentCard will be implemented
        }
    }

    @FXML
    void buyDevelopmentCard(ActionEvent event) {
        if (isStepActual) {
            // TODO buyDevelopmentCard will be implemented
        }
    }

    void rollDie() {
        if (isStepActual) {
            die.rollDie();
            Image img = new Image("./com/catan/assets/die"+die.getDice1()+".png");
            getImgDie1().setFill(new ImagePattern(img));
            getImgDie1().setStroke(Color.color(0.4,0.4,0.4));
            getImgDie1().setStrokeWidth(1);
            Image img2 = new Image("./com/catan/assets/die"+die.getDice2()+".png");
            getImgDie2().setFill(new ImagePattern(img2));
            getImgDie2().setStroke(Color.color(0.4,0.4,0.4));
            getImgDie2().setStrokeWidth(1);
        }
    }

    @FXML
    public void makeConstruction(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        if (isStepInitial) {
            makeConstructionInitial(circle);
        } else if (isStepActual) {
            makeConstructionActual(circle);
        }
    }

    @FXML
    public void endTurn(ActionEvent actionEvent) throws IOException {
        if(isStepInitial) {
            initialTurn();
        } else if (isStepActual) {
            //actualTurn();
            preActualTurn();
            gameLog.add(new String[] {"Round "  + noOfRound + " has ended.", "-1"});
            noOfRound++;
        }
        updateGameLogsInView();
        updateCardsOfActualPlayerInView();
    }

    private void updateCardsOfActualPlayerInView() {
        updateNumbersOfCardsInPanes();
        updateLocationsOfCardPanes();
    }

    private void updateNumbersOfCardsInPanes() {
        int imgHeight = 87;
        int imgWidth = 60;
        int spaceBetweenImages = 6;
        Player actualPlayer = getPlayers().get(0);
        HashMap<String, ArrayList<SourceCard>> sourceCards = actualPlayer.getSourceCards();
        Set<String> keys = sourceCards.keySet();

        for (String key: keys) {
            String noOfCards = sourceCards.get(key).size() == 0 ? "" : sourceCards.get(key).size() + "";
            if (key.equals("wool")) {
                woolLabel.setText(noOfCards);
                while (sourceCards.get(key).size() > woolImages.size() - 1) {
                    ImageView imgToAdd = new ImageView("./com/catan/assets/resource_sheep.jpg");
                    imgToAdd.setLayoutX(woolImages.get(woolImages.size() - 1).getLayoutX() + spaceBetweenImages);
                    imgToAdd.setLayoutY(woolImages.get(woolImages.size() - 1).getLayoutY());
                    imgToAdd.setFitHeight(imgHeight);
                    imgToAdd.setFitWidth(imgWidth);
                    woolImages.add(imgToAdd);
                    woolsPane.getChildren().add(imgToAdd);
                }
                while (sourceCards.get(key).size() < woolImages.size() - 1) {
                    woolsPane.getChildren().remove(woolImages.get(woolImages.size() - 1));
                    woolImages.remove(woolImages.size() - 1);
                }
            } else if (key.equals("ore")) {
                oreLabel.setText(noOfCards);
                while (sourceCards.get(key).size() > oreImages.size() - 1) {
                    ImageView imgToAdd = new ImageView("./com/catan/assets/resource_ore.jpg");
                    imgToAdd.setLayoutX(oreImages.get(oreImages.size() - 1).getLayoutX() + spaceBetweenImages);
                    imgToAdd.setLayoutY(oreImages.get(oreImages.size() - 1).getLayoutY());
                    imgToAdd.setFitHeight(imgHeight);
                    imgToAdd.setFitWidth(imgWidth);
                    oreImages.add(imgToAdd);
                    oresPane.getChildren().add(imgToAdd);
                }
                while (sourceCards.get(key).size() < oreImages.size() - 1) {
                    oresPane.getChildren().remove(oreImages.get(oreImages.size() - 1));
                    oreImages.remove(oreImages.size() - 1);
                }
            } else if (key.equals("lumber")) {
                lumberLabel.setText(noOfCards);
                // size() - 1 because there will always be a dummy node in the list
                while (sourceCards.get(key).size() > lumberImages.size() - 1) {
                    ImageView imgToAdd = new ImageView("./com/catan/assets/resource_wood.jpg");
                    imgToAdd.setLayoutX(lumberImages.get(lumberImages.size() - 1).getLayoutX() + spaceBetweenImages);
                    imgToAdd.setLayoutY(lumberImages.get(lumberImages.size() - 1).getLayoutY());
                    imgToAdd.setFitHeight(imgHeight);
                    imgToAdd.setFitWidth(imgWidth);
                    lumberImages.add(imgToAdd);
                    lumbersPane.getChildren().add(imgToAdd);
                }
                while (sourceCards.get(key).size() < lumberImages.size() - 1) {
                    lumbersPane.getChildren().remove(lumberImages.get(lumberImages.size() - 1));
                    lumberImages.remove(lumberImages.size() - 1);
                }
            } if (key.equals("brick")) {
                brickLabel.setText(noOfCards);
                while (sourceCards.get(key).size() > brickImages.size() - 1) {
                    ImageView imgToAdd = new ImageView("./com/catan/assets/resource_brick.jpg");
                    imgToAdd.setLayoutX(brickImages.get(brickImages.size() - 1).getLayoutX() + spaceBetweenImages);
                    imgToAdd.setLayoutY(brickImages.get(brickImages.size() - 1).getLayoutY());
                    imgToAdd.setFitHeight(imgHeight);
                    imgToAdd.setFitWidth(imgWidth);
                    brickImages.add(imgToAdd);
                    bricksPane.getChildren().add(imgToAdd);
                }
                while (sourceCards.get(key).size() < brickImages.size() - 1) {
                    bricksPane.getChildren().remove(brickImages.get(brickImages.size() - 1));
                    brickImages.remove(brickImages.size() - 1);
                }
            } if (key.equals("grain")) {
                grainLabel.setText(noOfCards);
                while (sourceCards.get(key).size() > grainImages.size() - 1) {
                    ImageView imgToAdd = new ImageView("./com/catan/assets/resource_grain.jpg");
                    imgToAdd.setLayoutX(grainImages.get(grainImages.size() - 1).getLayoutX() + spaceBetweenImages);
                    imgToAdd.setLayoutY(grainImages.get(grainImages.size() - 1).getLayoutY());
                    imgToAdd.setFitHeight(imgHeight);
                    imgToAdd.setFitWidth(imgWidth);
                    grainImages.add(imgToAdd);
                    grainsPane.getChildren().add(imgToAdd);
                }
                while (sourceCards.get(key).size() < grainImages.size() - 1) {
                    grainsPane.getChildren().remove(grainImages.get(grainImages.size() - 1));
                    grainImages.remove(grainImages.size() - 1);
                }
            }
        }
    }

    private void updateLocationsOfCardPanes() {
        Arrays.sort(cardPanes, (a, b) -> b.getChildren().size() - a.getChildren().size());
        for (int i = 0; i < cardPanes.length; i++) {
            cardPanes[i].setLayoutX(cardsPaneLocations[i][0]);
            cardPanes[i].setLayoutY(cardsPaneLocations[i][1]);
        }
    }

    private void updateGameLogsInView() {
        for (; gameLogIterator < gameLog.size(); gameLogIterator++) {
            String[] log = gameLog.get(gameLogIterator);
            Label logLabel = new Label("  " + log[0]);
            logLabel.setMinWidth(400);
            logLabel.setMinHeight(35);
            logLabel.setCursor(Cursor.DEFAULT);
            String marginProperty = " -fx-padding: 2px;" + "-fx-border-insets: 2px;" + "-fx-background-insets: 2px;";
            if (log[0].indexOf("has ended") >= 0) {
                logLabel.setStyle("-fx-background-color: gray; -fx-text-fill: white;" + marginProperty);
            } else {
                Player playerOfLog = getPlayers().get(Integer.parseInt(log[1]));
                String playerColor = playerOfLog.getColor();
                logLabel.setStyle("-fx-background-color:" + playerColor + ";" + "-fx-text-fill: white;" + marginProperty);
            }
            gameLogsFlowPane.getChildren().add(0, logLabel);
        }
    }

    private void outputNotPossible(String warningType) {
        Label warning = getWarningLabel();
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.0), warning);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        //fadeTransition.setCycleCount(Animation.INDEFINITE);

        if(warningType.equals("Not possible")) {
            warning.setText("Not Possible");
            warning.setOpacity(1);
        }
        else if(warningType.equals("Thief")){
            warning.setText("You must move the thief");
            warning.setOpacity(1);
        }else{
            warning.setText("Not enough resource for this type construction");
            warning.setOpacity(1);
        }
        fadeTransition.play();
    }

    private boolean isVertexSuitableForConstruction(Vertex vertex) {
        ArrayList<Vertex> neighbors = vertex.getNeighbors();
        for (Vertex v: neighbors) {
            if (v.hasConstruction()) {
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
            Player player = getPlayers().get(playerTurn);
            currentPlayer = player;
            if(currentPlayer instanceof PlayerActual)
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

    private Road getCorrespondingRoad(Vertex vertex1, Vertex vertex2) {
        for (Road road: getRoads()) {
            if (road.containsRoad(vertex1, vertex2)) {
                return road;
            }
        }
        return null;
    }

    private Vertex getCorrespondingVertex(Circle shape) {
        for (Vertex vertex: getVertices()) {
            if (vertex.getShape() == shape) {
                return vertex;
            }
        }
        return null;
    }

    private Road tempRoad;
    private Settlement tempSettlement;


    public void activateAllVertices() {
        for (Vertex vertex: getVertices()) {
            if (!vertex.hasConstruction())
                vertex.setActive(true);
        }
    }

    public void deActivateAllVertices() {
        for (Vertex vertex: getVertices()) {
            vertex.setActive(false);
        }
    }

    public ArrayList<Vertex> getActivatedVertices() {
        ArrayList<Vertex> activated = new ArrayList<>();
        for(Vertex vertex: getVertices()) {
            if (vertex.isActive()) {
                activated.add(vertex);
            }
        }
        return activated;
    }

    public void activatePlayerVertices() {
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

    public void  activateNeighbours(Vertex vertex) {
        for (Vertex v: vertex.getNeighbors()) {
            Settlement settlement = v.getSettlement();
            if (settlement == null || settlement.getPlayer() == currentPlayer) {
                Road road = getCorrespondingRoad(vertex, v);
                if (road != null && !road.getRoad().isVisible()) {
                    v.setActive(true);
                }
            }
        }
    }

    public void setSelectedConstruction(String selectedConstruction) {
        this.selectedConstruction = selectedConstruction;
    }

    public boolean isSelectedSettlement() {
        return selectedConstruction.equals(Constants.VILLAGE)||
               selectedConstruction.equals(Constants.CITY)   ||
               selectedConstruction.equals(Constants.CIVILISATION);
    }

    // ------------ ACTUAL STEP METHODS ---------------- //

    private void preActualTurn() throws IOException {
        boolean gameWillContinue = true;
        playerTurn = playerTurn % 4;
        Player player = getPlayers().get(playerTurn);
        System.out.println(player.getName() + " : " + player.getColor());
        rollDie();
        System.out.println("die result: " + die.getDieSum());
        gameLog.add(new String[] {"Player " + playerTurn + ": has rolled " + die.getDieSum() + ".", "" + (playerTurn % 4)});
        if (die.getDieSum() == 7) {
            thiefResourceCardPunishAI();
            thiefResourceCardPunish();
            gameWillContinue = false;
        }
        // game will not contiuno if the player has to choose cards first.
        if (gameWillContinue) {
            actualTurn();
        }
    }
    private void actualTurn() throws IOException {
        deActivateAllVertices();
        playerTurn = playerTurn % 4;
        Player player = getPlayers().get(playerTurn);
        currentPlayer = player;
        playerTurn++;

        //  playing the thief
        if (die.getDieSum() == 7) {
            playThief(currentPlayer);
        }

        // AI player Trade
        boolean isTradeWithChest = Math.random() < 0.3;
        int noPlayerTradingWith = (int)(Math.random() * 100) % 4;

        if (currentPlayer instanceof PlayerAI && !getPlayers().get(noPlayerTradingWith).getName().equals(currentPlayer.getName())) {
            // setting trade materials
            Player tradingWith = getPlayers().get(noPlayerTradingWith);
            Map<String, Integer> requestedRC = ((PlayerAI)currentPlayer).getRequestedResourceCards();
            Map<String, Integer> offeredRC = ((PlayerAI)currentPlayer).getOfferedResourceCards(requestedRC);

            //trade request sent to actual player by playerAI
            if (!isTradeWithChest && tradingWith == playerActual) {
                //view pop up trade invitation to game scene
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(root.getScene().getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getClassLoader()
                        .getResource(Constants.PATH_VIEW_TRADE_REQUEST));
                dialog.setTitle("Incoming Trade Offer");
                dialog.getDialogPane().setContent(fxmlLoader.load());
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                ControllerTradeRequest tradeRequestController = fxmlLoader.getController();
                tradeRequestController.setActualPlayerAndLabels(tradingWith, currentPlayer, requestedRC, offeredRC);
            } // trade request sent to AI player by playerAI
            else if (tradingWith != playerActual) {
                Trade tradeAI = new Trade(currentPlayer, tradingWith, requestedRC, offeredRC, isTradeWithChest);
//                tradeAI.requestTrade();

                // output
                if (isTradeWithChest) {
                    System.out.println("Trade between " + player.getName() + " and CHEST " + " is " + tradeAI.isTradePossible());
                } else {
                    System.out.println("Trade between " + currentPlayer.getName() + " and " + tradingWith.getName() + " is " + tradeAI.isTradePossible());
                }
            }

        }
        getTurnProfit();
        // AI player
        if (player instanceof PlayerAI) {
            playAIActualTurn();
            preActualTurn();
        }
        // Actual Player
        else if (player instanceof PlayerActual) {
            deActivateAllVertices();
            refreshRoadSelectionVertices();
            constructionUnselect = true;
            unselectConstructions(null);
        }
    }

    private void thiefResourceCardPunish() throws IOException {
        Player realPlayer = null;
        for(Player player: getPlayers()){
            if(player instanceof PlayerActual)
            {
                realPlayer = player;
            }
        }
        if(realPlayer.getTotalCards() > 7){
            Stage window = (Stage) root.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.THIEF_VIEW));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerThiefPunishment controller = loader.getController();
            controller.setPlayer(realPlayer, this);
            final Stage pop = new Stage();
            pop.initModality(Modality.APPLICATION_MODAL);
            pop.initOwner(window);
            pop.setTitle("Thief will steal something");
            pop.setScene(new Scene(root, 375, 480));
            pop.show();
        }else{
            updateGameAfterPopUp();
        }
    }

    private void thiefResourceCardPunishAI(){
        for (int i = 0; i < getPlayers().size(); i++) {
            Player temp = getPlayers().get(i);
            if ( temp instanceof PlayerAI && temp.getTotalCards() > 7) {
                ((PlayerAI)temp).punish();
            }
        }
    }

    // i created this function to stop game, game continuous when use finishes selecting card by updateGame function

    private void playThief(Player currentPlayer){
        thiefCanMove = true;
        if (initialThief) {
            imgThiefDefaultLocation.setVisible(false);
            imgMovingThief.setLayoutX(imgThiefDefaultLocation.getLayoutX());
            imgMovingThief.setLayoutY(imgThiefDefaultLocation.getLayoutY());
            imgMovingThief.setVisible(true);
            initialThief = false;
        }
        if (currentPlayer instanceof PlayerAI) {
                int randomHex = (int)(Math.random()*18) + 1;
                thiefHexLoca.setThiefHere(false);
                TerrainHex thiefsNewLocation = getHexWithIndex(randomHex);
                thiefHexLoca = thiefsNewLocation;
                imgMovingThief.setLayoutX(thiefsNewLocation.getCircleNumberOnHex().getLayoutX());
                imgMovingThief.setLayoutY(thiefsNewLocation.getCircleNumberOnHex().getLayoutY());
                thiefsNewLocation.setThiefHere(true);
                thiefCanMove = false;
                punishThief();
        }
        // Actual Player
        else if (currentPlayer instanceof  PlayerActual) {
            outputNotPossible(Constants.THIEF_STRING);
        }
    }

    @FXML
    public void moveThief(MouseEvent mouseEvent){
        if (thiefCanMove) {
            thiefHexLoca.setThiefHere(false);
            imgMovingThief.setLayoutX(mouseEvent.getSceneX());
            imgMovingThief.setLayoutY(mouseEvent.getSceneY());
        }
    }

    @FXML
    public void thiefMoved(MouseEvent mouseEvent){
        if (thiefCanMove) {
            thiefHexLoca.setThiefHere(false);
            TerrainHex thiefsNewLocation = getHexWithCoordinates(imgMovingThief);
            if (thiefsNewLocation != null) {
                thiefCanMove = false;
                thiefHexLoca = thiefsNewLocation;
                imgMovingThief.setLayoutX(thiefsNewLocation.getCircleNumberOnHex().getLayoutX());
                imgMovingThief.setLayoutY(thiefsNewLocation.getCircleNumberOnHex().getLayoutY());
                thiefsNewLocation.setThiefHere(true);
                punishThief();
            }
        }
    }
    private void punishThief(){
        ArrayList<Player> players = thiefHexLoca.getPlayersAroundHere();
        int size = players.size();
        if (size == 0) {
            //there is nobody around thief
            return;
        }
        if (size == 1 && players.get(0) == currentPlayer) {
            //there is only one person and it is him
            return;
        }
        // if there is nobody around here, it will do nothing
        int randomPlayer = (int)Math.random()*size;

        Player playerToBePunished = null;
        boolean choosingPlayerToPunishIsNotOver = true;
        boolean noVictim = false;
        int count = 0;
        while (choosingPlayerToPunishIsNotOver)
        {
            playerToBePunished = players.get(randomPlayer);
            if (playerToBePunished != currentPlayer && playerToBePunished.getTotalCards() != 0) {
                choosingPlayerToPunishIsNotOver = false;
            } else {
                randomPlayer = (randomPlayer == 0) ? (size - 1) : (randomPlayer -1);
                count++;
                if (count == size) {
                    noVictim = true;
                    //nobody has any source, so playerToBePunished will be null afterwads.
                    choosingPlayerToPunishIsNotOver = false;
                }
            }
        }
        if (noVictim) // it means there is no appropriate player to steal from.
        {
            playerToBePunished = null;
        }
        if (playerToBePunished != null) { // it maybe null in the case that nobody has any source
            SourceCard punish = playerToBePunished.getPunishedByThief();
            currentPlayer.addResourceFromThief(punish);
        }
    }

    @Override
    public void updateGameAfterPopUp() throws IOException {
    //here the resources of player will be updated, this part has dependency of talha's work. so this will wait
            actualTurn();
            updateCardsOfActualPlayerInView();
    }
    private void getTurnProfit() {
        System.out.println("----------------------------------------------------------------------");
        for (Player player: getPlayers()) {
            player.getTurnProfit(die.getDieSum());
            player.showSourceCards();
        }
        System.out.println("----------------------------------------------------------------------");
    }

    private void playAIActualTurn() {
        ((PlayerAI) currentPlayer).getActualAIDecision(this, playerTurn, gameLog);
    }

    @Override
    public void makeConstructionActual(Circle circle) {
        if (!currentPlayer.hasEnoughResources(selectedConstruction)) {
            if(currentPlayer instanceof PlayerActual)
                outputNotPossible(Constants.CONSTRUCTION_STRING);
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
                        // FIXME: playerTurn is not who it should correspond to
                        // FIXME: like it should be blue but it is purple?
                        gameLog.add(new String[] {"Player " + playerTurn + ": has built a city.", "" + playerTurn});
                        break;
                    case Constants.VILLAGE:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                        settlement = new Village(imagePath, vertex, currentPlayer);
                        gameLog.add(new String[] {"Player " + playerTurn + ": has built a village.", "" + (playerTurn % 4)});
                        break;
                    default:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.CIVILISATION);
                        settlement = new Civilisation(imagePath, vertex, currentPlayer);
                        gameLog.add(new String[] {"Player " + playerTurn + ": has built civilisation.", "" + (playerTurn % 4)});
                        break;
                }
                Image img = new Image(settlement.getImagePath());
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
                if(currentPlayer instanceof PlayerActual)
                    outputNotPossible("Not possible");
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
            index = (int) (Math.random() * vertex1.getNeighbors().size());
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

    public void selectConstructionActual(Rectangle rectangle) {
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
        System.out.println("INITAL TURN");
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
                //actualTurn();
                preActualTurn();
                return;
            }
        }
    }

    private void playAIInitialTurn() {
        try {
            //Thread.sleep(1000);
            System.out.println("WAITED 1 SEC");
        }
        catch(Exception ex) {
            System.out.println("THREAD ERROR!");
            Thread.currentThread().interrupt();
        }
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

            //gameLog.add("Player " + playerTurn + ": has built road.");

            setSelectedConstruction(Constants.VILLAGE);
            if (tempRoad != null) {
                ArrayList<Vertex> twoVertex = new ArrayList<>();
                twoVertex.add(tempRoad.getVertices().get(0));
                twoVertex.add(tempRoad.getVertices().get(1));
                //gameLog.add("Player " + playerTurn + ": has built village.");
                if (isVertexSuitableForConstruction(twoVertex.get(0))) {
                    makeConstructionInitial(twoVertex.get(0).getShape());
                }
                else if (isVertexSuitableForConstruction(twoVertex.get(1))) {
                    makeConstructionInitial(twoVertex.get(0).getShape());
                }
            }
        }
        tempRoad = null;
        tempSettlement = null;
    }

    private void makeConstructionInitial(Circle circle) {
        if (selectedConstruction.equals(Constants.CITY)   ||
                selectedConstruction.equals(Constants.VILLAGE)||
                selectedConstruction.equals(Constants.CIVILISATION)) {

            if (isRoadBuild && !isConstructionBuild) {
                selectConstructionInitial(imgVillage);
            }

            Vertex vertex = getCorrespondingVertex(circle);
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
                unselectConstructions(null);
                activatePlayerVertices();
                tempSettlement = settlement;
                System.out.println("ADDED SETTLEMENTs");
            } else {
                if(currentPlayer instanceof PlayerActual)
                    outputNotPossible("Not possible");
            }
        }
        else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (!isRoadBuild) {
                    selectConstructionInitial(imgRoad);
                }

                constructionUnselect = false;
                Vertex vertex = getCorrespondingVertex(circle);
                if (vertex != null && !vertex.hasConstruction()) {
                    roadVertex1 = vertex;
                    Color color = Constants.COLOR_ROAD_SELECTION_VERTEX;
                    roadVertex1.getShape().setFill(color);
                }
            }
            else {
                constructionUnselect = true;
                Vertex vertex = getCorrespondingVertex(circle);
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