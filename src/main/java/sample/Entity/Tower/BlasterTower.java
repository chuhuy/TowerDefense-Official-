package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.Entity.Bullet.BlasterBullet;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.Enemy;
import sample.Helper;

import java.util.List;

public class BlasterTower extends Tower {
    private final String baseLvl1 = "towerRound_sampleE_E";
    final String baseLvl2 = "towerSquare_sampleE_E";

    final String tower_E = "024";
    final String tower_N = "025";
    final String tower_S = "027";
    final String tower_W = "026";

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    public BlasterTower(double x, double y, List<Enemy> ene, List<Bullet> bu)
    {

        Helper helper = new Helper();
        setRange(Config.blasterRange);
        setFireRate(Config.blasterFireRate);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        this.enemies = ene;
        this.bullets = bu;


        int i = helper.xyToI(x, y);
        int j = helper.xyToJ(x, y);
        double X = helper.ijToX(i, j);
        double Y = helper.ijToY(i, j);
        setX(X);
        setY(Y);
    }

    public String directionSprite(Enemy enemy)
    {
        String sprite;
        if(enemy.getX() > this.getX() && enemy.getY() > this.getY()) sprite = "024";
        else if(enemy.getX() > this.getX() && enemy.getY() <= this.getY()) sprite = "025";
            else if(enemy.getX() <= this.getX() && enemy.getY() > this.getY()) sprite = "027";
                else sprite = "026";
         return sprite;
    }

    @Override
        public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl1 +".png"), x, y, width, height
        );
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + tower_E +".png"), x, y - 15, width, height
        );
    }

    @Override
    public void fire(Enemy target) {
        bullets.add(new BlasterBullet(this.getX() , this.getY() , target));
    }

    @Override
    public void update() {
        this.updateTargetQueue(enemies);
        System.out.println(this.enemiesQueue.size());
        if(!this.enemiesQueue.isEmpty()){
            fire(enemiesQueue.peek());
        }
    }
}
