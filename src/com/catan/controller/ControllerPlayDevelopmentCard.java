package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.interfaces.InterfaceDevelopmentCard;
import com.catan.modal.DevelopmentCard;
import com.catan.modal.Player;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControllerPlayDevelopmentCard {

    @FXML
    private Rectangle imgProfitExchange;
    @FXML
    private Rectangle imgRoadDestruction;
    @FXML
    private Rectangle imgInvention;
    @FXML
    private Rectangle imgVictory;
    @FXML
    private Rectangle imgKnight;
    @FXML
    private Rectangle imgMonopoly;
    @FXML
    private Label labelProfitExchange;
    @FXML
    private Label labelRoadDestruction;
    @FXML
    private Label labelInvention;
    @FXML
    private Label labelVictory;
    @FXML
    private Label labelKnight;
    @FXML
    private Label labelMonopoly;
    @FXML
    private JFXButton buttonClose;
    @FXML
    private ImageView imgCloseButton;
    @FXML
    private AnchorPane root;

    // properties
    private Player player;
    private InterfaceDevelopmentCard interfaceCard;

    @FXML
    public void initialize() {
        root.setStyle("-fx-background-image: url("+ Constants.PATH_BG_INVENTION() +");\n" +
                "-fx-background-size: cover;\n");
        Image imgForRoadDestruction = new Image(Constants.PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION());
        Image imgForProfitExchange = new Image(Constants.PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE());
        Image imgForVictory = new Image(Constants.PATH_DEVELOPMENT_CARD_VICTORY_POINT());
        Image imgForInvention = new Image(Constants.PATH_DEVELOPMENT_CARD_INVENTION());
        Image imgForMonopoly = new Image(Constants.PATH_DEVELOPMENT_CARD_MONOPOL());
        Image imgForKnight = new Image(Constants.PATH_DEVELOPMENT_CARD_KNIGHT());
        imgRoadDestruction.setFill(new ImagePattern(imgForRoadDestruction));
        imgProfitExchange.setFill(new ImagePattern(imgForProfitExchange));
        imgInvention.setFill(new ImagePattern(imgForInvention));
        imgMonopoly.setFill(new ImagePattern(imgForMonopoly));
        imgVictory.setFill(new ImagePattern(imgForVictory));
        imgKnight.setFill(new ImagePattern(imgForKnight));
    }

    public void setProperties(Player player, InterfaceDevelopmentCard interfaceCard) {
        this.interfaceCard = interfaceCard;
        this.player = player;
        int num1 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE);
        labelProfitExchange.setText(num1 + "");
        int num2 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION);
        labelRoadDestruction.setText(num2 + "");
        int num3 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_VICTORY_POINT);
        labelVictory.setText(num3 + "");
        int num4 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_INVENTION);
        labelInvention.setText(num4 + "");
        int num5 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_MONOPOL);
        labelMonopoly.setText(num5 + "");
        int num6 = player.getDevelopmentCards().get(Constants.DEVELOPMENT_CARD_KNIGHT);
        labelKnight.setText(num6 + "");
    }

    @FXML
    void playDevelopmentCard(MouseEvent event) {
        DevelopmentCard card = null;

        if (event.getSource() == imgRoadDestruction) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION);
        } else if (event.getSource() == imgProfitExchange) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE);
        } else if (event.getSource() == imgVictory) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_VICTORY_POINT);
        } else if (event.getSource() == imgInvention) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_INVENTION);
        } else if (event.getSource() == imgMonopoly) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_MONOPOL);
        } else if (event.getSource() == imgKnight) {
            card = new DevelopmentCard(Constants.DEVELOPMENT_CARD_KNIGHT);
        }

        // close window
        if (card != null && player.hasDevelopmentCard(card)) {
            player.removeDevelopmentCard(card.getName());
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.close();
            interfaceCard.setDevelopmentCardInvention(card);
        }
    }
    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        Stage window = (Stage) root.getScene().getWindow();
        window.close();
    }
}