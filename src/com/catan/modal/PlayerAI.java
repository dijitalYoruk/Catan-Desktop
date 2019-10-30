package com.catan.modal;

import com.catan.interfaces.InterfaceMakeInitialConstruction;

import java.util.ArrayList;

public class PlayerAI extends Player {

    public PlayerAI(String color) {
        super(color);
    }

    public void makeInitialConstruction(InterfaceMakeInitialConstruction makeConstruction) {
        ArrayList<Vertex> vertices = makeConstruction.getVertices();

        makeConstruction.setSelectedConstruction("road");
        int index = (int) (Math.random() * vertices.size());
        Vertex vertex = vertices.get(index);

        makeConstruction.makeConstruction(vertex.getShape());
        index = (int) (Math.random() * vertex.getNeighbors().size());
        vertex = vertex.getNeighbors().get(index);
        makeConstruction.makeConstruction(vertex.getShape());

        makeConstruction.deActivateAllVertices();
        makeConstruction.activatePlayerVertices();
        vertices = makeConstruction.getActivatedVertices();
        makeConstruction.setSelectedConstruction("village");
        index = (int) (Math.random() * vertices.size());
        vertex = vertices.get(index);
        makeConstruction.makeConstruction(vertex.getShape());
    }
}
