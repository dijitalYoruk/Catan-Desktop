package com.catan.modal;

public class SourceCard extends Card {

    // properties
    private String hexType;

    // constructor
    public SourceCard(String name,String type) {
        super(name);
        hexType = type;
    }

    // methods
    public String getHexType() {
        return hexType;
    }

    public void setHexType(String newHexType) {
        hexType = newHexType;
    }

    @Override
    public String toString() {
        return "SourceCard{" + "hexType='" + hexType + '\'' + '}';
    }
}
