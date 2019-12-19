package com.catan.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class ControllerMusic {

    @FXML
    private JFXButton musicButton = new JFXButton("");
    ;

    public static boolean isMusicOn = false;
    public static InputStream music;
    public static Clip clip;


    @FXML
    public void playMusic(File filename) {//String filename) {
        try {
            System.out.println("lol hi");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(filename));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isMusicOn = true;
            /*
            //change button image
            musicButton.setStyle("-fx-background-image: url('/../assets/musicbutton.jpg');");
            musicButton.setStyle("-fx-background-size: cover;");
            FileInputStream input = new FileInputStream("./com/catan/assets/music-button.jpg");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            musicButton = new JFXButton("",imageView);
            */
        } catch (Exception exc) {
            System.out.println("lol byeee");
            exc.printStackTrace(System.out);
        }
    }

    @FXML
    public void settingsMusic(File filename) {//String filename) {
        if (isMusicOn) {
            clip.stop();
            isMusicOn = false;
        }
        playMusic(filename);
    }

    @FXML
    public void switchMusic(ActionEvent event) throws FileNotFoundException {

        if (isMusicOn) {
            /*
            //change button image
            musicButton.setStyle("-fx-background-image: url('/../assets/musicbutton-muted.png')");
            FileInputStream input = new FileInputStream("./com/catan/assets/music-button-muted.jpg");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            musicButton = new JFXButton("",imageView);
           */

            //stop music
            clip.stop();
            isMusicOn = false;
        } else {
            /*
            //change button image
            musicButton.setStyle("-fx-background-image: url('/../assets/musicbutton-muted.png')");
            FileInputStream input = new FileInputStream("./com/catan/assets/music-button-muted.jpg");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            musicButton = new JFXButton("",imageView);
            */

            //play music
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\persistent_data\\settings.txt")));
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
            if (line.equals("Space")) {
                playMusic(new File("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\music\\space_theme.wav"));
            } else if (line.equals("Karadeniz")) {
                playMusic(new File("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\music\\karadeniz_theme.wav"));
            } else if (line.equals("CatanDefault")) {
                playMusic(new File("C:\\Users\\Cerag\\Documents\\GitHub\\CS319-3B-CA\\src\\com\\catan\\music\\catan_theme.wav"));
            }
        }
    }
}