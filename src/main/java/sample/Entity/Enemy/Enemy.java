package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import sample.DIRECTION;
import sample.GameEntity;

abstract public class Enemy extends GameEntity {
    protected double damage = 5;
    protected double speed = 5;
    protected int prize = 0;
    protected DIRECTION direction = DIRECTION.NORTH;
    protected boolean dead = false;

    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }
    public DIRECTION getDirection() {
        return direction;
    }
    public void setPrize(int prize) {
        this.prize = prize;
    }
    public int getPrize() {
        return prize;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
    public double getDamage() {
        return damage;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getSpeed() {
        return speed;
    }

    abstract public void render(GraphicsContext gc);
    abstract public void update();
}
