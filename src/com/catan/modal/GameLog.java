package com.catan.modal;

import java.util.ArrayList;
import java.util.List;


public class GameLog {
    private static GameLog GameLogInstance = null;
    private List<String[]> gameLog;

    private GameLog() {
        gameLog = new ArrayList<>();
    }

    public static GameLog getInstance() {
        if (GameLogInstance == null) {
            GameLogInstance = new GameLog();
        }
        return GameLogInstance;
    }

    public void clear() {
        gameLog.clear();
    }

    public void addLog(String log, String color) {
        gameLog.add(new String[] {log, color});
    }

    public String[] getLog(int index) {
        return gameLog.get(index);
    }

    public int size() {
        return gameLog.size();
    }
}

