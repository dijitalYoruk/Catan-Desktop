package com.catan.modal;

import com.catan.Util.Constants;

import java.util.*;

public class Trade {

    // properties
    private Player playerTrader;
    private Player playerToBeTraded;
    private HashMap<String, Integer> requestedResourceCards;
    private HashMap<String, Integer> offeredResourceCards;
    private boolean isTradeWithChest;
    private boolean isTradePossible;
    private boolean isTradeCompleted;
    private String errorMessage;

    // constructor
    public Trade(Player trader, Player playerToBeTraded, HashMap<String, Integer> reqCards, HashMap<String, Integer> offeredCards, boolean isTradeWithChest) {
        this.isTradeWithChest = isTradeWithChest;
        playerTrader = trader;
        requestedResourceCards = reqCards;
        offeredResourceCards = offeredCards;
        isTradePossible = true;
        isTradeCompleted = false;
        errorMessage = "";

        if (!isTradeWithChest) {
            this.playerToBeTraded = playerToBeTraded;
        }

        // evaluation of the possibility of trade
        if (offeredResourceCards.get(Constants.CARD_WOOL)   == 0 &&
            offeredResourceCards.get(Constants.CARD_GRAIN)  == 0 &&
            offeredResourceCards.get(Constants.CARD_LUMBER) == 0 &&
            offeredResourceCards.get(Constants.CARD_BRICK)  == 0 &&
            offeredResourceCards.get(Constants.CARD_ORE)    == 0) {
            isTradePossible = false;
            errorMessage = "yazılmayan";
            return;
        }
        if (requestedResourceCards.get(Constants.CARD_WOOL)   == 0 &&
            requestedResourceCards.get(Constants.CARD_GRAIN)  == 0 &&
            requestedResourceCards.get(Constants.CARD_LUMBER) == 0 &&
            requestedResourceCards.get(Constants.CARD_BRICK)  == 0 &&
            requestedResourceCards.get(Constants.CARD_ORE)    == 0) {
            isTradePossible = false;
            return;
        }

        if (playerToBeTraded != null) {
            HashMap<String, ArrayList<SourceCard>> resourceCards = playerToBeTraded.getSourceCards();
            isTradePossible = resourceCards.get(Constants.CARD_WOOL).size()   >= requestedResourceCards.get(Constants.CARD_WOOL)   &&
                              resourceCards.get(Constants.CARD_GRAIN).size()  >= requestedResourceCards.get(Constants.CARD_GRAIN)  &&
                              resourceCards.get(Constants.CARD_LUMBER).size() >= requestedResourceCards.get(Constants.CARD_LUMBER) &&
                              resourceCards.get(Constants.CARD_BRICK).size()  >= requestedResourceCards.get(Constants.CARD_BRICK)  &&
                              resourceCards.get(Constants.CARD_ORE).size()    >= requestedResourceCards.get(Constants.CARD_ORE);
        }

        if (isTradePossible) {
            if (playerToBeTraded instanceof PlayerAI) {
                boolean aiDecision = ((PlayerAI) playerToBeTraded).respondToTradeRequest(
                        requestedResourceCards, offeredResourceCards);

                if (aiDecision) {
                    printPlayerDetails();
                    completeTrade();
                    printPlayerDetails();
                } else {
                    errorMessage = "The trade request from " + playerTrader.getName() +
                            " was denied by " + playerToBeTraded.getName() + ".";
                    printTradeDetails();
                }
            }
            else if (isTradeWithChest) {
                printPlayerDetails();
                completeTrade();
                printPlayerDetails();
            }
        } else {
            printTradeDetails();
        }
    }

    // methods

    public void completeTrade() {
        ArrayList<String> resourceNames = new ArrayList<>(
                Arrays.asList(Constants.CARD_ORE, Constants.CARD_BRICK, Constants.CARD_LUMBER,
                        Constants.CARD_GRAIN, Constants.CARD_WOOL)
        );

        if (isTradePossible) {
            ArrayList<Integer> tradeDifferences = new ArrayList<>();
            // calculating differences
            for (String resourceName: resourceNames) {
                int difference = requestedResourceCards.get(resourceName) - offeredResourceCards.get(resourceName);
                tradeDifferences.add(difference);
            }
            // arranging resource cards of players
            exchangeResources(resourceNames, tradeDifferences, playerTrader);

            if (playerToBeTraded != null) {
                // Resource Cards of the player to be traded.
                tradeDifferences = new ArrayList<>();
                // calculating differences
                for (String resourceName: resourceNames) {
                    int difference = offeredResourceCards.get(resourceName) - requestedResourceCards.get(resourceName);
                    tradeDifferences.add(difference);
                }
                // arranging resource cards of players
                exchangeResources(resourceNames, tradeDifferences, playerToBeTraded);
            }

            printTradeDetails();
        }
    }

    private void exchangeResources(ArrayList<String> resourceNames, ArrayList<Integer> tradeDifferences, Player player) {
        for (int i = 0; i < tradeDifferences.size(); i++) {
            if (tradeDifferences.get(i) > 0) {
                player.addResources(resourceNames.get(i), tradeDifferences.get(i));
            } else if (tradeDifferences.get(i) < 0) {
                int difference = -1 * tradeDifferences.get(i);
                player.removeResources(resourceNames.get(i), difference);
            }
        }
    }

    public void printTradeDetails() {
        System.out.println("**********************************************************************");
        if (errorMessage.isEmpty()) {
            if (isTradeWithChest) {
                System.out.println("Trade between " + playerTrader.getName() + " and CHEST:" + isTradePossible);
            }

            System.out.println("------------------------------");
            System.out.println(">>>>" + "OBTAINED SOURCES" + "<<<<");
            Set<String> keySet = requestedResourceCards.keySet();
            for (String key: keySet) {
                System.out.println("* " +  key + ": " + requestedResourceCards.get(key));
            }
            System.out.println(">>>>" + "GIVEN SOURCES" + "<<<<");
            for (String key: keySet) {
                System.out.println("* " +  key + ": " + offeredResourceCards.get(key));
            }
            System.out.println("------------------------------");
            isTradeCompleted = true;

        } else {
            System.out.println(errorMessage);
        }
        System.out.println("**********************************************************************");
    }

    public void printPlayerDetails() {
        System.out.println("**********************************************************************");
        System.out.println("TRADER: " + playerTrader.getName());
        System.out.println("------------------------------");
        playerTrader.showSourceCards();
        if (playerToBeTraded != null) {
            System.out.println("------------------------------");
            System.out.println("TRADED: " + playerToBeTraded.getName());
            System.out.println("------------------------------");
            playerToBeTraded.showSourceCards();
        }
        System.out.println("**********************************************************************");
    }

    public Player getPlayerTrader() {
        return playerTrader;
    }

    public Player getPlayerToBeTraded() {
        return playerToBeTraded;
    }

    public HashMap<String, Integer> getRequestedResourceCards() {
        return requestedResourceCards;
    }

    public HashMap<String, Integer> getOfferedResourceCards() {
        return offeredResourceCards;
    }

    public void outputNotPossible() {
        System.out.println("trade not possible");
    }

    public boolean isTradePossible() {
        return isTradePossible;
    }

    public boolean isTradeCompleted() {
        return isTradeCompleted;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}