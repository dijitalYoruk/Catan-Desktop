package com.catan.modal;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Instruction {
    private String generalInstruction;

    public Instruction() {
        generalInstruction = "";
        try {
            File file = new File("./src/com/catan/persistent_data/instructions.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                generalInstruction += " • " + sc.nextLine() + "\n";
            }
        } catch (IOException e) {
            generalInstruction = "";
            e.printStackTrace();
        }
    }

    public void setGeneralInst(String inst) {
        generalInstruction = inst;
    }

    public String getGeneralInst() {
        return generalInstruction;
    }
}
