package sample;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Config {
    public final static String GAME_NAME = "Tower Defense";
    public final static double pixels = 32.0;
    public final static double SCREEN_HEIGHT = 700.0;
    public final static double SCREEN_WIDTH = 1200.0;
    public final static double PLAYGROUND_HEIGHT = 480.0;
    public final static double PLAYGROUND_WIDTH = 992.0;
    public final static double VERTICAL_TILE = 20;
    public final static double HORIZONAL_TILE = 20;


//Enemy Config
    //Normal Enemy
    public final static double normalHealth = 50.0;
    public final static double normalDamage = 5.0;
    public final static double normalSpeed = 2.0;
    //Tanker Enemy
    public final static double TankerHealth = 100.0;
    public final static double TankerDamage = 5.0;
    public final static double TankerSpeed = 3.0;
    //Smaller Enemy
    public final static double smallerHealth = 25.0;
    public final static double smallerDamage = 15.0;
    public final static double smallerSpeed = 5.0;
    //Boss Enemy
    public final static double bossHealth = 150.0;
    public final static double bossDamage = 15.0;
    public final static double bossSpeed = 4.0;

//Tower Config
    //Ballista
    public final static double ballistaRange = 4.0;
    public final static double ballistaFireRate = 5.0;
    public final static int ballistaCost = 50;
    public final static int ballistaUpgradeCost1 = 50;
    public final static int ballistaUpgradeCost2 = 100;
    //Blaster
    public final static double blasterRange = 3.0;
    public final static double blasterFireRate = 2.0;
    public final static int blasterCost = 100;
    public final static int blasterUpgradeCost1 = 50;
    public final static int blasterUpgradeCost2 = 100;
    //Cannon
    public final static double cannonRange = 2.0;
    public final static double cannonFireRate = 2.0;
    public final static int cannonCost = 65;
    public final static int cannonUpgradeCost1 = 50;
    public final static int cannonUpgradeCost2 = 100;
    //Catapult
    public final static double catapultRange = 1.0;
    public final static double catapultFireRate = 1.0;
    public final static int catapultCost = 75;
    public final static int catapultUpgradeCost1 = 50;
    public final static int catapultUpgradeCost2 = 100;

//Bullet Config
    //Ballista Bullet
    public final static double ballistaSpeed = 2.0;
    public final static double ballistaDamage = 1.0;
    public final static int ballistaLastingTime = 5;

    //Blaster Bullet
    public final static double blasterSpeed = 2.0;
    public final static double blasterDamage = 10.0;
    public final static int blasterLastingTime = 5;

    //Cannon Bullet
    public final static double cannonSpeed = 10.0;
    public final static double cannonDamage = 1.0;
    public final static int cannonLastingTime = 5;

    //Catapult Bullet
    public final static double catapultSpeed = 10.0;
    public final static double catapultDamage = 1.0;
    public final static int catapultLastingTime = 5;

    public final static double bulletSize = 8.0;

//Spawner Config
    final static double normalInterval = 10.0;
    public final static double smallerInterval = 10.0;
    public final static double tankerInterval = 10.0;
    public final static String enemylvl1 = "1111";

//AutoPlay
    final static int towerRefreshRate = 35;

//Audio
    public static String themePath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/2_t5shot.mp3";
    public static Media theme = new Media(new File(themePath).toURI().toString());

    public static String balPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/2_t5shot.mp3";
    public static Media ballista = new Media(new File(balPath).toURI().toString());

    public static String blasPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/4_t1shot.mp3";
    public static Media blaster = new Media(new File(blasPath).toURI().toString());

    public static String canPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/1_t2shot.mp3";
    public static Media cannon = new Media(new File(canPath).toURI().toString());

    public static String cataPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/1_t2shot.mp3";
    public static Media catapult = new Media(new File(cataPath).toURI().toString());

    public static String towerPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/3_turretbuild.mp3";
    public static Media towerBuild = new Media(new File(towerPath).toURI().toString());
    //public static MediaPlayer mediaPlayer1 = new MediaPlayer(Config.towerBuild);

    public static String clickPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/7_click.mp3";
    public static Media click = new Media(new File(clickPath).toURI().toString());

    public static String coinPath = "./src/main/java/TowerDefense/AssetsKit_1/sounds/coin.mp3";
    public static Media coin = new Media(new File(coinPath).toURI().toString());

    //MediaPlayer mediaPlayer = new MediaPlayer(media);

    Config(){

    }
}
