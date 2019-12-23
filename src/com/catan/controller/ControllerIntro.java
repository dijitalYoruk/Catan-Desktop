package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.MusicPlayer;
import com.catan.modal.Settings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerIntro {

    @FXML
    MediaView mediaView;
    private MediaPlayer videoPlayer;

    @FXML
    public void initialize() {
        try {
            File videoFile = new File(Constants.INTRO);
            Media video = new Media(videoFile.toURI().toURL().toExternalForm());
            videoPlayer = new MediaPlayer(video);
            videoPlayer.setAutoPlay(true);

            //so when the intro finishes, I direct to game
            videoPlayer.setOnEndOfMedia(() -> {
                goToGame();
            });
            mediaView.setMediaPlayer(videoPlayer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // user may want to skip intro
    @FXML
    public void skipIntroWithKeyboard(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER))
            goToGame();
    }

    @FXML
    public void skipIntroWithMouse(MouseEvent event){
        goToGame();
    }

    public void goToGame(){
        try {
            mediaView.getMediaPlayer().stop();
            Constants.THEME_FOLDER = Settings.getInstance().getCurrentTheme().toLowerCase();
            MusicPlayer.getMusicPlayer().playMusic(Settings.getInstance().getCurrentTheme());
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.getInstance().getCurrentLanguage()),  new UTF8Control());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(Constants.PATH_VIEW_PROGRAM)), bundle);
            Stage window = (Stage)mediaView.getScene().getWindow();
            window.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
