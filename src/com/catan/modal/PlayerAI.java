package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;
import com.catan.interfaces.InterfaceMakeTrade;

import java.util.*;

public class PlayerAI extends Player {

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

    public  HashMap<String, Integer> getRequestedResourceCards(Player playerToBeTraded) {
        HashMap<String, Integer> requestedResourceCards = new HashMap<>();
        HashMap<String, ArrayList<SourceCard>> opponentsCards = playerToBeTraded.getSourceCards();
        int reqBrick = 0;
        int reqLumber = 0;
        int reqGrain = 0;
        int reqOre = 0;
        int reqWool = 0;
        int totalReqCount = 0;

        if (getSourceCards().get(Constants.CARD_WOOL).size() < 1 && Math.random() < 0.7 &&
                totalReqCount < 4 && opponentsCards.get(Constants.CARD_WOOL).size() > 0) {
            reqWool += Math.random() < 0.3 && opponentsCards.get(Constants.CARD_WOOL).size() > 1 ? 2 : 1;
            totalReqCount += reqWool;
        }
        if (getSourceCards().get(Constants.CARD_ORE).size() < 1 && Math.random() < 0.7 &&
                totalReqCount < 4 && opponentsCards.get(Constants.CARD_ORE).size() > 0) {
            reqOre += Math.random() < 0.3 && opponentsCards.get(Constants.CARD_ORE).size() > 1 ? 2 : 1;
            totalReqCount += reqOre;
        }
        if (getSourceCards().get(Constants.CARD_BRICK).size() < 1 && Math.random() < 0.7 &&
                totalReqCount < 4 && opponentsCards.get(Constants.CARD_BRICK).size() > 0) {
            reqBrick += Math.random() < 0.3 && opponentsCards.get(Constants.CARD_BRICK).size() > 1 ? 2 : 1;
            totalReqCount += reqBrick;
        }
        if (getSourceCards().get(Constants.CARD_LUMBER).size() < 1 && Math.random() < 0.7 &&
                totalReqCount < 4  && opponentsCards.get(Constants.CARD_LUMBER).size() > 0) {
            reqLumber += Math.random() < 0.3 && opponentsCards.get(Constants.CARD_LUMBER).size() > 1 ? 2 : 1;
            totalReqCount += reqLumber;
        }
        if (getSourceCards().get(Constants.CARD_GRAIN).size() < 1 && Math.random() < 0.7 &&
                totalReqCount < 4 && opponentsCards.get(Constants.CARD_GRAIN).size() > 0) {
            reqGrain += Math.random() < 0.3 && opponentsCards.get(Constants.CARD_GRAIN).size() > 1 ? 2 : 1;
            totalReqCount += reqGrain;
        }
        if (getSourceCards().get(Constants.CARD_BRICK).size() <= 2 && Math.random() < 0.5 &&
                reqBrick == 0 && totalReqCount < 4 && opponentsCards.get(Constants.CARD_BRICK).size() > 0) {
            reqBrick += 1;
            totalReqCount++;
        }
        if (getSourceCards().get(Constants.CARD_LUMBER).size() <= 2 && Math.random() < 0.5 &&
                reqLumber == 0 && totalReqCount < 4 && opponentsCards.get(Constants.CARD_LUMBER).size() > 0) {
            reqLumber += 1;
            totalReqCount++;
        }
        if (getSourceCards().get(Constants.CARD_WOOL).size() <= 2 && Math.random() < 0.5 &&
                reqWool == 0 && totalReqCount < 4 && opponentsCards.get(Constants.CARD_WOOL).size() > 0) {
            reqWool += 1;
            totalReqCount++;
        }
        if (getSourceCards().get(Constants.CARD_ORE).size() <= 2 && Math.random() < 0.5 &&
                reqOre == 0 && totalReqCount < 4 && opponentsCards.get(Constants.CARD_ORE).size() > 0) {
            reqOre += 1;
            totalReqCount++;
        }
        if (getSourceCards().get(Constants.CARD_GRAIN).size() <= 2 && Math.random() < 0.5 &&
                reqGrain == 0 && totalReqCount < 4 && opponentsCards.get(Constants.CARD_GRAIN).size() > 0) {
            reqGrain += 1;
        }

        requestedResourceCards.put(Constants.CARD_WOOL, reqWool);
        requestedResourceCards.put(Constants.CARD_BRICK, reqBrick);
        requestedResourceCards.put(Constants.CARD_LUMBER, reqLumber);
        requestedResourceCards.put(Constants.CARD_GRAIN, reqGrain);
        requestedResourceCards.put(Constants.CARD_ORE, reqOre);
        return requestedResourceCards;
    }

