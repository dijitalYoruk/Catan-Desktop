package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    // properties
    private ArrayList<Settlement> settlements;
    private ArrayList<SourceCard> sourceCards;
    private ArrayList<Road> roads;
    private String color;

    // constructor
    public Player(String color) {
        settlements = new ArrayList<>();
        sourceCards = new ArrayList<>();
        roads = new ArrayList<>();
        this.color = color;
    }

    // methods
    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(ArrayList<Settlement> settlements) {
        this.settlements = settlements;
    }

    public ArrayList<SourceCard> getSourceCards() {
        return sourceCards;
    }

    public void setSourceCards(ArrayList<SourceCard> sourceCards) {
        this.sourceCards = sourceCards;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSettlementImagePath(String construction) {
        if (construction.equals(Constants.VILLAGE)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_VILLAGE_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_VILLAGE_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_VILLAGE_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_VILLAGE_BLUE;
            }
        }
        else if (construction.equals(Constants.CITY)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_CITY_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_CITY_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_CITY_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_CITY_BLUE;
            }
        }
        else if (construction.equals(Constants.CIVILISATION)) {
            switch (color) {
                case Constants.COLOR_RED:
                    return Constants.PATH_CIVILISATION_RED;
                case Constants.COLOR_PURPLE:
                    return Constants.PATH_CIVILISATION_PURPLE;
                case Constants.COLOR_GREEN:
                    return Constants.PATH_CIVILISATION_GREEN;
                case Constants.COLOR_BLUE:
                    return Constants.PATH_CIVILISATION_BLUE;
            }
        }

        return Constants.PATH_CIVILISATION_BLUE;
    }

    public Color getRoadColor() {
        switch (color) {
            case Constants.COLOR_RED:
                return Constants.COLOR_RGB_RED;
            case Constants.COLOR_PURPLE:
                return Constants.COLOR_RGB_PURPLE;
            case Constants.COLOR_GREEN:
                return Constants.COLOR_RGB_GREEN;
            default:
                return Constants.COLOR_RGB_BLUE;
        }
    };

}
