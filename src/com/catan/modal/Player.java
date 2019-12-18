package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.paint.Color;

import java.util.*;

public class Player {

    // properties
    private HashMap<String, ArrayList<SourceCard>> sourceCards;
    private HashMap<String, Integer> developmentCards;
    private ArrayList<Settlement> settlements;
    private ArrayList<Road> roads;
    private String color;
    private String name;
    private int knightCount;
    private int longestRoad;
    private int victoryPoints;
    private int pointsFromVictoryPointsCard;
    private EntitlementCard strongestArmyCard;
    private EntitlementCard longestArmyCard;


    // constructor
    public Player(String color, String name) {
        developmentCards = new HashMap<>();
        settlements = new ArrayList<>();
        sourceCards = new HashMap<>();
        roads = new ArrayList<>();
        this.victoryPoints = 0;
        this.knightCount = 0;
        this.color = color;
        this.name = name;
        this.strongestArmyCard = null;
        this.longestArmyCard = null;
        this.pointsFromVictoryPointsCard = 0;
        this.longestRoad = 0;
        for (String resourceName: Constants.resourceNames) {
            sourceCards.put(resourceName,  new ArrayList<>());
        }
        for (String devCardName: Constants.developmentCardNames) {
            developmentCards.put(devCardName, 0);
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
    public void addRoad(Road road) {

        ArrayList<Vertex> vertices = new ArrayList<>();
        for(int i = 0; i < roads.size(); i++)
            vertices.addAll(roads.get(i).getVertices());

        Set<Vertex> vertexSet = new HashSet<>(vertices);
        vertices.clear();
        vertices.addAll(vertexSet);
        if(vertices.size() == 0)
            longestRoad++;
        if(vertices.contains(road.getVertices().get(0)))
        {
            ArrayList<Vertex> neigbours = road.getVertices().get(0).getNeighbors();
            int count = 0;
            if(vertices.contains(neigbours.get(0)))
                count++;
            if(vertices.contains(neigbours.get(1)))
                count++;
            if(vertices.contains(neigbours.get(2)))
                count++;
            if(count < 2)
                longestRoad++;
        }
        if(vertices.contains(road.getVertices().get(1)))
        {
            ArrayList<Vertex> neigbours = road.getVertices().get(1).getNeighbors();
            int count = 0;
            if(vertices.contains(neigbours.get(0)))
                count++;
            if(vertices.contains(neigbours.get(1)))
                count++;
            if(vertices.contains(neigbours.get(2)))
                count++;
            if(count < 2)
                longestRoad++;
        }
        roads.add(road);
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

    public void incrementKnightCount() {
        knightCount++;
    }

    public int getKnightCount() {
        return knightCount;
    }
    
    public int getLongestRoad() {
        return longestRoad;
    }

    public void incrementVictoryPoints() {
        pointsFromVictoryPointsCard++;
    }

    public int getVictoryPoints() {
        return victoryPoints;
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
        System.out.print("| ");
        for (String resourceName: Constants.resourceNames) {
            int count = getSourceCards().get(resourceName).size();
            System.out.print(resourceName + " --> " + count + " | ");
        }
        System.out.println(" => " + name);
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

    public int getTotalDevelopmentCards(){
        int totalCount = 0;
        for (String name: Constants.developmentCardNames) {
            totalCount += developmentCards.get(name);
        }
        return totalCount;
    }

    public boolean hasEnoughResources(String selectedItem) {
        Map<String, Integer> price = getPrice(selectedItem);;
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

    public void removeDevelopmentCard(String cardType) {
        int count = developmentCards.get(cardType);
        developmentCards.put(cardType, count - 1);
    }

    public HashMap<String, Integer> getDevelopmentCards() {
        return developmentCards;
    }

    private void addDevelopmentCard(String developmentType) {
        int count = developmentCards.get(developmentType);
        developmentCards.put(developmentType, count + 1);
    }

    @Override
    public String toString() {
        return "Player{" + name + ", color='" + color + '\'' + '}';
    }

    public void buyDevelopmentCard(Chest chest) {
        try {
            boolean hasEnoughResources = hasEnoughResources(Constants.DEVELOPMENT_CARD);
            if (hasEnoughResources) {
                DevelopmentCard card = chest.getDevelopmentCard();
                addDevelopmentCard(card.getName());
                System.out.println("==============================================================================================");
                System.out.println(getName() + " bought " + card.getName());
                System.out.println("==============================================================================================");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setStrongestArmyCard(EntitlementCard strongestArmyCard) {
         this.strongestArmyCard= strongestArmyCard;
    }

    public void setLongestArmyCard(EntitlementCard longestArmyCard) {
        this.longestArmyCard= longestArmyCard;
    }
    
    public void refreshVictoryPoints() {
        victoryPoints = 0;
        for(int i = 0; i < settlements.size(); i++)
            victoryPoints += settlements.get(i).getSourceCardProfit();
        if(strongestArmyCard != null)
            victoryPoints += strongestArmyCard.getVictoryPoints();
        if(longestArmyCard != null)
            victoryPoints += longestArmyCard.getVictoryPoints();

        victoryPoints += pointsFromVictoryPointsCard;
    }
}
