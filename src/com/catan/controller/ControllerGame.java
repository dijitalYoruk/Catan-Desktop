package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;
import com.catan.modal.*;
import com.sun.deploy.security.SelectableSecurityManager;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

import javax.naming.ldap.Control;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ControllerGame extends ControllerBaseGame implements InterfaceMakeConstruction {

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
    @Override
    public void initialize() {
        super.initialize();
        selectConstructionInitial(imgRoad);
        currentPlayer = getPlayers().get(0);
        activateAllVertices();
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
    void trade(ActionEvent event) {
        if (isStepActual) {
            // TODO trade will be implemented
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
            preTurnThenActualTurn();
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
                outputNotPossible("Construction");
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

    private void preTurnThenActualTurn() throws IOException {
        boolean gameContinuoin = true;
        rollDie();
        System.out.println("Roll die result: " + die.getDieSum());
        if(die.getDieSum() == 7){
            thiefResourceCardPunishAI();
            thiefResourceCardPunish();
            gameContinuoin = false;
        }
        // game will not contiuno if the player has to choose cards first.
        if(gameContinuoin)
            actualTurn();
    }
    private void actualTurn() throws IOException {
        System.out.println("Actual turn is called");
        deActivateAllVertices();
        System.out.println("while loop");
        playerTurn = playerTurn % 4;
        Player player = getPlayers().get(playerTurn);
        currentPlayer = player;
        playerTurn++;
        if(die.getDieSum() == 7){
            System.out.println("thief moved");
            playThieft(currentPlayer);
        }
        getTurnProfit();
        // AI player
        if (player instanceof PlayerAI) {
            System.out.println("fake  player's turn");
            playAIActualTurn();
            preTurnThenActualTurn();
        }
        // Actual Player
        else if (player instanceof  PlayerActual) {
            System.out.println("real player's turn");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/thiefCardPunishment.fxml"));
            Parent root = loader.load();
            ControllerThiefPunishment controller = loader.getController();
            controller.setPlayer(currentPlayer, this);
            final Stage pop = new Stage();
            pop.initModality(Modality.APPLICATION_MODAL);
            pop.initOwner(window);
            pop.setTitle("Thief will steal something");
            pop.setScene(new Scene(root, 375, 480));
            pop.show();
        }else{
            updateGame();
        }
    }

    private void thiefResourceCardPunishAI(){
        for(int i = 0; i  < getPlayers().size(); i++){
            Player temp = getPlayers().get(0);
            if ( temp instanceof PlayerAI && temp.getTotalCards() >= 7){
                ((PlayerAI)temp).punish();
            }
        }
    }

    // i created this function to stop game, game contionous when use finishes selecting card by updateGame function

    private void playThieft(Player currentPlayer){
        thiefCanMove = true;
        if(initialThief){
            System.out.println("initial thief will be moved");
            thiefDefaultLoca.setVisible(false);
            movingThief.setLayoutX(thiefDefaultLoca.getLayoutX());
            movingThief.setLayoutY(thiefDefaultLoca.getLayoutY());
            movingThief.setVisible(true);
            initialThief = false;
        }
        if (currentPlayer instanceof PlayerAI) {
                System.out.println("bot is moving thief");
                int randomHex = (int)Math.random()*18 + 1;
                System.out.println(randomHex + "'s hexagon is thief moved");
                thiefHexLoca.setThiefHere(false);
                System.out.println();
                TerrainHex thiefsNewLocation = getHexWithIndex(randomHex);
                thiefHexLoca = thiefsNewLocation;
                movingThief.setLayoutX(thiefsNewLocation.getCircleNumberOnHex().getLayoutX());
                movingThief.setLayoutY(thiefsNewLocation.getCircleNumberOnHex().getLayoutY());
                thiefsNewLocation.setThiefHere(true);
                thiefCanMove = false;
        }
        // Actual Player
        else if (currentPlayer instanceof  PlayerActual) {
//            Stage window = (Stage) root.getScene().getWindow();
//            final Stage dialog = new Stage();
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            dialog.initOwner(window);
//            VBox dialogVbox = new VBox(20);
//            dialogVbox.getChildren().add(new Text("You must move the thief"));
//            Scene dialogScene = new Scene(dialogVbox, 100, 100);
//            dialog.setScene(dialogScene);
//            dialog.show();
            outputNotPossible("Thief");
        }
    }

    @FXML
    public void moveThief(MouseEvent mouseEvent){
        if(thiefCanMove){
            thiefHexLoca.setThiefHere(false);
            movingThief.setLayoutX(mouseEvent.getSceneX());
            movingThief.setLayoutY(mouseEvent.getSceneY());
        }
    }

    @FXML
    public void thiefMoved(MouseEvent mouseEvent){
        if(thiefCanMove) {
            thiefHexLoca.setThiefHere(false);
            TerrainHex thiefsNewLocation = getHexWithCoordinates(movingThief);
            if (thiefsNewLocation != null) {
                thiefHexLoca = thiefsNewLocation;
                movingThief.setLayoutX(thiefsNewLocation.getCircleNumberOnHex().getLayoutX());
                movingThief.setLayoutY(thiefsNewLocation.getCircleNumberOnHex().getLayoutY());
                thiefsNewLocation.setThiefHere(true);
                ArrayList<Player> players = thiefHexLoca.getPlayersAroundHere();
                int size = players.size();
                // if there is nobody around here, it will do nothing
                if(size != 0){
                    int randomPlayer = (int)Math.random()*size;
                    //Player playerToBePunished = players.get(randomPlayer);
                    //currentPlayer.addResourceFromThief(playerToBePunished.getPunishedByThief());
                }
            }
        }
        thiefCanMove = false;
    }

    public void updateGame() throws IOException{
        //here the resources of player will be updated, this part has dependency of talha's work. so this will wait
        System.out.println("Process for selecting cards is over now turn starts");
        actualTurn();
        System.out.println("actual turn is over");
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
        ((PlayerAI) currentPlayer).getActualAIDecision(this);
    }

    @Override
    public void makeConstructionActual(Circle circle) {
        if (!currentPlayer.hasEnoughResources(selectedConstruction)) {
            if(currentPlayer instanceof PlayerActual)
                outputNotPossible("Construction");
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
            outputNotPossible("Construction");
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
                preTurnThenActualTurn();
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
                ArrayList<Vertex> twoVertex = new ArrayList<>();
                twoVertex.add(tempRoad.getVertices().get(0));
                twoVertex.add(tempRoad.getVertices().get(1));

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