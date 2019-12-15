package com.catan.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class ControllerMusic {

    @FXML
    private JFXButton musicButton;
    @FXML
    private ImageView imageViewMusicButton;

    public static boolean isMusicOn = false;
    public static InputStream music;
    public static Clip clip;


    @FXML
    public void playMusic(String filename) {
        try
        {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMusicOn = true;
            //change button image
            Image image = new Image(getClass().getResourceAsStream("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\assets\\musicbutton.png"));
            imageViewMusicButton.setFitHeight(50);
            imageViewMusicButton.setFitWidth(50);
            imageViewMusicButton.setImage(image);
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    @FXML
    public void settingsMusic(String filename){
        if (isMusicOn) {
            System.out.println("musc off -settings");
            clip.stop();
            isMusicOn = false;
        }
        playMusic(filename);
    }
    @FXML
    public void switchMusic(ActionEvent event) throws FileNotFoundException {
        if (isMusicOn) {
            System.out.println("musc off -switch music");
            //change button image
            Image image = new Image(getClass().getResourceAsStream("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\assets\\musicbutton-muted.png"));
            imageViewMusicButton.setFitHeight(50);
            imageViewMusicButton.setFitWidth(50);
            imageViewMusicButton.setImage(image);
            //stop music
            clip.stop();
            isMusicOn = false;
        }
        else {
            System.out.println("musc on -switch music");
            //change button image
            Image image = new Image(getClass().getResourceAsStream("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\assets\\musicbutton-muted.png"));
            imageViewMusicButton.setFitHeight(50);
            imageViewMusicButton.setFitWidth(50);
            imageViewMusicButton.setImage(image);
            //play music
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\persistent_data\\settings.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line.equals("CatanDefault")){
                playMusic("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\catan_theme.wav");
            }
            else if(line.equals("Space")){
                playMusic("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\space_theme.wav");
            }
            else if(line.equals("Karadeniz")){
                playMusic("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\karadeniz_theme.wav");
            }
        }
    }
}
