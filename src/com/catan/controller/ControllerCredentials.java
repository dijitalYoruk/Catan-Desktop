package com.catan.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class ControllerCredentials {
    @FXML
    TextFlow credentials;
    @FXML
    public void initialize() {
        //credentials.setLineSpacing(2);
        //TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                moveTextUp();
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task,0,1000);
    }

    //public void moveTextUp(){
//        credentials.setLayoutY(credentials.getLayoutY()-5);
//    }
}
