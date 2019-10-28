package com.catan.modal;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class TerrainHex extends Field{

    private int numberOnHex;
    private String sourceCardName;

    public TerrainHex (Polygon shape, String name) {
        super(name,shape);
        this.numberOnHex = 0;
        this.sourceCardName = "";
    }

    public int getNumberOnHex() {
        return numberOnHex;
    }
    public void setNumberOnHex(int numberOnHex)
    {
        this.numberOnHex = numberOnHex;
    }

    public String getSourceCardName() {
        return sourceCardName;
    }
    public void setSourceCardName(String sourceCardName)
    {
        this.sourceCardName = sourceCardName;
    }
}
