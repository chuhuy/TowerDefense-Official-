package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.*;
import sample.Entity.Spawner.Spawner;
import sample.Entity.Target;
import sample.Entity.Tower.BallistaTower;
import sample.Entity.Tower.BlasterTower;
import sample.Entity.Tower.CannonTower;
import sample.Entity.Tower.CatapultTower;

import java.io.IOException;
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
    private boolean moneyChange = true;

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
    /*
    public void getEnemy() {
        Scanner sc = new Scanner(Config.enemylvl1);
        while(sc.hasNext()){
            switch (sc.next()){
                case "1": enemyList.add(new NormalEnemy(sp.getX(),sp.getY(), map));
                case "2": enemyList.add(new SmallerEnemy(sp.getX(),sp.getY(), map));
                case "3": enemyList.add(new TankerEnemy(sp.getX(),sp.getY(), map));
                case "4": enemyList.add(new BossEnemy(sp.getX(),sp.getY(), map));
            }
        }
    }

     */

    GameStage(int level){
        /*
        String[][] map = new String[20][20];
        try{
            Helper helper = new Helper();
            map = helper.getMapFromText(1 );
        } catch (IOException e) {
            e.printStackTrace();
        }

         */

        this.level = level;
        this.getMap();
        //this.getEnemy();
        enemyList.add(new NormalEnemy(sp.getX(),sp.getY(), map));
        enemyList.add(new NormalEnemy(sp.getX(),sp.getY(), map));
        enemyList.add(new TankerEnemy(sp.getX(),sp.getY(), map));
        /*
        enemies.add(new TankerEnemy(spawner.getX(), spawner.getY(), map));
        enemies.add(new SmallerEnemy(spawner.getX(),spawner.getY(), map));
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
        gc.drawImage(
                new Image("file:src/main/java/images/setting.png"),
                5, 5, 40, 40
        );
        gc.drawImage(
                new Image("file:src/main/java/images/cancel.png"),
                50, 12, 25, 25
        );
        for(int i = 1; i <= 4; i++) {
            gc.drawImage(
                    new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/00" + i + ".png"),
                    i*30 + (i-1)*61, Config.pixels * 17 + 50, 61, 30
            );
        }
    }
    private void renderEnemy(GraphicsContext gc){
        if(!enemyList.isEmpty()) {
            if (waveInterval == 0) {
                enemies.add(enemyList.poll());
                waveInterval = Config.normalInterval;
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
        //if(moneyChange) {
            int x = 850;
            String m = toString().valueOf(money);
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 600, 50, 50);
            for (int i = 0; i < m.length(); i++) {
                x += 25;
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 600, 50, 50);
            }
            //moneyChange = false;
        //}
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
        if(enemies.get(0).isDead()) {
            moneyChange = true;
            money += enemies.get(0).getPrize();
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
                    if (X >= 31 && X <= 93) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/001.png")));
                        eventType = 1;
                    }
                    if (X >= 125 && X <= 184) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/002.png")));
                        eventType = 2;
                    }
                    if (X >= 213 && X <= 275) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/003.png")));
                        eventType = 3;
                    }
                    if (X >= 305 && X <= 366) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/004.png")));
                        eventType = 4;
                    }
                }
            }
            else if(eventType == 1){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19){
                    gameEntities.add(new BallistaTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 2){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new BlasterTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 3){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new CannonTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 4){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new CatapultTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
        });
    }

}

//}