package com.catan.modal;

import com.catan.Util.Constants;

import java.util.HashMap;
import java.util.Map;

public class PriceCard extends Card {

        private Map<String, Integer> priceOfVillage;
        private Map<String, Integer> priceOfRoad;
        private Map<String, Integer> priceOfCity;
        private Map<String, Integer> priceOfCivilization;
        private Map<String, Integer> priceOfDevelopmentCard;


        public PriceCard(String name) {
            super(name);
            priceOfDevelopmentCard = new HashMap<>();
            priceOfCivilization = new HashMap<>();
            priceOfVillage = new HashMap<>();
            priceOfCity = new HashMap<>();
            priceOfRoad = new HashMap<>();

            // road price
            priceOfRoad.put(Constants.CARD_BRICK, 1 );
            priceOfRoad.put(Constants.CARD_WOOL, 0);
            priceOfRoad.put(Constants.CARD_ORE, 0);
            priceOfRoad.put(Constants.CARD_GRAIN, 0);
            priceOfRoad.put(Constants.CARD_LUMBER, 1);

            // village price
            priceOfVillage.put(Constants.CARD_BRICK, 1 );
            priceOfVillage.put(Constants.CARD_WOOL, 1);
            priceOfVillage.put(Constants.CARD_ORE, 0);
            priceOfVillage.put(Constants.CARD_GRAIN, 1);
            priceOfVillage.put(Constants.CARD_LUMBER, 1);

            // city price
            priceOfCity.put(Constants.CARD_BRICK, 0 );
            priceOfCity.put(Constants.CARD_WOOL, 0);
            priceOfCity.put(Constants.CARD_ORE, 3);
            priceOfCity.put(Constants.CARD_GRAIN, 2);
            priceOfCity.put(Constants.CARD_LUMBER, 0);

            // civilization price
            priceOfCivilization.put(Constants.CARD_BRICK, 1 );
            priceOfCivilization.put(Constants.CARD_WOOL, 0);
            priceOfCivilization.put(Constants.CARD_ORE, 3);
            priceOfCivilization.put(Constants.CARD_GRAIN, 2);
            priceOfCivilization.put(Constants.CARD_LUMBER, 1);

            // development card price
            priceOfDevelopmentCard.put(Constants.CARD_BRICK, 0 );
            priceOfDevelopmentCard.put(Constants.CARD_WOOL, 1);
            priceOfDevelopmentCard.put(Constants.CARD_ORE, 1);
            priceOfDevelopmentCard.put(Constants.CARD_GRAIN, 1);
            priceOfDevelopmentCard.put(Constants.CARD_LUMBER, 0);
        }

        public Map<String, Integer> getVillagePrice() {
            return priceOfVillage ;
        }

        public void setVillagePrice(Map<String, Integer> villagePrice) {
            priceOfVillage = villagePrice;
        }

        public Map<String, Integer> getRoadPrice() {
            return priceOfRoad;
        }

        public void setRoadPrice(Map<String, Integer> roadPrice) {
            priceOfRoad = roadPrice;
        }

        public Map<String, Integer> getCityPrice() {
            return priceOfCity;
        }

        public void setCityPrice(Map<String, Integer> cityPrice) {
            priceOfCity = cityPrice;
        }

        public Map<String, Integer> getCivilizationPrice() {
            return priceOfCivilization;
        }

        public void setCivilizationPrice(Map<String, Integer> civilizationPrice) {
            priceOfCivilization = civilizationPrice;
        }

        public Map<String, Integer> getCardPrice() { //dev card
            return priceOfDevelopmentCard;
        }

        public void setCardPrice(Map<String, Integer> cardPrice) { //dev card
            priceOfDevelopmentCard = cardPrice;
        }
}