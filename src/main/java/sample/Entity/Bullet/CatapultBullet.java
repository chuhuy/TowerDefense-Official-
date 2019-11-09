package sample.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import sample.Config;

public class CatapultBullet extends Bullet{
    final String image = "towerDefense_CatapultBullet";

    public CatapultBullet() {
        setDamage(Config.catapultDamage);
        setSpeed(Config.catapultSpeed);
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update() {

    }
}
