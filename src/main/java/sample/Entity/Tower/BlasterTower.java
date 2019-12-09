package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.Entity.Bullet.BlasterBullet;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.Enemy;
import sample.GameStage;
import sample.Helper;

import java.util.List;

public class BlasterTower extends Tower {
    private final String baseLvl1 = "towerRound_bottomB_E";
    final String baseLvl2 = "towerRound_sampleC_E";
    final String baseLvl3 = "towerSquare_sampleE_E";
    final String blaster_E = "024";
    final String blaster_N = "025";
    final String blaster_S = "027";
    final String blaster_W = "026";
    double cooldown = Config.blasterFireRate;

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    public BlasterTower(double x, double y, List<Enemy> ene, List<Bullet> bu)
    {
        Helper helper = new Helper();
        setRange(Config.blasterRange);
        setFireRate(Config.blasterFireRate);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        setLevel(1);
        this.enemies = ene;
        this.bullets = bu;

        int i = helper.xyToI(x, y);
        int j = helper.xyToJ(x, y);
        double X = helper.ijToX(i, j);
        double Y = helper.ijToY(i, j);
        setX(X);
        setY(Y);
    }

    @Override
    public int getCost() {
        return Config.blasterCost;
    }

    @Override
    public void upgrade(GameStage stage) {
        if(level == 1 && stage.getMoney() >= Config.blasterUpgradeCost1) {
            this.setLevel(2);
            stage.setMoney(stage.getMoney() - Config.blasterUpgradeCost1);
        }
        else if(level == 2 && stage.getMoney() >= Config.blasterUpgradeCost2) {
            this.setLevel(3);
            stage.setMoney(stage.getMoney() - Config.blasterUpgradeCost2);
        }
    }

    /*
    public String directionSprite(Enemy enemy)
    {
        String sprite;
        if(enemy.getX() > this.getX() && enemy.getY() > this.getY()) sprite = "024";
        else if(enemy.getX() > this.getX() && enemy.getY() <= this.getY()) sprite = "025";
            else if(enemy.getX() <= this.getX() && enemy.getY() > this.getY()) sprite = "027";
                else sprite = "026";
         return sprite;
    }
     */

    @Override
    public void render(GraphicsContext gc) {
        switch (level) {
            case 1: {
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl1 + ".png"), x, y, width, height
                );
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + blaster_E + ".png"), x, y - 15, width, height
                );
                break;
            }
            case 2:{
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl2 + ".png"), x, y, width, height
                );
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + blaster_E + ".png"), x, y - 22, width, height
                );
                break;
            }
            case 3:{
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl3 + ".png"), x, y, width, height
                );
                gc.drawImage(
                        new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + blaster_E + ".png"), x, y - 22, width, height
                );
                break;
            }
        }
    }

    @Override
    public void fire(Enemy target) {
        bullets.add(new BlasterBullet(this.getX() , this.getY() , target));
    }

    @Override
    public void update() {
        this.updateTargetQueue(enemies);
        //System.out.println(this.enemiesQueue.size());
        if(!this.enemiesQueue.isEmpty()){
            if(cooldown == 0) {
                fire(enemiesQueue.peek());
                cooldown = Config.blasterFireRate;
            }
            else {
                cooldown --;
            }
        }
    }
}
