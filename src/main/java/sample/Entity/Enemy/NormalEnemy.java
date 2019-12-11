package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sample.Config;
import sample.DIRECTION;

public class NormalEnemy extends Enemy {
    private String[][] map;
    public NormalEnemy(double x, double y, String[][] map, int health, DIRECTION direction){
        setHealth(health);
        setDirection(direction);
        setDamage(Config.normalDamage);
        setSpeed(Config.normalSpeed);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        setPrize(50);
        setX(x);
        setY(y);
        this.map = map;
    }
    public NormalEnemy(double x, double y, String[][] map){
        setHealth(Config.normalHealth);
        setDamage(Config.normalDamage);
        setSpeed(Config.normalSpeed);
        setHeight(Config.pixels * 5);
        setWidth(Config.pixels * 5);
        setPrize(50);
        setX(x);
        setY(y);
        this.map = map;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(
                new Image("file:src/main/java/TowerDefense/AssetsKit_3/Isometric/036.png"), x, y, width, height
        );
        //gc.fillOval(getX()+92, getY()+92, 10, 10);
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
                    case "384": case "082":
                        if (this.getDirection() == DIRECTION.NORTH)
                            this.setDirection(DIRECTION.EAST);
                        else if (this.getDirection() == DIRECTION.WEST)
                            this.setDirection(DIRECTION.SOUTH);
                        break;
                    case "385": case "074":
                        if (this.getDirection() == DIRECTION.EAST)
                            this.setDirection(DIRECTION.SOUTH);
                        else if (this.getDirection() == DIRECTION.NORTH)
                            this.setDirection(DIRECTION.WEST);
                        break;
                    case "386": case "055":
                        if (this.getDirection() == DIRECTION.SOUTH)
                            this.setDirection(DIRECTION.WEST);
                        else if (this.getDirection() == DIRECTION.EAST)
                            this.setDirection(DIRECTION.NORTH);
                        break;
                    case "387": case "061":
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
            this.setSurvive(true);
        }

    }


}
