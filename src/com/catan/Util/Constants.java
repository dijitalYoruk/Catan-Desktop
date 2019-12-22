package com.catan.Util;

import com.catan.modal.Settings;
import javafx.scene.paint.Color;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    public static String THEME_FOLDER = "default";

    public static final String VIEW_THIEF = "../view/thiefCardPunishment.fxml";
    public static final String CONSTRUCTION_STRING = "Construction";
    public static final String THIEF_STRING = "Thief";
    public static final String INTRO = "src/com/catan/assets/CatanIntro.mp4";

    public static final String ROAD = "road";
    public static final String VILLAGE = "village";
    public static final String CITY = "city";
    public static final String CIVILISATION = "civilisation";
    public static final String HARBOUR = "harbour";
    public static final String DEVELOPMENT_CARD = "development_card";
    public static final int CONSTRUCTION_RADIUS = 22;
    public static final String PATH_DIE_GIF  = "./com/catan/assets/" + THEME_FOLDER + "/gif_die.gif";
    public static final Color COLOR_CONSTRUCTION_UNSELECTED = Color.color(0.4,0.4,0.4);
    public static final Color COLOR_CONSTRUCTION_SELECTED = Color.color(1,0,0);
    public static final Color COLOR_BLUR_VERTEX = Color.valueOf("#ffde2173");
    public static final Color COLOR_BLEND_VERTEX = Color.valueOf("#ffdd21");
    public static final Color COLOR_ROAD_SELECTION_VERTEX = Color.valueOf("#FF9800");

    public static final String COLOR_RED = "red";
    public static final String COLOR_GREEN = "green";
    public static final String COLOR_PURPLE = "purple";
    public static final String COLOR_BLUE = "blue";

    public static final Color COLOR_RGB_RED = Color.valueOf("#ff0000");
    public static final Color COLOR_RGB_GREEN = Color.valueOf("#00ff00");
    public static final Color COLOR_RGB_PURPLE = Color.valueOf("#9013FE");
    public static final Color COLOR_RGB_BLUE = Color.valueOf("#0000ff");
    public static final String ICON_THIEF =  "./com/catan/assets/" + THEME_FOLDER + "/icon_thief.gif";

    public static final String CARD_GRAIN = "grain";
    public static final String CARD_WOOL = "wool";
    public static final String CARD_ORE = "ore";
    public static final String CARD_BRICK = "brick";
    public static final String CARD_LUMBER = "lumber";

    public static final ArrayList<String> developmentCardPaths = new ArrayList<>(
            Arrays.asList(PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE(),
                          PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION(),
                          PATH_DEVELOPMENT_CARD_INVENTION(),
                          PATH_DEVELOPMENT_CARD_VICTORY_POINT(),
                          PATH_DEVELOPMENT_CARD_KNIGHT(),
                          PATH_DEVELOPMENT_CARD_MONOPOL()));

    public static final String DEVELOPMENT_CARD_PROFIT_EXCHANGE  = "Profit Exchange Development Card";
    public static final String DEVELOPMENT_CARD_ROAD_DESTRUCTION = "Road Destruction Development Card";
    public static final String DEVELOPMENT_CARD_INVENTION = "Invention Development Card";
    public static final String DEVELOPMENT_CARD_VICTORY_POINT = "Victory Development Card";
    public static final String DEVELOPMENT_CARD_KNIGHT  = "Knight Development Card";
    public static final String DEVELOPMENT_CARD_MONOPOL = "Monopoly Development Card";

    public static final String DEVELOPMENT_CARD_VIEW_CLOSE_ICON = "./com/catan/assets/closeIcon.png";

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

    public static final String PATH_DIRECTORY = Paths.get(".").toAbsolutePath().normalize().toString();
    public static final String THEME_DEFAULT = "Default";
    public static final String THEME_SPACE = "Space";
    public static final String THEME_KARADENIZ = "Karadeniz";
    public static final String PATH_SETTINGS_TEXT_FILE = PATH_DIRECTORY + "/src/com/catan/persistentData/settings.txt";
    public static final String CURRENT_THEME = "CURRENT_THEME";
    public static final String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
    public static final String THRESHOLD_VICTORY = "THRESHOLD_VICTORY";
    public static final String THRESHOLD_ROAD = "THRESHOLD_ROAD";
    public static final String THRESHOLD_ARMY = "THRESHOLD_ARMY";
    public static final String AUDIO_DEFAULT = PATH_DIRECTORY +  "\\src\\com\\catan\\music\\catan_theme.mp3";
    public static final String AUDIO_SPACE = PATH_DIRECTORY + "\\src\\com\\catan\\music\\space_theme.mp3";
    public static final String AUDIO_KARADENIZ = PATH_DIRECTORY + "\\src\\com\\catan\\music\\karadeniz_theme.mp3";
    public static final String IMG_SOUND_MUTE = "com/catan/assets/" + THEME_FOLDER + "/music-button.jpg";
    public static final String IMG_SOUND_UNMUTED = "com/catan/assets/" + THEME_FOLDER + "/music-button-muted.jpg";

    public static String PATH_ROAD() {
        return "./com/catan/assets/" + THEME_FOLDER + "/road.png";
    }

    public static String PATH_VILLAGE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/village.png";
    }

    public static String PATH_CITY() {
        return "./com/catan/assets/" + THEME_FOLDER + "/city.png";
    }

    public static String PATH_CIVILIZATION() {
        return "./com/catan/assets/" + THEME_FOLDER + "/civilisation.png";
    }

    public static String PATH_CARD_PRICE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/price_card.png";
    }

    public static String PATH_HARBOUR() {
        return "./com/catan/assets/" + THEME_FOLDER + "/harbour.gif";
    }

    public static String PATH_CITY_RED() {
        return  "./com/catan/assets/" + THEME_FOLDER + "/city_red.png";
    }

    public static String PATH_CITY_BLUE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/city_blue.png";
    }

    public static String PATH_CITY_PURPLE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/city_purple.png";
    }

    public static String PATH_CITY_GREEN() {
        return "./com/catan/assets/" + THEME_FOLDER + "/city_green.png";
    }

    public static String PATH_VILLAGE_RED() {
        return "./com/catan/assets/" + THEME_FOLDER + "/village_red.png";
    }

    public static String PATH_VILLAGE_BLUE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/village_blue.png";
    }

    public static String PATH_VILLAGE_PURPLE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/village_purple.png";
    }

    public static String PATH_VILLAGE_GREEN() {
        return "./com/catan/assets/" + THEME_FOLDER + "/village_green.png";
    }

    public static String PATH_CIVILISATION_RED() {
        return "./com/catan/assets/" + THEME_FOLDER + "/civilisation_red.png";
    }

    public static String PATH_CIVILISATION_BLUE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/civilisation_blue.png";
    }

    public static String PATH_CIVILISATION_PURPLE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/civilisation_purple.png";
    }

    public static String PATH_CIVILISATION_GREEN() {
        return "./com/catan/assets/" + THEME_FOLDER + "/civilisation_green.png";
    }

    public static String PATH_RESOURCE_LUMBER() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_lumber.jpg";
    }

    public static String PATH_RESOURCE_BRICK() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_brick.jpg";
    }

    public static String PATH_RESOURCE_GRAIN() {
        return  "./com/catan/assets/" + THEME_FOLDER + "/resource_grain.jpg";
    }

    public static String PATH_RESOURCE_ORE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_ore.jpg";
    }

    public static String PATH_RESOURCE_WOOL() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_wool.jpg";
    }

    public static String PATH_RESOURCE_LUMBER_GRAYSCALE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_lumber_grayscale.jpg";
    }

    public static String PATH_RESOURCE_BRICK_GRAYSCALE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_brick_grayscale.jpg";
    }

    public static String PATH_RESOURCE_GRAIN_GRAYSCALE() {
        return  "./com/catan/assets/" + THEME_FOLDER + "/resource_grain_grayscale.jpg";
    }

    public static String PATH_RESOURCE_ORE_GRAYSCALE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_ore_grayscale.jpg";
    }

    public static String PATH_RESOURCE_WOOL_GRAYSCALE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/resource_wool_grayscale.jpg";
    }

    public static String PATH_HEX_PASTURE() {
        return "./com/catan/assets/" + THEME_FOLDER + "/pasture.png";
    }

    public static String PATH_HEX_FOREST() {
        return "./com/catan/assets/" + THEME_FOLDER + "/forest.png";
    }

    public static String PATH_HEX_DESERT() {
        return "./com/catan/assets/" + THEME_FOLDER + "/desert.png";
    }

    public static String PATH_HEX_MOUNTAINS() {
        return "./com/catan/assets/" + THEME_FOLDER + "/mountains.png";
    }

    public static String PATH_HEX_FIELDS() {
        return "./com/catan/assets/" + THEME_FOLDER + "/fields.png";
    }

    public static String PATH_HEX_HILL() {
        System.out.println("./com/catan/assets/" + THEME_FOLDER + "/hill.png");
        return "./com/catan/assets/" + THEME_FOLDER + "/hill.png";
    }

    public static String PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_profit.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_road_destruction.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_INVENTION() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_invention.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_VICTORY_POINT() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_victory.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_KNIGHT() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_knight.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_MONOPOL() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_monopoly.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_PROFIT_EXCHANGE_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_profit_grayscale.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_ROAD_DESTRUCTION_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_road_destruction_grayscale.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_INVENTION_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_invention_grayscale.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_VICTORY_POINT_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_victory_grayscale.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_KNIGHT_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_knight_grayscale.jpg";
    }

    public static String PATH_DEVELOPMENT_CARD_MONOPOL_GRAYSCALE() {
        return "com/catan/assets/" + THEME_FOLDER + "/dev_card_monopoly_grayscale.jpg";
    }

    public static String PATH_TRADE_1() {
        return "com/catan/assets/" + THEME_FOLDER + "/img_merchant.png";
    }

    public static String PATH_TRADE_2() {
        return "com/catan/assets/" + THEME_FOLDER + "/img_market.png";
    }

    public static String PATH_ARROW_LEFT() {
        return "com/catan/assets/" + THEME_FOLDER + "/left_arrow_instructions.png";
    }

    public static String PATH_ARROW_RIGHT() {
        return "com/catan/assets/" + THEME_FOLDER + "/right_arrow_instruction.png";
    }

    public static String PATH_BG_GAME() {
        return "com/catan/assets/" + THEME_FOLDER + "/background_game.jpg";
    }

    public static String PATH_BG_GAME_ENTRANCE() {
        return "com/catan/assets/" + THEME_FOLDER + "/background_game_entrance.jpg";
    }

    public static String PATH_BG_PROGRAM() {
        return "com/catan/assets/" + THEME_FOLDER + "/background.jpg";
    }

    public static String PATH_BG_SETTINGS() {
        return "com/catan/assets/" + THEME_FOLDER + "/background_settings.jpg";
    }

    public static String PATH_BG_INFORMATION() {
        return "com/catan/assets/" + THEME_FOLDER + "/background_information.jpg";
    }

    public static String PATH_BG_INVENTION() {
        return "com/catan/assets/" + THEME_FOLDER + "/invention_ng.jpg";
    }

    public static ArrayList<String> PATH_DIES() {
        return new ArrayList<>(Arrays.asList(
                "com/catan/assets/" + THEME_FOLDER + "/die1.png",
                "com/catan/assets/" + THEME_FOLDER + "/die1.png",
                "com/catan/assets/" + THEME_FOLDER + "/die2.png",
                "com/catan/assets/" + THEME_FOLDER + "/die3.png",
                "com/catan/assets/" + THEME_FOLDER + "/die4.png",
                "com/catan/assets/" + THEME_FOLDER + "/die5.png",
                "com/catan/assets/" + THEME_FOLDER + "/die6.png"));
    }

    public static ArrayList<String> getResourcePaths() {
        return new ArrayList<>(Arrays.asList(
                PATH_RESOURCE_ORE(),
                PATH_RESOURCE_BRICK(),
                PATH_RESOURCE_LUMBER(),
                PATH_RESOURCE_GRAIN(),
                PATH_RESOURCE_WOOL()));
    }

}