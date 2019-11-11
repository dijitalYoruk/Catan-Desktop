package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javax.xml.transform.Source;
import java.util.ArrayList;

public class ControllerThiefPunishment {
    @FXML
    Label information;
    @FXML
    Label woolCurrent;
    @FXML
    Label grainCurrent;
    @FXML
    Label oreCurrent;
    @FXML
    Label brickCurrent;
    @FXML
    Label lumberCurrent;

    @FXML
    HBox thief_hbox_1, thief_hbox_2, thief_hbox_3, thief_hbox_4;

    private ArrayList<HBox> hBoxes;
    private int totalP;
    private int requiredP;
    private int woolP, woolMax;
    private int grainP, grainMax;
    private int oreP, oreMax;
    private int brickP, brickMax;
    private int lumberP, lumberMax;
    Player player;
    public void setPlayer(Player current){
        player = current;
        totalP = 0;
        hBoxes = new ArrayList<>();
        hBoxes.add(thief_hbox_1);
        hBoxes.add(thief_hbox_2);
        hBoxes.add(thief_hbox_3);
        hBoxes.add(thief_hbox_4);
        requiredP = player.getTotalCards() / 2;
        woolP = 0; woolMax = player.getSourceCards().get(Constants.CARD_WOOL).size();
        grainP = 0; grainMax = player.getSourceCards().get(Constants.CARD_GRAIN).size();
        brickP = 0; brickMax = player.getSourceCards().get(Constants.CARD_BRICK).size();
        lumberP = 0; lumberMax = player.getSourceCards().get(Constants.CARD_LUMBER).size();
        oreP = 0; oreMax = player.getSourceCards().get(Constants.CARD_ORE).size();
        woolCurrent.setText("x " + woolMax);
        grainCurrent.setText("x " + grainMax);
        oreCurrent.setText("x " + oreMax);
        brickCurrent.setText("x " + brickMax);
        lumberCurrent.setText("x " + lumberMax);
        information.setText("You need to choose " + requiredP + " cards");
    }

    @FXML
    public void incResource(MouseEvent event){
        if (((ImageView)event.getSource()).getId().equals(Constants.CARD_BRICK)){
            if(brickP != brickMax && totalP != requiredP) {
                brickP++;
                brickCurrent.setText("x" + (brickMax - brickP));
                addResouceToView(Constants.CARD_BRICK);
            }
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_ORE)){
            if(oreP != oreMax && totalP != requiredP) {
                oreP++;
                oreCurrent.setText("x" + (oreMax-oreP));
                addResouceToView(Constants.CARD_ORE);
            }
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_GRAIN)){
            if(grainP != grainMax && totalP != requiredP) {
                grainP++;
                grainCurrent.setText("x" + (grainMax - grainP));
                addResouceToView(Constants.CARD_GRAIN);
            }
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_WOOL)){
            if(woolP != woolMax && totalP != requiredP) {
                woolP++;
                woolCurrent.setText("x" + (woolMax - woolP));
                addResouceToView("sheep");
            }
        }else {
            if(lumberP != lumberMax && totalP != requiredP) {
                lumberP++;
                lumberCurrent.setText("x" + (lumberMax - lumberP));
                addResouceToView("wood");
            }
        }
    }

    private void addResouceToView(String resource){
        ImageView temp = new ImageView();
        temp.setId(Constants.CARD_BRICK + (totalP % 4));
        temp.setFitWidth(40);
        temp.setFitHeight(50);
        temp.setImage(new Image(".\\com\\catan\\assets\\resource_"+resource+".jpg"));
        temp.setOnMouseClicked(this::decResource);
        hBoxes.get(totalP/4).getChildren().add(temp);
        totalP++;
    }
    private void removeResourceFromView(ImageView resource){
        totalP--;
        ((HBox)resource.getParent()).getChildren().remove(resource);
    }
    @FXML
    public void decResource(MouseEvent event){
        if (((ImageView)event.getSource()).getId().equals(Constants.CARD_BRICK)){
            brickP--;
            removeResourceFromView((ImageView)event.getSource());
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_ORE)){
            oreP--;
            removeResourceFromView((ImageView)event.getSource());
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_GRAIN)){
            grainP--;
            removeResourceFromView((ImageView)event.getSource());
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_WOOL)){
            woolP--;
            removeResourceFromView((ImageView)event.getSource());
        }else {
            lumberP--;
            removeResourceFromView((ImageView)event.getSource());
        }
    }
    @FXML
    public void confirmPunish(){
        player.punishLumber(lumberP);
        player.punishWool(woolP);
        player.punishGrain(grainP);
        player.punishOre(oreP);
        player.punishBrick(brickP);
    }
}
