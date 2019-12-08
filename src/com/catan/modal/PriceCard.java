package com.catan.modal;

import com.catan.Util.Constants;

import java.util.HashMap;
import java.util.Map;

public class PriceCard {

        // properties
        private HashMap<String, HashMap<String, Integer>> prices;
        private static PriceCard priceCard = new PriceCard();

        // constructor
        private PriceCard() {
            prices = new HashMap<>();
            prices.put(Constants.ROAD, new HashMap<>());
            prices.put(Constants.CITY, new HashMap<>());
            prices.put(Constants.VILLAGE, new HashMap<>());
            prices.put(Constants.CIVILISATION, new HashMap<>());
            prices.put(Constants.DEVELOPMENT_CARD, new HashMap<>());
            // road price
            prices.get(Constants.ROAD).put(Constants.CARD_BRICK, 1 );
            prices.get(Constants.ROAD).put(Constants.CARD_WOOL, 0);
            prices.get(Constants.ROAD).put(Constants.CARD_ORE, 0);
            prices.get(Constants.ROAD).put(Constants.CARD_GRAIN, 0);
            prices.get(Constants.ROAD).put(Constants.CARD_LUMBER, 1);
            // village price
            prices.get(Constants.VILLAGE).put(Constants.CARD_BRICK, 1 );
            prices.get(Constants.VILLAGE).put(Constants.CARD_WOOL, 1);
            prices.get(Constants.VILLAGE).put(Constants.CARD_ORE, 0);
            prices.get(Constants.VILLAGE).put(Constants.CARD_GRAIN, 1);
            prices.get(Constants.VILLAGE).put(Constants.CARD_LUMBER, 1);
            // city price
            prices.get(Constants.CITY).put(Constants.CARD_BRICK, 0 );
            prices.get(Constants.CITY).put(Constants.CARD_WOOL, 0);
            prices.get(Constants.CITY).put(Constants.CARD_ORE, 3);
            prices.get(Constants.CITY).put(Constants.CARD_GRAIN, 2);
            prices.get(Constants.CITY).put(Constants.CARD_LUMBER, 0);
            // civilization price
            prices.get(Constants.CIVILISATION).put(Constants.CARD_BRICK, 1 );
            prices.get(Constants.CIVILISATION).put(Constants.CARD_WOOL, 0);
            prices.get(Constants.CIVILISATION).put(Constants.CARD_ORE, 3);
            prices.get(Constants.CIVILISATION).put(Constants.CARD_GRAIN, 2);
            prices.get(Constants.CIVILISATION).put(Constants.CARD_LUMBER, 1);
            // development card price
            prices.get(Constants.DEVELOPMENT_CARD).put(Constants.CARD_BRICK, 0 );
            prices.get(Constants.DEVELOPMENT_CARD).put(Constants.CARD_WOOL, 1);
            prices.get(Constants.DEVELOPMENT_CARD).put(Constants.CARD_ORE, 1);
            prices.get(Constants.DEVELOPMENT_CARD).put(Constants.CARD_GRAIN, 1);
            prices.get(Constants.DEVELOPMENT_CARD).put(Constants.CARD_LUMBER, 0);
        }

        // methods
        public HashMap<String, Integer> getPrice(String type) {
            return prices.get(type);
        }

        public static PriceCard getInstance() {
            return priceCard;
        }
}