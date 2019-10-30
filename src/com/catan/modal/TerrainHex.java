package com.catan.modal;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class TerrainHex extends Field{

    private String sourceCardName;
    private Circle circleNumberOnHex;
    private Label labelNumberOnHex;

    public TerrainHex (Polygon shape, String name, Circle circleNumberOnHex, Label labelNumberOnHex) {
        super(name,shape);
        this.sourceCardName = "";
        this.circleNumberOnHex = circleNumberOnHex;
        this.labelNumberOnHex = labelNumberOnHex;
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
}
