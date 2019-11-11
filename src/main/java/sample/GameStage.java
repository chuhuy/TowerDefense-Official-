package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.Enemy;
import sample.Entity.Enemy.NormalEnemy;
import sample.Entity.Enemy.TankerEnemy;
import sample.Entity.Spawner.Spawner;
import sample.Entity.Target;
import sample.Entity.Tower.BallistaTower;
import sample.Entity.Tower.BlasterTower;
import sample.Entity.Tower.CannonTower;
import sample.Entity.Tower.CatapultTower;

import java.util.ArrayList;
import java.util.List;


public class GameStage{
    private int level = 1;
    private int eventType = 0;
    private List<GameEntity> gameEntities = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    private GameEntity spawner = new Spawner(3, 19);
    private GameEntity target = new Target(18,0);

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    GameStage(int level){
        this.level = level;
        enemies.add(new NormalEnemy(spawner.getX(),spawner.getY()));
        enemies.add(new TankerEnemy(spawner.getX(), spawner.getY()));

        gameEntities.add(spawner);
        gameEntities.add(target);
    }

    private void renderMap(GraphicsContext gc){
        gc.drawImage(new Image("file:src/main/java/images/background.jpg"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        gc.drawImage(new Image("file:src/main/java/maps/map2.png"),
                0, 0, 1200 , 545);
    }

    private void renderBar(GraphicsContext gc){
        gc.drawImage(
                new Image("file:src/main/java/images/bar.png"),
                0, Config.pixels*17, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT-Config.pixels*17
        );
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
        for(Enemy enemy : enemies){
            enemy.render(gc);
        }
    }
    private void renderBullet(GraphicsContext gc){
        for(Bullet bullet : bullets){
            bullet.render(gc);
        }
    }
    private void renderTower(GraphicsContext gc){
        for(GameEntity gameEntity:gameEntities){
            gameEntity.render(gc);
        }
    }
    public void render(GraphicsContext gc){
        renderMap(gc);
        renderBar(gc);
        renderTower(gc);
        renderEnemy(gc);
        renderBullet(gc);

    }
    public void update(){
        //Enemy Update
        for(GameEntity gameEntity : gameEntities){
            gameEntity.update();
        }

        enemies.removeIf(Enemy::isDead);
        for(Enemy enemy : enemies){
            enemy.update();
        }


        bullets.removeIf(Bullet::isDisposed);
        for(Bullet bullet : bullets){
            bullet.update();
        }
    }

    void event(Scene scene){
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            double X = mouseEvent.getX();
            double Y = mouseEvent.getY();
            if(eventType == 0) {
                if (Y >= 597.0 && Y <= 627.0) {
                    if (X >= 31 && X <= 93) {
                        eventType = 1;
                    }
                    if (X >= 125 && X <= 184) {
                        eventType = 2;
                    }
                    if (X >= 213 && X <= 275) {
                        eventType = 3;
                    }
                    if (X >= 305 && X <= 366) {
                        eventType = 4;
                    }
                }
            }
            else if(eventType == 1){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19){
                    gameEntities.add(new BallistaTower(X - 92, Y - 92));
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
                    gameEntities.add(new CannonTower(X - 92, Y - 92));
                }
                eventType = 0;
            }
            else if(eventType == 4){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new CatapultTower(X - 92, Y - 92));
                }
                eventType = 0;
            }
        });
    }

}
