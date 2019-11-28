package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerAI extends Player {

    private ArrayList<Player> playersList;
    public PlayerAI(String color, String name) {
        super(color, name);
    }

    private ArrayList<Settlement> getSettlementTypes(String type) {
        ArrayList<Settlement> settlements = new ArrayList<>();
        for (Settlement settlement: getSettlements()) {
            if (settlement.getType().equals(type)) {
                settlements.add(settlement);
            }
        }
        return settlements;
    }

    public void getActualAIDecision(InterfaceMakeConstruction makeConstruction) {
        // Civilisation
        if (hasEnoughResources(Constants.CIVILISATION)) {
            if (Math.random() > 0.1) {
                ArrayList<Settlement> cities = getSettlementTypes(Constants.CITY);
                if (cities.size() > 0) {
                    makeConstruction.selectActualConstructionForAI(Constants.CIVILISATION);
                    makeSettlement(cities, makeConstruction);
                }
            }
        }

        // City
        if (hasEnoughResources(Constants.CITY)) {
            if (Math.random() > 0.15) {
                ArrayList<Settlement> villages = getSettlementTypes(Constants.VILLAGE);
                if (villages.size() > 0) {
                    makeConstruction.selectActualConstructionForAI(Constants.CITY);
                    makeSettlement(villages, makeConstruction);
                }
            }
        }

        // Village
        if ( hasEnoughResources(Constants.VILLAGE) ) {
            ArrayList<Settlement> settlements = getSettlementTypes(Constants.VILLAGE);
            int villageCount = settlements.size();
            if ((villageCount == 0 && Math.random() > 0.40) ||
                (villageCount == 1 && Math.random() > 0.80) ||
                (villageCount == 2 && Math.random() > 0.90) ||
                (villageCount == 3 && Math.random() > 0.95) ||
                (villageCount == 4 && Math.random() > 0.99)) {
                makeConstruction.selectActualConstructionForAI(Constants.VILLAGE);
                makeConstruction.makeVillageActualForAI();
            }
        }
        // Road
        if (hasEnoughResources(Constants.ROAD)) {
            if ((roads.size() < 5 && Math.random() > 0.2) ||
                (roads.size() < 6 && Math.random() > 0.5) ||
                (roads.size() < 7 && Math.random() > 0.6) ||
                (roads.size() >= 7 && Math.random() > 0.8)) {
                makeConstruction.selectActualConstructionForAI(Constants.ROAD);
                makeConstruction.makeRoadActualForAI();
            }
        }
    }

    private void makeSettlement(ArrayList<Settlement> settlements, InterfaceMakeConstruction makeConstruction) {
        int index = (int) (Math.random() * settlements.size());
        Settlement civilisation = settlements.get(index);
        if (civilisation != null) {
            Vertex vertex = civilisation.getVertex();
            makeConstruction.makeConstructionActual(vertex.getShape());
        }
    }

    public void punish(){
        int punish = getTotalCards()/2;
        for(int i = 0; i < punish; i++){
            int randomRes = (int) Math.random()*5 + 1;
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
            sourceCards.get(res).remove(sourceCards.get(res).get(0));
        }
    }
    public  Map<String, Integer> getOfferedResourceCards() {
        Map<String, Integer> offeredResourceCards = new HashMap<String, Integer>();
        int offBrick = 0;
        int offLumber = 0;
        int offGrain = 0;
        int offOre = 0;
        int offWool = 0;

        if ( getSourceCards().get(Constants.CARD_WOOL).size() > 2) {
            offWool = getSourceCards().get(Constants.CARD_WOOL).size() - 1;
        }
        else if ( getSourceCards().get(Constants.CARD_ORE).size() > 2) {
            offOre = getSourceCards().get(Constants.CARD_ORE).size() - 1;
        }
        if ( getSourceCards().get(Constants.CARD_BRICK).size() > 2) {
            offBrick = getSourceCards().get(Constants.CARD_BRICK).size() - 1;
        }
        else if ( getSourceCards().get(Constants.CARD_LUMBER).size() > 2) {
            offLumber = getSourceCards().get(Constants.CARD_LUMBER).size() - 1;
        }
        if ( getSourceCards().get(Constants.CARD_GRAIN).size() > 2) {
            offGrain = getSourceCards().get(Constants.CARD_GRAIN).size() - 1;
        }
        offeredResourceCards.put("wool", offWool);
        offeredResourceCards.put("brick", offBrick);
        offeredResourceCards.put("lumber", offLumber);
        offeredResourceCards.put("grain", offGrain);
        offeredResourceCards.put("ore", offOre);
        return offeredResourceCards;
    }

    public  Map<String, Integer> getRequestedResourceCards() {
        Map<String, Integer> requestedResourceCards = new HashMap<String, Integer>();
        int reqBrick = 0;
        int reqLumber = 0;
        int reqGrain = 0;
        int reqOre = 0;
        int reqWool = 0;

        if ( getSourceCards().get(Constants.CARD_WOOL).size() < 2) {
            reqWool += 1;
        }
        else if ( getSourceCards().get(Constants.CARD_ORE).size() < 2) {
            reqOre += 1;
        }
        if ( getSourceCards().get(Constants.CARD_BRICK).size() < 2) {
            reqBrick += 1;
        }
        else if ( getSourceCards().get(Constants.CARD_LUMBER).size() < 2) {
            reqLumber += 1;
        }
        if ( getSourceCards().get(Constants.CARD_GRAIN).size() < 2) {
            reqGrain += 1;
        }
        requestedResourceCards.put("wool", reqWool);
        requestedResourceCards.put("brick", reqBrick);
        requestedResourceCards.put("lumber", reqLumber);
        requestedResourceCards.put("grain", reqGrain);
        requestedResourceCards.put("ore", reqOre);
        return requestedResourceCards;
    }
}