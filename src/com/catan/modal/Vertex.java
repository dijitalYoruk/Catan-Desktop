package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class Vertex {

    // properties
    private ArrayList<TerrainHex> terrainHexes;
    private ArrayList<Vertex> neighbors;
    private Settlement settlement;
    private boolean isActive;
    private String name;
    private Circle shape;

    // constructor
    public Vertex(Circle shape) {
        this.terrainHexes = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.settlement = null;
        this.isActive = false;
        this.shape = shape;
    }

    // methods
    public ArrayList<TerrainHex> getFields() {
        return terrainHexes;
    }

    public void setFields(ArrayList<TerrainHex> terrainHexes) {
        this.terrainHexes = terrainHexes;
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Vertex> neighbors) {
        this.neighbors = neighbors;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }

    public void addNeighbor(Vertex vertex) {
        neighbors.add(vertex);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean hasConstruction() {
        return shape.getRadius() == Constants.CONSTRUCTION_RADIUS;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public void addHex(TerrainHex terrainHex) {
        terrainHexes.add(terrainHex);
        terrainHex.addVertex(this);
    }

    public HashMap<String, Integer> getTurnProfit(int dieNumber, TerrainHex terrainHex) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String resourceName: Constants.resourceNames) {
            map.put(resourceName, 0);
        }
        for (TerrainHex hex: terrainHexes) {
            String sourceCardName = hex.getSourceCardName();
            // if thief is in a hex, you cannot get profit from there so, i added "hex != terrainHex" expression.
            if (!sourceCardName.equals("") && hex.getNumberOnHex() == dieNumber && hex != terrainHex) {
                map.put(sourceCardName, map.get(sourceCardName) + 1);
            }
        }
        return map;
    }
}