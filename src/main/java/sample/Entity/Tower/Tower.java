package sample.Entity.Tower;

import javafx.scene.canvas.GraphicsContext;
import sample.DIRECTION;
import sample.Entity.Enemy.Enemy;
import sample.GameEntity;
import sample.Helper;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


abstract public class Tower extends GameEntity{
    protected double range;
    protected double fireRate;
    protected Queue<Enemy> enemiesQueue = new LinkedList<Enemy>();
    protected DIRECTION direction = DIRECTION.NORTH;
    //protected int level;
    //final protected int MAX_LEVEL = 3;

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public void updateTargetQueue(List<Enemy> enemies)
    {
        enemiesQueue.clear();
        Helper helper = new Helper();
        for(Enemy enemy : enemies){
            if(helper.getDistanceInIJ(this, enemy) <= this.getRange()){
                enemiesQueue.add(enemy);
            }
        }
    }

    public abstract void fire(Enemy target);

    //public int getLevel() { return level; }

    //public void setLevel(int level) { this.level = level; }

    abstract public void render(GraphicsContext gc);
    abstract public void update();
    public String toJsonStr(){
        int type = 0;
        if(this instanceof BallistaTower){
            type = 1;
        }
        else if(this instanceof BlasterTower){
            type = 2;
        }
        else if(this instanceof CannonTower){
            type = 3;
        }
        else if(this instanceof CatapultTower){
            type = 4;
        }
        return "{\"type\":" + type + ",\"x\":" + x + ",\"y\":" + y + "}";
    }
}
