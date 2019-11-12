package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.DIRECTION;

public class BossEnemy extends Enemy {
    private String[][] map = new String[20][20];

    public BossEnemy(double x, double y, String[][] map){
        setHealth(Config.bossHealth);
        setDamage(Config.bossDamage);
        setSpeed(Config.bossSpeed);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        setX(x);
        setY(y);
        this.map = map;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/037.png"), x, y, width, height
        );
    }

    @Override
    public void update() {
        int i =  (int)((3.1 * (this.getY() + 10) - 1.55 * (this.getX() - 520)) / 64 + 0.5);
        int j =  (int)((3.1 * (this.getY() + 5) + 1.55 * (this.getX() - 520)) / 64 + 0.5);
        if(i >= 0 && i <= 19 && j >= 0 && j <=19) {
            if(this.getHealth() <= 0){
                this.setDead(true);
            }
            else {
                switch (map[i][j]) {
                    case "384":
                        if (this.getDirection() == DIRECTION.NORTH)
                            this.setDirection(DIRECTION.EAST);
                        else if (this.getDirection() == DIRECTION.WEST)
                            this.setDirection(DIRECTION.SOUTH);
                        break;
                    case "385":
                        if (this.getDirection() == DIRECTION.EAST)
                            this.setDirection(DIRECTION.SOUTH);
                        else if (this.getDirection() == DIRECTION.NORTH)
                            this.setDirection(DIRECTION.WEST);
                        break;
                    case "386":
                        if (this.getDirection() == DIRECTION.SOUTH)
                            this.setDirection(DIRECTION.WEST);
                        else if (this.getDirection() == DIRECTION.EAST)
                            this.setDirection(DIRECTION.NORTH);
                        break;
                    case "387":
                        if (this.getDirection() == DIRECTION.WEST)
                            this.setDirection(DIRECTION.NORTH);
                        else if (this.getDirection() == DIRECTION.SOUTH)
                            this.setDirection(DIRECTION.EAST);
                        break;
                }

                if (this.getDirection() == DIRECTION.NORTH) {
                    this.setX(this.getX() + this.getSpeed());
                    this.setY(this.getY() - this.getSpeed() * 0.5);
                } else if (this.getDirection() == DIRECTION.EAST) {
                    this.setX(this.getX() + this.getSpeed());
                    this.setY(this.getY() + this.getSpeed() * 0.5);
                } else if (this.getDirection() == DIRECTION.SOUTH) {
                    this.setX(this.getX() - this.getSpeed());
                    this.setY(this.getY() + this.getSpeed() * 0.5);
                } else {
                    this.setX(this.getX() - this.getSpeed());
                    this.setY(this.getY() - this.getSpeed() * 0.5);
                }
            }
        }
        else{
            this.setDead(true);
        }

    }
}
