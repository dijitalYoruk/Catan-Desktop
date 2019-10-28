package com.catan.modal;

public class Settlement extends Construction {

    private Vertex vertex;
    private int sourceCardProfit;

    public Settlement(String imagePath, Vertex vertex,int sourceCardProfit) {
        super(imagePath);
        this.vertex = vertex;
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

}
