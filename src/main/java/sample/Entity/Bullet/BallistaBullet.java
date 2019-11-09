package sample.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import sample.Config;

public class BallistaBullet extends Bullet {
    final String image = "150";

    public BallistaBullet() {
        setDamage(Config.ballistaDamage);
        setSpeed(Config.ballistaSpeed);
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update() {

    }
}