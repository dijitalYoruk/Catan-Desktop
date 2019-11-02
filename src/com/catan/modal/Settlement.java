package com.catan.modal;

public class Settlement extends Construction {

    private Vertex vertex;
    private int sourceCardProfit;
    private Player player;

    public Settlement(String imagePath, Vertex vertex, int sourceCardProfit, Player player) {
        super(imagePath);
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
}
