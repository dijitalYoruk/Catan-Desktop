package com.catan.modal;

import javafx.scene.shape.Polygon;

public class Harbour extends Field {

    // properties
    private int tradeRatio;
    private String resourceAssociated;

    // constructor
    public Harbour(String name, Polygon shape, int tradeRatio, String resourceType) {
        super(name, shape);
        this.tradeRatio = tradeRatio;
        resourceAssociated = resourceType;
    }

    // methods
    public String getAssociatedResourceType() { return resourceAssociated; }

    public int getTradeRatio() {
        return tradeRatio;
    }

    public void setTradeRatio(int tradeRatio) {
        this.tradeRatio = tradeRatio;
    }
}
