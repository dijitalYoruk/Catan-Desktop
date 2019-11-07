package com.catan.modal;

public class Construction {

    private String type;
    private String imagePath;

    public Construction(String type, String imagePath) {
        this.type = type;
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}