package com.catan.modal;

import com.catan.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trade {

    // properties
    private Player playerTrader;
    private Player playerToBeTraded;
    private Map<String, Integer> requestedResourceCards;
    private Map<String, Integer> offeredResourceCards;
    private boolean isTradeWithChest;
    private boolean isTradePossible;

    // constructor
    public Trade(Player trader, Player trading, Map<String, Integer> reqCards, Map<String, Integer> offeredCards, boolean isTradeWithChest) {
        this.isTradeWithChest = isTradeWithChest;
        playerTrader = trader;
        requestedResourceCards = reqCards;
        offeredResourceCards = offeredCards;
        isTradePossible = true;

        if (!isTradeWithChest) {
            playerToBeTraded = trading;
        }

        // evaluation of the possibility of trade
        if ((offeredResourceCards.get(Constants.CARD_WOOL)   == 0  &&
             offeredResourceCards.get(Constants.CARD_GRAIN)  == 0  &&
             offeredResourceCards.get(Constants.CARD_LUMBER) == 0  &&
             offeredResourceCards.get(Constants.CARD_BRICK)  == 0  &&
             offeredResourceCards.get(Constants.CARD_ORE)    == 0) ||
            (requestedResourceCards.get(Constants.CARD_WOOL)   == 0 &&
             requestedResourceCards.get(Constants.CARD_GRAIN)  == 0 &&
             requestedResourceCards.get(Constants.CARD_LUMBER) == 0 &&
             requestedResourceCards.get(Constants.CARD_BRICK)  == 0 &&
             requestedResourceCards.get(Constants.CARD_ORE)    == 0)) {
            isTradePossible = false;
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
            completeTrade();
        }
    }

    // methods

    /**
     * subtracts and adds Resource Cards after Trade is Accepted.
     */
    public void completeTrade() {

        if (isTradePossible) {

//            int differenceOre    = requestedResourceCards.get(Constants.CARD_ORE) - offeredResourceCards.get(Constants.CARD_ORE);
//            int differenceBrick  = requestedResourceCards.get(Constants.CARD_BRICK) - offeredResourceCards.get(Constants.CARD_BRICK);
//            int differenceLumber = requestedResourceCards.get(Constants.CARD_LUMBER) - offeredResourceCards.get(Constants.CARD_LUMBER);
//            int differenceGrain  = requestedResourceCards.get(Constants.CARD_GRAIN) - offeredResourceCards.get(Constants.CARD_GRAIN);
//            int differenceWool   = requestedResourceCards.get(Constants.CARD_WOOL) - offeredResourceCards.get(Constants.CARD_WOOL);

            // Resource Cards of actual player
            int newOreOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_ORE).size() + requestedResourceCards.get(Constants.CARD_ORE) - offeredResourceCards.get(Constants.CARD_ORE);
            int newBrickOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() + requestedResourceCards.get(Constants.CARD_BRICK) - offeredResourceCards.get(Constants.CARD_BRICK);
            int newLumberOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() + requestedResourceCards.get(Constants.CARD_LUMBER) - offeredResourceCards.get(Constants.CARD_LUMBER);
            int newGrainOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() + requestedResourceCards.get(Constants.CARD_GRAIN) - offeredResourceCards.get(Constants.CARD_GRAIN);
            int newWoolOfPlayerActual = playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() + requestedResourceCards.get(Constants.CARD_WOOL) - offeredResourceCards.get(Constants.CARD_WOOL);

            //    System.out.println("*me* ore:" + newOreOfPlayerActual + "brick" + newBrickOfPlayerActual + "lumber" + newLumberOfPlayerActual
            //    + "grain" + newGrainOfPlayerActual + "wool" + newWoolOfPlayerActual);

            while (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() != newOreOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() < newOreOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_ORE).add(new SourceCard(Constants.CARD_ORE, Constants.CARD_ORE));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_ORE).size() > newOreOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_ORE).remove(playerTrader.sourceCards.get(Constants.CARD_ORE).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() != newBrickOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() < newBrickOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_BRICK).add(new SourceCard(Constants.CARD_BRICK, Constants.CARD_BRICK));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_BRICK).size() > newBrickOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_BRICK).remove(playerTrader.sourceCards.get(Constants.CARD_BRICK).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() != newLumberOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() < newLumberOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_LUMBER).add(new SourceCard(Constants.CARD_LUMBER, Constants.CARD_LUMBER));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_LUMBER).size() > newLumberOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_LUMBER).remove(playerTrader.sourceCards.get(Constants.CARD_LUMBER).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() != newGrainOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() < newGrainOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_GRAIN).add(new SourceCard(Constants.CARD_GRAIN, Constants.CARD_GRAIN));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_GRAIN).size() > newGrainOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_GRAIN).remove(playerTrader.sourceCards.get(Constants.CARD_GRAIN).get(0));
                }
            }
            while (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() != newWoolOfPlayerActual) {
                if (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() < newWoolOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_WOOL).add(new SourceCard(Constants.CARD_WOOL, Constants.CARD_WOOL));
                } else if (playerTrader.getSourceCards().get(Constants.CARD_WOOL).size() > newWoolOfPlayerActual) {
                    playerTrader.sourceCards.get(Constants.CARD_WOOL).remove(playerTrader.sourceCards.get(Constants.CARD_WOOL).get(0));
                }
            }
        }
        if (isTradePossible && !isTradeWithChest) {
            // Resource Cards of OTHER player
            int newOreOfPlayerOther = playerToBeTraded.getSourceCards().get(Constants.CARD_ORE).size() + offeredResourceCards.get(Constants.CARD_ORE) - requestedResourceCards.get(Constants.CARD_ORE);
            int newBrickOfPlayerOther = playerToBeTraded.getSourceCards().get(Constants.CARD_BRICK).size() + offeredResourceCards.get(Constants.CARD_BRICK) - requestedResourceCards.get(Constants.CARD_BRICK);
            int newLumberOfPlayerOther = playerToBeTraded.getSourceCards().get(Constants.CARD_LUMBER).size() + offeredResourceCards.get(Constants.CARD_LUMBER) - requestedResourceCards.get(Constants.CARD_LUMBER);
            int newGrainOfPlayerOther = playerToBeTraded.getSourceCards().get(Constants.CARD_GRAIN).size() + offeredResourceCards.get(Constants.CARD_GRAIN) - requestedResourceCards.get(Constants.CARD_GRAIN);
            int newWoolOfPlayerOther = playerToBeTraded.getSourceCards().get(Constants.CARD_WOOL).size() + offeredResourceCards.get(Constants.CARD_WOOL) - requestedResourceCards.get(Constants.CARD_WOOL);

            while (playerToBeTraded.getSourceCards().get(Constants.CARD_ORE).size() != newOreOfPlayerOther) {
                if (playerToBeTraded.getSourceCards().get(Constants.CARD_ORE).size() < newOreOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_ORE).add(new SourceCard(Constants.CARD_ORE, Constants.CARD_ORE));
                } else if (playerToBeTraded.getSourceCards().get(Constants.CARD_ORE).size() > newOreOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_ORE).remove(playerToBeTraded.sourceCards.get(Constants.CARD_ORE).get(0));
                }
            }
            while (playerToBeTraded.getSourceCards().get(Constants.CARD_BRICK).size() != newBrickOfPlayerOther) {
                if (playerToBeTraded.getSourceCards().get(Constants.CARD_BRICK).size() < newBrickOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_BRICK).add(new SourceCard(Constants.CARD_BRICK, Constants.CARD_BRICK));
                } else if (playerToBeTraded.getSourceCards().get(Constants.CARD_BRICK).size() > newBrickOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_BRICK).remove(playerToBeTraded.sourceCards.get(Constants.CARD_BRICK).get(0));
                }
            }
            while (playerToBeTraded.getSourceCards().get(Constants.CARD_LUMBER).size() != newLumberOfPlayerOther) {
                if (playerToBeTraded.getSourceCards().get(Constants.CARD_LUMBER).size() < newLumberOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_LUMBER).add(new SourceCard(Constants.CARD_LUMBER, Constants.CARD_LUMBER));
                } else if (playerToBeTraded.getSourceCards().get(Constants.CARD_LUMBER).size() > newLumberOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_LUMBER).remove(playerToBeTraded.sourceCards.get(Constants.CARD_LUMBER).get(0));
                }
            }
            while (playerToBeTraded.getSourceCards().get(Constants.CARD_GRAIN).size() != newGrainOfPlayerOther) {
                if (playerToBeTraded.getSourceCards().get(Constants.CARD_GRAIN).size() < newGrainOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_GRAIN).add(new SourceCard(Constants.CARD_GRAIN, Constants.CARD_GRAIN));
                } else if (playerToBeTraded.getSourceCards().get(Constants.CARD_GRAIN).size() > newGrainOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_GRAIN).remove(playerToBeTraded.sourceCards.get(Constants.CARD_GRAIN).get(0));
                }
            }
            while (playerToBeTraded.getSourceCards().get(Constants.CARD_WOOL).size() != newWoolOfPlayerOther) {
                if (playerToBeTraded.getSourceCards().get(Constants.CARD_WOOL).size() < newWoolOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_WOOL).add(new SourceCard(Constants.CARD_WOOL, Constants.CARD_WOOL));
                } else if (playerToBeTraded.getSourceCards().get(Constants.CARD_WOOL).size() > newWoolOfPlayerOther) {
                    playerToBeTraded.sourceCards.get(Constants.CARD_WOOL).remove(playerToBeTraded.sourceCards.get(Constants.CARD_WOOL).get(0));
                }
            }
        }
    }

    public void outputNotPossible() {
        System.out.println("trade not possible");
    }

    public boolean isTradePossible() {
        return isTradePossible;
    }


}
