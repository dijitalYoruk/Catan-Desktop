package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.controller.ControllerGame;
import com.catan.interfaces.*;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.*;

public class PlayerAI extends Player {

    // properties
    private AI ai;

    // constructor
    public PlayerAI(String color, String name) {
        super(color, name);
        this.ai = new AI();
    }

    // methods
    public void punish(){
        int punish = getTotalCards()/2;
        for(int i = 0; i < punish; i++){
            int randomRes = (int) (Math.random() * 5 + 1);
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
            getSourceCards().get(res).remove(0);
        }
    }

    public void makeSettlement(ArrayList<Settlement> settlements, InterfaceMakeConstruction makeConstruction) {
        ai.makeSettlement(settlements, makeConstruction);
    }

    public void decideToMakeConstruction(InterfaceMakeConstruction makeConstruction) {
        ai.decideToMakeConstruction(makeConstruction, this);
    }

    public  HashMap<String, Integer> getRequestedResourceCards(Player playerToBeTraded) {
        return ai.getRequestedResourceCards(playerToBeTraded, getSourceCards());
    }

    public  HashMap<String, Integer> getRequestedResourceCardForChest() {
        return ai.getRequestedResourceCardForChest(getSourceCards());
    }

    public  HashMap<String, Integer> getOfferedResourceCardForChest(HashMap<String, Integer> requestedRC) {
        return ai.getOfferedResourceCardForChest(requestedRC, getSourceCards());
    }

    public  HashMap<String, Integer> getOfferedResourceCards(Map<String, Integer> requestedResources) {
        return ai.getOfferedResourceCards(requestedResources, getSourceCards());
    }

    public boolean respondToTradeRequest(HashMap<String, Integer> requestedResources, HashMap<String, Integer> offeredRequests) {
        return ai.respondToTradeRequest(requestedResources, offeredRequests);
    }

    public void decideToMakeTrade(InterfaceMakeTrade makeTrade) {
        ai.decideToMakeTrade(makeTrade, getSourceCards());
    }

    public HashMap<String, Integer> getInventionResourceCardSelection() {
        return ai.getInventionResourceCardSelection(getSourceCards());
    }

    public void decideToPlayDevelopmentCard(InterfaceDevelopmentCard interfaceDevelopmentCard) {
        ai.decideToPlayDevelopmentCard(interfaceDevelopmentCard, this);
    }

    public String getMonopolResourceCardDecision() {
        return ai.getMonopolResourceCardDecision(getSourceCards());
    }

    public void decideForHexesToExchangeProfit(ArrayList<TerrainHex> terrainHexes, InterfaceExchangeTurnProfit exchangeTurnProfit) {
        ai.decideForHexesToExchangeProfit(terrainHexes, exchangeTurnProfit, this);
    }

    public void decideForDestroyingRoad(ArrayList<Player> players, InterfaceDestroyRoad destroyRoad) {
        ai.decideForDestroyingRoad(players, destroyRoad, this);
    }

    public void decideToBuyDevelopmentCard(InterfaceDevelopmentCard interfaceDevelopmentCard) {
        ai.decideToBuyDevelopmentCard(interfaceDevelopmentCard, this);
    }
}