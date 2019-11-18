package com.catan.interfaces;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public interface InterfaceMakeConstruction {
    void makeConstructionActual(Circle circle);
    void makeRoadActualForAI();
    void makeVillageActualForAI();
    void selectActualConstructionForAI(String type);
}
