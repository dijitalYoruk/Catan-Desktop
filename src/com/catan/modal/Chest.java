package com.catan.modal;

import com.catan.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class Chest {

    // properties
    private HashMap<String, Integer> developmentCards;

    // constructor
    public Chest() {
        developmentCards = new HashMap<>();
        developmentCards.put(Constants.DEVELOPMENT_CARD_INVENTION, 4);
        developmentCards.put(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION, 2);
        developmentCards.put(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE, 3);
        developmentCards.put(Constants.DEVELOPMENT_CARD_VICTORY_POINT, 2);
        developmentCards.put(Constants.DEVELOPMENT_CARD_MONOPOL, 3);
        developmentCards.put(Constants.DEVELOPMENT_CARD_KNIGHT, 5);
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
        return null;
    }

}