    public  HashMap<String, Integer> getRequestedResourceCardForChest() {
        HashMap<String, Integer> requestedResourceCards = new HashMap<>();
        int reqBrick = 0;
        int reqLumber = 0;
        int reqGrain = 0;
        int reqOre = 0;
        int reqWool = 0;

        if (getSourceCards().get(Constants.CARD_BRICK).size()       < 2 && Math.random() < 0.4) { reqBrick  += 1; }
        else if (getSourceCards().get(Constants.CARD_LUMBER).size() < 2 && Math.random() < 0.4) { reqLumber += 1; }
        else if (getSourceCards().get(Constants.CARD_WOOL).size()   < 2 && Math.random() < 0.4) { reqWool   += 1; }
        else if (getSourceCards().get(Constants.CARD_ORE).size()    < 2 && Math.random() < 0.4) { reqOre    += 1; }
        else if (getSourceCards().get(Constants.CARD_GRAIN).size()  < 2 && Math.random() < 0.4) { reqGrain  += 1; }

        requestedResourceCards.put(Constants.CARD_WOOL, reqWool);
        requestedResourceCards.put(Constants.CARD_BRICK, reqBrick);
        requestedResourceCards.put(Constants.CARD_LUMBER, reqLumber);
        requestedResourceCards.put(Constants.CARD_GRAIN, reqGrain);
        requestedResourceCards.put(Constants.CARD_ORE, reqOre);
        return requestedResourceCards;
    }

    public  HashMap<String, Integer> getOfferedResourceCardForChest(HashMap<String, Integer> requestedRC) {
        HashMap<String, Integer> offered = new HashMap<>();
        ArrayList<String> resourceNames = new ArrayList<>(
                Arrays.asList(Constants.CARD_ORE, Constants.CARD_BRICK, Constants.CARD_LUMBER,
                        Constants.CARD_GRAIN, Constants.CARD_WOOL)
        );

        for (String resourceName: resourceNames) {
            offered.put(resourceName, 0);
        }

        for (String resourceName: resourceNames) {
            if (sourceCards.get(resourceName).size() >= 4 &&
                    requestedRC.get(resourceName) == 0) {
                offered.put(resourceName, 4);
                break;
            }
        }

        return offered;
    }

