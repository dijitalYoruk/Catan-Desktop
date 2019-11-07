package com.catan.modal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Settlement extends Construction {

    private Vertex vertex;
    private int sourceCardProfit;
    private Player player;

    public Settlement(String type, String imagePath, Vertex vertex, int sourceCardProfit, Player player) {
        super(type, imagePath);
        this.vertex = vertex;
        this.player = player;
        this.sourceCardProfit = sourceCardProfit;
    }

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

    public HashMap<String, Integer> getTurnProfit(int dieNumber) {
        HashMap<String, Integer> profit = vertex.getTurnProfit(dieNumber);
        Set<String> keys = profit.keySet();
        for (String key: keys) {
            profit.put(key, profit.get(key) * sourceCardProfit);
        }
        return profit;
    }

    @Override
    public String toString() {
        return "Settlement{" +
                "vertex=" + vertex +
                ", sourceCardProfit=" + sourceCardProfit +
                ", player=" + player +
                '}';
    }
}
