package com.catan.modal;

import com.catan.Util.Constants;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;

public class CustomFileReader {

    // singleton properties
    private static CustomFileReader customFileReader = new CustomFileReader();

    // constructor
    private CustomFileReader() { }

    // methods
    public HashMap<String, Object> readSettingsFile() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            FileReader reader = new FileReader(Constants.PATH_SETTINGS_TEXT_FILE);
            BufferedReader fileReader = new BufferedReader(reader);
            String currentTheme = fileReader.readLine();
            Integer victThreshold = Integer.parseInt(fileReader.readLine());
            Integer armyThreshold = Integer.parseInt(fileReader.readLine());
            Integer roadThreshold = Integer.parseInt(fileReader.readLine());
            String currentLanguage = fileReader.readLine();
            map.put(Constants.CURRENT_THEME, currentTheme);
            map.put(Constants.THRESHOLD_ARMY, armyThreshold);
            map.put(Constants.THRESHOLD_ROAD, roadThreshold);
            map.put(Constants.THRESHOLD_VICTORY, victThreshold);
            map.put(Constants.CURRENT_LANGUAGE, currentLanguage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void writeSettingsFile(HashMap<String, Object> map) {
        String currentTheme = (String) map.get(Constants.CURRENT_THEME);
        Integer armyThreshold = (Integer) map.get(Constants.THRESHOLD_ARMY);
        Integer roadThreshold = (Integer) map.get(Constants.THRESHOLD_ROAD);
        Integer victThreshold = (Integer) map.get(Constants.THRESHOLD_VICTORY);
        String currentLanguage = (String) map.get(Constants.CURRENT_LANGUAGE);

        try {
            PrintWriter writer = new PrintWriter(Constants.PATH_SETTINGS_TEXT_FILE, "UTF-8");
            writer.println(currentTheme);
            writer.println(victThreshold);
            writer.println(armyThreshold);
            writer.println(roadThreshold);
            writer.println(currentLanguage);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomFileReader getInstance() {
        return customFileReader;
    }
}
