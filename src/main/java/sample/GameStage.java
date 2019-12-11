package sample;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Group;
//import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
//import javafx.scene.paint.ImagePattern;
//import javafx.scene.shape.Circle;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Entity.Bullet.Bullet;
import sample.Entity.Enemy.*;
import sample.Entity.Spawner.Spawner;
import sample.Entity.Target;
import sample.Entity.Tower.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class GameStage extends MyStage{

    private Stack<MyStage> stageStack = new Stack<>();
    private int level;
    private int eventType = 0;
    private List<GameEntity> gameEntities = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    private Queue<Enemy> enemyList = new LinkedList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private int money = 1000;
    private int playerHealth = 1000;
    private double waveInterval = Config.normalInterval;
    private boolean isAuto = false;
    private int buildRate = Config.towerRefreshRate;

    private String[][] map = new String[20][20];

    private GameEntity sp = new Spawner(3, 19);
    private GameEntity target = new Target(18,0);

    private int getLevel() {
        return level;
    }
    //public void setLevel(int level) { this.level = level; }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    private void getMap() {
        try{
            Helper helper = new Helper();
            map = helper.getMapFromText(this.getLevel());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getEnemy() {
        String m = Config.enemylvl1;
        for(int i=0; i < m.length(); i++) {
            if(m.charAt(i) == '1') enemyList.add(new NormalEnemy(sp.getX(), sp.getY(), map));
            else if(m.charAt(i) == '2') enemyList.add(new SmallerEnemy(sp.getX(), sp.getY(), map));
                else if(m.charAt(i) == '3') enemyList.add(new TankerEnemy(sp.getX(), sp.getY(), map));
                    else if(m.charAt(i) == '4') enemyList.add(new BossEnemy(sp.getX(), sp.getY(), map));
        }
    }

    //Init game from save game file
    GameStage(Stack<MyStage> stageStack) throws IOException {
        Helper helper = new Helper();
        this.stageStack = stageStack;
        String jsonStr = helper.loadJson();
        JSONObject json = new JSONObject(jsonStr);

        level = json.getInt("level");
        money = json.getInt("money");
        this.getMap();
        JSONArray enemyJson = json.getJSONArray("enemies");
        for(int i = 0; i < enemyJson.length(); i++){
            double x = enemyJson.getJSONObject(i).getDouble("x");
            double y = enemyJson.getJSONObject(i).getDouble("y");
            int health = enemyJson.getJSONObject(i).getInt("health");
            String direction = enemyJson.getJSONObject(i).getString("direction");

            switch (enemyJson.getJSONObject(i).getInt("type")){
                case 1:{
                    enemies.add(new BossEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                    gameEntities.add(new BossEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 2:{
                    enemies.add(new NormalEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                    gameEntities.add(new NormalEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 3:{
                    enemies.add(new SmallerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                    gameEntities.add(new SmallerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 4:{
                    enemies.add(new TankerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                    gameEntities.add(new TankerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
            }
        }
        JSONArray enemyQueue = json.getJSONArray("enemyqueue");
        for(int i = 0; i < enemyQueue.length(); i++){
            double x = enemyQueue.getJSONObject(i).getDouble("x");
            double y = enemyQueue.getJSONObject(i).getDouble("y");
            int health = enemyQueue.getJSONObject(i).getInt("health");
            String direction = enemyQueue.getJSONObject(i).getString("direction");

            switch (enemyQueue.getJSONObject(i).getInt("type")){
                case 1:{
                    enemyList.add(new BossEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 2:{
                    enemyList.add(new NormalEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 3:{
                    enemyList.add(new SmallerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
                case 4:{
                    enemyList.add(new TankerEnemy(x, y, map, health, DIRECTION.valueOf(direction)));
                }
                break;
            }
        }
        JSONArray tower = json.getJSONArray("towers");
        for(int i = 0; i < tower.length(); i++){
            double x = tower.getJSONObject(i).getDouble("x");
            double y = tower.getJSONObject(i).getDouble("y");
            int level = tower.getJSONObject(i).getInt("level");

            switch (tower.getJSONObject(i).getInt("type")){
                case 1:{
                    gameEntities.add(new BallistaTower(x, y, enemies, bullets, level));
                }
                break;
                case 2:{
                    gameEntities.add(new BlasterTower(x, y, enemies, bullets, level));
                }
                break;
                case 3:{
                    gameEntities.add(new CannonTower(x, y, enemies, bullets, level));
                }
                break;
                case 4:{
                    gameEntities.add(new CatapultTower(x, y, enemies, bullets, level));
                }
                break;
            }
        }

        gameEntities.add(sp);
        gameEntities.add(target);
    }

    //Init game stage from the beginning
    GameStage(int level, Stack<MyStage> stageStack){
        this.stageStack = stageStack;
        this.level = level;
        this.getMap();
        this.getEnemy();
        gameEntities.add(sp);
        gameEntities.add(target);
    }

    private void renderMap(GraphicsContext gc){
        gc.drawImage(new Image("file:src/main/java/images/background.png"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        gc.drawImage(new Image("file:src/main/java/maps/map" + this.level + ".png"),
                0, 0, 1200 , 545);
    }

    private void renderBar(GraphicsContext gc, Group root){
        Button auto = new Button();
        auto.setMaxSize(40, 35);
        auto.setMinSize(40, 35);
        auto.setLayoutX(5);
        auto.setLayoutY(5);
        auto.setStyle("-fx-background-color: transparent;");
        root.getChildren().add(auto);

        auto.setOnMouseClicked(some ->{
            isAuto = !isAuto;
            System.out.println("Auto: " + isAuto);
        });
        gc.drawImage(
                new Image("file:src/main/java/images/auto.png"),
                5, 5, 40, 35
        );
        gc.drawImage(
                new Image("file:src/main/java/images/cancel.png"),
                50, 12, 25, 25
        );
        for(int i = 1; i <= 4; i++) {
            gc.drawImage(
                    new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/00" + i + ".png"),
                    i*30 + (i-1)*61, Config.pixels * 17 + 50, 61, 30
            );
        }

        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_1/buttons/DefineButton2_221/1.png"), 900, 500, 65, 30);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_1/buttons/DefineButton2_217/1.png"), 900, 535, 40, 30);

        int x = 15;
        String m = toString().valueOf(Config.ballistaCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 105;
        m = toString().valueOf(Config.blasterCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 210;
        m = toString().valueOf(Config.cannonCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }

        x = 300;
        m = toString().valueOf(Config.catapultCost);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 625, 35, 35);
        for (int i = 0; i < m.length(); i++) {
            x += 17;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 625, 35, 35);
        }


    }

    private void renderBullet(GraphicsContext gc){
        for(Bullet bullet : bullets){
            bullet.render(gc);
        }
    }

    /*
    private void renderEnemy(GraphicsContext gc){
        if(!enemyList.isEmpty()) {
            if (waveInterval == 0) {
                enemies.add(enemyList.poll());
                //gameEntities.add(enemyList.poll());
                waveInterval = Config.normalInterval;
                //System.out.println(enemyList.size());
            } else waveInterval--;
        }

        for(int i=0; i<enemies.size()-1; i++){
            for (int j=i+1; j<enemies.size(); j++){
                if(enemies.get(i).getY() > enemies.get(j).getY()){
                    Enemy tmp = enemies.get(i);
                    enemies.set(i, enemies.get(j));
                    enemies.set(j, tmp);
                }
            }
        }



        if(!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                enemy.render(gc);
            }
        }
    }
    */

    /*
    private void renderTower(GraphicsContext gc) {
        //for (GameEntity gameEntity : gameEntities) {
            //gameEntity.render(gc);
        //}

        for(int i=0; i<towers.size()-1; i++){
            for (int j=i+1; j<towers.size(); j++){
                if(towers.get(i).getY() > towers.get(j).getY()){
                    Tower tmp = towers.get(i);
                    towers.set(i, towers.get(j));
                    towers.set(j, tmp);
                }
            }
        }

        for(Tower tower : towers){
            tower.render(gc);
        }
    }

     */


    private void renderEntities(GraphicsContext gc){

        if(!enemyList.isEmpty()) {
            if (waveInterval == 0) {
                //enemies.add(enemyList.poll());
                enemies.add(enemyList.peek());
                gameEntities.add(enemyList.poll());
                waveInterval = Config.normalInterval;
                //System.out.println(enemyList.size());
            } else waveInterval--;
        }


        for(int i=0; i<gameEntities.size(); i++){
            for(int j=i+1; j<gameEntities.size(); j++){
                if(gameEntities.get(i).getY() > gameEntities.get(j).getY()){
                    GameEntity tmp = gameEntities.get(i);
                    gameEntities.set(i, gameEntities.get(j));
                    gameEntities.set(j, tmp);
                }
            }
        }




        /*
        if(!enemyList.isEmpty()) {
            if (waveInterval == 0) {
                if(!enemies.isEmpty()) {
                    for (int i = 0; i < enemies.size(); i++) {
                        //assert enemyList.peek() != null;//Để tạm đây nhá, máy nó sửa đấy
                        if (enemyList.peek().getY() >= enemies.get(i).getY()) {
                            enemies.add(i, enemyList.poll());
                        }
                    }
                }
                else enemies.add(enemyList.poll());
                //gameEntities.add(enemyList.poll());
                waveInterval = Config.normalInterval;
                //System.out.println(enemyList.size());
            } else waveInterval--;
        }

        if(enemies.size() >= 2) {
            for (int i = 0; i < enemies.size() - 1; i++) {
                for (int j = i + 1; j < enemies.size(); j++) {
                    if (enemies.get(i).getY() > enemies.get(j).getY()) {
                        Enemy tmp = enemies.get(i);
                        enemies.set(i, enemies.get(j));
                        enemies.set(j, tmp);
                    }
                }
            }
        }


        if(towers.size() >= 2) {
            for (int i = 0; i < towers.size() - 1; i++) {
                for (int j = i + 1; j < towers.size(); j++) {
                    if (towers.get(i).getY() > towers.get(j).getY()) {
                        Tower tmp = towers.get(i);
                        towers.set(i, towers.get(j));
                        towers.set(j, tmp);
                    }
                }
            }
        }



        int Ecount = 0, Tcount = 0;
        if(!enemies.isEmpty()) {
            if(towers.isEmpty()){
                gameEntities.addAll(enemies);
            }
            else {
                while (Ecount < enemies.size() && Tcount < towers.size()){
                    if (enemies.get(Ecount).getY() >= towers.get(Tcount).getY()) {
                        gameEntities.add(towers.get(Tcount));
                        Tcount++;
                    } else {
                        gameEntities.add(enemies.get(Ecount));
                        Ecount++;
                    }
                }
                while(Ecount < enemies.size()){
                    gameEntities.add(enemies.get(Ecount));
                    Ecount++;
                }
                while(Tcount < towers.size()){
                    gameEntities.add(towers.get(Tcount));
                    Tcount++;
                }
            }
        }
        */

        for (GameEntity gameEntity : gameEntities) {
            gameEntity.render(gc);
        }
    }

    private void renderMoney(GraphicsContext gc){
        int x = 20;
        String m = toString().valueOf(money);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_money.png"), x, 500, 50, 50);
        for (int i = 0; i < m.length(); i++) {
            x += 25;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 500, 50, 50);
        }
    }

    public void renderHealth(GraphicsContext gc){
        int x = 800;
        String m = toString().valueOf(playerHealth);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_health(temporary).png"), x, 41, 45, 45);
        for (int i = 0; i < m.length(); i++) {
            x += 30;
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_digit" + m.charAt(i) + ".png"), x, 30, 60, 60);
        }
    }

    @Override
    public void render(GraphicsContext gc, Group root){
        renderMap(gc);
        renderBar(gc, root);
        renderEntities(gc);
        //renderTower(gc);
        //renderEnemy(gc);
        renderBullet(gc);
        renderMoney(gc);
        renderHealth(gc);
    }

    private void autoBuild(){
        int I = 0;
        int J = 0;
        int[][] quantities = new int[20][20];
        for(int j = 0; j < 20; j++){
            for(int i = 0; i < 20; i++){
                quantities[i][j] = 0;
            }
        }
        int max = 0;
        for(var enemy : enemies){
            Helper helper = new Helper();
            int i = helper.xyToI(enemy.x, enemy.y);
            int j = helper.xyToJ(enemy.x, enemy.y);

            int[] _i = {1, -1, 0, 0};
            int[] _j = {0, 0 , 1, -1};
            for(int k = 0; k < 4; k++){
                if(i + _i[k] >= 0 && i + _i[k] <= 19 && j + _j[k] >= 0 && j + _j[k] <= 19){
                    if(map[i + _i[k]][j + _j[k]].equals("418")) quantities[i + _i[k]][j + _j[k]]++;
                }
            }
        }
        for(int j = 0; j < 20; j++){
            for(int i = 0; i < 20; i++){
                if(quantities[i][j] > max) {
                    max = quantities[i][j];
                    I = i;
                    J = j;
                }
            }
        }
        if(max != 0) {
            Helper helper = new Helper();
            double x = helper.ijToX(I, J);
            double y = helper.ijToY(I, J);
            if (money >= 200) {
                gameEntities.add(new BlasterTower(x, y, enemies, bullets));
                money -= Config.blasterCost;
            } else if (money >= 150) {
                gameEntities.add(new CatapultTower(x, y, enemies, bullets));
                money -= Config.catapultCost;
            } else if (money >= 130) {
                gameEntities.add(new CannonTower(x, y, enemies, bullets));
                money -= Config.cannonCost;
            } else if (money >= 50) {
                gameEntities.add(new BallistaTower(x, y, enemies, bullets));
                money -= Config.ballistaCost;
            }
        }
    }

    public void update(){

        if(enemyList.isEmpty() && enemies.isEmpty()){
            FinalStage fStage = new FinalStage(true, stageStack, level);
            stageStack.push(fStage);
        }
        else if(playerHealth <= 0){
            FinalStage fStage = new FinalStage(false, stageStack, level);
            stageStack.push(fStage);
        }
        //Enemy Update
        for(GameEntity gameEntity : gameEntities) {
            gameEntity.update();
        }

        if(!enemies.isEmpty()) {
            if (enemies.get(0).isDead()) {
                money += enemies.get(0).getPrize();
            }
        }
        enemies.removeIf(Enemy::isDead);
        enemies.removeIf(Enemy::isSurvive);

        for(int i=0; i<gameEntities.size(); i++){
            if(gameEntities.get(i)instanceof Enemy){
                if(((Enemy) gameEntities.get(i)).isDead() || ((Enemy) gameEntities.get(i)).isSurvive()){
                    if(((Enemy) gameEntities.get(i)).isSurvive()) playerHealth -= ((Enemy) gameEntities.get(i)).getDamage();
                    gameEntities.remove(i);
                }
            }
        }
        for(Enemy enemy : enemies) {
            enemy.update();
            if(enemy.isSurvive()){
                playerHealth -= enemy.getDamage();
            }
        }

        //bullets.removeIf(Bullet::isDisposed);
        for(Bullet bullet : bullets){
            bullet.update();
        }
        bullets.removeIf(Bullet::isDisposed);


    }

    /*
    public void towerAction(Group group, Scene scene){
        ImageView imageView = new ImageView();
        imageView.setX(600);
        imageView.setY(600);
        group.getChildren().add(imageView);


        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            double X = mouseEvent.getX();
            double Y = mouseEvent.getY();

            for (GameEntity tmp : gameEntities) {
                if (tmp instanceof Tower) {
                    if (((Tower) tmp).inArea(X, Y)) {
                        if(tmp instanceof BallistaTower){
                            imageView.setImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/001.png"));
                        }
                        else if(tmp instanceof BlasterTower){
                            imageView.setImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/002.png"));
                        }
                        else if(tmp instanceof CannonTower){
                            imageView.setImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/003.png"));
                        }
                        else {
                            imageView.setImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/004.png"));
                        }
                        /*
                        ContextMenu cm = new ContextMenu();
                        MenuItem mItem1 = new MenuItem("Upgrade");
                        MenuItem mItem2 = new MenuItem("Sell");

                        Circle circle = new Circle(tmp.getX()+90, tmp.getY()+95, 10);
                        circle.setFill(new ImagePattern(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Digit/towerDefense_health(temporary).png")));

                        circle.setVisible(true);

                        //circle.managedProperty().bind(circle.visibleProperty());

                        mItem1.setOnAction(actionEvent -> {
                            System.out.println("Upgraded!");
                            ((Tower) tmp).upgrade(this);
                            //System.out.println(money);
                        });

                        mItem2.setOnAction(actionEvent -> {
                            System.out.println("Sold!");
                            money += ((Tower) tmp).getCost()/2;
                            gameEntities.remove(tmp);
                            circle.setVisible(false);
                            //circle.managedProperty().bind(circle.visibleProperty());
                            //circle.setMouseTransparent(true);
                            //group.getChildren().remove(circle);
                            //Scene scene2 = new Scene(group);
                        });

                        cm.getItems().addAll(mItem1, mItem2);
                        */

                        /*
                        Rectangle rec = new Rectangle();
                        rec.setX(X);
                        rec.setY(Y);
                        rec.setHeight(tmp.height);
                        rec.setWidth(tmp.width);
                        rec.setVisible(true);



                        Rectangle rec = new Rectangle();
                        rec.setX(X-20);
                        rec.setY(Y-25);
                        rec.setHeight(tmp.height-120);
                        rec.setWidth(tmp.width-130);
                        rec.setVisible(true);

                        rec.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                                                          @Override
                                                          public void handle(ContextMenuEvent contextMenuEvent) {
                                                              cm.show(rec, rec.getX(), rec.getY());
                                                          }
                                                      });


                         */

                        /*
                        circle.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                            @Override
                            public void handle(ContextMenuEvent contextMenuEvent) {
                                cm.show(circle, circle.getCenterX(), circle.getCenterY());
                            }
                        });

                         */

                        //TextArea textArea = new TextArea();
                        //textArea.setLayoutX(100);
                        //textArea.setLayoutY(100);
                        //textArea.setContextMenu(cm);

                        /*
                        Rectangle rec = new Rectangle();
                        rec.setX(X-20);
                        rec.setY(Y-25);
                        rec.setHeight(tmp.height-120);
                        rec.setWidth(tmp.width-130);
                        rec.setVisible(true);

                        */

                        /*
                        rec.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                System.out.println("HCN");
                                Button source = (Button) mouseEvent.getSource();
                                group.getChildren().remove(source);
                            }
                        });
                        */


                        /*
                        Button bt1 = new Button("Upgrade");
                        Button bt2 = new Button("Sell");

                        bt1.setLayoutX(775);
                        bt1.setLayoutY(580);
                        bt2.setLayoutX(bt1.getLayoutX()+120);
                        bt2.setLayoutY(bt1.getLayoutY());
                        bt1.setPrefHeight(30);
                        bt1.setPrefWidth(100);
                        bt2.setPrefHeight(30);
                        bt2.setPrefWidth(100);

                        bt1.setStyle("-fx-background-color : #29B6F6;\n"
                                + "    -fx-text-fill        : #E1F5FE;\n"
                                + "    -fx-font-weight      : 900;");
                        bt2.setStyle("-fx-background-color : #29B6F6;\n"
                                + "    -fx-text-fill        : #E1F5FE;\n"
                                + "    -fx-font-weight      : 900;");
                        group.getChildren().addAll(bt1, bt2);


                        bt1.setOnAction(actionEvent -> {
                            System.out.println("Button 1 clicked");
                            ((Tower) tmp).upgrade(GameStage.this);
                            bt1.setVisible(false);
                            bt2.setVisible(false);
                        });

                        bt2.setOnAction(actionEvent -> {
                            System.out.println("Button 2 clicked");
                            money += ((Tower) tmp).getCost()/2;
                            gameEntities.remove(tmp);
                            imageView.setImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/towerDefense_trueFilter.png"));
                            bt1.setVisible(false);
                            bt2.setVisible(false);
                        });
                    }
                }
            }
        });
    }
    */



    //public void event(Scene scene){
    public void event(Group group, Scene scene, GraphicsContext gc){

        //towerAction(group, scene);

        if(!isAuto) {
            scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

                double X = mouseEvent.getX();
                double Y = mouseEvent.getY();

                //Save Game
                if (X >= 50 && X <= 75 && Y >= 12 && Y <= 37) {
                    try {
                        saveGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //Build, Upgrade or Sell Towers
                if (eventType == 0) {
                    if (Y >= 597.0 && Y <= 627.0) {
                        if (X >= 31 && X <= 93 && money >= Config.ballistaCost) {
                            //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/001.png")));
                            eventType = 1;
                            money -= Config.ballistaCost;
                        }
                        if (X >= 125 && X <= 184 && money >= Config.blasterCost) {
                            //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/002.png")));
                            eventType = 2;
                            money -= Config.blasterCost;
                        }
                        if (X >= 213 && X <= 275 && money >= Config.cannonCost) {
                            //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/003.png")));
                            eventType = 3;
                            money -= Config.cannonCost;
                        }
                        if (X >= 305 && X <= 366 && money >= Config.catapultCost) {
                            //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/004.png")));
                            eventType = 4;
                            money -= Config.catapultCost;
                        }
                    }
                    if(Y >= 500 && Y <= 530 && X >= 900 && X <= 965){
                        eventType = 5;
                        System.out.println("Upgrade clicked");
                    }
                    if(Y >= 535 && Y <= 565 && X >= 900 && X <= 940){
                        eventType = 6;
                        System.out.println("Sell clicked");
                    }
                } else if (eventType == 1) {
                    if (X >= 31 && X <= 93 && Y >= 597.0 && Y <= 627.0) {
                        eventType = 0;
                        money += Config.ballistaCost;
                    } //Re-click the tower symbol to cancel planting tower
                    else {
                        Helper helper = new Helper();
                        int i = helper.xyToI(X - 92, Y - 92);
                        int j = helper.xyToJ(X - 92, Y - 92);
                        if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                            gameEntities.add(new BallistaTower(X - 92, Y - 92, enemies, bullets));
                            MediaPlayer mediaPlayer1 = new MediaPlayer(Config.towerBuild);
                            mediaPlayer1.setAutoPlay(true);
                        }
                        eventType = 0;
                    }
                } else if (eventType == 2) {
                    if (X >= 125 && X <= 184 && Y >= 597.0 && Y <= 627.0) {
                        eventType = 0;
                        money += Config.blasterCost;
                    }
                    else {
                        Helper helper = new Helper();
                        int i = helper.xyToI(X - 92, Y - 92);
                        int j = helper.xyToJ(X - 92, Y - 92);
                        if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                            gameEntities.add(new BlasterTower(X - 92, Y - 92, enemies, bullets));
                            MediaPlayer mediaPlayer1 = new MediaPlayer(Config.towerBuild);
                            mediaPlayer1.setAutoPlay(true);
                        }
                        eventType = 0;
                    }
                } else if (eventType == 3) {
                    if (X >= 213 && X <= 275 && Y >= 597.0 && Y <= 627.0) {
                        eventType = 0;
                        money += Config.cannonCost;
                    }
                    else {
                        Helper helper = new Helper();
                        int i = helper.xyToI(X - 92, Y - 92);
                        int j = helper.xyToJ(X - 92, Y - 92);
                        if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                            gameEntities.add(new CannonTower(X - 92, Y - 92, enemies, bullets));
                            MediaPlayer mediaPlayer1 = new MediaPlayer(Config.towerBuild);
                            mediaPlayer1.setAutoPlay(true);
                        }
                        eventType = 0;
                    }
                } else if (eventType == 4) {
                    if (X >= 305 && X <= 366 && Y >= 597.0 && Y <= 627.0) {
                        eventType = 0;
                        money += Config.catapultCost;
                    }
                    else {
                        Helper helper = new Helper();
                        int i = helper.xyToI(X - 92, Y - 92);
                        int j = helper.xyToJ(X - 92, Y - 92);
                        if (i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                            gameEntities.add(new CatapultTower(X - 92, Y - 92, enemies, bullets));
                            MediaPlayer mediaPlayer1 = new MediaPlayer(Config.towerBuild);
                            mediaPlayer1.play();
                        }
                        eventType = 0;
                    }
                } else if (eventType == 5){
                    for (GameEntity tmp : gameEntities) {
                        if (tmp instanceof Tower) {
                            if (((Tower) tmp).inArea(X, Y) && money >= ((Tower) tmp).getUpgradeCost()) {
                                System.out.println("Button 1 clicked");
                                ((Tower) tmp).upgrade(GameStage.this);
                            }
                        }
                    }
                    eventType = 0;
                } else if (eventType == 6){
                    for (GameEntity tmp : gameEntities) {
                        if (tmp instanceof Tower) {
                            if (((Tower) tmp).inArea(X, Y) && money >= ((Tower) tmp).getUpgradeCost()) {
                                System.out.println("Button 2 clicked");
                                money += ((Tower) tmp).getCost() / 2;
                                gameEntities.remove(tmp);
                            }
                        }
                    }
                    eventType = 0;
                } else {
                    eventType = 0;
                }
            });
        }
        else{
            if (buildRate == 0) {
                autoBuild();
                buildRate = Config.towerRefreshRate;
            } else buildRate--;
        }
    }
    /*
    Note: Enemy Type: 1 - Boss, 2 - Normal, 3 - Smaller, 4 - Tanker
          Tower Type: 1 - Ballista, 2 - Blaster, 3 - Cannon, 4 - Catapult
     */

    private void saveGame() throws IOException {
        StringBuilder jsonString = new StringBuilder("{\"level\":" + level + ",\"money\":" + money + ",\"enemies\":[");
        boolean isFirst = true;
        for(var enemy : enemies){
            if(isFirst) {
                jsonString.append(enemy.toJsonStr());
                isFirst = false;
            }
            else{
                jsonString.append(",").append(enemy.toJsonStr());
            }
        }
        jsonString.append("],\"towers\":[");
        isFirst = true;
        for(var tower : gameEntities){
            if(tower instanceof Tower){
                if(isFirst) {
                    jsonString.append(((Tower) tower).toJsonStr());
                    isFirst = false;
                }
                else{
                    jsonString.append(",").append(((Tower) tower).toJsonStr());
                }
            }
        }
        jsonString.append("],\"enemyqueue\":[");
        isFirst = true;
        for(var enemy : enemyList){
            if(isFirst) {
                jsonString.append(enemy.toJsonStr());
                isFirst = false;
            }
            else{
                jsonString.append(",").append(enemy.toJsonStr());
            }
        }
        jsonString.append("]}");

        System.out.println(jsonString);
        PrintWriter writer = new PrintWriter("./src/main/java/savegame/saveGame.txt", StandardCharsets.UTF_8);
        writer.println(jsonString);
        writer.close();
        System.exit(0);
    }

}
