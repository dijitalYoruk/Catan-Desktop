package com.catan.modal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Settings {
    private String currentTheme;
    private int victoryThreshold;
    private int armyThreshold;
    private int roadThreshold;

    public Settings(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            currentTheme = reader.readLine();
            victoryThreshold = Integer.parseInt(reader.readLine());
            armyThreshold = Integer.parseInt(reader.readLine());
            roadThreshold = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentTheme(String theme){
        currentTheme = theme;
    }
    public void setVictoryThreshold(int victoryTh){
        victoryThreshold = victoryTh;
    }
    public void setArmyThreshold(int armyTh){
        armyThreshold = armyTh;
    }
    public void setRoadThreshold(int roadTh){
        roadThreshold = roadTh;
    }

    public int getVictoryThreshold(){
        return victoryThreshold;
    }
    public int getArmyThreshold(){
        return armyThreshold;
    }
    public int getRoadThreshold(){
        return roadThreshold;
    }
}
