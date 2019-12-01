package sample;

import javafx.scene.canvas.GraphicsContext;
import sample.Entity.Enemy.Enemy;

abstract public class GameEntity {
    protected double x ;
    protected double y;
    protected double width;
    protected double height;


    //getter and setter

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getHeight() {
        return height;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getWidth() {
        return width;
    }

    abstract public void render(GraphicsContext gc);
    abstract public void update();
}
