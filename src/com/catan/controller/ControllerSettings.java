package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.MusicPlayer;
import com.catan.modal.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private AnchorPane root;
    @FXML
    private ComboBox<String> selectorLanguage;

    // properties
    private final static HashMap<String, String> languageMappings = new HashMap<>();

    @FXML
    public void initialize() {
        super.initialize();
        root.setStyle(
                "-fx-background-image: url("+ Constants.PATH_BG_SETTINGS() +");\n" +
                "-fx-background-size: cover;\n" +
                "-fx-pref-width: 1920;\n" +
                "-fx-pref-height: 1080;"
        );
        updateSoundImg();
        languageMappings.put("English", "en");
        languageMappings.put("Turkce", "tr");
        languageMappings.put("en", "English");
        languageMappings.put("tr", "Turkce");

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

        String currentLanguage = Settings.getInstance().getCurrentLanguage();
        selectorLanguage.getItems().add("English");
        selectorLanguage.getItems().add("Turkce");
        selectorLanguage.setPromptText(languageMappings.get(currentLanguage));
        selectorLanguage.setStyle("-fx-font: 20px \"Book " +
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
        if (theme.toLowerCase().equals(Constants.THEME_FOLDER)) return;
        Settings.getInstance().setCurrentTheme(theme);
        Constants.THEME_FOLDER = theme.toLowerCase();
        if (MusicPlayer.getMusicPlayer().isPlaying()) {
            MusicPlayer.getMusicPlayer().changeMusic(theme);
        }
        root.setStyle("-fx-background-image: url("+ Constants.PATH_BG_SETTINGS() +");\n" +
                      "-fx-background-size: cover;\n" +
                      "-fx-pref-width: 1920;\n" +
                      "-fx-pref-height: 1080;"
        );
    }

    @FXML
    public void selectLanguage(ActionEvent event) {
        String language = selectorLanguage.getValue();
        Settings.getInstance().setCurrentLanguage(languageMappings.get(language));
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"), bundle);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}