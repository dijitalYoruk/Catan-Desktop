package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceMakeConstruction;

import java.util.ArrayList;

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
        if (hasEnoughResources(Constants.VILLAGE)) {
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
}