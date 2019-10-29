package com.catan.modal;

import java.util.Map;

public class PriceCard extends Card {

        private Map<String, Integer> priceOfVillage;
        private Map<String, Integer> priceOfRoad;
        private Map<String, Integer> priceOfCity;
        private Map<String, Integer> priceOfCivilization;
        private Map<String, Integer> priceOfDevelopmentCard;


        public PriceCard(String name) {
            super(name);

            // road price
            priceOfRoad.put("Brick", 1 );
            priceOfRoad.put("Wool", 0);
            priceOfRoad.put("Ore",0);
            priceOfRoad.put("Grain",0);
            priceOfRoad.put("Lumber",1);

            // village price
            priceOfVillage.put("Brick", 1 );
            priceOfVillage.put("Wool", 1);
            priceOfVillage.put("Ore",0);
            priceOfVillage.put("Grain",1);
            priceOfVillage.put("Lumber",1);

            // city price
            priceOfCity.put("Brick", 0 );
            priceOfCity.put("Wool", 0);
            priceOfCity.put("Ore",3);
            priceOfCity.put("Grain",2);
            priceOfCity.put("Lumber",0);

            // civilization price
            priceOfCivilization.put("Brick", 1 );
            priceOfCivilization.put("Wool", 0);
            priceOfCivilization.put("Ore",3);
            priceOfCivilization.put("Grain",2);
            priceOfCivilization.put("Lumber",1);

            // development card price
            priceOfDevelopmentCard.put("Brick", 0 );
            priceOfDevelopmentCard.put("Wool", 1);
            priceOfDevelopmentCard.put("Ore",1);
            priceOfDevelopmentCard.put("Grain",1);
            priceOfDevelopmentCard.put("Lumber",0);
        }

        public Map<String, Integer> getVillagePrice() {
            return priceOfRoad;
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


