package com.catan.modal;

public class Settlement extends Construction {

    private Vertex vertex;

    public Settlement(String imagePath, Vertex vertex) {
        super(imagePath);
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public boolean isOnVertex(Vertex vertex) {
        return this.vertex == vertex;
    }

}
