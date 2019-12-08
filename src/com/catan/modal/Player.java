package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.paint.Color;

import javax.xml.transform.Source;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Player {

    // properties
    protected HashMap<String, ArrayList<SourceCard>> sourceCards;
    protected ArrayList<Settlement> settlements;
    protected ArrayList<Road> roads;
    private String color;
    private String name;

    // constructor
    public Player(String color, String name) {
        settlements = new ArrayList<>();
        sourceCards = new HashMap<>();
        roads = new ArrayList<>();
        this.color = color;
        this.name = name;
        for (String resourceName: Constants.resourceNames) {
            sourceCards.put(resourceName,  new ArrayList<>());
        }
    }

    // methods
    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public HashMap<String, ArrayList<SourceCard>> getSourceCards() {
        return sourceCards;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getSettlementImagePath(String construction) {
        switch (construction) {
            case Constants.VILLAGE:
                switch (color) {
                    case Constants.COLOR_RED:    return Constants.PATH_VILLAGE_RED;
                    case Constants.COLOR_PURPLE: return Constants.PATH_VILLAGE_PURPLE;
                    case Constants.COLOR_GREEN:  return Constants.PATH_VILLAGE_GREEN;
                    case Constants.COLOR_BLUE:   return Constants.PATH_VILLAGE_BLUE;
                }
                break;
            case Constants.CITY:
                switch (color) {
                    case Constants.COLOR_RED:    return Constants.PATH_CITY_RED;
                    case Constants.COLOR_PURPLE: return Constants.PATH_CITY_PURPLE;
                    case Constants.COLOR_GREEN:  return Constants.PATH_CITY_GREEN;
                    case Constants.COLOR_BLUE:   return Constants.PATH_CITY_BLUE;
                }
                break;
            case Constants.CIVILISATION:
                switch (color) {
                    case Constants.COLOR_RED:    return Constants.PATH_CIVILISATION_RED;
                    case Constants.COLOR_PURPLE: return Constants.PATH_CIVILISATION_PURPLE;
                    case Constants.COLOR_GREEN:  return Constants.PATH_CIVILISATION_GREEN;
                    case Constants.COLOR_BLUE:   return Constants.PATH_CIVILISATION_BLUE;
                }
                break;
        }
        return Constants.PATH_CIVILISATION_BLUE;
    }

    public Color getRoadColor() {
        switch (color) {
            case Constants.COLOR_RED:    return Constants.COLOR_RGB_RED;
            case Constants.COLOR_PURPLE: return Constants.COLOR_RGB_PURPLE;
            case Constants.COLOR_GREEN:  return Constants.COLOR_RGB_GREEN;
            default: return Constants.COLOR_RGB_BLUE;
        }
    }

    public void showSourceCards() {
        System.out.println(">>>>>>>" + name + "<<<<<<<" + "  " + getColor());
        Set<String> keys = sourceCards.keySet();
        for (String key: keys) {
            System.out.println(key + ": " + sourceCards.get(key).size());
        }
    }

    public void getTurnProfit(int dieNumber, TerrainHex terrainHex) {
        for (Settlement settlement: settlements) {
            HashMap<String, Integer> profit = settlement.getTurnProfit(dieNumber, terrainHex);
            for (String key: Constants.resourceNames) {
                for (int i = 0; i < profit.get(key); i++) {
                    sourceCards.get(key).add(new SourceCard(key, key));
                }
            }
        }
    }

    public String getPunishedByThief(){
        // getting potential resource keys.
        ArrayList<String> keys = new ArrayList<>(sourceCards.keySet());
        for (int i = 0; i < keys.size(); i++) {
            if (sourceCards.get(keys.get(i)).size() == 0) {
                keys.remove(i--);
            }
        }
        // removing and choosing one random card to give.
        int index = (int)(Math.random() * keys.size());
        String resourceToBeRemoved = keys.get(index);
        removeResources(resourceToBeRemoved, 1);
        return resourceToBeRemoved;
    }

    public int getTotalCards(){
        int totalCount = 0;
        for (String resourceName: Constants.resourceNames) {
            totalCount += sourceCards.get(resourceName).size();
        }
        return totalCount;
    }

    public boolean hasEnoughResources(String selectedConstruction) {
        Map<String, Integer> price = getPrice(selectedConstruction);;
        for (String resourceName: Constants.resourceNames) {
            if (sourceCards.get(resourceName).size() < price.get(resourceName)) { return false; }
        }
        return true;
    }

    public void subtractPriceOfConstruction(String selectedConstruction) {
        Map<String, Integer> price = getPrice(selectedConstruction);
        for (String resourceName: Constants.resourceNames) {
            int priceOfCard = price.get(resourceName);
            removeResources(resourceName, priceOfCard);
        }
    }

    private Map<String, Integer> getPrice(String item) {
        return PriceCard.getInstance().getPrice(item);
    }

    public void addResources(String resourceType, int resourceCount) {
        for (int i = 0; i < resourceCount; i++) {
            sourceCards.get(resourceType).add(new SourceCard(resourceType, resourceType));
        }
    }

    public void removeResources(String resourceType, int removeCount) {
        if (removeCount < sourceCards.get(resourceType).size()) {
            sourceCards.get(resourceType).subList(0, removeCount).clear();
        } else {
            sourceCards.get(resourceType).clear();
        }
    }

    @Override
    public String toString() {
        return "Player{" + name + ", color='" + color + '\'' + '}';
    }

}
