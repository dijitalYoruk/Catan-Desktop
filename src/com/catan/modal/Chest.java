package com.catan.modal;

import com.catan.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Chest {

    // properties
    private HashMap<String, Integer> developmentCards;
    private ArrayList<Player> players;
    private EntitlementCard strongestArmy;
    private EntitlementCard longestRoad;
    private Player strongestArmyOwner;
    private Player longestRoadOwner;
    private Settings settings;

    // constructor
    public Chest(ArrayList<Player> players,Settings settings) {
        developmentCards = new HashMap<>();
        developmentCards.put(Constants.DEVELOPMENT_CARD_INVENTION, 4);
        developmentCards.put(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION, 1);
        developmentCards.put(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE, 3);
        developmentCards.put(Constants.DEVELOPMENT_CARD_VICTORY_POINT, 2);
        developmentCards.put(Constants.DEVELOPMENT_CARD_MONOPOL, 3);
        developmentCards.put(Constants.DEVELOPMENT_CARD_KNIGHT, 5);
        this.players = players;
        this.settings = settings;
        strongestArmy = new EntitlementCard("strongestArmyCard",2,settings.getArmyThreshold());
        longestRoad = new EntitlementCard("longestRoadCard",2,settings.getRoadThreshold());
        strongestArmyOwner = null;
        longestRoadOwner = null;
    }

    // methods
    public DevelopmentCard getDevelopmentCard() {
        ArrayList<String> keys = new ArrayList<>(developmentCards.keySet());
        for (int i = 0; i < keys.size(); i++) {
            if (developmentCards.get(keys.get(i)) == 0) { keys.remove(i--); }
        }
        if (keys.size() > 0) {
            int index = (int)(Math.random() * keys.size());
            int value = developmentCards.get(keys.get(index));
            developmentCards.put(keys.get(index), value-1);
            return new DevelopmentCard(keys.get(index));
        }

        System.out.println("");
        return null;
    }
    public String getStrongestArmyOwner() {
        if(strongestArmyOwner == null)
            return "";
        else
            return strongestArmyOwner.getName();
    }

    public EntitlementCard getStrongestArmyCard() {
        return strongestArmy;
    }

    public EntitlementCard getLongestRoad() {
        return longestRoad;
    }

    public void refreshStrongestArmyOwner() {
        Player cur;
        int knightCount;
        for (int i = 0; i < players.size(); i++) {
            cur = players.get(i);
            knightCount = cur.getKnightCount();
            if(strongestArmyOwner == null & knightCount >= strongestArmy.getThreshold())
                strongestArmyOwner = cur;
            else if(strongestArmyOwner != null)
            {
                if(cur.getKnightCount() > strongestArmyOwner.getKnightCount())
                    strongestArmyOwner = cur;
            }
        }
    }
    public String getLongestRoadOwner() {
        if(longestRoadOwner == null)
            return "";
        else
            return longestRoadOwner.getName();
    }
    public void refreshLongestRoadOwner() {
        Player cur;
        int roadCount;
        for (int i = 0; i < players.size(); i++) {
            cur = players.get(i);
            roadCount = cur.getLongestRoad();
            if(longestRoadOwner == null & roadCount >= longestRoad.getThreshold())
                longestRoadOwner = cur;
            else if(longestRoadOwner != null)
            {
                if(cur.getLongestRoad() > longestRoadOwner.getLongestRoad())
                    longestRoadOwner = cur;
            }
        }
    }
}