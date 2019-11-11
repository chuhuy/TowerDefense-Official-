package sample.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.Entity.Enemy.Enemy;

public class CatapultBullet extends Bullet{
    private double T_x ;
    private double T_y ;
    private double E_x ;
    private double E_y ;
    private int lastingTime;

    public CatapultBullet(double x, double y, Enemy target) {
        setDamage(Config.catapultDamage);
        setSpeed(Config.catapultSpeed);
        setX(x + 90);
        setY(y + 90);
        setWidth(Config.bulletSize);
        setHeight(Config.bulletSize);
        setTarget(target);
        lastingTime = Config.catapultLastingTime;

        T_x = x;
        T_y = y;
        E_x = target.getX();
        E_y = target.getY();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_1/shapes/137.png"), x, y, width, height
        );
    }

    @Override
    public void update() {
        if(lastingTime == 0){
            this.setDisposed(true);
            target.setHealth(target.getHealth() - this.getDamage());
        }
        else {
            this.setX(this.getX() - (T_x - E_x) * this.getSpeed() / 10);
            this.setY(this.getY() - (T_y - E_y) * this.getSpeed() / 20);
            lastingTime--;
        }
    }
}
