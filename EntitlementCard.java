package com.catan.modal;

public class EntitlementCard extends Card {

    private int numOfVictoryPoints;
    private int threshold;

    public EntitlementCard(String name, String instruction, int points, int threshold) {
        super(name, instruction);
        numOfVictoryPoints = points;
        this.threshold = threshold;
    }

    public int getVictoryPoints() {
        return numOfVictoryPoints;
    }

    public void setVictoryPoints(int points) {
        numOfVictoryPoints = points;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int newThreshold) {
        threshold = newThreshold;
    }
}
