package sample.Entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.GameEntity;

public class Target extends GameEntity {
    private double health = 100;
    final String image = "044";

    public void setHealth(double health) {
        this.health = health;
    }

    public Target(int targetPosX, int targetPosY)
    {
        setX((targetPosX-targetPosY)*Config.pixels/1.55 + Config.SCREEN_WIDTH/2 - 81);
        setY((targetPosY+targetPosX)*Config.pixels/3.2);
        setHealth(100);
        setWidth(Config.pixels * 5);
        setHeight(Config.pixels * 5);

    }

    public double getHealth() {
        return this.health;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
            new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + image + ".png"),
            this.getX(), this.getY(), this.width, this.height
        );
    }

    @Override
    public void update() {

    }

    public boolean isDestroyed()
    {
        if (health <= 0) return false;
        return true;
    }
}
