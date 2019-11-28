package com.catan.modal;

import com.catan.Util.Constants;

import java.util.Map;

public class Trade {

    //attributes
    private Player playerTrader;
    private Player playerTrading;
    private Map<String, Integer> requestedResourceCards;
    private Map<String, Integer> offeredResourceCards;

    private boolean isTradeWithChest;
    private boolean isTradePossible;
    //private Chest chest;  to be implemented...
    private int givenLumber;
    private int givenBrick;
    private int givenOre;
    private int givenWool;
    private int givenGrain;
    private int receivedLumber;
    private int receivedBrick;
    private int receivedOre;
    private int receivedWool;
    private int receivedGrain;

    //methods

    public Trade(Player trader, Player trading, Map<String, Integer> reqCards, Map<String, Integer> offeredCards, boolean isTradeWithChest) {
        this.isTradeWithChest = isTradeWithChest;
        playerTrader = trader;
        if (!isTradeWithChest) {
            playerTrading = trading;
        }
        requestedResourceCards = reqCards;
        offeredResourceCards = offeredCards;
        isTradePossible = true;
    }

    public boolean isTradePossible() {
        return isTradePossible;
    }

    public void completeTrade() { //subtracts and adds Resource Cards after Trade is Accepted.

        if (isTradePossible) {
            // Resource Cards of actual player
            int newOreOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_ORE).size() + requestedResourceCards.get("ore") - offeredResourceCards.get("ore");
            int newBrickOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() + requestedResourceCards.get("brick") - offeredResourceCards.get("brick");
            int newLumberOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() + requestedResourceCards.get("lumber") - offeredResourceCards.get("lumber");
            int newGrainOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() + requestedResourceCards.get("grain") - offeredResourceCards.get("grain");
            int newWoolOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() + requestedResourceCards.get("wool") - offeredResourceCards.get("wool");

            //    System.out.println("*me* ore:" + newOreOfPlayerActual + "brick" + newBrickOfPlayerActual + "lumber" + newLumberOfPlayerActual
            //    + "grain" + newGrainOfPlayerActual + "wool" + newWoolOfPlayerActual);

            while (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() != newOreOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() < newOreOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_ORE).add(new SourceCard("ore", "ore"));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() > newOreOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_ORE).remove(playerTrader.sourceCards.get(Constants.CARD_ORE).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() != newBrickOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() < newBrickOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_BRICK).add(new SourceCard("brick", "brick"));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() > newBrickOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_BRICK).remove(playerTrader.sourceCards.get(Constants.CARD_BRICK).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() != newLumberOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() < newLumberOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_LUMBER).add(new SourceCard("lumber", "lumber"));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() > newLumberOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_LUMBER).remove(playerTrader.sourceCards.get(Constants.CARD_LUMBER).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() != newGrainOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() < newGrainOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_GRAIN).add(new SourceCard("grain", "grain"));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() > newGrainOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_GRAIN).remove(playerTrader.sourceCards.get(Constants.CARD_GRAIN).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() != newWoolOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() < newWoolOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_WOOL).add(new SourceCard("wool", "wool"));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() > newWoolOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_WOOL).remove(playerTrader.sourceCards.get(Constants.CARD_WOOL).get(0));
                }
            }
        }
        if (isTradePossible && !isTradeWithChest) {
            // Resource Cards of OTHER player
            int newOreOfPlayerOther = playerTrading.getSourceCards().get(Constants.CARD_ORE).size() + offeredResourceCards.get("ore") - requestedResourceCards.get("ore");
            int newBrickOfPlayerOther = playerTrading.getSourceCards().get(Constants.CARD_BRICK).size() + offeredResourceCards.get("brick") - requestedResourceCards.get("brick");
            int newLumberOfPlayerOther = playerTrading.getSourceCards().get(Constants.CARD_LUMBER).size() + offeredResourceCards.get("lumber") - requestedResourceCards.get("lumber");
            int newGrainOfPlayerOther = playerTrading.getSourceCards().get(Constants.CARD_GRAIN).size() + offeredResourceCards.get("grain") - requestedResourceCards.get("grain");
            int newWoolOfPlayerOther = playerTrading.getSourceCards().get(Constants.CARD_WOOL).size() + offeredResourceCards.get("wool") - requestedResourceCards.get("wool");

            while (playerTrading.getSourceCards().get(Constants.CARD_ORE).size() != newOreOfPlayerOther) {
                if (playerTrading.getSourceCards().get(Constants.CARD_ORE).size() < newOreOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_ORE).add(new SourceCard("ore", "ore"));
                } else if (playerTrading.getSourceCards().get(Constants.CARD_ORE).size() > newOreOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_ORE).remove(playerTrading.sourceCards.get(Constants.CARD_ORE).get(0));
                }
            }
            while (playerTrading.getSourceCards().get(Constants.CARD_BRICK).size() != newBrickOfPlayerOther) {
                if (playerTrading.getSourceCards().get(Constants.CARD_BRICK).size() < newBrickOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_BRICK).add(new SourceCard("brick", "brick"));
                } else if (playerTrading.getSourceCards().get(Constants.CARD_BRICK).size() > newBrickOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_BRICK).remove(playerTrading.sourceCards.get(Constants.CARD_BRICK).get(0));
                }
            }
            while (playerTrading.getSourceCards().get(Constants.CARD_LUMBER).size() != newLumberOfPlayerOther) {
                if (playerTrading.getSourceCards().get(Constants.CARD_LUMBER).size() < newLumberOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_LUMBER).add(new SourceCard("lumber", "lumber"));
                } else if (playerTrading.getSourceCards().get(Constants.CARD_LUMBER).size() > newLumberOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_LUMBER).remove(playerTrading.sourceCards.get(Constants.CARD_LUMBER).get(0));
                }
            }
            while (playerTrading.getSourceCards().get(Constants.CARD_GRAIN).size() != newGrainOfPlayerOther) {
                if (playerTrading.getSourceCards().get(Constants.CARD_GRAIN).size() < newGrainOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_GRAIN).add(new SourceCard("grain", "grain"));
                } else if (playerTrading.getSourceCards().get(Constants.CARD_GRAIN).size() > newGrainOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_GRAIN).remove(playerTrading.sourceCards.get(Constants.CARD_GRAIN).get(0));
                }
            }
            while (playerTrading.getSourceCards().get(Constants.CARD_WOOL).size() != newWoolOfPlayerOther) {
                if (playerTrading.getSourceCards().get(Constants.CARD_WOOL).size() < newWoolOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_WOOL).add(new SourceCard("wool", "wool"));
                } else if (playerTrading.getSourceCards().get(Constants.CARD_WOOL).size() > newWoolOfPlayerOther) {
                    playerTrading.sourceCards.get(Constants.CARD_WOOL).remove(playerTrading.sourceCards.get(Constants.CARD_WOOL).get(0));
                }
            }
        }

        //output
        System.out.println(isTradeWithChest);
        System.out.println("TRADER:");
        playerTrader.showSourceCards();
        if (!isTradeWithChest) {
            System.out.println("TRADING:");
            playerTrading.showSourceCards();
        }
    }

    public void outputNotPossible() {
        System.out.println("trade not possible");
    }

    public void requestTrade() {
        // check trading players' resource cards
        if (!isTradeWithChest) {
            if (playerTrading.getSourceCards().get(Constants.CARD_WOOL).size() < requestedResourceCards.get("wool") ||
                    playerTrading.getSourceCards().get(Constants.CARD_GRAIN).size() < requestedResourceCards.get("grain") ||
                    playerTrading.getSourceCards().get(Constants.CARD_LUMBER).size() < requestedResourceCards.get("lumber") ||
                    playerTrading.getSourceCards().get(Constants.CARD_BRICK).size() < requestedResourceCards.get("brick") ||
                    playerTrading.getSourceCards().get(Constants.CARD_ORE).size() < requestedResourceCards.get("ore")) {
                isTradePossible = false;
            }
        }
        else {  //trade is with chest
            // TO DO: will implement a logical trade decision algo here
            isTradePossible = true;
            //substract and add Resource Cards
            completeTrade();
        }
    }


}
