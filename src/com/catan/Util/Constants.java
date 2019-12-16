package com.catan.Util;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    public static final String VIEW_THIEF = "../view/thiefCardPunishment.fxml";

    public static final String CONSTRUCTION_STRING = "Construction";
    public static final String THIEF_STRING = "Thief";

    public static final String ROAD = "road";
    public static final String VILLAGE = "village";
    public static final String CITY = "city";
    public static final String CIVILISATION = "civilisation";
    public static final String HARBOUR = "harbour";
    public static final String DEVELOPMENT_CARD = "development_card";
    public static final int CONSTRUCTION_RADIUS = 22;

    public static final Color COLOR_CONSTRUCTION_UNSELECTED = Color.color(0.4,0.4,0.4);
    public static final Color COLOR_CONSTRUCTION_SELECTED = Color.color(1,0,0);
    public static final Color COLOR_BLUR_VERTEX = Color.valueOf("#ffde2173");
    public static final Color COLOR_BLEND_VERTEX = Color.valueOf("#ffdd21");
    public static final Color COLOR_ROAD_SELECTION_VERTEX = Color.valueOf("#FF9800");

    public static final String PATH_ROAD = "./com/catan/assets/road.png";
    public static final String PATH_VILLAGE = "./com/catan/assets/village.jpg";
    public static final String PATH_CITY = "./com/catan/assets/city.png";
    public static final String PATH_CIVILISATION = "./com/catan/assets/civilisation.jpg";
    public static final String PATH_CARD_PRICE = "./com/catan/assets/price_card.png";
    public static final String PATH_HARBOUR = "./com/catan/assets/harbour.gif";

    public static final String PATH_CITY_RED    = "./com/catan/assets/city_red.png";
    public static final String PATH_CITY_BLUE   = "./com/catan/assets/city_blue.png";
    public static final String PATH_CITY_PURPLE = "./com/catan/assets/city_purple.png";
    public static final String PATH_CITY_GREEN  = "./com/catan/assets/city_green.png";

    public static final String PATH_VILLAGE_RED    = "./com/catan/assets/village_red.png";
    public static final String PATH_VILLAGE_BLUE   = "./com/catan/assets/village_blue.png";
    public static final String PATH_VILLAGE_PURPLE = "./com/catan/assets/village_purple.png";
    public static final String PATH_VILLAGE_GREEN   = "./com/catan/assets/village_green.png";

    public static final String PATH_CIVILISATION_RED    = "./com/catan/assets/civilisation_red.png";
    public static final String PATH_CIVILISATION_BLUE   = "./com/catan/assets/civilisation_blue.png";
    public static final String PATH_CIVILISATION_PURPLE = "./com/catan/assets/civilisation_purple.png";
    public static final String PATH_CIVILISATION_GREEN  = "./com/catan/assets/civilisation_green.png";

    public static final String PATH_HEX_PASTURE = "./com/catan/assets/pasture.png";
    public static final String PATH_HEX_FOREST = "./com/catan/assets/forest.png";
    public static final String PATH_HEX_DESERT = "./com/catan/assets/desert.png";
    public static final String PATH_HEX_MOUNTAINS = "./com/catan/assets/mountains.png";
    public static final String PATH_HEX_FIELDS = "./com/catan/assets/fields.png";
    public static final String PATH_HEX_HILL = "./com/catan/assets/hill.png";

    public static final String COLOR_RED = "red";
    public static final String COLOR_GREEN = "green";
    public static final String COLOR_PURPLE = "purple";
    public static final String COLOR_BLUE = "blue";

    public static final Color COLOR_RGB_RED = Color.valueOf("#ff0000");
    public static final Color COLOR_RGB_GREEN = Color.valueOf("#00ff00");
    public static final Color COLOR_RGB_PURPLE = Color.valueOf("#ff00f7");
    public static final Color COLOR_RGB_BLUE = Color.valueOf("#0000ff");
    public static final String ICON_THIEF =  "./com/catan/assets/icon_thief.gif";

    public static final String CARD_GRAIN = "grain";
    public static final String CARD_WOOL = "wool";
    public static final String CARD_ORE = "ore";
    public static final String CARD_BRICK = "brick";
    public static final String CARD_LUMBER = "lumber";


    public static final String PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE = "com/catan/assets/dev_card_profit.jpg";
    public static final String PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION = "com/catan/assets/dev_card_road_destruction.jpg";
    public static final String PATH_DEVELOPMENT_CARD_INVENTION = "com/catan/assets/dev_card_invention.jpg";
    public static final String PATH_DEVELOPMENT_CARD_VICTORY_POINT = "com/catan/assets/dev_card_victory.jpg";
    public static final String PATH_DEVELOPMENT_CARD_KNIGHT = "com/catan/assets/dev_card_knight.jpg";
    public static final String PATH_DEVELOPMENT_CARD_MONOPOL = "com/catan/assets/dev_card_monopoly.jpg";

    public static final ArrayList<String> developmentCardPaths = new ArrayList<>(
            Arrays.asList(Constants.PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE,
                          Constants.PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION,
                          Constants.PATH_DEVELOPMENT_CARD_INVENTION,
                          Constants.PATH_DEVELOPMENT_CARD_VICTORY_POINT,
                          Constants.PATH_DEVELOPMENT_CARD_KNIGHT,
                          Constants.PATH_DEVELOPMENT_CARD_MONOPOL));

    public static final String DEVELOPMENT_CARD_PROFIT_EXCHANGE  = "Profit Exchange Development Card";
    public static final String DEVELOPMENT_CARD_ROAD_DESTRUCTION = "Road Destruction Development Card";
    public static final String DEVELOPMENT_CARD_INVENTION = "Invention Development Card";
    public static final String DEVELOPMENT_CARD_VICTORY_POINT = "Victory Development Card";
    public static final String DEVELOPMENT_CARD_KNIGHT  = "Knight Development Card";
    public static final String DEVELOPMENT_CARD_MONOPOL = "Monopoly Development Card";

    public static final ArrayList<String> developmentCardNames = new ArrayList<>(
            Arrays.asList(Constants.DEVELOPMENT_CARD_PROFIT_EXCHANGE,
                          Constants.DEVELOPMENT_CARD_ROAD_DESTRUCTION,
                          Constants.DEVELOPMENT_CARD_INVENTION,
                          Constants.DEVELOPMENT_CARD_VICTORY_POINT,
                          Constants.DEVELOPMENT_CARD_MONOPOL,
                          Constants.DEVELOPMENT_CARD_KNIGHT));

    public static final int PANE_WIDTH = 1920;
    public static final int PANE_HEIGHT = 1080;

    public static String PATH_VIEW_TRADE_REQUEST = "com/catan/view/tradeRequest.fxml";
    public static String PATH_VIEW_PLAY_DEVELOPMENT_CARD = "com/catan/view/playDevelopmentCard.fxml";
    public static String PATH_VIEW_DEV_MONOPOL_CARD = "com/catan/view/monopolDevCard.fxml";
    public static String PATH_VIEW_DEV_INVENTION_CARD = "com/catan/view/inventionDevCard.fxml";
    public static String PATH_VIEW_TRADE_OFFER = "com/catan/view/tradeOffer.fxml";
    public static String PATH_VIEW_PUNISHMENT = "com/catan/view/thiefCardPunishment.fxml";
    public static String PATH_VIEW_ENDGAME = "com/catan/view/endGame.fxml";
    public static final double HARBOUR_NO_RATIO = -1;

    public static final ArrayList<String> resourceNames = new ArrayList<>(
            Arrays.asList(Constants.CARD_ORE,
                          Constants.CARD_BRICK,
                          Constants.CARD_LUMBER,
                          Constants.CARD_GRAIN,
                          Constants.CARD_WOOL));

    public static final ArrayList<String> constructionNames = new ArrayList<>(
            Arrays.asList(Constants.ROAD,
                          Constants.VILLAGE,
                          Constants.CITY,
                          Constants.CIVILISATION)
    );

    public static final ArrayList<String> settlementNames = new ArrayList<>(
            Arrays.asList(Constants.VILLAGE,
                          Constants.CITY,
                          Constants.CIVILISATION)
    );

}