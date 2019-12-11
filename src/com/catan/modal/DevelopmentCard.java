package com.catan.modal;

import com.catan.Util.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentCard extends Card {

    public DevelopmentCard(String name) {
        super(name);
    }

    public void performCardFeatures() {
        //////to do later
    }

    public void performInventionCardFeatures(Player currentPlayer, HashMap<String, Integer> desiredResources) {
        System.out.print("[ ");
        for (String resourceName: Constants.resourceNames) {
            System.out.print(resourceName + " --> " + currentPlayer.getSourceCards().get(resourceName).size() + " | ");
        }
        System.out.println(" ]");

        System.out.print("[ ");
        for (String resourceName: Constants.resourceNames) {
            if (desiredResources.get(resourceName) != 0) {
                System.out.print(resourceName + " ");
                currentPlayer.addResources(resourceName, desiredResources.get(resourceName));
            }
        }
        System.out.println(" ]");

        System.out.print("[ ");
        for (String resourceName: Constants.resourceNames) {
            System.out.print(resourceName + " --> " + currentPlayer.getSourceCards().get(resourceName).size() + " | ");
        }
        System.out.println(" ]");
    }

    public void performKnightCardFeatures(Player currentPlayer) {
        currentPlayer.incrementKnightCount();
        System.out.println("--------------------------------");
        System.out.println("Name:" + currentPlayer.getName() + " Knight Count: " + currentPlayer.getKnightCount());
        System.out.println("--------------------------------");
    }

    public void performMonopolyCardFeatures(String resourceName, ArrayList<Player> allPlayers, Player currentPlayer) {
        System.out.print("[ ");
        for (String resource: Constants.resourceNames) {
            System.out.print(resource + " --> " + currentPlayer.getSourceCards().get(resource).size() + " | ");
        }
        System.out.println(" ]");

        int totalTransmissionCount = 0;
        for (Player player: allPlayers) {
            if (currentPlayer != player) {
                int count = player.getSourceCards().get(resourceName).size();
                player.removeResources(resourceName, count);
                totalTransmissionCount += count;
            }
        }

        System.out.println("Name: " + currentPlayer.getName() + " | Resource Name: "
                + resourceName + " | Transmission Count: " + totalTransmissionCount);
        currentPlayer.addResources(resourceName, totalTransmissionCount);

        System.out.print("[ ");
        for (String resource: Constants.resourceNames) {
            System.out.print(resource + " --> " + currentPlayer.getSourceCards().get(resource).size() + " | ");
        }
        System.out.println(" ]");

    }

    public void performVictoryCardFeatures(Player currentPlayer) {
        currentPlayer.incrementVictoryPoints();
        System.out.println("--------------------------------");
        System.out.println("Name:" + currentPlayer.getName() + " Victory Points: " + currentPlayer.getVictoryPoints());
        System.out.println("--------------------------------");

    }
}