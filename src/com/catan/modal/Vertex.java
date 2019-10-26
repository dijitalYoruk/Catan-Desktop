package com.catan.modal;

import javafx.scene.shape.Circle;

public class Vertex {

    private Circle shape;

    public Vertex(Circle shape) {
        this.shape = shape;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }
}
