package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ControllerGame extends ControllerBaseGame {

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
        System.out.println(vertex.isActive());
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


    @FXML
    void rollDie(ActionEvent event) {
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
            makeConstruction(circle);
        }
    }

    @FXML
    public void endTurn(ActionEvent actionEvent) {
        if(isStepInitial) {
            initialTurn();
        } else if (isStepActual) {
            actualTurn();
        }
    }

    private void outputNotPossible() {
        System.out.println("Not Possible");
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

            if (isStepInitial && currentPlayer instanceof PlayerActual) {
                selectConstructionActual(imgVillage);
            }
            if (isStepInitial && currentPlayer instanceof PlayerAI) {
                tempRoad = road;
            }
        } else {
            outputNotPossible();
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
                if (selectedConstruction.equals(Constants.ROAD)) {
                    vertex.setActive(true);
                    activateNeighbours(vertex);
                }
                else if (isSelectedSettlement()) {
                    System.out.println("selected settlement");
                    if (!vertex.hasConstruction() && isVertexSuitableForConstruction(vertex)) {
                        vertex.setActive(true);
                        System.out.println("entered settlement");
                    }
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

    private void actualTurn() {
        // TODO the actual step will be implemented in the future.
    }

    public void makeConstruction(Circle circle) {
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
                unselectConstructions(null);
                deActivateAllVertices();
            } else {
                outputNotPossible();
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

        activatePlayerVertices();

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }

    // ------------ INITIAL STEP METHODS ---------------- //

    private void initialTurn() {
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
                    outputNotPossible();
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
                currentPlayer = getPlayers().get(0);
                deActivateAllVertices();
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
                unselectConstructions(null);
                activatePlayerVertices();
                tempSettlement = settlement;

            } else {
                outputNotPossible();
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
                    outputNotPossible();
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
        //}

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }
}