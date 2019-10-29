package com.catan.modal;

import java.util.Map;

public class PriceCard extends Card {

        private Map<String, Integer> priceOfVillage;
        private Map<String, Integer> priceOfRoad;
        private Map<String, Integer> priceOfCity;
        private Map<String, Integer> priceOfCivilization;
        private Map<String, Integer> priceOfDevelopmentCard;


        public PriceCard(String name, String instruction, Map<String, Integer> pVillage, Map<String, Integer> pRoad,
                         Map<String, Integer> pCity, Map<String, Integer> pCivilization, Map<String, Integer> pDevelopmentCard) {
            super(name, instruction);
            priceOfVillage = pVillage;
            priceOfRoad = pRoad;
            priceOfCity = pCity;
            priceOfCivilization = pCivilization;
            priceOfDevelopmentCard = pDevelopmentCard;
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


