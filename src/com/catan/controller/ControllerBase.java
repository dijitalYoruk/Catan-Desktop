package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.GameLog;
import com.catan.modal.MusicPlayer;

import com.catan.modal.Settings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerBase {

    @FXML
    private ImageView imgSound;

    @FXML
    public void initialize() {
        if (MusicPlayer.getMusicPlayer().isPlaying()) {
            Image image = new Image(Constants.IMG_SOUND_MUTE);
            imgSound.setImage(image);
        } else {
            Image image = new Image(Constants.IMG_SOUND_UNMUTED);
            imgSound.setImage(image);
        }
    }

    @FXML
    public void returnToProgram(ActionEvent actionEvent) {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.PATH_VIEW_PROGRAM)), bundle);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
            GameLog gameLog = GameLog.getInstance();
            gameLog.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void arrangeSound(MouseEvent event) {
        if (MusicPlayer.getMusicPlayer().isPlaying()) {
            MusicPlayer.getMusicPlayer().unmute();
            Image image = new Image(Constants.IMG_SOUND_UNMUTED);
            imgSound.setImage(image);
        } else {
            MusicPlayer.getMusicPlayer().mute();
            Image image = new Image(Constants.IMG_SOUND_MUTE);
            imgSound.setImage(image);
        }
    }

    void updateSoundImg() {
        if (!MusicPlayer.getMusicPlayer().isPlaying()) {
            Image image = new Image(Constants.IMG_SOUND_UNMUTED);
            imgSound.setImage(image);
        } else {
            Image image = new Image(Constants.IMG_SOUND_MUTE);
            imgSound.setImage(image);
        }
    }
}
