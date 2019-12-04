package sample.Entity.Enemy;

import javafx.scene.canvas.GraphicsContext;
import sample.DIRECTION;
import sample.GameEntity;
import sample.Helper;

abstract public class Enemy extends GameEntity {
    private double health = 100;
    private double damage = 5;
    private double speed = 5;
    private int prize;
    private DIRECTION direction = DIRECTION.NORTH;
    private boolean dead = false;
    private boolean survive = false;

    public void setHealth(double health) {
        this.health = health;
    }
    public double getHealth() {
        return health;
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) { this.dead = dead; }
    public boolean isSurvive() { return survive; }
    public void setSurvive(boolean survive) { this.survive = survive; }
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
    public String toJsonStr(){
        int type = 0;
        if(this instanceof BossEnemy){
            type = 1;
        }
        else if(this instanceof NormalEnemy){
            type = 2;
        }
        else if(this instanceof SmallerEnemy){
            type = 3;
        }
        else if(this instanceof TankerEnemy){
            type = 4;
        }
        return "{\"type\":" + type + ",\"health\":" + this.health + ",\"x\":" + x + ",\"y\":" + y + ",\"direction\":\"" + this.direction + "\"}";
    }
}
