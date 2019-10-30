package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeInitialConstruction;
import com.catan.modal.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ControllerGame extends ControllerBaseGame implements InterfaceMakeInitialConstruction {

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
        selectConstruction(imgRoad);
        currentPlayer = getPlayers().get(0);
        activateAllVertices();

    }

    @FXML
    public void selectConstruction(MouseEvent mouseEvent) {
        Rectangle rectangle = (Rectangle) mouseEvent.getSource();
        selectConstruction(rectangle);
    }

    public void selectConstruction(Rectangle rectangle) {
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

        if (currentPlayer instanceof PlayerActual) {
            if (isStepInitial && !isRoadBuild) {
                selectedConstruction = Constants.ROAD;
                rectangle = imgRoad;
            }

            else if (isStepInitial && !isConstructionBuild) {
                selectedConstruction = Constants.VILLAGE;
                rectangle = imgVillage;
            }

            else if (isStepInitial) {
                return;
            }
        }

        // setting the color of selected construction.
        rectangle.setStroke(Constants.COLOR_CONSTRUCTION_SELECTED);

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }

    @FXML
    public void unselectConstructions(MouseEvent mouseEvent) {
        if (isStepActual && constructionUnselect) {
            imgRoad.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgCity.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgVillage.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            imgCivilisation.setStroke(Constants.COLOR_CONSTRUCTION_UNSELECTED);
            selectedConstruction = "";
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
        if (circle.getRadius() != Constants.CONSTRUCTION_RADIUS) {
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
    void rollDie(ActionEvent event) {
        if (isStepActual) {
            // TODO rollDie will be implemented
        }

        die.rollDie();
        System.out.println(die.getDice1());
        Image img = new Image("./com/catan/assets/die"+die.getDice1()+".png");
        getImgDie1().setFill(new ImagePattern(img));
        getImgDie1().setStroke(Color.color(0.4,0.4,0.4));
        getImgDie1().setStrokeWidth(1);
        Image img2 = new Image("./com/catan/assets/die"+die.getDice2()+".png");
        getImgDie2().setFill(new ImagePattern(img2));
        getImgDie2().setStroke(Color.color(0.4,0.4,0.4));
        getImgDie2().setStrokeWidth(1);
    }

    @FXML
    void buyDevelopmentCard(ActionEvent event) {
        if (isStepActual) {
            // TODO buyDevelopmentCard will be implemented
        }
    }

    @FXML
    public void makeConstruction(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        makeConstruction(circle);
    }

    public void makeConstruction(Circle circle) {
        if (selectedConstruction.equals(Constants.CITY) || selectedConstruction.equals(Constants.VILLAGE) || selectedConstruction.equals(Constants.CIVILISATION)) {
            if (currentPlayer instanceof PlayerActual && isStepInitial && isRoadBuild && !isConstructionBuild) {
                selectConstruction(imgVillage);
                isConstructionBuild = true;
                selectConstruction((Rectangle) null);
            }

            Vertex vertex = getCorrespondingVertex(circle);

            if (vertex != null && isVertexSuitableForConstruction(vertex)) {
                Settlement settlement;
                String imagePath = "";
                switch (selectedConstruction) {
                    case Constants.CITY:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.CITY);
                        settlement = new City(imagePath, vertex);
                        break;
                    case Constants.VILLAGE:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.VILLAGE);
                        settlement = new Village(imagePath, vertex);
                        break;
                    default:
                        imagePath = currentPlayer.getSettlementImagePath(Constants.CIVILISATION);
                        settlement = new Civilisation(imagePath, vertex);
                        break;
                }

                Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(Constants.CONSTRUCTION_RADIUS);
                getSettlements().add(settlement);
                currentPlayer.getSettlements().add(settlement);
                unselectConstructions(null);
            } else {
                outputNotPossible();
            }
        }
        else if (selectedConstruction.equals(Constants.ROAD)) {
            if (roadVertex1 == null) {
                if (currentPlayer instanceof PlayerActual && isStepInitial && !isRoadBuild) {
                    selectConstruction(imgRoad);
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

    private void outputNotPossible() {
        System.out.println("Not Possible");
    }

    private boolean isVertexSuitableForConstruction(Vertex vertex) {
        ArrayList<Vertex> neighbors = vertex.getNeighbors();
        for (Settlement construction: getSettlements()) {
            for (Vertex v: neighbors) {
                if (construction.isOnVertex(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void constructRoad() {
        Road road = getCorrespondingRoad();
        if (road != null && !road.getRoad().isVisible()) {
            Color color = currentPlayer.getRoadColor();
            road.getRoad().setStroke(color);
            road.getRoad().setVisible(true);
            currentPlayer.getRoads().add(road);

            if (currentPlayer instanceof PlayerActual && (isStepInitial && !isRoadBuild)) {
                isRoadBuild =true;
                selectConstruction(imgVillage);
                activatePlayerVertices();
            }

        } else {
            outputNotPossible();
        }
        unselectConstructions(null);
    }

    private void refreshRoadSelectionVertices() {
        if (roadVertex1 != null && roadVertex1.getShape().getRadius() != Constants.CONSTRUCTION_RADIUS) {
            roadVertex1.getShape().setFill(Constants.COLOR_BLUR_VERTEX);
        }
        if (roadVertex2 != null && roadVertex2.getShape().getRadius() != Constants.CONSTRUCTION_RADIUS) {
            roadVertex2.getShape().setFill(Constants.COLOR_BLUR_VERTEX);
        }
        roadVertex1 = null;
        roadVertex2 = null;
    }

    private Road getCorrespondingRoad() {
        for (Road road: getRoads()) {
            if (road.containsRoad(roadVertex1, roadVertex2)) {
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

    @FXML
    public void returnToProgram(ActionEvent actionEvent) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../view/program.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void endTurn(ActionEvent actionEvent) {
        boolean flag = false;

        for (int i = playerTurn; i < 5; i++) {
            i = i % 4;
            playerTurn = i;
            Player player = getPlayers().get(i);
            currentPlayer = player;

            if (isStepInitial) {
                activateAllVertices();
            }

            if (player instanceof PlayerAI) {
                if (isStepInitial) {
                    ((PlayerAI) player).makeInitialConstruction(this);
                }

                if (isStepActual) {
                    System.out.println("passes as actual ai");
                }
            }

            else if (player instanceof  PlayerActual) {
                System.out.println(isStepInitial);

                if ((isStepInitial && isRoadBuild && isConstructionBuild)) {
                    isRoadBuild = false;
                    isConstructionBuild = false;
                    System.out.println("turn ended initial");
                } else if (isStepActual && flag) {
                    return;
                } else if (isStepActual) {
                    System.out.println("turn ended");
                } else {
                    outputNotPossible();
                    if (isStepInitial) {
                        System.out.println("select construction");
                        selectConstruction(imgRoad);
                    }

                    return;
                }
            }

            flag = true;

            if (isStepInitial) {
                initialStepCount++;
                System.out.println(initialStepCount);
            }

            if (initialStepCount == 8) {
                isStepActual = true;
                isStepInitial = false;
                System.out.println("actual oldu");
            }
        }

        currentPlayer = getPlayers().get(0);
    }

    public void activateAllVertices() {
        for (Vertex vertex: getVertices()) {
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
                if (vertex.getShape().getRadius() != Constants.CONSTRUCTION_RADIUS && isVertexSuitableForConstruction(vertex)) {
                    vertex.setActive(true);
                }
            }
        }
    }


    public void setSelectedConstruction(String selectedConstruction) {
        this.selectedConstruction = selectedConstruction;
    }
}