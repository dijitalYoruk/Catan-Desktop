package com.catan.controller;

import com.catan.modal.Settings;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettings extends ControllerBase {
    @FXML
    private Label largestArmyThreshold;
    @FXML
    private Label longestRoadThreshold;
    @FXML
    private Label victoryPointThreshold;
    @FXML
    private Rectangle right1;
    @FXML
    private Rectangle right2;
    @FXML
    private Rectangle right3;
    @FXML
    private Rectangle left1;
    @FXML
    private Rectangle left2;
    @FXML
    private Rectangle left3;
    @FXML
    private MenuButton themesDropdown;
    @FXML
    private MenuButton languagesDropdown;

    // properties
    private Settings settings;

    @FXML
    public void initialize() {
        Image right = new Image("./com/catan/assets/right-arrow.png");
        Image left = new Image("./com/catan/assets/left-arrow.png");
        right1.setFill(new ImagePattern(right));
        right1.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        right1.setStrokeWidth(1);

        right2.setFill(new ImagePattern(right));
        right2.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        right2.setStrokeWidth(1);

        right3.setFill(new ImagePattern(right));
        right3.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        right3.setStrokeWidth(1);

        left1.setFill(new ImagePattern(left));
        left1.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        left1.setStrokeWidth(1);

        left2.setFill(new ImagePattern(left));
        left2.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        left2.setStrokeWidth(1);

        left3.setFill(new ImagePattern(left));
        left3.setStroke(javafx.scene.paint.Color.color(0.4,0.4,0.4));
        left3.setStrokeWidth(1);

        settings = new Settings();
        largestArmyThreshold.setText(settings.getArmyThreshold() + "");
        victoryPointThreshold.setText(settings.getVictoryThreshold() + "");
        longestRoadThreshold.setText(settings.getRoadThreshold() + "");
        themesDropdown.setText(settings.getCurrentTheme());
        languagesDropdown.setText(settings.getCurrentLanguage());
    }

    @FXML
    public void changeTheme(ActionEvent actionEvent){
        settings.setCurrentTheme(((MenuItem)actionEvent.getTarget()).getText());
        themesDropdown.setText(settings.getCurrentTheme());
    }

    @FXML
    public void changeLanguage(ActionEvent actionEvent){
        settings.setCurrentLanguage(((MenuItem)actionEvent.getTarget()).getText());
        languagesDropdown.setText(settings.getCurrentLanguage());
    }

    @FXML
    public void changeThreshold(MouseEvent mouseEvent){
        String id = (((Rectangle)mouseEvent.getSource()).getId()).toString();

        if(id.substring(0, 1).equals("r"))
        {
            if(id.substring(5, 6).equals("1")){

                settings.setVictoryThreshold(settings.getVictoryThreshold()+1);
            }
            if(id.substring(5, 6).equals("2")){
                settings.setArmyThreshold(settings.getArmyThreshold()+1);
            }
            if(id.substring(5, 6).equals("3")){
                settings.setRoadThreshold(settings.getRoadThreshold()+1);
            }
        }else{
            if(id.substring(4, 5).equals("1")){
                if(settings.getVictoryThreshold() > 0)
                    settings.setVictoryThreshold(settings.getVictoryThreshold()-1);
            }
            if(id.substring(4, 5).equals("2")){
                if (settings.getArmyThreshold() > 0)
                    settings.setArmyThreshold(settings.getArmyThreshold()-1);
            }
            if(id.substring(4, 5).equals("3")){
                if (settings.getRoadThreshold() > 0)
                    settings.setRoadThreshold(settings.getRoadThreshold()-1);
            }
        }
        largestArmyThreshold.setText(settings.getArmyThreshold() + "");
        victoryPointThreshold.setText(settings.getVictoryThreshold() + "");
        longestRoadThreshold.setText(settings.getRoadThreshold() + "");
    }


}
