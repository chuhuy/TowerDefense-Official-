package sample.Entity.Spawner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.GameEntity;

public class Spawner extends GameEntity {

    public Spawner(int targetPosX, int targetPosY){
        setX((targetPosX-targetPosY)* Config.pixels/1.55 + Config.SCREEN_WIDTH/2 - 80);
        setY((targetPosY+targetPosX)*Config.pixels/3.1);
        setWidth(Config.pixels * 5);
        setHeight(Config.pixels * 5);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/spawn_hole.png"),
                this.getX(), this.getY(), this.width, this.height
        );
    }

    public void update(){

    }
}
