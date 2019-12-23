package com.catan.modal;

import com.catan.Util.Constants;

import java.util.HashMap;

public class Settings {

    // singleton properties
    private static Settings settings = new Settings();

    // properties
    private String currentTheme;
    private String currentLanguage;
    private Integer victoryThreshold;
    private Integer armyThreshold;
    private Integer roadThreshold;

    // constructor
    public Settings(){
        HashMap<String, Object> map = CustomFileReader.getInstance().readSettingsFile();
        currentTheme = (String) map.get(Constants.CURRENT_THEME);
        armyThreshold = (Integer) map.get(Constants.THRESHOLD_ARMY);
        roadThreshold = (Integer) map.get(Constants.THRESHOLD_ROAD);
        currentLanguage = (String) map.get(Constants.CURRENT_LANGUAGE);
        victoryThreshold = (Integer) map.get(Constants.THRESHOLD_VICTORY);
    }

    // methods
    private void saveRecords(){
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constants.CURRENT_THEME, currentTheme);
        map.put(Constants.THRESHOLD_ARMY, armyThreshold);
        map.put(Constants.THRESHOLD_ROAD, roadThreshold);
        map.put(Constants.THRESHOLD_VICTORY, victoryThreshold);
        map.put(Constants.CURRENT_LANGUAGE, currentLanguage);
        CustomFileReader.getInstance().writeSettingsFile(map);
    }

    public void setCurrentTheme(String theme){
        currentTheme = theme;
        saveRecords();
    }

    public void setVictoryThreshold(int victoryTh){
        victoryThreshold = victoryTh;
        saveRecords();
    }

    public void setArmyThreshold(int armyTh){
        armyThreshold = armyTh;
        saveRecords();
    }

    public void setRoadThreshold(int roadTh){
        roadThreshold = roadTh;
        saveRecords();
    }

    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
        saveRecords();
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

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void incrementThreshold(String resourceType) {
        switch (resourceType) {
            case Constants.THRESHOLD_VICTORY: victoryThreshold++; break;
            case Constants.THRESHOLD_ARMY: armyThreshold++; break;
            case Constants.THRESHOLD_ROAD: roadThreshold++; break;
        }
        saveRecords();
    }

    public void decrementThreshold(String resourceType) {
        switch (resourceType) {
            case Constants.THRESHOLD_VICTORY: victoryThreshold--; break;
            case Constants.THRESHOLD_ARMY: armyThreshold--; break;
            case Constants.THRESHOLD_ROAD: roadThreshold--; break;
        }
        saveRecords();
    }

    public static Settings getInstance() {
        return settings;
    }
}
