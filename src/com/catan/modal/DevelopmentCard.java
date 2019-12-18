package com.catan.modal;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceDestroyRoad;
import com.catan.interfaces.InterfaceDevelopmentCard;
import com.catan.interfaces.InterfaceExchangeTurnProfit;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentCard extends Card implements InterfaceExchangeTurnProfit, InterfaceDestroyRoad {

    // properties
    private ArrayList<TerrainHex> terrainHexes;
    private boolean isDevelopmentCardUsed = false;
    private TerrainHex profitTerrainHex1;
    private TerrainHex profitTerrainHex2;

    public DevelopmentCard(String name) {
        super(name);
        profitTerrainHex1 = null;
        profitTerrainHex2 = null;
    }

    public void performInventionCardFeatures(Player currentPlayer, HashMap<String, Integer> desiredResources) {
        currentPlayer.showSourceCards();
        System.out.print("| ");
        for (String resourceName: Constants.resourceNames) {
            if (desiredResources.get(resourceName) != 0) {
                int count = desiredResources.get(resourceName);
                currentPlayer.addResources(resourceName, count);
                System.out.print(resourceName + " --> " + count + " | ");
            }
        }
        System.out.println("=> Desired Resources");
        currentPlayer.showSourceCards();
    }

    private void performKnightCardFeatures(Player currentPlayer) {
        currentPlayer.incrementKnightCount();
        String name = currentPlayer.getName();
        int count = currentPlayer.getKnightCount();
        System.out.println("Name:" + name + " Knight Count: " + count);
    }

    public void performMonopolyCardFeatures(String resourceName, ArrayList<Player> allPlayers, Player currentPlayer) {
        currentPlayer.showSourceCards();
        int totalTransmissionCount = 0;
        for (Player player: allPlayers) {
            if (currentPlayer != player) {
                int count = player.getSourceCards().get(resourceName).size();
                player.removeResources(resourceName, count);
                totalTransmissionCount += count;
            }
        }
        String name = currentPlayer.getName();
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.print("Name: " + name + " | ");
        System.out.print("Resource Name: " + resourceName + " | ");
        System.out.println("Transmission Count: " + totalTransmissionCount);
        currentPlayer.addResources(resourceName, totalTransmissionCount);
        System.out.println("---------------------------------------------------------------------------------------------");
        currentPlayer.showSourceCards();
    }

    private void performVictoryCardFeatures(Player currentPlayer) {
        currentPlayer.incrementVictoryPoints();
        System.out.print("Name:" + currentPlayer.getName() + " ");
        System.out.println("Victory Points: " + currentPlayer.getVictoryPoints());
    }

    @Override
    public void exchangeTurnProfit(Circle circle, Player player) {
        TerrainHex hex = getCorrespondingHex(circle);

        if (profitTerrainHex1 == null) {
            profitTerrainHex1 = hex;
            if (player instanceof PlayerActual) {
                circle.setFill(Color.ORANGE);
            }
        }
        else if (profitTerrainHex2 == null) {
            profitTerrainHex2 = hex;
            System.out.println("---------------------------------------------------------------------------------------------");
            System.out.print("Changed Labels: ");
            System.out.print("Hex1 --> "  + profitTerrainHex1.getNumberOnHex());
            System.out.print(" Hex2 --> " + profitTerrainHex2.getNumberOnHex());
            System.out.println();
            System.out.println("----------------------------------------------------------------------------------------------");

            if (player instanceof PlayerActual) {
                circle.setFill(Color.ORANGE);
                new Thread(() -> {
                    try { Thread.sleep(1500); }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(this::changeNumbersOnHexes);
                }).start();
            }
            else { changeNumbersOnHexes(); }
        }
    }

    private void changeNumbersOnHexes() {
        String number1 = profitTerrainHex1.getLabelOnHex().getText();
        String number2 = profitTerrainHex2.getLabelOnHex().getText();
        profitTerrainHex1.getLabelOnHex().setText(number2);
        profitTerrainHex2.getLabelOnHex().setText(number1);
        isDevelopmentCardUsed = true;
        refreshProfitExchangeHexes();
    }

    private TerrainHex getCorrespondingHex(Circle circle) {
        for (TerrainHex hex: terrainHexes) {
            if (hex.getCircleNumberOnHex() == circle) { return hex; }
        }
        return null;
    }

    private void refreshProfitExchangeHexes() {
        if (profitTerrainHex1 != null) { profitTerrainHex1.getCircleNumberOnHex().setFill(Color.WHITE); }
        if (profitTerrainHex2 != null) { profitTerrainHex2.getCircleNumberOnHex().setFill(Color.WHITE); }
        profitTerrainHex1 = null;
        profitTerrainHex2 = null;
    }

    public void destroyRoad(Road road, Player currentPlayer, ArrayList<Player> allPlayers) {
        if (road != null) {
            Player player = getOwnerOfRoad(road, allPlayers);
            if (player != null && player != currentPlayer) {
                Color color = Color.BLACK;
                road.getRoad().setStroke(color);
                road.getRoad().setVisible(false);
                player.getRoads().remove(road);
            }
        }
    }

    private Player getOwnerOfRoad(Road road, ArrayList<Player> allPlayers) {
        for (Player player: allPlayers) {
            for (Road r: player.getRoads()) {
                if (r == road) { return player; }
            }
        }
        return null;
    }

    public boolean isDevelopmentCardUsed() {
        return isDevelopmentCardUsed;
    }

    public void performDevelopmentCardFeatures(Player currentPlayer, ArrayList<Player> allPlayers,
                                               ArrayList<TerrainHex> terrainHexes,
                                               InterfaceDevelopmentCard interfaceDevelopmentCard) {

        System.out.println("==============================================================================================");
        System.out.println("Development Card Name: " + getName() + " | Player Name: " + currentPlayer.getName());

        Player playerActual = null;
        for (Player player: allPlayers) {
            if (player instanceof PlayerActual) { playerActual = player; }
        }

        if (playerActual == null) return;

        switch (getName()) {
            // Invention Development card.
            case Constants.DEVELOPMENT_CARD_INVENTION: {
                if (currentPlayer == playerActual) {
                    interfaceDevelopmentCard.openDialog(Constants.PATH_VIEW_DEV_INVENTION_CARD,
                            "Invention Development Card", this, null);
                } else {
                    HashMap<String, Integer> desiredResources = ((PlayerAI) currentPlayer).getInventionResourceCardSelection();
                    performInventionCardFeatures(currentPlayer, desiredResources);
                }
            } break;

            // Knight Development Card
            case Constants.DEVELOPMENT_CARD_KNIGHT: { performKnightCardFeatures(currentPlayer); } break;

            // Monopol Development Card
            case Constants.DEVELOPMENT_CARD_MONOPOL: {
                if (currentPlayer == playerActual) {
                    interfaceDevelopmentCard.openDialog(Constants.PATH_VIEW_DEV_MONOPOL_CARD,
                            "Monopol Development Card", this, null);
                } else {
                    String resourceName = ((PlayerAI) currentPlayer).getMonopolResourceCardDecision();
                    performMonopolyCardFeatures(resourceName, allPlayers, currentPlayer);
                }
            } break;

            // Profit Exchange Development Card
            case Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE: {
                this.terrainHexes = terrainHexes;
                if (currentPlayer == playerActual) {
                    interfaceDevelopmentCard.displayWarning("ProfitExchange");
                } else {
                    ((PlayerAI) currentPlayer).decideForHexesToExchangeProfit(terrainHexes, this);
                }
            } break;

            // Victory Development Card
            case Constants.DEVELOPMENT_CARD_VICTORY_POINT: { performVictoryCardFeatures(currentPlayer); } break;

            // Road Destruction Development Card
            case Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION: {
                if (currentPlayer == playerActual) {
                    interfaceDevelopmentCard.displayWarning("RoadDestruction");
                } else {
                    ((PlayerAI) currentPlayer).decideForDestroyingRoad(allPlayers, this);
                }
            } break;
        }

        System.out.println("==============================================================================================");
    }

}