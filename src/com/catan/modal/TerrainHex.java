package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.awt.*;
import java.util.ArrayList;

public class TerrainHex extends Field{

    // properties
    private ArrayList<Vertex> adjacentVertices;
    private String sourceCardName;
    private Circle circleNumberOnHex;
    private Label labelNumberOnHex;
    private Polygon shape;

    // constructor
    public TerrainHex (Polygon shape, String name, Circle circleNumberOnHex, Label labelNumberOnHex) {
        super(name,shape);
        this.shape = shape;
        this.sourceCardName = "";
        this.circleNumberOnHex = circleNumberOnHex;
        this.labelNumberOnHex = labelNumberOnHex;
        adjacentVertices = new ArrayList<>();
    }

    // methods
    public boolean isShapeInside(Shape check){
        return shape.getBoundsInParent().intersects(check.getBoundsInParent());
    }

    public int getNumberOnHex() {
        String s = labelNumberOnHex.getText();
        return Integer.parseInt(s);
    }

    public void setNumberOnHex(int number) {
        labelNumberOnHex.setText(number + "");
    }

    public String getSourceCardName() {
        return sourceCardName;
    }

    public void setSourceCardName(String sourceCardName) {
        this.sourceCardName = sourceCardName;
    }

    public Circle getCircleNumberOnHex() {
        return circleNumberOnHex;
    }

    public void setCircleNumberOnHex(Circle circleNumberOnHex) {
        this.circleNumberOnHex = circleNumberOnHex;
    }

    public Label getLabelOnHex() {
        return labelNumberOnHex;
    }

    public void setLabelOnHex(Label labelNumberOnHex) {
        this.labelNumberOnHex = labelNumberOnHex;
    }

    public void addVertex(Vertex vertex){
        adjacentVertices.add(vertex);
    }

    public ArrayList<Player> getPlayersAround(){
        ArrayList<Player> playersAround = new ArrayList<>();
        for(Vertex vertex: adjacentVertices){
            Settlement settlementOfVertex = vertex.getSettlement();
            if(settlementOfVertex != null){
                Player playerOfVertex = settlementOfVertex.getPlayer();
                playersAround.add(playerOfVertex);
            }
        }
        return playersAround;
    }

}