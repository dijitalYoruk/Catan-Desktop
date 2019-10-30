package com.catan.controller;

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

public class ControllerGame extends ControllerBaseGame{

    // constants
    private final String ROAD = "road";
    private final String VILLAGE = "village";
    private final String CITY = "city";
    private final String CIVILISATION = "civilisation";
    private final int CONSTRUCTION_RADIUS = 22;

    private final Color COLOR_CONSTRUCTION_UNSELECTED = Color.color(0.4,0.4,0.4);
    private final Color COLOR_CONSTRUCTION_SELECTED = Color.color(1,0,0);
    private final Color COLOR_BLUR_VERTEX = Color.valueOf("#ffde2173");
    private final Color COLOR_BLEND_VERTEX = Color.valueOf("#ffdd21");
    private final Color COLOR_ROAD_SELECTION_VERTEX = Color.valueOf("#FF9800");

    private final String PATH_CITY_RED    = "./com/catan/assets/city_red.png";
    private final String PATH_CITY_BLUE   = "./com/catan/assets/city_blue.png";
    private final String PATH_CITY_PURPLE = "./com/catan/assets/city_purple.png";
    private final String PATH_CITY_GREEN  = "./com/catan/assets/city_green.png";

    private final String PATH_VILLAGE_RED    = "./com/catan/assets/village_red.png";
    private final String PATH_VILLAGE_BLUE   = "./com/catan/assets/village_blue.png";
    private final String PATH_VILLAGE_PURPLE = "./com/catan/assets/village_purple.png";
    private final String PATH_VILLAGE_GREEN   = "./com/catan/assets/village_green.png";

    private final String PATH_CIVILISATION_RED    = "./com/catan/assets/civilisation_red.png";
    private final String PATH_CIVILISATION_BLUE   = "./com/catan/assets/civilisation_blue.png";
    private final String PATH_CIVILISATION_PURPLE = "./com/catan/assets/civilisation_purple.png";
    private final String PATH_CIVILISATION_GREEN  = "./com/catan/assets/civilisation_green.png";

    // properties
    private boolean constructionUnselect = true;
    private String selectedConstruction = "";
    private Vertex roadVertex1 = null;
    private Vertex roadVertex2 = null;

    @FXML
    public void selectConstruction(MouseEvent mouseEvent) {
        // setting all of the pane as unselected
        imgRoad.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
        imgCity.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
        imgVillage.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
        imgCivilisation.setStroke(COLOR_CONSTRUCTION_UNSELECTED);

        // setting the color of selected construction.
        Rectangle rectangle = (Rectangle) mouseEvent.getSource();
        rectangle.setStroke(COLOR_CONSTRUCTION_SELECTED);

        // setting selected construction
        if (rectangle == imgRoad) {
            selectedConstruction = ROAD;
        } else if (rectangle == imgCity) {
            selectedConstruction = CITY;
        } else if (rectangle == imgVillage) {
            selectedConstruction = VILLAGE;
        } else if (rectangle == imgCivilisation) {
            selectedConstruction = CIVILISATION;
        }

        // disabling road selection
        refreshRoadSelectionVertices();
        constructionUnselect = false;
    }

    @FXML
    public void unselectConstructions(MouseEvent mouseEvent) {
        if (constructionUnselect) {
            imgRoad.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
            imgCity.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
            imgVillage.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
            imgCivilisation.setStroke(COLOR_CONSTRUCTION_UNSELECTED);
            selectedConstruction = "";
            refreshRoadSelectionVertices();
        }
        constructionUnselect = true;
    }

    @FXML
    public void blurVertex(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        if (circle.getRadius() != CONSTRUCTION_RADIUS && circle.getFill() != COLOR_ROAD_SELECTION_VERTEX) {
            circle.setFill(COLOR_BLUR_VERTEX);
        }
    }

    @FXML
    public void blendVertex(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();
        if (circle.getRadius() != CONSTRUCTION_RADIUS) {
            circle.setFill(COLOR_BLEND_VERTEX);
        }
    }

    @FXML
    void trade(ActionEvent event) {
        // TODO trade will be implemented
    }

    @FXML
    void playDevelopmentCard(ActionEvent event) {
        // TODO playDevelopmentCard will be implemented
    }

    @FXML
    void rollDie(ActionEvent event) {

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
        // TODO buyDevelopmentCard will be implemented
    }

    @FXML
    public void makeConstruction(MouseEvent mouseEvent) {
        Circle circle = (Circle) mouseEvent.getSource();

        if (selectedConstruction.equals(CITY) || selectedConstruction.equals(VILLAGE) || selectedConstruction.equals(CIVILISATION)) {
            Vertex vertex = getCorrespondingVertex(circle);

            if (vertex != null && isVertexSuitableForConstruction(vertex)) {
                Settlement settlement;
                switch (selectedConstruction) {
                    case CITY:
                        settlement = new City(PATH_CITY_RED, vertex);
                        break;
                    case VILLAGE:
                        settlement = new Village(PATH_VILLAGE_GREEN, vertex);
                        break;
                    default:
                        settlement = new Civilisation(PATH_CIVILISATION_PURPLE, vertex);
                        break;
                }

                Image img = new Image(settlement.getImagePath());
                vertex.getShape().setFill(new ImagePattern(img));
                vertex.getShape().setRadius(CONSTRUCTION_RADIUS);
                getSettlements().add(settlement);
                selectedConstruction = "";
                constructionUnselect = true;
            } else {
                outputNotPossible();
            }
        }
        else if (selectedConstruction.equals(ROAD)) {
            if (roadVertex1 == null) {
                constructionUnselect = false;
                roadVertex1 = getCorrespondingVertex(circle);
                if (circle.getRadius() != CONSTRUCTION_RADIUS) {
                    circle.setFill(COLOR_ROAD_SELECTION_VERTEX);
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
            road.getRoad().setVisible(true);
        } else {
            outputNotPossible();
        }

        refreshRoadSelectionVertices();
        selectedConstruction = "";
    }

    private void refreshRoadSelectionVertices() {
        if (roadVertex1 != null && roadVertex1.getShape().getRadius() != CONSTRUCTION_RADIUS) {
            roadVertex1.getShape().setFill(COLOR_BLUR_VERTEX);
        }
        if (roadVertex2 != null && roadVertex2.getShape().getRadius() != CONSTRUCTION_RADIUS) {
            roadVertex2.getShape().setFill(COLOR_BLUR_VERTEX);
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

}