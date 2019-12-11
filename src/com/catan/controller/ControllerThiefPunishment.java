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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ControllerThiefPunishment {
    @FXML
    private AnchorPane thiefAnchorPane;
    @FXML
    private ImageView imgBrick;
    @FXML
    private ImageView imgGrain;
    @FXML
    private ImageView imgOre;
    @FXML
    private ImageView imgWool;
    @FXML
    private ImageView imgLumber;
    @FXML
    private Label labelBrick;
    @FXML
    private Label labelGrain;
    @FXML
    private Label labelOre;
    @FXML
    private Label labelWool;
    @FXML
    private Label labelLumber;
    @FXML
    private Label labelInformation;
    @FXML
    private HBox horizontalBox1;
    @FXML
    private HBox horizontalBox2;
    @FXML
    private HBox horizontalBox3;
    @FXML
    private HBox horizontalBox4;

    // properties
    private ArrayList<HBox> horizontalBoxes;
    private Player player;
    private HashMap<String, Integer> resourcesOfPlayer;
    private HashMap<String, Integer> resourcesOfChosen;
    private int totalChosenResources;
    private int requiredResources;

    private ArrayList<String> resourceNames = new ArrayList<>(
            Arrays.asList(Constants.CARD_ORE, Constants.CARD_BRICK, Constants.CARD_LUMBER,
                    Constants.CARD_GRAIN, Constants.CARD_WOOL)
    );

    // methods
    public void setPlayer(Player player){
        this.player = player;
        horizontalBoxes = new ArrayList<>();
        horizontalBoxes.add(horizontalBox1);
        horizontalBoxes.add(horizontalBox2);
        horizontalBoxes.add(horizontalBox3);
        horizontalBoxes.add(horizontalBox4);
        resourcesOfChosen = new HashMap<>();
        resourcesOfPlayer = new HashMap<>();

        for (String resourceName: resourceNames) {
            int count =  player.getSourceCards().get(resourceName).size();
            resourcesOfPlayer.put(resourceName, count);
            resourcesOfChosen.put(resourceName, 0);
        }

        totalChosenResources = 0;
        requiredResources = player.getTotalCards() / 2;
        labelWool.setText(  "x " + resourcesOfPlayer.get(Constants.CARD_WOOL   ));
        labelGrain.setText( "x " + resourcesOfPlayer.get(Constants.CARD_GRAIN  ));
        labelOre.setText(   "x " + resourcesOfPlayer.get(Constants.CARD_ORE    ));
        labelBrick.setText( "x " + resourcesOfPlayer.get(Constants.CARD_BRICK  ));
        labelLumber.setText("x " + resourcesOfPlayer.get(Constants.CARD_LUMBER ));
        labelInformation.setText("You need to choose " + requiredResources + " cards");
    }

    @FXML
    public void incrementResource(MouseEvent event){
        Object view = event.getSource();

        if (view == imgBrick){
            int playerBrickCount = resourcesOfPlayer.get(Constants.CARD_BRICK);
            int chosenBrickCount = resourcesOfChosen.get(Constants.CARD_BRICK);

            if(chosenBrickCount != playerBrickCount && totalChosenResources != requiredResources) {
                resourcesOfChosen.put(Constants.CARD_BRICK, ++chosenBrickCount);
                labelBrick.setText("x" + (playerBrickCount - chosenBrickCount));
                addResouceToView(Constants.CARD_BRICK);
            }
        }
        else if (view == imgOre){
            int playerOreCount = resourcesOfPlayer.get(Constants.CARD_ORE);
            int chosenOreCount = resourcesOfChosen.get(Constants.CARD_ORE);

            if(chosenOreCount != playerOreCount && totalChosenResources != requiredResources) {
                resourcesOfChosen.put(Constants.CARD_ORE, ++chosenOreCount);
                labelOre.setText("x" + (playerOreCount - chosenOreCount));
                addResouceToView(Constants.CARD_ORE);
            }
        }
        else if (view == imgGrain){
            int playerGrainCount = resourcesOfPlayer.get(Constants.CARD_GRAIN);
            int chosenGrainCount = resourcesOfChosen.get(Constants.CARD_GRAIN);

            if(chosenGrainCount != playerGrainCount && totalChosenResources != requiredResources) {
                resourcesOfChosen.put(Constants.CARD_GRAIN, ++chosenGrainCount);
                labelGrain.setText("x" + (playerGrainCount - chosenGrainCount));
                addResouceToView(Constants.CARD_GRAIN);
            }
        }
        else if (view == imgWool){
            int playerWoolCount = resourcesOfPlayer.get(Constants.CARD_WOOL);
            int chosenWoolCount = resourcesOfChosen.get(Constants.CARD_WOOL);

            if(chosenWoolCount != playerWoolCount && totalChosenResources != requiredResources) {
                resourcesOfChosen.put(Constants.CARD_WOOL, ++chosenWoolCount);
                labelWool.setText("x" + (playerWoolCount - chosenWoolCount));
                addResouceToView(Constants.CARD_WOOL);
            }
        }
        else if (view == imgLumber){
            int playerLumberCount = resourcesOfPlayer.get(Constants.CARD_LUMBER);
            int chosenLumberCount = resourcesOfChosen.get(Constants.CARD_LUMBER);

            if(chosenLumberCount != playerLumberCount && totalChosenResources != requiredResources) {
                resourcesOfChosen.put(Constants.CARD_LUMBER, ++chosenLumberCount);
                labelLumber.setText("x" + (playerLumberCount - chosenLumberCount));
                addResouceToView(Constants.CARD_LUMBER);
            }
        }
    }

    private void addResouceToView(String resource){
        ImageView temp = new ImageView();
        temp.setId(resource);
        temp.setFitWidth(40);
        temp.setFitHeight(50);
        temp.setImage(new Image(".\\com\\catan\\assets\\resource_"+resource+".jpg"));
        temp.setOnMouseClicked(this::decreaseResource);
        horizontalBoxes.get(totalChosenResources/4).getChildren().add(temp);
        totalChosenResources++;
    }

    private void removeResourceFromView(ImageView resource){
        ((HBox)resource.getParent()).getChildren().remove(resource);
        totalChosenResources--;
    }

    @FXML
    public void decreaseResource(MouseEvent event){
        ImageView view = (ImageView) event.getSource();

        switch (view.getId()) {
            case Constants.CARD_BRICK:
                int playerBrickCount = resourcesOfPlayer.get(Constants.CARD_BRICK);
                int chosenBrickCount = resourcesOfChosen.get(Constants.CARD_BRICK);
                resourcesOfChosen.put(Constants.CARD_BRICK, --chosenBrickCount);
                labelBrick.setText("x" + (playerBrickCount - chosenBrickCount));
                removeResourceFromView(view);
                break;
            case Constants.CARD_ORE:
                int playerOreCount = resourcesOfPlayer.get(Constants.CARD_ORE);
                int chosenOreCount = resourcesOfChosen.get(Constants.CARD_ORE);
                resourcesOfChosen.put(Constants.CARD_ORE, --chosenOreCount);
                labelOre.setText("x" + (playerOreCount - chosenOreCount));
                removeResourceFromView(view);
                break;
            case Constants.CARD_GRAIN:
                int playerGrainCount = resourcesOfPlayer.get(Constants.CARD_GRAIN);
                int chosenGrainCount = resourcesOfChosen.get(Constants.CARD_GRAIN);
                resourcesOfChosen.put(Constants.CARD_BRICK, --chosenGrainCount);
                labelGrain.setText("x" + (playerGrainCount - chosenGrainCount));
                removeResourceFromView(view);
                break;
            case Constants.CARD_WOOL:
                int playerWoolCount = resourcesOfPlayer.get(Constants.CARD_WOOL);
                int chosenWoolCount = resourcesOfChosen.get(Constants.CARD_WOOL);
                resourcesOfChosen.put(Constants.CARD_WOOL, --chosenWoolCount);
                labelWool.setText("x" + (playerWoolCount - chosenWoolCount));
                removeResourceFromView(view);
                break;
            case Constants.CARD_LUMBER:
                int playerLumberCount = resourcesOfPlayer.get(Constants.CARD_LUMBER);
                int chosenLumberCount = resourcesOfChosen.get(Constants.CARD_LUMBER);
                resourcesOfChosen.put(Constants.CARD_LUMBER, --chosenLumberCount);
                labelLumber.setText("x" + (playerLumberCount - chosenLumberCount));
                removeResourceFromView(view);
                break;
        }
    }

    @FXML
    public void confirmPunish() {
        if (totalChosenResources == requiredResources) {
            // subtracting resources from player.
            for (String resourceName: resourceNames) {
                int count = resourcesOfChosen.get(resourceName);
                player.removeResources(resourceName, count);
            }
            // closing dialog
            Stage stage = (Stage)thiefAnchorPane.getScene().getWindow();
            stage.close();
        }
        else {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), labelInformation);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        }
    }
}
