package com.catan.modal;

public class PlayerActual extends Player {

    private boolean isConstructionBuild = false;
    private boolean isRoadBuild = false;

    public PlayerActual(String color, String name) {
        super(color, name);
        isConstructionBuild = false;
        isRoadBuild = false;
    }

    public boolean isConstructionBuild() {
        return isConstructionBuild;
    }

    public void setConstructionBuild(boolean constructionBuild) {
        isConstructionBuild = constructionBuild;
    }

    public boolean isRoadBuild() {
        return isRoadBuild;
    }

    public void setRoadBuild(boolean roadBuild) {
        isRoadBuild = roadBuild;
    }


}
