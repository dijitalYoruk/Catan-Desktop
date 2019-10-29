package com.catan.modal;

public class Card {
    
    private String name;
    private String instruction;

    public Card(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction0(String newInstruction) {
        instruction = newInstruction;
    }

}
