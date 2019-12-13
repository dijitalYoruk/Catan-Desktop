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
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettings extends ControllerBase {
    @FXML
    private Label largestArmyTh;
    @FXML
    private Label longestRoadTh;
    @FXML
    private Label victoryPointTh;
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
    private MenuButton themes;

    // properties
    private Settings settingTemp;
    private InputStream music;

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

        settingTemp = new Settings();
        largestArmyTh.setText(settingTemp.getArmyThreshold()+"");
        victoryPointTh.setText(settingTemp.getVictoryThreshold()+"");
        longestRoadTh.setText(settingTemp.getRoadThreshold()+"");
        themes.setText(settingTemp.getCurrentTheme());
    }

    @FXML
    public void changeTheme(ActionEvent actionEvent){
        settingTemp.setCurrentTheme(((MenuItem)actionEvent.getTarget()).getText());
        themes.setText(settingTemp.getCurrentTheme());

        //change music
        //TODO: add other theme music
        if(settingTemp.getCurrentTheme().equals("CatanDefault")){
            playMusic("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\music\\catan_theme.wav");
        }
    }

    @FXML
    public void playMusic(String filepath) {
        try
        {
            InputStream music = new FileInputStream(new File(filepath));
            AudioStream themeSong = new AudioStream(music);
            AudioPlayer.player.start(themeSong);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"music error");
        }
    }
    @FXML
    public void changeTh(MouseEvent mouseEvent){
        String id = (((Rectangle)mouseEvent.getSource()).getId()).toString();

        if(id.substring(0, 1).equals("r"))
        {

            if(id.substring(5, 6).equals("1")){

                settingTemp.setVictoryThreshold(settingTemp.getVictoryThreshold()+1);
            }
            if(id.substring(5, 6).equals("2")){
                settingTemp.setArmyThreshold(settingTemp.getArmyThreshold()+1);
            }
            if(id.substring(5, 6).equals("3")){
                settingTemp.setRoadThreshold(settingTemp.getRoadThreshold()+1);
            }
        }else{
            if(id.substring(4, 5).equals("1")){
                if(settingTemp.getVictoryThreshold() > 0)
                    settingTemp.setVictoryThreshold(settingTemp.getVictoryThreshold()-1);
            }
            if(id.substring(4, 5).equals("2")){
                if (settingTemp.getArmyThreshold() > 0)
                    settingTemp.setArmyThreshold(settingTemp.getArmyThreshold()-1);
            }
            if(id.substring(4, 5).equals("3")){
                if (settingTemp.getRoadThreshold() > 0)
                    settingTemp.setRoadThreshold(settingTemp.getRoadThreshold()-1);
            }
        }
        largestArmyTh.setText(settingTemp.getArmyThreshold()+"");
        victoryPointTh.setText(settingTemp.getVictoryThreshold()+"");
        longestRoadTh.setText(settingTemp.getRoadThreshold()+"");
    }


}
