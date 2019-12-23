package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Thief {

    // properties
    private TerrainHex terrainHex;
    private Circle imageThief;
    private boolean canThiefMove;

    // constructor
    public Thief(TerrainHex terrainHex, Circle imageThief) {
        this.imageThief = imageThief;
        this.imageThief.setVisible(true);
        Image imgThief = new Image(Constants.ICON_THIEF);
        this.imageThief.setFill(new ImagePattern(imgThief));
        setTerrainHex(terrainHex);
        canThiefMove = false;
    }

    // methods
    public TerrainHex getTerrainHex() {
        return terrainHex;
    }

    public void setTerrainHex(TerrainHex terrainHex) {
        this.terrainHex = terrainHex;
        imageThief.setLayoutX(terrainHex.getCircleNumberOnHex().getLayoutX());
        imageThief.setLayoutY(terrainHex.getCircleNumberOnHex().getLayoutY());
    }

    public void updateLocation(double x, double y) {
        imageThief.setLayoutX(x);
        imageThief.setLayoutY(y);
    }

    public boolean canThiefMove() {
        return canThiefMove;
    }

    public void setCanThiefMove(boolean canThiefMove) {
        this.canThiefMove = canThiefMove;
    }

    public void punishUsersAroundHexByThief(Player currentPlayer){
        ArrayList<Player> playersAround = getTerrainHex().getPlayersAround();
        playersAround.remove(currentPlayer);

        for (int i = 0; i < playersAround.size(); i++) {
            if ((playersAround.get(i) == currentPlayer) ||
                    playersAround.get(i).getTotalCards() == 0) {
                playersAround.remove(playersAround.get(i--));
            }
        }

        // there is nobody around
        int size = playersAround.size();
        if ((size == 0)) { return; }

        // selecting one random card and transferring the card.
        int randomPlayerIndex = (int) (Math.random() * size);
        Player playerToBePunished = playersAround.get(randomPlayerIndex);
        String randomResource = playerToBePunished.getPunishedByThief();
        currentPlayer.addResources(randomResource, 1);
    }

}