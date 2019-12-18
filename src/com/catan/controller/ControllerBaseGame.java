package com.catan.controller;

import com.catan.Util.Constants;
import com.catan.modal.*;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerBaseGame extends ControllerBase {

    // FXML Properties
    @FXML
    protected AnchorPane root;
    @FXML
    private Line road8;
    @FXML
    private Line road12;
    @FXML
    private Line road13;
    @FXML
    private Line road22;
    @FXML
    private Line road17;
    @FXML
    private Line road16;
    @FXML
    private Line road9;
    @FXML
    private Line road15;
    @FXML
    private Line road14;
    @FXML
    private Line road38;
    @FXML
    private Line road37;
    @FXML
    private Line road21;
    @FXML
    private Line road28;
    @FXML
    private Line road27;
    @FXML
    private Line road25;
    @FXML
    private Line road26;
    @FXML
    private Line road20;
    @FXML
    private Line road31;
    @FXML
    private Line road30;
    @FXML
    private Line road29;
    @FXML
    private Line road42;
    @FXML
    private Line road45;
    @FXML
    private Line road44;
    @FXML
    private Line road43;
    @FXML
    private Line road36;
    @FXML
    private Line road41;
    @FXML
    private Line road35;
    @FXML
    private Line road32;
    @FXML
    private Line road52;
    @FXML
    private Line road58;
    @FXML
    private Line road57;
    @FXML
    private Line road56;
    @FXML
    private Line road51;
    @FXML
    private Line road48;
    @FXML
    private Line road47;
    @FXML
    private Line road46;
    @FXML
    private Line road65;
    @FXML
    private Line road64;
    @FXML
    private Line road61;
    @FXML
    private Line road53;
    @FXML
    private Line road60;
    @FXML
    private Line road59;
    @FXML
    private Line road66;
    @FXML
    private Line road72;
    @FXML
    private Line road71;
    @FXML
    private Line road70;
    @FXML
    private Line road69;
    @FXML
    private Line road68;
    @FXML
    private Line road67;
    @FXML
    private Line road63;
    @FXML
    private Line road55;
    @FXML
    private Line road50;
    @FXML
    private Line road40;
    @FXML
    private Line road34;
    @FXML
    private Line road24;
    @FXML
    private Line road19;
    @FXML
    private Line road5;
    @FXML
    private Line road6;
    @FXML
    private Line road18;
    @FXML
    private Line road10;
    @FXML
    private Line road23;
    @FXML
    private Line road33;
    @FXML
    private Line road39;
    @FXML
    private Line road49;
    @FXML
    private Line road54;
    @FXML
    private Line road62;
    @FXML
    private Line road11;
    @FXML
    private Line road4;
    @FXML
    private Line road3;
    @FXML
    private Line road2;
    @FXML
    private Line road1;
    @FXML
    private Line road7;
    @FXML
    private Polygon terrainHex1;
    @FXML
    private Polygon terrainHex2;
    @FXML
    private Polygon terrainHex5;
    @FXML
    private Polygon terrainHex3;
    @FXML
    private Polygon terrainHex4;
    @FXML
    private Polygon terrainHex6;
    @FXML
    private Polygon terrainHex7;
    @FXML
    private Polygon terrainHex8;
    @FXML
    private Polygon terrainHex12;
    @FXML
    private Polygon terrainHex9;
    @FXML
    private Polygon terrainHex10;
    @FXML
    private Polygon terrainHex11;
    @FXML
    private Polygon terrainHex14;
    @FXML
    private Polygon terrainHex13;
    @FXML
    private Polygon terrainHex15;
    @FXML
    private Polygon terrainHex16;
    @FXML
    private Polygon terrainHex17;
    @FXML
    private Polygon terrainHex18;
    @FXML
    private Polygon terrainHex19;
    @FXML
    private Circle vertex40;
    @FXML
    private Circle vertex11;
    @FXML
    private Circle vertex12;
    @FXML
    private Circle vertex13;
    @FXML
    private Circle vertex14;
    @FXML
    private Circle vertex20;
    @FXML
    private Circle vertex21;
    @FXML
    private Circle vertex22;
    @FXML
    private Circle vertex23;
    @FXML
    private Circle vertex24;
    @FXML
    private Circle vertex25;
    @FXML
    private Circle vertex19;
    @FXML
    private Circle vertex30;
    @FXML
    private Circle vertex31;
    @FXML
    private Circle vertex32;
    @FXML
    private Circle vertex33;
    @FXML
    private Circle vertex34;
    @FXML
    private Circle vertex35;
    @FXML
    private Circle vertex36;
    @FXML
    private Circle vertex41;
    @FXML
    private Circle vertex42;
    @FXML
    private Circle vertex43;
    @FXML
    private Circle vertex44;
    @FXML
    private Circle vertex45;
    @FXML
    private Circle vertex46;
    @FXML
    private Circle vertex37;
    @FXML
    private Circle vertex5;
    @FXML
    private Circle vertex26;
    @FXML
    private Circle vertex15;
    @FXML
    private Circle vertex10;
    @FXML
    private Circle vertex29;
    @FXML
    private Circle vertex9;
    @FXML
    private Circle vertex3;
    @FXML
    private Circle vertex18;
    @FXML
    private Circle vertex50;
    @FXML
    private Circle vertex52;
    @FXML
    private Circle vertex1;
    @FXML
    private Circle vertex2;
    @FXML
    private Circle vertex4;
    @FXML
    private Circle vertex6;
    @FXML
    private Circle vertex7;
    @FXML
    private Circle vertex16;
    @FXML
    private Circle vertex27;
    @FXML
    private Circle vertex38;
    @FXML
    private Circle vertex47;
    @FXML
    private Circle vertex54;
    @FXML
    private Circle vertex53;
    @FXML
    private Circle vertex51;
    @FXML
    private Circle vertex49;
    @FXML
    private Circle vertex48;
    @FXML
    private Circle vertex39;
    @FXML
    private Circle vertex28;
    @FXML
    private Circle vertex17;
    @FXML
    private Circle vertex8;
    @FXML
    protected Circle imgMovingThief;
    @FXML
    protected Rectangle imgRoad;
    @FXML
    protected Rectangle imgVillage;
    @FXML
    protected Rectangle imgCity;
    @FXML
    protected Rectangle imgCivilisation;
    @FXML
    protected Rectangle imgPriceCard;
    @FXML
    private Label labelTurn;
    @FXML
    private Rectangle imgDie1;
    @FXML
    private Rectangle imgDie2;
    @FXML
    private Label labelPlayer;
    @FXML
    private Label labelLogs;
    @FXML
    private Label labelWarning;
    @FXML
    private Circle circleNumberOnHex1;
    @FXML
    private Circle circleNumberOnHex2;
    @FXML
    private Circle circleNumberOnHex3;
    @FXML
    private Circle circleNumberOnHex4;
    @FXML
    private Circle circleNumberOnHex5;
    @FXML
    private Circle circleNumberOnHex6;
    @FXML
    private Circle circleNumberOnHex7;
    @FXML
    private Circle circleNumberOnHex8;
    @FXML
    private Circle circleNumberOnHex9;
    @FXML
    private Circle circleNumberOnHex10;
    @FXML
    private Circle circleNumberOnHex11;
    @FXML
    private Circle circleNumberOnHex12;
    @FXML
    private Circle circleNumberOnHex13;
    @FXML
    private Circle circleNumberOnHex14;
    @FXML
    private Circle circleNumberOnHex15;
    @FXML
    private Circle circleNumberOnHex16;
    @FXML
    private Circle circleNumberOnHex17;
    @FXML
    private Circle circleNumberOnHex18;
    @FXML
    private Circle circleNumberOnHex19;
    @FXML
    private Label labelHexNum1;
    @FXML
    private Label labelHexNum2;
    @FXML
    private Label labelHexNum3;
    @FXML
    private Label labelHexNum4;
    @FXML
    private Label labelHexNum5;
    @FXML
    private Label labelHexNum6;
    @FXML
    private Label labelHexNum7;
    @FXML
    private Label labelHexNum8;
    @FXML
    private Label labelHexNum9;
    @FXML
    private Label labelHexNum10;
    @FXML
    private Label labelHexNum11;
    @FXML
    private Label labelHexNum12;
    @FXML
    private Label labelHexNum13;
    @FXML
    private Label labelHexNum14;
    @FXML
    private Label labelHexNum15;
    @FXML
    private Label labelHexNum16;
    @FXML
    private Label labelHexNum17;
    @FXML
    private Label labelHexNum18;
    @FXML
    private Label labelHexNum19;
    @FXML
    private Polygon harbour18;
    @FXML
    private Polygon harbour17;
    @FXML
    private Polygon harbour16;
    @FXML
    private Polygon harbour15;
    @FXML
    private Polygon harbour14;
    @FXML
    private Polygon harbour13;
    @FXML
    private Polygon harbour1;
    @FXML
    private Polygon harbour2;
    @FXML
    private Polygon harbour3;
    @FXML
    private Polygon harbour12;
    @FXML
    private Polygon harbour11;
    @FXML
    private Polygon harbour10;
    @FXML
    private Polygon harbour9;
    @FXML
    private Polygon harbour8;
    @FXML
    private Polygon harbour7;
    @FXML
    private Polygon harbour6;
    @FXML
    private Polygon harbour5;
    @FXML
    private Polygon harbour4;
    @FXML
    protected ScrollPane gameLogsScrollPane;
    @FXML
    protected Label woolLabel;
    @FXML
    protected Label oreLabel;
    @FXML
    protected Label lumberLabel;
    @FXML
    protected Label brickLabel;
    @FXML
    protected Label grainLabel;
    @FXML
    protected ImageView ImgViewLumberDummy;
    @FXML
    protected ImageView ImgViewWoolDummy;
    @FXML
    protected ImageView ImgViewGrainDummy;
    @FXML
    protected ImageView ImgViewBrickDummy;
    @FXML
    protected ImageView ImgViewOreDummy;
    @FXML
    protected Pane actualPlayerCardsPane;
    @FXML
    protected Pane paneLumbers;
    @FXML
    protected Pane paneWools;
    @FXML
    protected Pane paneOres;
    @FXML
    protected Pane paneGrains;
    @FXML
    protected Pane paneBricks;
    @FXML
    protected Label inventionLabel;
    @FXML
    protected Label knightLabel;
    @FXML
    protected Label monopolyLabel;
    @FXML
    protected Label profitLabel;
    @FXML
    protected Label victoryLabel;
    @FXML
    protected Label roadDestructionLabel;
    @FXML
    protected ImageView ImgViewInventionDummy;
    @FXML
    protected ImageView ImgViewKnightDummy;
    @FXML
    protected ImageView ImgViewMonopolyDummy;
    @FXML
    protected ImageView ImgViewProfitDummy;
    @FXML
    protected ImageView ImgViewRoadDestructionDummy;
    @FXML
    protected ImageView ImgViewVictoryDummy;
    @FXML
    protected Pane paneInvention;
    @FXML
    protected Pane paneKnight;
    @FXML
    protected Pane paneMonopoly;
    @FXML
    protected Pane paneProfit;
    @FXML
    protected Pane paneRoadDestruction;
    @FXML
    protected Pane paneVictory;

    // Properties
    protected ArrayList<TerrainHex> terrainHexes;
    protected ArrayList<Vertex> vertices;
    private ArrayList<Road> roads;
    private ArrayList<Settlement> settlements;
    private ArrayList<Player> players;
    private ArrayList<Harbour> harbours;
    protected Settings settings;
    protected Die die;
    protected Player playerActual;
    protected Thief thief;


    @FXML
    public void initialize() {
        terrainHexes = new ArrayList<>();
        vertices = new ArrayList<>();
        roads = new ArrayList<>();
        settlements = new ArrayList<>();
        players = new ArrayList<>();
        harbours = new ArrayList<>();
        playerActual = new PlayerActual(Constants.COLOR_RED, "PlayerActual");
        players.add(playerActual);
        players.add(new PlayerAI(Constants.COLOR_BLUE, "PlayerArtificial1"));
        players.add(new PlayerAI(Constants.COLOR_PURPLE, "PlayerArtificial2"));
        players.add(new PlayerAI(Constants.COLOR_GREEN, "PlayerArtificial3"));
        die = new Die();

        settings = new Settings();
        constructHexesArray();
        constructVerticesAndRoads();
        initializeBoard();
        assignNumbersToHexes();
        initializeConstructionBox();
        initializeHarbours();

        Image imgForDice1 = new Image("./com/catan/assets/die6.png");
        imgDie1.setFill(new ImagePattern(imgForDice1));
        Image img2ForDice2 = new Image("./com/catan/assets/die6.png");
        imgDie2.setFill(new ImagePattern(img2ForDice2));
        Image img = new Image(Constants.PATH_CARD_PRICE);
        imgPriceCard.setFill(new ImagePattern(img));
    }

    private void initializeHarbours() {
        harbours.add( new Harbour(Constants.HARBOUR, harbour1, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour2, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour3, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour4, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour5, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour6, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour7, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour8, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour9, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour10, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour11, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour12, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour13, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour14, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour15, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour16, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour17, Constants.HARBOUR_NO_RATIO) );
        harbours.add( new Harbour(Constants.HARBOUR, harbour18, Constants.HARBOUR_NO_RATIO) );

        for (Harbour harbour: harbours) {
            Image img = new Image(Constants.PATH_HARBOUR);
            harbour.getShape().setFill(new ImagePattern(img));
            harbour.getShape().setStroke(Color.color(0,0.3,1));
            harbour.getShape().setStrokeWidth(1);
        }
    }

    private void assignNumbersToHexes() {
        int i = 0;
        int[] numbers = {
                2, 3, 3, 4, 4, 5,
                5, 6, 6, 8, 8, 9,
                9, 10, 10, 11, 11, 12
        };

        while (i < 18) {
            int index = (int) (Math.random() * terrainHexes.size());
            TerrainHex hex = terrainHexes.get(index);

            if (hex != null) {
                if (hex.getNumberOnHex() == 0 && !hex.getSourceCardName().equals("")) {
                    hex.setNumberOnHex(numbers[i]);

                    if (numbers[i] > 9) {
                        double newX = hex.getLabelOnHex().getLayoutX() - 6;
                        hex.getLabelOnHex().setLayoutX(newX);
                    }
                    i++;
                }
            }
        }
    }

    private void initializeConstructionBox() {
        Image img = new Image(Constants.PATH_ROAD);
        imgRoad.setFill(new ImagePattern(img));
        imgRoad.setStroke(Color.color(0.4,0.4,0.4));
        imgRoad.setStrokeWidth(1);

        Image img2 = new Image(Constants.PATH_VILLAGE);
        imgVillage.setFill(new ImagePattern(img2));
        imgVillage.setStroke(Color.color(0.4,0.4,0.4));
        imgVillage.setStrokeWidth(1);

        Image img3 = new Image(Constants.PATH_CITY);
        imgCity.setFill(new ImagePattern(img3));
        imgCity.setStroke(Color.color(0.4,0.4,0.4));
        imgCity.setStrokeWidth(1);

        Image img4 = new Image(Constants.PATH_CIVILISATION);
        imgCivilisation.setFill(new ImagePattern(img4));
        imgCivilisation.setStroke(Color.color(0.4,0.4,0.4));
        imgCivilisation.setStrokeWidth(1);
    }

    private void constructVerticesAndRoads() {
        Vertex v1 = new Vertex(vertex1);
        Vertex v2 = new Vertex(vertex2);
        Vertex v3 = new Vertex(vertex3);
        Vertex v4 = new Vertex(vertex4);
        Vertex v5 = new Vertex(vertex5);
        Vertex v6 = new Vertex(vertex6);
        Vertex v7 = new Vertex(vertex7);
        Vertex v8 = new Vertex(vertex8);
        Vertex v9 = new Vertex(vertex9);
        Vertex v10 = new Vertex(vertex10);
        Vertex v11 = new Vertex(vertex11);
        Vertex v12 = new Vertex(vertex12);
        Vertex v13 = new Vertex(vertex13);
        Vertex v14 = new Vertex(vertex14);
        Vertex v15 = new Vertex(vertex15);
        Vertex v16 = new Vertex(vertex16);
        Vertex v17 = new Vertex(vertex17);
        Vertex v18 = new Vertex(vertex18);
        Vertex v19 = new Vertex(vertex19);
        Vertex v20 = new Vertex(vertex20);
        Vertex v21 = new Vertex(vertex21);
        Vertex v22 = new Vertex(vertex22);
        Vertex v23 = new Vertex(vertex23);
        Vertex v24 = new Vertex(vertex24);
        Vertex v25 = new Vertex(vertex25);
        Vertex v26 = new Vertex(vertex26);
        Vertex v27 = new Vertex(vertex27);
        Vertex v28 = new Vertex(vertex28);
        Vertex v29 = new Vertex(vertex29);
        Vertex v30 = new Vertex(vertex30);
        Vertex v31 = new Vertex(vertex31);
        Vertex v32 = new Vertex(vertex32);
        Vertex v33 = new Vertex(vertex33);
        Vertex v34 = new Vertex(vertex34);
        Vertex v35 = new Vertex(vertex35);
        Vertex v36 = new Vertex(vertex36);
        Vertex v37 = new Vertex(vertex37);
        Vertex v38 = new Vertex(vertex38);
        Vertex v39 = new Vertex(vertex39);
        Vertex v40 = new Vertex(vertex40);
        Vertex v41 = new Vertex(vertex41);
        Vertex v42 = new Vertex(vertex42);
        Vertex v43 = new Vertex(vertex43);
        Vertex v44 = new Vertex(vertex44);
        Vertex v45 = new Vertex(vertex45);
        Vertex v46 = new Vertex(vertex46);
        Vertex v47 = new Vertex(vertex47);
        Vertex v48 = new Vertex(vertex48);
        Vertex v49 = new Vertex(vertex49);
        Vertex v50 = new Vertex(vertex50);
        Vertex v51 = new Vertex(vertex51);
        Vertex v52 = new Vertex(vertex52);
        Vertex v53 = new Vertex(vertex53);
        Vertex v54 = new Vertex(vertex54);

        v1.addNeighbor(v9);
        v1.addNeighbor(v2);
        v1.addHex(terrainHexes.get(0));

        v2.addNeighbor(v1);
        v2.addNeighbor(v3);
        v1.addHex(terrainHexes.get(0));

        v3.addNeighbor(v2);
        v3.addNeighbor(v4);
        v3.addNeighbor(v11);
        v3.addHex(terrainHexes.get(0));
        v3.addHex(terrainHexes.get(1));

        v4.addNeighbor(v3);
        v4.addNeighbor(v5);
        v4.addHex(terrainHexes.get(1));

        v5.addNeighbor(v4);
        v5.addNeighbor(v6);
        v5.addNeighbor(v13);
        v5.addHex(terrainHexes.get(1));
        v5.addHex(terrainHexes.get(2));

        v6.addNeighbor(v5);
        v6.addNeighbor(v7);
        v6.addHex(terrainHexes.get(2));

        v7.addNeighbor(v6);
        v7.addNeighbor(v15);
        v7.addHex(terrainHexes.get(2));

        v8.addNeighbor(v9);
        v8.addNeighbor(v18);
        v8.addHex(terrainHexes.get(3));

        v9.addNeighbor(v1);
        v9.addNeighbor(v8);
        v9.addNeighbor(v10);
        v9.addHex(terrainHexes.get(0));
        v9.addHex(terrainHexes.get(3));

        v10.addNeighbor(v9);
        v10.addNeighbor(v11);
        v10.addNeighbor(v20);
        v10.addHex(terrainHexes.get(0));
        v10.addHex(terrainHexes.get(3));
        v10.addHex(terrainHexes.get(4));

        v11.addNeighbor(v3);
        v11.addNeighbor(v10);
        v11.addNeighbor(v12);
        v11.addHex(terrainHexes.get(0));
        v11.addHex(terrainHexes.get(1));
        v11.addHex(terrainHexes.get(4));

        v12.addNeighbor(v11);
        v12.addNeighbor(v13);
        v12.addNeighbor(v22);
        v12.addHex(terrainHexes.get(1));
        v12.addHex(terrainHexes.get(4));
        v12.addHex(terrainHexes.get(5));

        v13.addNeighbor(v5);
        v13.addNeighbor(v12);
        v13.addNeighbor(v14);
        v13.addHex(terrainHexes.get(1));
        v13.addHex(terrainHexes.get(2));
        v13.addHex(terrainHexes.get(5));

        v14.addNeighbor(v13);
        v14.addNeighbor(v15);
        v14.addNeighbor(v24);
        v14.addHex(terrainHexes.get(2));
        v14.addHex(terrainHexes.get(5));
        v14.addHex(terrainHexes.get(6));

        v15.addNeighbor(v7);
        v15.addNeighbor(v14);
        v15.addNeighbor(v16);
        v15.addHex(terrainHexes.get(2));
        v15.addHex(terrainHexes.get(6));

        v16.addNeighbor(v15);
        v16.addNeighbor(v26);
        v16.addHex(terrainHexes.get(6));

        v17.addNeighbor(v18);
        v17.addNeighbor(v28);
        v17.addHex(terrainHexes.get(7));

        v18.addNeighbor(v8);
        v18.addNeighbor(v17);
        v18.addNeighbor(v19);
        v18.addHex(terrainHexes.get(3));
        v18.addHex(terrainHexes.get(7));

        v19.addNeighbor(v18);
        v19.addNeighbor(v20);
        v19.addNeighbor(v30);
        v19.addHex(terrainHexes.get(3));
        v19.addHex(terrainHexes.get(7));
        v19.addHex(terrainHexes.get(8));

        v20.addNeighbor(v10);
        v20.addNeighbor(v19);
        v20.addNeighbor(v21);
        v20.addHex(terrainHexes.get(3));
        v20.addHex(terrainHexes.get(4));
        v20.addHex(terrainHexes.get(8));

        v21.addNeighbor(v20);
        v21.addNeighbor(v22);
        v21.addNeighbor(v32);
        v21.addHex(terrainHexes.get(4));
        v21.addHex(terrainHexes.get(8));
        v21.addHex(terrainHexes.get(9));

        v22.addNeighbor(v12);
        v22.addNeighbor(v21);
        v22.addNeighbor(v23);
        v22.addHex(terrainHexes.get(4));
        v22.addHex(terrainHexes.get(5));
        v22.addHex(terrainHexes.get(9));

        v23.addNeighbor(v22);
        v23.addNeighbor(v24);
        v23.addNeighbor(v34);
        v23.addHex(terrainHexes.get(5));
        v23.addHex(terrainHexes.get(9));
        v23.addHex(terrainHexes.get(10));

        v24.addNeighbor(v14);
        v24.addNeighbor(v23);
        v24.addNeighbor(v25);
        v24.addHex(terrainHexes.get(5));
        v24.addHex(terrainHexes.get(6));
        v24.addHex(terrainHexes.get(10));

        v25.addNeighbor(v24);
        v25.addNeighbor(v26);
        v25.addNeighbor(v36);
        v25.addHex(terrainHexes.get(6));
        v25.addHex(terrainHexes.get(10));
        v25.addHex(terrainHexes.get(11));

        v26.addNeighbor(v16);
        v26.addNeighbor(v25);
        v26.addNeighbor(v27);
        v26.addHex(terrainHexes.get(6));
        v26.addHex(terrainHexes.get(11));

        v27.addNeighbor(v26);
        v27.addNeighbor(v38);
        v27.addHex(terrainHexes.get(11));

        v28.addNeighbor(v17);
        v28.addNeighbor(v29);
        v28.addHex(terrainHexes.get(7));

        v29.addNeighbor(v28);
        v29.addNeighbor(v30);
        v29.addNeighbor(v39);
        v29.addHex(terrainHexes.get(7));
        v29.addHex(terrainHexes.get(12));

        v30.addNeighbor(v29);
        v30.addNeighbor(v31);
        v30.addNeighbor(v19);
        v30.addHex(terrainHexes.get(7));
        v30.addHex(terrainHexes.get(8));
        v30.addHex(terrainHexes.get(12));

        v31.addNeighbor(v30);
        v31.addNeighbor(v32);
        v31.addNeighbor(v41);
        v31.addHex(terrainHexes.get(8));
        v31.addHex(terrainHexes.get(12));
        v31.addHex(terrainHexes.get(13));

        v32.addNeighbor(v31);
        v32.addNeighbor(v33);
        v32.addNeighbor(v21);
        v32.addHex(terrainHexes.get(8));
        v32.addHex(terrainHexes.get(9));
        v32.addHex(terrainHexes.get(13));

        v33.addNeighbor(v32);
        v33.addNeighbor(v34);
        v33.addNeighbor(v43);
        v33.addHex(terrainHexes.get(9));
        v33.addHex(terrainHexes.get(13));
        v33.addHex(terrainHexes.get(14));

        v34.addNeighbor(v33);
        v34.addNeighbor(v35);
        v34.addNeighbor(v23);
        v34.addHex(terrainHexes.get(9));
        v34.addHex(terrainHexes.get(10));
        v34.addHex(terrainHexes.get(14));

        v35.addNeighbor(v34);
        v35.addNeighbor(v36);
        v35.addNeighbor(v45);
        v35.addHex(terrainHexes.get(10));
        v35.addHex(terrainHexes.get(14));
        v35.addHex(terrainHexes.get(15));

        v36.addNeighbor(v35);
        v36.addNeighbor(v37);
        v36.addNeighbor(v25);
        v36.addHex(terrainHexes.get(10));
        v36.addHex(terrainHexes.get(11));
        v36.addHex(terrainHexes.get(15));

        v37.addNeighbor(v36);
        v37.addNeighbor(v38);
        v37.addNeighbor(v47);
        v37.addHex(terrainHexes.get(11));
        v37.addHex(terrainHexes.get(15));

        v38.addNeighbor(v37);
        v38.addNeighbor(v27);
        v38.addHex(terrainHexes.get(11));

        v39.addNeighbor(v29);
        v39.addNeighbor(v40);
        v39.addHex(terrainHexes.get(12));

        v40.addNeighbor(v39);
        v40.addNeighbor(v41);
        v40.addNeighbor(v48);
        v40.addHex(terrainHexes.get(12));
        v40.addHex(terrainHexes.get(16));

        v41.addNeighbor(v40);
        v41.addNeighbor(v42);
        v41.addNeighbor(v31);
        v41.addHex(terrainHexes.get(12));
        v41.addHex(terrainHexes.get(13));
        v41.addHex(terrainHexes.get(16));

        v42.addNeighbor(v41);
        v42.addNeighbor(v43);
        v42.addNeighbor(v50);
        v42.addHex(terrainHexes.get(13));
        v42.addHex(terrainHexes.get(16));
        v42.addHex(terrainHexes.get(17));

        v43.addNeighbor(v33);
        v43.addNeighbor(v42);
        v43.addNeighbor(v44);
        v43.addHex(terrainHexes.get(13));
        v43.addHex(terrainHexes.get(14));
        v43.addHex(terrainHexes.get(17));

        v44.addNeighbor(v43);
        v44.addNeighbor(v45);
        v44.addNeighbor(v52);
        v44.addHex(terrainHexes.get(14));
        v44.addHex(terrainHexes.get(17));
        v44.addHex(terrainHexes.get(18));

        v45.addNeighbor(v44);
        v45.addNeighbor(v46);
        v45.addNeighbor(v35);
        v45.addHex(terrainHexes.get(14));
        v45.addHex(terrainHexes.get(15));
        v45.addHex(terrainHexes.get(18));

        v46.addNeighbor(v45);
        v46.addNeighbor(v47);
        v46.addNeighbor(v54);
        v46.addHex(terrainHexes.get(15));
        v46.addHex(terrainHexes.get(18));

        v47.addNeighbor(v37);
        v47.addNeighbor(v46);
        v47.addHex(terrainHexes.get(15));

        v48.addNeighbor(v40);
        v48.addNeighbor(v49);
        v48.addHex(terrainHexes.get(16));

        v49.addNeighbor(v48);
        v49.addNeighbor(v50);
        v49.addHex(terrainHexes.get(16));

        v50.addNeighbor(v49);
        v50.addNeighbor(v51);
        v50.addNeighbor(v42);
        v50.addHex(terrainHexes.get(16));
        v50.addHex(terrainHexes.get(17));

        v51.addNeighbor(v50);
        v51.addNeighbor(v52);
        v51.addHex(terrainHexes.get(17));

        v52.addNeighbor(v44);
        v52.addNeighbor(v51);
        v52.addNeighbor(v53);
        v52.addHex(terrainHexes.get(17));
        v52.addHex(terrainHexes.get(18));

        v53.addNeighbor(v52);
        v53.addNeighbor(v54);
        v53.addHex(terrainHexes.get(18));

        v54.addNeighbor(v53);
        v54.addNeighbor(v46);
        v54.addHex(terrainHexes.get(18));

        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        vertices.add(v5);
        vertices.add(v6);
        vertices.add(v7);
        vertices.add(v8);
        vertices.add(v9);
        vertices.add(v10);
        vertices.add(v11);
        vertices.add(v12);
        vertices.add(v13);
        vertices.add(v14);
        vertices.add(v15);
        vertices.add(v16);
        vertices.add(v17);
        vertices.add(v18);
        vertices.add(v19);
        vertices.add(v20);
        vertices.add(v21);
        vertices.add(v22);
        vertices.add(v23);
        vertices.add(v24);
        vertices.add(v25);
        vertices.add(v26);
        vertices.add(v27);
        vertices.add(v28);
        vertices.add(v29);
        vertices.add(v30);
        vertices.add(v31);
        vertices.add(v32);
        vertices.add(v33);
        vertices.add(v34);
        vertices.add(v35);
        vertices.add(v36);
        vertices.add(v37);
        vertices.add(v38);
        vertices.add(v39);
        vertices.add(v40);
        vertices.add(v41);
        vertices.add(v42);
        vertices.add(v43);
        vertices.add(v44);
        vertices.add(v45);
        vertices.add(v46);
        vertices.add(v47);
        vertices.add(v48);
        vertices.add(v49);
        vertices.add(v50);
        vertices.add(v51);
        vertices.add(v52);
        vertices.add(v53);
        vertices.add(v54);

        Road r1 = new Road(road1, v1, v2);
        Road r2 = new Road(road2, v2, v3);
        Road r3 = new Road(road3, v3, v4);
        Road r4 = new Road(road4, v4, v5);
        Road r5 = new Road(road5, v5, v6);
        Road r6 = new Road(road6, v6, v7);
        Road r7 = new Road(road7, v1, v9);
        Road r8 = new Road(road8, v3, v11);
        Road r9 = new Road(road9, v5, v13);
        Road r10 = new Road(road10, v7, v15);
        Road r11 = new Road(road11, v8, v9);
        Road r12 = new Road(road12, v9, v10);
        Road r13 = new Road(road13, v10, v11);
        Road r14 = new Road(road14, v11, v12);
        Road r15 = new Road(road15, v12, v13);
        Road r16 = new Road(road16, v13, v14);
        Road r17 = new Road(road17, v14, v15);
        Road r18 = new Road(road18, v15, v16);
        Road r19 = new Road(road19, v8, v18);
        Road r20 = new Road(road20, v10, v20);
        Road r21 = new Road(road21, v12, v22);
        Road r22 = new Road(road22, v14, v24);
        Road r23 = new Road(road23, v16, v26);
        Road r24 = new Road(road24, v17, v18);
        Road r25 = new Road(road25, v18, v19);
        Road r26 = new Road(road26, v19, v20);
        Road r27 = new Road(road27, v20, v21);
        Road r28 = new Road(road28, v21, v22);
        Road r29 = new Road(road29, v22, v23);
        Road r30 = new Road(road30, v23, v24);
        Road r31 = new Road(road31, v24, v25);
        Road r32 = new Road(road32, v25, v26);
        Road r33 = new Road(road33, v26, v27);
        Road r34 = new Road(road34, v17, v28);
        Road r35 = new Road(road35, v19, v30);
        Road r36 = new Road(road36, v21, v32);
        Road r37 = new Road(road37, v23, v34);
        Road r38 = new Road(road38, v25, v36);
        Road r39 = new Road(road39, v27, v38);
        Road r40 = new Road(road40, v28, v29);
        Road r41 = new Road(road41, v29, v30);
        Road r42 = new Road(road42, v30, v31);
        Road r43 = new Road(road43, v31, v32);
        Road r44 = new Road(road44, v32, v33);
        Road r45 = new Road(road45, v33, v34);
        Road r46 = new Road(road46, v34, v35);
        Road r47 = new Road(road47, v35, v36);
        Road r48 = new Road(road48, v36, v37);
        Road r49 = new Road(road49, v37, v38);
        Road r50 = new Road(road50, v29, v39);
        Road r51 = new Road(road51, v31, v41);
        Road r52 = new Road(road52, v33, v43);
        Road r53 = new Road(road53, v35, v45);
        Road r54 = new Road(road54, v37, v47);
        Road r55 = new Road(road55, v39, v40);
        Road r56 = new Road(road56, v40, v41);
        Road r57 = new Road(road57, v41, v42);
        Road r58 = new Road(road58, v42, v43);
        Road r59 = new Road(road59, v43, v44);
        Road r60 = new Road(road60, v44, v45);
        Road r61 = new Road(road61, v45, v46);
        Road r62 = new Road(road62, v46, v47);
        Road r63 = new Road(road63, v40, v48);
        Road r64 = new Road(road64, v42, v50);
        Road r65 = new Road(road65, v44, v52);
        Road r66 = new Road(road66, v46, v54);
        Road r67 = new Road(road67, v48, v49);
        Road r68 = new Road(road68, v49, v50);
        Road r69 = new Road(road69, v50, v51);
        Road r70 = new Road(road70, v51, v52);
        Road r71 = new Road(road71, v52, v53);
        Road r72 = new Road(road72, v53, v54);
        roads.add(r1);
        roads.add(r2);
        roads.add(r3);
        roads.add(r4);
        roads.add(r5);
        roads.add(r6);
        roads.add(r7);
        roads.add(r8);
        roads.add(r9);
        roads.add(r10);
        roads.add(r11);
        roads.add(r12);
        roads.add(r13);
        roads.add(r14);
        roads.add(r15);
        roads.add(r16);
        roads.add(r17);
        roads.add(r18);
        roads.add(r19);
        roads.add(r20);
        roads.add(r21);
        roads.add(r22);
        roads.add(r23);
        roads.add(r24);
        roads.add(r25);
        roads.add(r26);
        roads.add(r27);
        roads.add(r28);
        roads.add(r29);
        roads.add(r30);
        roads.add(r31);
        roads.add(r32);
        roads.add(r33);
        roads.add(r34);
        roads.add(r35);
        roads.add(r36);
        roads.add(r37);
        roads.add(r38);
        roads.add(r39);
        roads.add(r40);
        roads.add(r41);
        roads.add(r42);
        roads.add(r43);
        roads.add(r44);
        roads.add(r45);
        roads.add(r46);
        roads.add(r47);
        roads.add(r48);
        roads.add(r49);
        roads.add(r50);
        roads.add(r51);
        roads.add(r52);
        roads.add(r53);
        roads.add(r54);
        roads.add(r55);
        roads.add(r56);
        roads.add(r57);
        roads.add(r58);
        roads.add(r59);
        roads.add(r60);
        roads.add(r61);
        roads.add(r62);
        roads.add(r63);
        roads.add(r64);
        roads.add(r65);
        roads.add(r66);
        roads.add(r67);
        roads.add(r68);
        roads.add(r69);
        roads.add(r70);
        roads.add(r71);
        roads.add(r72);

        for (Road road : roads) {
            road.getRoad().setVisible(false);
        }
    }

    private void initializeBoard() {
        int countOfPasture = 4;
        int countOfForest = 4;
        int countOfDesert = 1;
        int countOfFields = 3;
        int countOfHill = 4;
        int countOfMountain = 3;
        int placedFields = 0;

        while (placedFields != 19) {
            TerrainHex hex = terrainHexes.get(placedFields);

            int tmp = (int)(Math.random() * 6) + 1;
            Image img = null;
            Color color = null;

            if (tmp == 1 && countOfPasture > 0) {
                countOfPasture--;
                img = new Image(Constants.PATH_HEX_PASTURE);
                color = Color.color(0.2,1,0.2);
                hex.setSourceCardName(Constants.CARD_WOOL);
            }
            else if (tmp == 2 && countOfForest > 0) {
                countOfForest--;
                img = new Image(Constants.PATH_HEX_FOREST);
                color = Color.color(0.3,1,0.2);
                hex.setSourceCardName(Constants.CARD_LUMBER);
            }
            else if (tmp == 3 && countOfDesert > 0) {
                countOfDesert--;
                img = new Image(Constants.PATH_HEX_DESERT);
                color = Color.color(0.4,0.3,0.2);
                thief = new Thief(hex, imgMovingThief);
            }
            else if (tmp == 4 && countOfMountain > 0) {
                countOfMountain--;
                img = new Image(Constants.PATH_HEX_MOUNTAINS);
                color = Color.color(0.4,0.4,0.4);
                hex.setSourceCardName(Constants.CARD_ORE);
            }
            else if (tmp == 5 && countOfFields > 0) {
                countOfFields--;
                img = new Image(Constants.PATH_HEX_FIELDS);
                color = Color.color(1,0.5,0);
                hex.setSourceCardName(Constants.CARD_GRAIN);
            }
            else if (tmp == 6 && countOfHill > 0) {
                countOfHill--;
                img = new Image(Constants.PATH_HEX_HILL);
                color = Color.color(1,0,0);
                hex.setSourceCardName(Constants.CARD_BRICK);
            }
            if (img != null) {
                placedFields++;
                hex.getShape().setFill(new ImagePattern(img));
                hex.getShape().setStroke(color);
                hex.getShape().setStrokeWidth(1);
            }
        }
    }

    private void constructHexesArray() {

        terrainHexes.add(new TerrainHex(terrainHex1,"hex1", circleNumberOnHex1, labelHexNum1));
        terrainHexes.add(new TerrainHex(terrainHex2,"hex2", circleNumberOnHex2, labelHexNum2));
        terrainHexes.add(new TerrainHex(terrainHex3,"hex3", circleNumberOnHex3, labelHexNum3));
        terrainHexes.add(new TerrainHex(terrainHex4,"hex4", circleNumberOnHex4, labelHexNum4));
        terrainHexes.add(new TerrainHex(terrainHex5,"hex5", circleNumberOnHex5, labelHexNum5));
        terrainHexes.add(new TerrainHex(terrainHex6,"hex6", circleNumberOnHex6, labelHexNum6));
        terrainHexes.add(new TerrainHex(terrainHex7,"hex7", circleNumberOnHex7, labelHexNum7));
        terrainHexes.add(new TerrainHex(terrainHex8,"hex8", circleNumberOnHex8, labelHexNum8));
        terrainHexes.add(new TerrainHex(terrainHex9,"hex9", circleNumberOnHex9, labelHexNum9));
        terrainHexes.add(new TerrainHex(terrainHex10,"hex10", circleNumberOnHex10, labelHexNum10));
        terrainHexes.add(new TerrainHex(terrainHex11,"hex11", circleNumberOnHex11, labelHexNum11));
        terrainHexes.add(new TerrainHex(terrainHex12,"hex12", circleNumberOnHex12, labelHexNum12));
        terrainHexes.add(new TerrainHex(terrainHex13,"hex13", circleNumberOnHex13, labelHexNum13));
        terrainHexes.add(new TerrainHex(terrainHex14,"hex14", circleNumberOnHex14, labelHexNum14));
        terrainHexes.add(new TerrainHex(terrainHex15,"hex15", circleNumberOnHex15, labelHexNum15));
        terrainHexes.add(new TerrainHex(terrainHex16,"hex16", circleNumberOnHex16, labelHexNum16));
        terrainHexes.add(new TerrainHex(terrainHex17,"hex17", circleNumberOnHex17, labelHexNum17));
        terrainHexes.add(new TerrainHex(terrainHex18,"hex18", circleNumberOnHex18, labelHexNum18));
        terrainHexes.add(new TerrainHex(terrainHex19,"hex19", circleNumberOnHex19, labelHexNum19));
    }
    public Label getWarningLabel(){
        return labelWarning;
    }
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public ArrayList<Settlement> getSettlements() {
        return settlements;
    }

    public void setSettlements(ArrayList<Settlement> settlements) {
        this.settlements = settlements;
    }

    public Label getLabelTurn() {
        return labelTurn;
    }

    public void setLabelTurn(Label labelTurn) {
        this.labelTurn = labelTurn;
    }

    public Rectangle getImgDie1() {
        return imgDie1;
    }

    public void setImgDie1(Rectangle imgDie1) {
        this.imgDie1 = imgDie1;
    }

    public Rectangle getImgDie2() {
        return imgDie2;
    }

    public void setImgDie2(Rectangle imgDie2) {
        this.imgDie2 = imgDie2;
    }

    public Label getLabelLogs() {
        return labelLogs;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setLabelLogs(Label labelLogs) {
        this.labelLogs = labelLogs;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public TerrainHex getHexWithCoordinates(Circle circleToBeChecked){
        for(int i = 0; i < terrainHexes.size(); i++){
            if(terrainHexes.get(i).isShapeInside(circleToBeChecked)){
                return terrainHexes.get(i);
            }
        }
        return null;
    }

}
