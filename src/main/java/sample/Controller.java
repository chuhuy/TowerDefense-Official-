package sample;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.Entity.Tower.BallistaTower;
import sample.Entity.Tower.BlasterTower;
import sample.Entity.Tower.CannonTower;
import sample.Entity.Tower.CatapultTower;

public class Controller {
    /*
    private int eventType = 0;

    void event(Scene scene){
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            double X = mouseEvent.getX();
            double Y = mouseEvent.getY();
            if(eventType == 0) {
                if (Y >= 597.0 && Y <= 627.0) {
                    if (X >= 31 && X <= 93) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/001.png")));
                        eventType = 1;
                    }
                    if (X >= 125 && X <= 184) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/002.png")));
                        eventType = 2;
                    }
                    if (X >= 213 && X <= 275) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/003.png")));
                        eventType = 3;
                    }
                    if (X >= 305 && X <= 366) {
                        //scene.setCursor(new ImageCursor(new Image("file:src/main/java/TowerDefense/AssetsKit_3/Side/004.png")));
                        eventType = 4;
                    }
                }
            }
            else if(eventType == 1){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19){
                    gameEntities.add(new BallistaTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 2){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new BlasterTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 3){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new CannonTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
            else if(eventType == 4){
                Helper helper = new Helper();
                int i = helper.xyToI(X - 92, Y - 92);
                int j = helper.xyToJ(X - 92, Y - 92);
                if(i >= 0 && i <= 19 && j >= 0 && j <= 19) {
                    gameEntities.add(new CatapultTower(X - 92, Y - 92, enemies, bullets));
                }
                eventType = 0;
            }
        });
    }
    */

}
