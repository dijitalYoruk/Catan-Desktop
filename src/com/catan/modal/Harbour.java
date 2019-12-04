package com.catan.modal;

import javafx.scene.shape.Polygon;

public class Harbour extends Field {

    // properties
    private double tradeRatio;

    // constructor
    public Harbour(String name, Polygon shape, double tradeRatio) {
        super(name, shape);
        this.tradeRatio = tradeRatio;
    }

    // methods
    public double getTradeRatio() {
        return tradeRatio;
    }

    public void setTradeRatio(double tradeRatio) {
        this.tradeRatio = tradeRatio;
    }
}
