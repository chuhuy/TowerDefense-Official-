package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Bullet.CannonBullet;
import sample.Entity.Enemy.Enemy;
import sample.Helper;

import java.util.List;

public class CannonTower extends Tower{
    final String baseLvl1 = "towerRound_bottomB_E";
    final String baseLvl2 = "towerSquare_sampleE_E";
    final String cannon_E = "031";
    double cooldown = Config.cannonFireRate;

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    public CannonTower(double x, double y, List<Enemy> ene, List<Bullet> bu)
    {
        Helper helper = new Helper();
        setRange(Config.cannonRange);
        setFireRate(Config.cannonFireRate);
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

    @Override
    public void fire(Enemy target) {
        bullets.add(new CannonBullet(this.getX() , this.getY() , target));
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl1 +".png"), x, y, width, height
        );
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + cannon_E +".png"), x, y - 14, width, height
        );
    }

    @Override
    public void update() {
        this.updateTargetQueue(enemies);
        System.out.println(this.enemiesQueue.size());
        if(!this.enemiesQueue.isEmpty()){
            if(cooldown == 0) {
                fire(enemiesQueue.peek());
                cooldown = Config.cannonFireRate;
            }
            else {
                cooldown --;
            }
        }
    }
}
