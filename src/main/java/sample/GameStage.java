package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.*;
import sample.Entity.Spawner.Spawner;
import sample.Entity.Target;
import sample.Entity.Tower.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class GameStage extends MyStage{
    private int level;
    private int eventType = 0;
    private List<GameEntity> gameEntities = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public Queue<Enemy> enemyList = new LinkedList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private int money = 100;
    private double waveInterval = Config.normalInterval;

    private String[][] map = new String[20][20];

    private GameEntity sp = new Spawner(3, 19);
    private GameEntity target = new Target(18,0);

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void getMap() {
        try{
            Helper helper = new Helper();
            map = helper.getMapFromText(this.getLevel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getEnemy() {
        //Scanner sc = new Scanner(Config.enemylvl1);
        //while(sc.hasNext()){
        String m = Config.enemylvl1;
        System.out.println(m);
        System.out.println(m.length());
        for(int i=0; i < m.length(); i++) {
            /*
            switch (m.charAt(i)) {
                case '1':
                    enemyList.add(new NormalEnemy(sp.getX(), sp.getY(), map));
                case '2':
                    enemyList.add(new SmallerEnemy(sp.getX(), sp.getY(), map));
                case '3':
                    enemyList.add(new TankerEnemy(sp.getX(), sp.getY(), map));
                case '4':
                    enemyList.add(new BossEnemy(sp.getX(), sp.getY(), map));
            }
             */
            if(m.charAt(i) == '1') enemyList.add(new NormalEnemy(sp.getX(), sp.getY(), map));
            else if(m.charAt(i) == '2') enemyList.add(new SmallerEnemy(sp.getX(), sp.getY(), map));
                else if(m.charAt(i) == '3') enemyList.add(new TankerEnemy(sp.getX(), sp.getY(), map));
                    else if(m.charAt(i) == '4') enemyList.add(new BossEnemy(sp.getX(), sp.getY(), map));
        }
    }

    //Init game from save game file
    GameStage() throws IOException {
        Helper helper = new Helper();
        String jsonStr = helper.loadJson();
        JSONObject json = new JSONObject(jsonStr);

        level = json.getInt("level");
        money = json.getInt("money");
        this.getMap();
        JSONArray enemyJson = json.getJSONArray("enemies");
        for(int i = 0; i < enemyJson.length(); i++){
            double x = enemyJson.getJSONObject(i).getDouble("x");
            double y = enemyJson.getJSONObject(i).getDouble("y");
            int health = enemyJson.getJSONObject(i).getInt("health");
            String direction = enemyJson.getJSONObject(i).getString("direction");

            switch (enemyJson.getJSONObject(i).getInt("type")){
                case 1:{
                    enemies.add(new BossEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 2:{
                    enemies.add(new NormalEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 3:{
                    enemies.add(new SmallerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 4:{
                    enemies.add(new TankerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
            }
        }

        gameEntities.add(sp);
        gameEntities.add(target);
    }

    //Init game stage from the beginning
    GameStage(int level){
        this.level = level;
        this.getMap();
        this.getEnemy();
        /*
        enemyList.add(new NormalEnemy(sp.getX(),sp.getY(), map));
        enemyList.add(new NormalEnemy(sp.getX(),sp.getY(), map));
        enemyList.add(new TankerEnemy(sp.getX(),sp.getY(), map));
        enemies.add(new TankerEnemy(sp.getX(), sp.getY(), map));
        enemies.add(new SmallerEnemy(sp.getX(),sp.getY(), map));
        enemies.add(new SmallerEnemy(sp.getX(), sp.getY(), map));
        enemies.add(new BossEnemy(sp.getX(),sp.getY(), map));
        enemies.add(new TankerEnemy(sp.getX(), sp.getY(), map));
         */

        gameEntities.add(sp);
        gameEntities.add(target);
    }

    private void renderMap(GraphicsContext gc){
        gc.drawImage(new Image("file:src/main/java/images/background.png"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        gc.drawImage(new Image("file:src/main/java/maps/map1.png"),
                0, 0, 1200 , 545);
    }


    private void renderBar(GraphicsContext gc){
        //gc.drawImage(
        //        new Image("file:src/main/java/images/setting.png"),
        //        5, 5, 40, 40
        //);
        //gc.drawImage(
        //        new Image("file:src/main/java/images/cancel.png"),
        //        50, 12, 25, 25
        //);
        for(int i = 1; i <= 4; i++) {
            gc.drawImage(
                    new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/00" + i + ".png"),
                    i*30 + (i-1)*61, Config.pixels * 17 + 50, 61, 30
            );
        }

        int x = 15;
        String m = toString().valueOf(Config.ballistaCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 105;
        m = toString().valueOf(Config.blasterCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 210;
        m = toString().valueOf(Config.cannonCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 300;
        m = toString().valueOf(Config.catapultCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }


    }
    private void renderEnemy(GraphicsContext gc){

        if(!enemyList.isEmpty()) {
            if (waveInterval == 0) {
                enemies.add(enemyList.poll());
                waveInterval = Config.normalInterval;
                System.out.println(enemyList.size());
            } else waveInterval--;
        }


        if(!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.render(gc);
            }
        }
    }
    private void renderBullet(GraphicsContext gc){
        for(Bullet bullet : bullets){
            bullet.render(gc);
        }
    }
    private void renderTower(GraphicsContext gc) {
        for (GameEntity gameEntity : gameEntities) {
            gameEntity.render(gc);
        }
    }

    private void renderMoney(GraphicsContext gc){
        int x = 850;
        String m = toString().valueOf(money);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 600, 50, 50);
        for (int i = 0; i < m.length(); i++) {
            x += 25;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 600, 50, 50);
        }
    }
    public void render(GraphicsContext gc, Group root){
        renderMap(gc);
        renderBar(gc);
        renderTower(gc);
        renderEnemy(gc);
        renderBullet(gc);
        //renderMoney(gc);
    }

    public void update(){
        //Enemy Update
        for(GameEntity gameEntity : gameEntities) {
            gameEntity.update();
        }
        if(!enemies.isEmpty()) {
            if (enemies.get(0).isDead()) {
                money += enemies.get(0).getPrize();
            }
        }
        enemies.removeIf(Enemy::isDead);
        for(Enemy enemy : enemies){
            enemy.update();
        }


        //bullets.removeIf(Bullet::isDisposed);
        for(Bullet bullet : bullets){
            bullet.update();
        }
        bullets.removeIf(Bullet::isDisposed);
    }

    public void event(Scene scene){
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            double X = mouseEvent.getX();
            double Y = mouseEvent.getY();
            if(eventType == 0) {
                if (Y >= 597.0 && Y <= 627.0) {
                    if (X >= 31 && X <= 93 && money >= Config.ballistaCost) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/001.png")));
                        eventType = 1;
                        money -= Config.ballistaCost;
                        try {
                            saveGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (X >= 125 && X <= 184 && money >= Config.blasterCost) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/002.png")));
                        eventType = 2;
                        money -= Config.blasterCost;
                    }
                    if (X >= 213 && X <= 275 && money >= Config.cannonCost) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/003.png")));
                        eventType = 3;
                        money -= Config.cannonCost;
                    }
                    if (X >= 305 && X <= 366 && money >= Config.catapultCost) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/004.png")));
                        eventType = 4;
                        money -= Config.catapultCost;
                    }
                }
            }
            else if(eventType == 1){
                if (X >= 31 && X <= 93 && Y >= 597.0 && Y <= 627.0){
                    eventType = 0;
                    money += Config.ballistaCost;
                } //Re-click the tower symbol to cancel planting tower
                else {
                    Helper helper = new Helper();
                    int i = helper.xyToI(X - 92, Y - 92);
                    int j = helper.xyToJ(X - 92, Y - 92);
                    if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                        gameEntities.add(new BallistaTower(X - 92, Y - 92, enemies, bullets));
                    }
                    eventType = 0;
                }
            }
            else if(eventType == 2){
                if (X >= 125 && X <= 184 && Y >= 597.0 && Y <= 627.0){
                    eventType = 0;
                    money += Config.blasterCost;
                }
                else {
                    Helper helper = new Helper();
                    int i = helper.xyToI(X - 92, Y - 92);
                    int j = helper.xyToJ(X - 92, Y - 92);
                    if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                        gameEntities.add(new BlasterTower(X - 92, Y - 92, enemies, bullets));
                    }
                    eventType = 0;
                }
            }
            else if(eventType == 3){
                if (X >= 213 && X <= 275 && Y >= 597.0 && Y <= 627.0){
                    eventType = 0;
                    money += Config.cannonCost;
                }
                else {
                    Helper helper = new Helper();
                    int i = helper.xyToI(X - 92, Y - 92);
                    int j = helper.xyToJ(X - 92, Y - 92);
                    if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                        gameEntities.add(new CannonTower(X - 92, Y - 92, enemies, bullets));
                    }
                    eventType = 0;
                }
            }
            else if(eventType == 4){
                if (X >= 305 && X <= 366 && Y >= 597.0 && Y <= 627.0){
                    eventType = 0;
                    money += Config.catapultCost;
                }
                else {
                    Helper helper = new Helper();
                    int i = helper.xyToI(X - 92, Y - 92);
                    int j = helper.xyToJ(X - 92, Y - 92);
                    if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                        gameEntities.add(new CatapultTower(X - 92, Y - 92, enemies, bullets));
                    }
                    eventType = 0;
                }
            }
        });
    }
    /*
    Note: Enemy Type: 1 - Boss, 2 - Normal, 3 - Smaller, 4 - Tanker
          Tower Type: 1 - Ballista, 2 - Blaster, 3 - Cannon, 4 - Catapult
     */

    private void saveGame() throws IOException {
        StringBuilder jsonString = new StringBuilder("{\"level\":" + level + ",\"money\":" + money + ",\"enemies\":[");
        boolean isFirst = true;
        for(var enemy : enemies){
            if(isFirst) {
                jsonString.append(enemy.toJsonStr());
                isFirst = false;
            }
            else{
                jsonString.append(",").append(enemy.toJsonStr());
            }
        }
        jsonString.append("],\"towers\":[");
        isFirst = true;
        for(var tower : gameEntities){
            if(tower instanceof Tower){
                if(isFirst) {
                    jsonString.append(((Tower) tower).toJsonStr());
                    isFirst = false;
                }
                else{
                    jsonString.append(",").append(((Tower) tower).toJsonStr());
                }
            }
        }
        jsonString.append("],\"enemyqueue\":[");
        isFirst = true;
        for(var enemy : enemyList){
            if(isFirst) {
                jsonString.append(enemy.toJsonStr());
                isFirst = false;
            }
            else{
                jsonString.append(",").append(enemy.toJsonStr());
            }
        }
        jsonString.append("]}");

        System.out.println(jsonString);
        PrintWriter writer = new PrintWriter("./src/main/java/savegame/saveGame.txt", StandardCharsets.UTF_8);
        writer.println(jsonString);
        writer.close();
    }

}

//}