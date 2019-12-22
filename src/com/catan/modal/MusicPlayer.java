package com.catan.modal;

import com.catan.Util.Constants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    // singleton properties
    private static MusicPlayer musicPlayer = new MusicPlayer();
    private boolean isPlaying;

    // properties
    private MediaPlayer mediaPlayer;
    private String currentMusic;

    // constructor
    private MusicPlayer() {
        isPlaying = false;
    }

    // methods
    public void playMusic(String theme) {
        String path = "";
        switch (theme) {
            case Constants.THEME_DEFAULT: { path = Constants.AUDIO_DEFAULT; break; }
            case Constants.THEME_SPACE: { path = Constants.AUDIO_SPACE; break; }
            case Constants.THEME_KARADENIZ: { path = Constants.AUDIO_KARADENIZ; break; }
        }
        currentMusic = theme;
        System.out.println(">>>>" + path + "<<<<<<<");
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
        isPlaying = true;
    }

    public void changeMusic(String theme) {
        if (theme.equals(currentMusic)) return;
        mediaPlayer.setOnEndOfMedia(null);
        mediaPlayer.stop();
        playMusic(theme);
    }

    public void mute() {
        String theme = Settings.getInstance().getCurrentTheme();
        playMusic(theme);
    }

    public void unmute() {
        mediaPlayer.setOnEndOfMedia(null);
        mediaPlayer.stop();
        isPlaying = false;
    }

    public boolean isPlaying() {
         return isPlaying;
    }

    public static MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
