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
import java.util.ResourceBundle;

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
    @FXML
    ResourceBundle resources;
    // properties
    private HashMap<String, Integer> resourcesOfPlayer;
    private HashMap<String, Integer> resourcesOfChosen;
    private ArrayList<ImageView> imgResources;
    private ArrayList<Label> labelsResources;
    private ArrayList<HBox> horizontalBoxes;
    private int totalChosenResources;
    private int requiredResources;
    private Player player;

    private ArrayList<String> resourceNames = new ArrayList<>(
            Arrays.asList(Constants.CARD_ORE, Constants.CARD_BRICK, Constants.CARD_LUMBER,
                    Constants.CARD_GRAIN, Constants.CARD_WOOL)
    );

    // methods
    public void setPlayer(Player player){
        this.player = player;
        totalChosenResources = 0;
        resourcesOfChosen = new HashMap<>();
        resourcesOfPlayer = new HashMap<>();
        requiredResources = player.getTotalCards() / 2;

        horizontalBoxes = new ArrayList<>(Arrays.asList(
                horizontalBox1, horizontalBox2,
                horizontalBox3, horizontalBox4
        ));

        imgResources = new ArrayList<>(Arrays.asList(
                imgOre, imgBrick, imgLumber, imgGrain, imgWool
        ));

        labelsResources = new ArrayList<>(Arrays.asList(
                labelOre, labelBrick, labelLumber,
                labelGrain, labelWool
        ));

        for (int i = 0; i < Constants.resourceNames.size(); i++) {
            String resourceName = Constants.resourceNames.get(i);
            int count =  player.getSourceCards().get(resourceName).size();
            resourcesOfPlayer.put(resourceName, count);
            resourcesOfChosen.put(resourceName, 0);
            labelsResources.get(i).setText("x " + count);
        }

        labelInformation.setText(resources.getString("thiefCardPunishmentView_ExplanationPart1") +
                requiredResources + " " + resources.getString("thiefCardPunishmentView_ExplanationPart2"));
    }

    @FXML
    public void incrementResource(MouseEvent event){
        Object view = event.getSource();

        for (int i = 0; i < Constants.resourceNames.size(); i++) {
            String resourceName = Constants.resourceNames.get(i);
            if (view == imgResources.get(i)){
                int playerResourcesCount = resourcesOfPlayer.get(resourceName);
                int chosenResourcesCount = resourcesOfChosen.get(resourceName);

                if(chosenResourcesCount != playerResourcesCount && totalChosenResources != requiredResources) {
                    resourcesOfChosen.put(resourceName, ++chosenResourcesCount);
                    labelsResources.get(i).setText("x" + (playerResourcesCount - chosenResourcesCount));
                    addResourceToView(resourceName);
                }
                break;
            }
        }
    }

    @FXML
    public void decreaseResource(MouseEvent event){
        ImageView view = (ImageView) event.getSource();
        for (int i = 0; i < Constants.resourceNames.size(); i++) {
            String resourceName = Constants.resourceNames.get(i);
            if (view.getId().equals(resourceName)){
                int playerResourceCount = resourcesOfPlayer.get(resourceName);
                int chosenResourceCount = resourcesOfChosen.get(resourceName);
                resourcesOfChosen.put(resourceName, --chosenResourceCount);
                int difference = playerResourceCount - chosenResourceCount;
                labelsResources.get(i).setText("x" + difference);
                removeResourceFromView(view);
                break;
            }
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

    private void addResourceToView(String resource){
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
}
