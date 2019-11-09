package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.Entity.Enemy.Enemy;
import sample.Helper;

public class CannonTower extends Tower{
    final String baseLvl2 = "towerRound_sampleE_E";

    final String image = "weapon_cannon_E";

    public CannonTower(double x, double y)
    {
        setRange(Config.cannonRange);
        setFireRate(Config.cannonFireRate);

        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        Helper helper = new Helper();
        int i = helper.xyToI(x, y);
        int j = helper.xyToJ(x, y);
        double X = helper.ijToX(i, j);
        double Y = helper.ijToY(i, j);
        setX(X);
        setY(Y);
    }

    @Override
    public void fire(Enemy target) {

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/" + baseLvl2 +".png"), x, y, width, height
        );
    }

    @Override
    public void update() {

    }
}
