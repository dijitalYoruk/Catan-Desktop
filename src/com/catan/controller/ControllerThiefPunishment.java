package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.Player;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerThiefPunishment {
    @FXML
    AnchorPane thiefAnchorPane;
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
    ControllerGame gameController;
    public void setPlayer(Player current, ControllerGame game){
        System.out.println("set player is called!! again");
        player = current;
        gameController = game;
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
        temp.setId(resource);
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
        System.out.println("decrement is called!!");
        if (((ImageView)event.getSource()).getId().equals(Constants.CARD_BRICK)){
            brickP--;
            removeResourceFromView((ImageView)event.getSource());
            brickCurrent.setText("x" + (brickMax - brickP));
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_ORE)){
            oreP--;
            removeResourceFromView((ImageView)event.getSource());
            oreCurrent.setText("x" + (oreMax-oreP));
        }else if (((ImageView)event.getSource()).getId().equals(Constants.CARD_GRAIN)){
            grainP--;
            removeResourceFromView((ImageView)event.getSource());
            grainCurrent.setText("x" + (grainMax - grainP));
        }else if (((ImageView)event.getSource()).getId().equals("sheep")){
            woolP--;
            removeResourceFromView((ImageView)event.getSource());
            woolCurrent.setText("x" + (woolMax - woolP));
        }else {
            lumberP--;
            removeResourceFromView((ImageView)event.getSource());
            lumberCurrent.setText("x" + (lumberMax - lumberP));
        }
    }
    @FXML
    public void confirmPunish() throws IOException {
        if (totalP == requiredP) {
            player.punishLumber(lumberP);
            player.punishWool(woolP);
            player.punishGrain(grainP);
            player.punishOre(oreP);
            player.punishBrick(brickP);
            gameController.updateGame();
            Stage stage = (Stage) thiefAnchorPane.getScene().getWindow();
            stage.close();
        }else{
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), information);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();
            System.out.println("You have to choose" + requiredP);
        }
    }
}
