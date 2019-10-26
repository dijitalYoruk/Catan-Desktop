package com.catan.modal;

import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Road extends Construction {

    private ArrayList<Vertex> vertices;
    private Line road;

    public Road(Line road, Vertex vertex1, Vertex vertex2) {
        super("");
        vertices = new ArrayList<>();
        vertices.add(vertex1);
        vertices.add(vertex2);
        this.road = road;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Line getRoad() {
        return road;
    }

    public void setRoad(Line road) {
        this.road = road;
    }

    public boolean containsRoad(Vertex vertex1, Vertex vertex2) {
        return vertices.contains(vertex1) && vertices.contains(vertex2);
    }


}
