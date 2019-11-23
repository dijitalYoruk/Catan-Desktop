package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class Vertex {

    private Circle shape;
    private ArrayList<Vertex> neighbors;
    private boolean isActive;
    private Settlement settlement;
    private ArrayList<TerrainHex> terrainHexes;

    public Vertex(Circle shape) {
        this.terrainHexes = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.shape = shape;
        this.isActive = false;
        this.settlement = null;
    }

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

    public HashMap<String, Integer> getTurnProfit(int dieNumber) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(Constants.CARD_BRICK, 0);
        map.put(Constants.CARD_GRAIN, 0);
        map.put(Constants.CARD_LUMBER, 0);
        map.put(Constants.CARD_ORE, 0);
        map.put(Constants.CARD_WOOL, 0);

        for (TerrainHex hex: terrainHexes) {
            String sourceCardName = hex.getSourceCardName();
            // if thief is in a hex, you cannot get profit from there so, i added "!hex.isThiefHere()" expression.
            if (!hex.getSourceCardName().equals("") && hex.getNumberOnHex() == dieNumber && !hex.isThiefHere()) {
                map.put(sourceCardName, map.get(sourceCardName) + 1);
            }
        }
        return map;
    }

    public void addHex(TerrainHex terrainHex) {
        terrainHexes.add(terrainHex);
        terrainHex.addVertex(this);
    }


}