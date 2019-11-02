package com.catan.controller;

import com.catan.modal.Instruction;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

public class ControllerInstructions extends ControllerBase {

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

}