package com.catan.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ControllerSettings implements Initializable {
    @FXML
    JFXTextArea largestArmyTh;
    @FXML
    JFXTextArea longestRoadTh;
    @FXML
    JFXTextArea victoryPointTh;
    @FXML
    public void returnToProgram(MouseEvent actionEvent){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("../view/program.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void submitSettings(ActionEvent actionEvent)  {
        try {
            FileOutputStream writer = new FileOutputStream(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt");
            writer.write(("").getBytes());
//            writer.close();
//            FileWriter writer = new FileWriter(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt", true);
            writer.write((victoryPointTh.getText() + "\n").getBytes());
            writer.write((largestArmyTh.getText() + "\n").getBytes());
            writer.write(longestRoadTh.getText().getBytes() );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Paths.get(".").toAbsolutePath().normalize().toString() + "/src/com/catan/persistent_data/settings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            int victory = Integer.parseInt(reader.readLine());
            int largest = Integer.parseInt(reader.readLine());
            int longest = Integer.parseInt(reader.readLine());
            System.out.println(victory);
            System.out.println(largest);
            System.out.println(longest);
            largestArmyTh.setText(largest+"");
            victoryPointTh.setText(victory+"");
            longestRoadTh.setText(longest+"");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
