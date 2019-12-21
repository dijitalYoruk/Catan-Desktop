package com.catan.controller;


import com.catan.Util.Constants;
import com.catan.modal.DevelopmentCard;
import com.catan.modal.Player;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class ControllerDevMonopol {

    @FXML
    private ImageView imgBrick;
    @FXML
    private ImageView imgGrain;
    @FXML
    private ImageView imgLumber;
    @FXML
    private ImageView imgWool;
    @FXML
    private ImageView imgOre;
    @FXML
    private Label labelTitle;
    @FXML
    private AnchorPane root;

    // properties
    private Player currentPlayer;
    private ArrayList<Player> allPlayers;
    private DevelopmentCard developmentCard;
    private ArrayList<ImageView> imageViews;

    @FXML
    public void initialize() {
        root.setStyle("-fx-background-image: url("+ Constants.PATH_BG_INVENTION() +");\n" +
                "-fx-background-size: cover;\n");
        imageViews = new ArrayList<>(Arrays.asList(imgOre, imgBrick, imgLumber, imgGrain, imgWool));
        for (int i = 0; i < Constants.getResourcePaths().size(); i++) {
            String resourcePath = Constants.getResourcePaths().get(i);
            Image image = new Image(resourcePath);
            imageViews.get(i).setImage(image);
        }
    }

    // methods
    public void setProperties(Player currentPlayer, DevelopmentCard developmentCard, ArrayList<Player> allPlayers) {
        this.currentPlayer = currentPlayer;
        this.developmentCard = developmentCard;
        this.allPlayers = allPlayers;
    }

    @FXML
    void chooseResourceCard(MouseEvent event) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i) == event.getSource()) {
                developmentCard.performMonopolyCardFeatures(Constants.resourceNames.get(i), allPlayers, currentPlayer);
                break;
            }
        }

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

}