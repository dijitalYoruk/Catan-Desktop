package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.Util.UTF8Control;
import com.catan.modal.*;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ControllerGameEntrance {

    @FXML
    private JFXTextField labelActualPlayerName;
    @FXML
    private Rectangle imgDie1;
    @FXML
    private Rectangle imgDie2;
    @FXML
    private Label labelNamePlayer1;
    @FXML
    private Rectangle imgDie3;
    @FXML
    private Rectangle imgDie4;
    @FXML
    private Label labelNamePlayer2;
    @FXML
    private Rectangle imgDie5;
    @FXML
    private Rectangle imgDie6;
    @FXML
    private Label labelNamePlayer3;
    @FXML
    private Rectangle imgDie7;
    @FXML
    private Rectangle imgDie8;
    @FXML
    private Rectangle colorRed;
    @FXML
    private Rectangle colorGreen;
    @FXML
    private Rectangle colorBlue;
    @FXML
    private Rectangle colorPurple;
    @FXML
    private Label resultPlayer1;
    @FXML
    private Label resultPlayer2;
    @FXML
    private Label resultPlayer3;
    @FXML
    private Label resultPlayer4;
    @FXML
    private VBox resultBox;
    @FXML
    private Rectangle resultShape;

    // properties
    private ArrayList<String> colorsName;
    private ArrayList<Rectangle> colorShapes;
    private ArrayList<Label> resultLabels;
    private ArrayList<Rectangle> dieImages;
    private ArrayList<Player> players;
    private ArrayList<Color> colors;
    private Rectangle chosenColor;
    private Die die;

    @FXML
    public void initialize() {
        colorShapes = new ArrayList<>(Arrays.asList(
                        colorRed, colorGreen,
                        colorBlue, colorPurple));

        colors = new ArrayList<>(Arrays.asList(
                        Constants.COLOR_RGB_RED,  Constants.COLOR_RGB_GREEN,
                        Constants.COLOR_RGB_BLUE, Constants.COLOR_RGB_PURPLE));

        colorsName = new ArrayList<>(Arrays.asList(
                        Constants.COLOR_RED, Constants.COLOR_GREEN,
                        Constants.COLOR_BLUE, Constants.COLOR_PURPLE));

        resultLabels = new ArrayList<>(Arrays.asList(
                        resultPlayer1, resultPlayer2,
                        resultPlayer3, resultPlayer4));

        dieImages = new ArrayList<>(Arrays.asList(
                imgDie1, imgDie2, imgDie3, imgDie4,
                imgDie5, imgDie6, imgDie7, imgDie8
        ));

        for (Rectangle dieImg: dieImages) {
            Image img = new Image(Constants.PATH_DIE_GIF);
            dieImg.setFill(new ImagePattern(img));
        }

        for (int i = 0; i < colorShapes.size(); i++) {
            colorShapes.get(i).setFill(colors.get(i));
        }

        chosenColor = colorRed;
        chosenColor.setStroke(Color.BLACK);
        chosenColor.setStrokeWidth(3);

        die = new Die();
        players = new ArrayList<>();
        PlayerActual playerActual = new PlayerActual(Constants.COLOR_RED, "");
        players.add(playerActual);
        players.add(new PlayerAI(Constants.COLOR_BLUE, "Tom"));
        players.add(new PlayerAI(Constants.COLOR_PURPLE, "Harry"));
        players.add(new PlayerAI(Constants.COLOR_GREEN, "John"));
        labelNamePlayer1.setText("Tom");
        labelNamePlayer2.setText("Harry");
        labelNamePlayer3.setText("John");
        resultBox.setVisible(false);
        resultShape.setVisible(false);
    }

    @FXML
    void rollDie(ActionEvent event) {
        if (labelActualPlayerName.getText().trim().isEmpty()) return;
        Image img = null;
        ArrayList<Integer> dieResults = new ArrayList<>();
        players.get(0).setName(labelActualPlayerName.getText());

        for (int i = 0; i < colorShapes.size(); i++) {
            if (chosenColor == colorShapes.get(i)) {
                players.get(0).setColor(colorsName.get(i));
                colorsName.remove(i);
                break;
            }
        }

        for (int i = 1; i < players.size(); i++) {
            int colorIndex = (int) (Math.random() * colorsName.size());
            players.get(i).setColor(colorsName.get(colorIndex));
            colorsName.remove(colorIndex);
        }

        colorsName = new ArrayList<>(Arrays.asList(
                Constants.COLOR_RED, Constants.COLOR_GREEN,
                Constants.COLOR_BLUE, Constants.COLOR_PURPLE));

        int imgIndex = 0;
        for (int i = 0; i < 4; i++) {
            die.rollDie();
            img = new Image("./com/catan/assets/die" + die.getDice1() +  ".png");
            dieImages.get(imgIndex++).setFill(new ImagePattern(img));
            img = new Image("./com/catan/assets/die" + die.getDice2() +  ".png");
            dieImages.get(imgIndex++).setFill(new ImagePattern(img));
            dieResults.add(die.getDieSum());
        }

        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i < dieResults.size(); i++) {
            int maxIndex = 0;
            for (int j = 0; j < dieResults.size(); j++) {
                if (dieResults.get(j) > dieResults.get(maxIndex)) { maxIndex = j; }
            }

            dieResults.set(maxIndex, -1);
            Player player = this.players.get(maxIndex);
            players.add(player);
        }

        for (int j = 0; j < players.size(); j++) {
            resultLabels.get(j).setStyle("-fx-background-color: " + players.get(j).getColor());
            resultLabels.get(j).setText(players.get(j).getName());
        }

        resultBox.setVisible(true);
        resultShape.setVisible(true);
        this.players = players;

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    initGame(event);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initGame(ActionEvent event) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.catan.resources.language",
                    new Locale(Settings.languauge),  new UTF8Control());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/game.fxml"),bundle);
            Parent root = fxmlLoader.load();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.getScene().setRoot(root);
            window.show();
            ControllerGame controller = fxmlLoader.getController();
            fxmlLoader.setController(controller);
            controller.init(players);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void chooseColor(MouseEvent mouseEvent) {
        chosenColor = (Rectangle)mouseEvent.getSource();
        colorRed.setStrokeWidth(0);
        colorPurple.setStrokeWidth(0);
        colorBlue.setStrokeWidth(0);
        colorGreen.setStrokeWidth(0);
        colorRed.setStrokeWidth(0);
        chosenColor.setStroke(Color.BLACK);
        chosenColor.setStrokeWidth(3);
    }
}
