package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import sample.Config;
import sample.Entity.Bullet.BallistaBullet;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.Enemy;
import sample.GameStage;
import sample.Helper;

import java.util.List;

public class BallistaTower extends Tower{
    final String baseLvl1 = "towerRound_bottomB_E";
    final String baseLvl2 = "towerRound_sampleC_E";
    final String baseLvl3 = "towerSquare_sampleE_E";
    final String ballista_E = "023";
    double cooldown = Config.ballistaFireRate;

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    @Override
    public int getUpgradeCost() {
        if(this.level == 1) return Config.ballistaUpgradeCost1;
        else if (this.level == 2) return Config.ballistaUpgradeCost2;
        else return 0;
    }

    public BallistaTower(double x, double y, List<Enemy> ene, List<Bullet> bu)
    {
        Helper helper = new Helper();
        setRange(Config.ballistaRange);
        setFireRate(Config.ballistaFireRate);
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
    public BallistaTower(double x, double y, List<Enemy> ene, List<Bullet> bu, int level)
    {
        Helper helper = new Helper();
        setRange(Config.ballistaRange);
        setFireRate(Config.ballistaFireRate);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        setLevel(level);
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
        return Config.ballistaCost;
    }

    @Override
    public void upgrade(GameStage stage) {
        if(level == 1 && stage.getMoney() >= Config.ballistaUpgradeCost1) {
        //if(level == 1 && stage.getMoney() >= this.getUpgradeCost1()) {
            this.setLevel(2);
            stage.setMoney(stage.getMoney() - Config.ballistaUpgradeCost1);
        }
        else if(level == 2 && stage.getMoney() >= Config.ballistaUpgradeCost2) {
            this.setLevel(3);
            stage.setMoney(stage.getMoney() - Config.ballistaUpgradeCost2);
        }
        //if(level < MAX_LEVEL) level++;
    }

    @Override
    public void fire(Enemy target) {
        bullets.add(new BallistaBullet(this.getX() , this.getY() , target));
        MediaPlayer med = new MediaPlayer(Config.ballista);
        med.setAutoPlay(true);
    }

    @Override
    public void render(GraphicsContext gc) {
        switch (level) {
            case 1: {
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl1 + ".png"), x, y, width, height);
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + ballista_E + ".png"), x, y - 15, width, height);
                break;
            }
            case 2:{
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl2 + ".png"), x, y, width, height);
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + ballista_E + ".png"), x, y - 22, width, height);
                break;
            }
            case 3:{
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl3 + ".png"), x, y, width, height);
                gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + ballista_E + ".png"), x, y - 22, width, height);
                break;
            }
        }
    }

    @Override
    public void update() {
        this.updateTargetQueue(enemies);
        //System.out.println(this.enemiesQueue.size());
        if(!this.enemiesQueue.isEmpty()){
            if(cooldown == 0) {
                fire(enemiesQueue.peek());
                cooldown = Config.ballistaFireRate;
            }
            else {
                cooldown --;
            }
        }
    }
}
