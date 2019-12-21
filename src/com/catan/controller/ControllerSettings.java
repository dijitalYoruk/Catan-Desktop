package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.MusicPlayer;
import com.catan.modal.Settings;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class ControllerSettings extends ControllerBase {

    @FXML
    private Polygon decrementVictory;
    @FXML
    private Label labelVictory;
    @FXML
    private Polygon incrementVictory;
    @FXML
    private Polygon decrementArmy;
    @FXML
    private Label labelArmy;
    @FXML
    private Polygon incrementArmy;
    @FXML
    private Polygon decrementRoad;
    @FXML
    private Label labelRoad;
    @FXML
    private Polygon incrementRoad;
    @FXML
    private ComboBox<String> selectorTheme;

    @FXML
    public void initialize() {
        super.initialize();
        labelVictory.setText(Settings.getInstance().getVictoryThreshold() + "");
        labelArmy.setText(Settings.getInstance().getArmyThreshold() + "");
        labelRoad.setText(Settings.getInstance().getRoadThreshold() + "");
        String currentTheme = Settings.getInstance().getCurrentTheme();
        selectorTheme.getItems().add("Default");
        selectorTheme.getItems().add("Space");
        selectorTheme.getItems().add("Karadeniz");
        selectorTheme.setPromptText(currentTheme);
        selectorTheme.setStyle("-fx-font: 20px \"Book " +
                "Antiqua\"; -fx-background-color: orange");
    }

    @FXML
    void decrementThreshold(MouseEvent event) {
        Polygon polygon = (Polygon)event.getSource();
        if (polygon == decrementVictory && Settings.getInstance().getVictoryThreshold() > 0) {
            Settings.getInstance().decrementThreshold(Constants.THRESHOLD_VICTORY);
            labelVictory.setText(Settings.getInstance().getVictoryThreshold() + "");
        } else if (polygon == decrementArmy && Settings.getInstance().getArmyThreshold() > 0) {
            Settings.getInstance().decrementThreshold(Constants.THRESHOLD_ARMY);
            labelArmy.setText(Settings.getInstance().getArmyThreshold() + "");
        } else if (polygon == decrementRoad && Settings.getInstance().getRoadThreshold() > 0) {
            Settings.getInstance().decrementThreshold(Constants.THRESHOLD_ROAD);
            labelRoad.setText(Settings.getInstance().getRoadThreshold() + "");
        }
    }

    @FXML
    void incrementThreshold(MouseEvent event) {
        Polygon polygon = (Polygon)event.getSource();
        if (polygon == incrementVictory ) {
            Settings.getInstance().incrementThreshold(Constants.THRESHOLD_VICTORY);
            labelVictory.setText(Settings.getInstance().getVictoryThreshold() + "");
        } else if (polygon == incrementArmy) {
            Settings.getInstance().incrementThreshold(Constants.THRESHOLD_ARMY);
            labelArmy.setText(Settings.getInstance().getArmyThreshold() + "");
        } else if (polygon == incrementRoad) {
            Settings.getInstance().incrementThreshold(Constants.THRESHOLD_ROAD);
            labelRoad.setText(Settings.getInstance().getRoadThreshold() + "");
        }
    }

    @FXML
    public void selectTheme(ActionEvent event) {
        String theme = selectorTheme.getValue();
        Settings.getInstance().setCurrentTheme(theme);
        MusicPlayer.getMusicPlayer().changeMusic(theme);
    }

}