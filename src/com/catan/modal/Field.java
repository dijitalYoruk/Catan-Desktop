package com.catan.modal;

import javafx.scene.shape.Polygon;


public class Field{
    private String name;
    public Polygon shape;

    public Field (String name, Polygon shape) {
        this.name = name;
        this.shape = shape;
    }

    public String getName() {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

}
