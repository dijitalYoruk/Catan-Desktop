package com.catan.controller;

import com.catan.modal.Instruction;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerInstructions {

    @FXML
    private JFXTextArea instructions;
    private String instructionsStr;
    Instruction instruction;

    @FXML
    public void initialize() {
        instruction = new Instruction();
        instructionsStr = instruction.getGeneralInst();
        instructions.setText(instructionsStr);
    }

    @FXML
    public void returnToProgram(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/program.fxml"));
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1500, 800));
            System.out.println(window);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
