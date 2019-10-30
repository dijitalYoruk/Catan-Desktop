package com.catan.interfaces;

import com.catan.modal.Road;
import com.catan.modal.Vertex;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public interface InterfaceMakeInitialConstruction {

    void makeConstruction(Circle circle);
    ArrayList<Vertex> getVertices();
    void setSelectedConstruction(String selectedConstruction);

    void activateAllVertices();
    void deActivateAllVertices();
    ArrayList<Vertex> getActivatedVertices();
    void activatePlayerVertices();

}
