package com.catan.modal;

import com.catan.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Settlement extends Construction {

    // properties
    private int sourceCardProfit;
    private Vertex vertex;
    private Player player;

    // constructor
    public Settlement(String type, String imagePath, Vertex vertex, int sourceCardProfit, Player player) {
        super(type, imagePath);
        this.vertex = vertex;
        this.player = player;
        this.sourceCardProfit = sourceCardProfit;
    }

    // methods
    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public int getSourceCardProfit() {
        return sourceCardProfit;
    }

    public void setSourceCardProfit(int sourceCardProfit) {
        this.sourceCardProfit = sourceCardProfit;
    }

    public boolean isOnVertex(Vertex vertex) {
        return this.vertex == vertex;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HashMap<String, Integer> getTurnProfit(int dieNumber, TerrainHex terrainHex) {
        HashMap<String, Integer> profit = vertex.getTurnProfit(dieNumber, terrainHex);
        for (String key: Constants.resourceNames) {
            profit.put(key, profit.get(key) * sourceCardProfit);
        }
        return profit;
    }

    @Override
    public String toString() {
        return "Settlement{" +
                "vertex=" + vertex +
                ", sourceCardProfit=" + sourceCardProfit +
                ", player=" + player.getName() +
                '}';
    }
}
