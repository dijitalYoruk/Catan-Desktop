package com.catan.modal;

import javafx.scene.shape.Polygon;


public class Field{
    private String name;
    private Polygon shape;

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

    public Polygon getShape() {
        return shape;
    }
    public void setShape(Polygon name)
    {
        this.shape = shape;
    }

}
