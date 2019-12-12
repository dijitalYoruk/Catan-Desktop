package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.controller.ControllerGame;
import com.catan.interfaces.*;
import javafx.scene.shape.Circle;

import java.util.*;

public class PlayerAI extends Player {

    // properties
    private AI ai;

    // constructor
    public PlayerAI(String color, String name) {
        super(color, name);
        this.ai = new AI();
    }

    // methods
    public void punish(){
        int punish = getTotalCards()/2;
        for(int i = 0; i < punish; i++){
            int randomRes = (int) (Math.random() * 5 + 1);
            punishHelper(randomRes);
            // 1 = wool; 2 = grain; 3 = ore; 4 = lumber; 5 = brick.
        }
    }

    private void punishHelper(int i){
        String res = "";
        if(i == 1)
            res = Constants.CARD_WOOL;
        else if ( i == 2)
            res = Constants.CARD_GRAIN;
        else if ( i == 3)
            res = Constants.CARD_ORE;
        else if ( i == 4)
            res = Constants.CARD_LUMBER;
        else
            res = Constants.CARD_BRICK;

        if( getSourceCards().get(res).size() == 0) {
            int another = (i == 1) ? 5 : (i-1);
            punishHelper(another);
        } else {
            getSourceCards().get(res).remove(0);
        }
    }

    public void makeSettlement(ArrayList<Settlement> settlements, InterfaceMakeConstruction makeConstruction) {
        int index = (int) (Math.random() * settlements.size());
        Settlement civilisation = settlements.get(index);
        if (civilisation != null) {
            Vertex vertex = civilisation.getVertex();
            makeConstruction.makeConstructionActual(vertex.getShape());
        }
    }

    public void decideToMakeConstruction(InterfaceMakeConstruction makeConstruction) {
        ai.decideToMakeConstruction(makeConstruction, this);
    }

    public  HashMap<String, Integer> getRequestedResourceCards(Player playerToBeTraded) {
        return ai.getRequestedResourceCards(playerToBeTraded, getSourceCards());
    }

    public  HashMap<String, Integer> getRequestedResourceCardForChest() {
        return ai.getRequestedResourceCardForChest(getSourceCards());
    }

    public  HashMap<String, Integer> getOfferedResourceCardForChest(HashMap<String, Integer> requestedRC) {
        return ai.getOfferedResourceCardForChest(requestedRC, getSourceCards());
    }

    public  HashMap<String, Integer> getOfferedResourceCards(Map<String, Integer> requestedResources) {
        return ai.getOfferedResourceCards(requestedResources, getSourceCards());
    }

    public boolean respondToTradeRequest(HashMap<String, Integer> requestedResources, HashMap<String, Integer> offeredRequests) {
        return ai.respondToTradeRequest(requestedResources, offeredRequests);
    }

    public void decideToMakeTrade(InterfaceMakeTrade makeTrade) {
        ai.decideToMakeTrade(makeTrade, getSourceCards());
    }

    public HashMap<String, Integer> getInventionResourceCardSelection() {
        HashMap<String, Integer> desiredResources = new HashMap<>();

        String minKey2 = null;
        Integer minValue2 = null;
        String minKey1 = Constants.resourceNames.get(0);
        Integer minValue1 = getSourceCards().get(Constants.resourceNames.get(0)).size();


        for (String resourceName: Constants.resourceNames) {
            desiredResources.put(resourceName, 0);
            if (getSourceCards().get(resourceName).size() <= minValue1 && !minKey1.equals(resourceName)) {
                minKey2 = minKey1;
                minValue2 = minValue1;
                minKey1 = resourceName;
                minValue1 = getSourceCards().get(resourceName).size();
            }
        }

        if (minKey2 != null && minValue1.equals(minValue2)) {
            desiredResources.put(minKey1, 1);
            desiredResources.put(minKey2, 1);
        } else {
            desiredResources.put(minKey1, 2);
        }

        return desiredResources;
    }

    public void decideToPlayDevelopmentCard(InterfaceDevelopmentCard interfaceDevelopmentCard) {
        if (getTotalDevelopmentCards() > 0) {
            boolean decision = Math.random() < 0.9;
            if (decision) {
                Set<String> keySet = getDevelopmentCards().keySet();
                ArrayList<String> keys = new ArrayList<>(keySet);

                for (int i = 0; i < keys.size(); i++) {
                    if (getDevelopmentCards().get(keys.get(i)) == 0) { keys.remove(i--); }
                }

                int randomIndex = (int)(Math.random() * keys.size());
                String key = keys.get(randomIndex);
                removeDevelopmentCard(key);
                DevelopmentCard developmentCard = new DevelopmentCard(key);
                interfaceDevelopmentCard.playDevelopmentCard(developmentCard);
            }
        }
    }

    public String getMonopolResourceCardDecision() {
        String minKey1 = Constants.resourceNames.get(0);
        int minValue1 = getSourceCards().get(Constants.resourceNames.get(0)).size();

        for (String resourceName: Constants.resourceNames) {
            if (getSourceCards().get(resourceName).size() <= minValue1 && !minKey1.equals(resourceName)) {
                minKey1 = resourceName;
            }
        }
        return minKey1;
    }

    public void decideForHexesToExchangeProfit(ArrayList<TerrainHex> terrainHexes, InterfaceExchangeTurnProfit exchangeTurnProfit) {
        ArrayList<TerrainHex> playerHexes = new ArrayList<>();
        ArrayList<TerrainHex> filteredHexes = new ArrayList<>();

        for (Settlement settlement: getSettlements()) {
            for (TerrainHex hex: settlement.getVertex().getFields()) {
                if (!playerHexes.contains(hex) &&
                    hex.getNumberOnHex() != 6  &&
                    hex.getNumberOnHex() != 8  &&
                    hex.getNumberOnHex() != 0) {
                    playerHexes.add(hex);
                }
            }
        }
        for (TerrainHex hex: terrainHexes) {
            if (!playerHexes.contains(hex) && (
                hex.getNumberOnHex() == 5 ||
                hex.getNumberOnHex() == 6 ||
                hex.getNumberOnHex() == 8 ||
                hex.getNumberOnHex() == 9)) {
                filteredHexes.add(hex);
            }
        }

        int randomIndex1 = (int) (Math.random() * playerHexes.size());
        int randomIndex2 = (int) (Math.random() * filteredHexes.size());
        Circle circle1 = playerHexes.get(randomIndex1).getCircleNumberOnHex();
        Circle circle2 = filteredHexes.get(randomIndex2).getCircleNumberOnHex();
        exchangeTurnProfit.exchangeTurnProfit(circle1, this);
        exchangeTurnProfit.exchangeTurnProfit(circle2, this);
    }

    public void decideForDestroyingRoad(ArrayList<Player> players, InterfaceDestroyRoad destroyRoad) {
        ArrayList<Road> roadsToBeDestroyed = new ArrayList<>();

        for (Player player: players) {
            if (player != this) { roadsToBeDestroyed.addAll(player.getRoads()); }
        }

        if (!roadsToBeDestroyed.isEmpty()) {
            int randomIndex = (int) (Math.random() * roadsToBeDestroyed.size());
            destroyRoad.destroyRoad(roadsToBeDestroyed.get(randomIndex), this, players);
        }
    }

    public void decideToBuyDevelopmentCard(InterfaceDevelopmentCard interfaceDevelopmentCard) {
        if (!hasEnoughResources(Constants.DEVELOPMENT_CARD)) return;
        boolean decision = Math.random() < 1;
        if (decision) { interfaceDevelopmentCard.buyDevelopmentCard(null); }
    }
}