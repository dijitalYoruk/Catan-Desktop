package com.catan.modal;

public class SourceCard extends Card {

    private String hexType;

    public SourceCard(String name,String type) {
        super(name);
        hexType = type;
    }

    public String getHexType() {
        return hexType;
    }

    public void setHexType(String newHexType) {
        hexType = newHexType;
    }
}
