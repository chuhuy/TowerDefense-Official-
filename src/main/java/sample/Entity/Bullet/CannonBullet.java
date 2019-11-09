package sample.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import sample.Config;

public class CannonBullet extends Bullet{
    final String image = "towerDefense_CannonBullet";

    public CannonBullet() {
        setDamage(Config.cannonDamage);
        setSpeed(Config.cannonSpeed);
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update() {

    }
}