    public  HashMap<String, Integer> getOfferedResourceCards(Map<String, Integer> requestedResources) {
        int maxCount = 1;
        int totalCount = 0;
        Set<String> keys = requestedResources.keySet();
        for (String key: keys) { maxCount += requestedResources.get(key); }

        HashMap<String, Integer> offeredResourceCards = new HashMap<>();
        int offBrick = 0;
        int offLumber = 0;
        int offGrain = 0;
        int offOre = 0;
        int offWool = 0;

        if (getSourceCards().get(Constants.CARD_WOOL).size() > 1 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_WOOL) == 0 && Math.random() < 0.7) {
            offWool += getSourceCards().get(Constants.CARD_WOOL).size() > 2 ? 2 : 1;
            totalCount += offWool;
        }
        if (getSourceCards().get(Constants.CARD_ORE).size() > 1 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_ORE) == 0 && Math.random() < 0.7) {
            offOre += getSourceCards().get(Constants.CARD_ORE).size() > 2 ? 2 : 1;
            totalCount += offOre;
        }
        if (getSourceCards().get(Constants.CARD_BRICK).size() > 1 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_BRICK) == 0 && Math.random() < 0.7) {
            offBrick += getSourceCards().get(Constants.CARD_BRICK).size() > 2 ? 2 : 1;
            totalCount += offBrick;
        }
        if (getSourceCards().get(Constants.CARD_LUMBER).size() > 1 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_LUMBER) == 0 && Math.random() < 0.7) {
            offLumber += getSourceCards().get(Constants.CARD_LUMBER).size() > 2 ? 2 : 1;
            totalCount += offLumber;
        }
        if (getSourceCards().get(Constants.CARD_GRAIN).size() > 1 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_GRAIN) == 0 && Math.random() < 0.7) {
            offGrain += getSourceCards().get(Constants.CARD_GRAIN).size() > 2 ? 2 : 1;
            totalCount += offGrain;
        }


        if (getSourceCards().get(Constants.CARD_WOOL).size() > 0 && totalCount < maxCount &&
            requestedResources.get(Constants.CARD_WOOL) == 0 && offWool == 0 && Math.random() < 0.5) {
            totalCount++;
            offWool = 1;
        }
        if (getSourceCards().get(Constants.CARD_ORE).size() > 0 && totalCount < maxCount &&
                requestedResources.get(Constants.CARD_ORE) == 0 && offOre == 0 && Math.random() < 0.5) {
            totalCount++;
            offOre = 1;
        }
        if (getSourceCards().get(Constants.CARD_BRICK).size() > 0 && totalCount < maxCount &&
                requestedResources.get(Constants.CARD_BRICK) == 0 && offBrick == 0 && Math.random() < 0.5) {
            totalCount++;
            offBrick = 1;
        }
        if (getSourceCards().get(Constants.CARD_LUMBER).size() > 0 && totalCount < maxCount &&
                requestedResources.get(Constants.CARD_LUMBER) == 0 && offLumber == 0 && Math.random() < 0.5) {
            totalCount++;
            offLumber = 1;
        }

        if (getSourceCards().get(Constants.CARD_GRAIN).size() > 0 && totalCount < maxCount &&
                requestedResources.get(Constants.CARD_GRAIN) == 0 && offGrain == 0 && Math.random() < 0.5) {
            offWool = 1;
        }

        offeredResourceCards.put(Constants.CARD_WOOL, offWool);
        offeredResourceCards.put(Constants.CARD_BRICK, offBrick);
        offeredResourceCards.put(Constants.CARD_LUMBER, offLumber);
        offeredResourceCards.put(Constants.CARD_GRAIN, offGrain);
        offeredResourceCards.put(Constants.CARD_ORE, offOre);
        return offeredResourceCards;
    }

    public boolean respondToTradeRequest(HashMap<String, Integer> requestedResources, HashMap<String, Integer> offeredRequests) {
        int offeredCount = 0;
        int requestCount = 0;
        Set<String> keySet = offeredRequests.keySet();
        for (String key: keySet) {
            requestCount += requestedResources.get(key);
            offeredCount += offeredRequests.get(key);
        }
        double ratio = (double) offeredCount / (double) (offeredCount + requestCount) - 0.1;
        return Math.random() < ratio;
    }

    public void decideToMakeTrade(InterfaceMakeTrade makeTrade) {
        int presentResourceTypeCount = 0;
        Set<String> keySet = sourceCards.keySet();
        for (String key: keySet) {
            if (sourceCards.get(key).size() > 0) {
                presentResourceTypeCount++;
            }
        }

        double ratio = (double) presentResourceTypeCount / (double)sourceCards.size() + 0.4;
        boolean decision = Math.random() < ratio;

        if (decision) {
            boolean isTradeWithChest = Math.random() < 0.1;
            makeTrade.makeTradeForAI(isTradeWithChest);
        }
    }

}