package sample.Entity.Bullet;

import javafx.scene.canvas.GraphicsContext;
import sample.Entity.Enemy.Enemy;
import sample.GameEntity;

abstract public class Bullet extends GameEntity {
    private double speed;
    private double damage;
    private boolean disposed = false;
    protected double x;
    protected double y;
    protected Enemy target;

    public boolean isDisposed() {
        return disposed;
    }
    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setTarget(Enemy target) {
        this.target = target;
    }
    public Enemy getTarget() {
        return target;
    }
    @Override
    public void setX(double x) {
        this.x = x;
    }
    @Override
    public double getX() {
        return x;
    }
    @Override
    public void setY(double y) {
        this.y = y;
    }
    @Override
    public double getY() {
        return y;
    }
    public double getDamage() {
        return damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public boolean collapse(Enemy target){
        if(Math.abs(this.getX() - target.getX()) <= 15 && Math.abs(this.getY() - target.getY()) <= 10) return true;
        return false;
    }


    abstract public void render(GraphicsContext gc);
    abstract public void update();
}
