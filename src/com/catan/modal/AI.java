package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;
import com.catan.interfaces.InterfaceMakeTrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AI {

    public void decideToMakeConstruction(InterfaceMakeConstruction makeConstruction, PlayerAI player) {
        // Civilisation
        if (player.hasEnoughResources(Constants.CIVILISATION)) {
            if (Math.random() > 0.1) {
                ArrayList<Settlement> cities = getSettlementTypes(Constants.CITY, player.getSettlements());
                if (cities.size() > 0) {
                    makeConstruction.selectActualConstructionForAI(Constants.CIVILISATION);
                    player.makeSettlement(cities, makeConstruction);
                }
            }
        }
        // City
        if (player.hasEnoughResources(Constants.CITY)) {
            if (Math.random() > 0.15) {
                ArrayList<Settlement> villages = getSettlementTypes(Constants.VILLAGE, player.getSettlements());
                if (villages.size() > 0) {
                    makeConstruction.selectActualConstructionForAI(Constants.CITY);
                    player.makeSettlement(villages, makeConstruction);
                }
            }
        }
        // Village
        if (player.hasEnoughResources(Constants.VILLAGE) ) {
            ArrayList<Settlement> settlements = getSettlementTypes(Constants.VILLAGE, player.getSettlements());
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
        if (player.hasEnoughResources(Constants.ROAD)) {
            if ((player.getRoads().size() < 5 && Math.random() > 0.2) ||
                (player.getRoads().size() < 6 && Math.random() > 0.5) ||
                (player.getRoads().size() < 7 && Math.random() > 0.6) ||
                (player.getRoads().size() >= 7 && Math.random() > 0.8)) {
                makeConstruction.selectActualConstructionForAI(Constants.ROAD);
                makeConstruction.makeRoadActualForAI();
            }
        }
    }

    private ArrayList<Settlement> getSettlementTypes(String type, ArrayList<Settlement> paramSettlements) {
        ArrayList<Settlement> settlements = new ArrayList<>();
        for (Settlement settlement: paramSettlements) {
            if (settlement.getType().equals(type)) {
                settlements.add(settlement);
            }
        }
        return settlements;
    }


    public HashMap<String, Integer> getRequestedResourceCards(Player playerToBeTraded, HashMap<String, ArrayList<SourceCard>> resourceCards) {
        HashMap<String, Integer> requestedResourceCards = new HashMap<>();
        HashMap<String, ArrayList<SourceCard>> opponentsCards = playerToBeTraded.getSourceCards();
        int totalReqCount = 0;

        for (String resourceName: Constants.resourceNames) {
            requestedResourceCards.put(resourceName, 0);
        }

        for (String resourceName: Constants.resourceNames) {
            if (resourceCards.get(resourceName).size() < 1 && Math.random() < 0.7 && totalReqCount < 4 && opponentsCards.get(resourceName).size() > 0) {
                int count = Math.random() < 0.3 && opponentsCards.get(resourceName).size() > 1 ? 2 : 1;
                requestedResourceCards.put(resourceName, count);
                totalReqCount += count;
            }
        }
        for (String resourceName: Constants.resourceNames) {
            int requested = requestedResourceCards.get(resourceName);
            if (resourceCards.get(resourceName).size() <= 2 && Math.random() < 0.5 && requested == 0 && totalReqCount < 4 && opponentsCards.get(resourceName).size() > 0) {
                requestedResourceCards.put(resourceName, requested + 1);
                totalReqCount++;
            }
        }
        return requestedResourceCards;
    }

    public  HashMap<String, Integer> getRequestedResourceCardForChest(HashMap<String, ArrayList<SourceCard>> resourceCards) {
        HashMap<String, Integer> requestedResourceCards = new HashMap<>();
        for (String resourceName: Constants.resourceNames) {
            requestedResourceCards.put(resourceName, 0);
        }

        for (String resourceName: Constants.resourceNames) {
            if (resourceCards.get(resourceName).size() < 2 && Math.random() < 0.4) {
                requestedResourceCards.put(resourceName, 1);
                break;
            }
        }
        return requestedResourceCards;
    }

    public  HashMap<String, Integer> getOfferedResourceCardForChest(HashMap<String, Integer> requestedRC, HashMap<String, ArrayList<SourceCard>> resourceCards) {
        HashMap<String, Integer> offered = new HashMap<>();
        for (String resourceName: Constants.resourceNames) {
            offered.put(resourceName, 0);
        }
        for (String resourceName: Constants.resourceNames) {
            if (resourceCards.get(resourceName).size() >= 4 && requestedRC.get(resourceName) == 0) {
                offered.put(resourceName, 4);
                break;
            }
        }
        return offered;
    }

    public  HashMap<String, Integer> getOfferedResourceCards(Map<String, Integer> requestedResources, HashMap<String, ArrayList<SourceCard>> resourceCards) {
        int maxCount = 1;
        int totalCount = 0;

        HashMap<String, Integer> offeredResourceCards = new HashMap<>();
        for (String resourceName: Constants.resourceNames) {
            maxCount += requestedResources.get(resourceName);
            offeredResourceCards.put(resourceName, 0);
        }

        for (String resourceName: Constants.resourceNames) {
            if (resourceCards.get(resourceName).size() > 1 && totalCount < maxCount &&
                    requestedResources.get(resourceName) == 0 && Math.random() < 0.7) {
                int offeredCount = resourceCards.get(resourceName).size() > 2 ? 2 : 1;
                offeredResourceCards.put(resourceName, offeredCount);
                totalCount += offeredCount;
            }
        }
        for (String resourceName: Constants.resourceNames) {
            int offeredCount = offeredResourceCards.get(resourceName);
            if (resourceCards.get(resourceName).size() > 0 && requestedResources.get(resourceName) == 0 &&
                    totalCount < maxCount && offeredCount == 0 && Math.random() < 0.5) {
                offeredResourceCards.put(resourceName, 1);
                totalCount++;
            }
        }
        return offeredResourceCards;
    }

    public boolean respondToTradeRequest(HashMap<String, Integer> requestedResources, HashMap<String, Integer> offeredRequests) {
        int offeredCount = 0;
        int requestCount = 0;
        for (String resourceName: Constants.resourceNames) {
            requestCount += requestedResources.get(resourceName);
            offeredCount += offeredRequests.get(resourceName);
        }
        double ratio = (double) offeredCount / (double) (offeredCount + requestCount) - 0.1;
        return Math.random() < ratio;
    }

    public void decideToMakeTrade(InterfaceMakeTrade makeTrade, HashMap<String, ArrayList<SourceCard>> resourceCards) {
        int presentResourceTypeCount = 0;
        for (String resourceName: Constants.resourceNames) {
            if (resourceCards.get(resourceName).size() > 0) {
                presentResourceTypeCount++;
            }
        }

        double ratio = (double) presentResourceTypeCount / (double)resourceCards.size() + 0.4;
        boolean decision = Math.random() < ratio;
        if (decision) {
            boolean isTradeWithChest = Math.random() < 0.1;
            makeTrade.makeTradeForAI(isTradeWithChest);
        }
    }



}
