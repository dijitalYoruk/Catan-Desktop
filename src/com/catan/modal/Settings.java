package com.catan.modal;

import java.io.*;
import java.nio.file.Paths;

public class Settings {
    public static final String languauge = "en";
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
    private void writeToLocal(){
        FileOutputStream writer = null;
        try {
            writer = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writer.write((currentTheme+ "\n").getBytes() );
            writer.write((victoryThreshold+ "\n").getBytes());
            writer.write((armyThreshold+"\n").getBytes() );
            writer.write((roadThreshold+"").getBytes());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setCurrentTheme(String theme){
        currentTheme = theme;
        writeToLocal();
    }
    public void setVictoryThreshold(int victoryTh){
        victoryThreshold = victoryTh;
        writeToLocal();
    }
    public void setArmyThreshold(int armyTh){
        armyThreshold = armyTh;
        writeToLocal();
    }
    public void setRoadThreshold(int roadTh){
        roadThreshold = roadTh;
        writeToLocal();
    }

    public String getCurrentTheme(){
        return currentTheme;
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
