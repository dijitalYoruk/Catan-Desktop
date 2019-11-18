package com.catan.modal;

public class Trade {

    //attributes
    private Player playerTrader;
    private Player playerTrading;
    private boolean isTradeWithChest;
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

    public Trade(Player trader, Player trading) {

    }//, Chest chest) {}  to be implemented...

    public boolean isTradePossible() {
        return true;
    }

    public void completeTrade() {

    }
    public void outputNotPossible() {

    }
    public void requestTrade() {

    }
    //new methods
    public void setTradeWith(String playerName) {
        //playerTrading = // player selected
    }


}
