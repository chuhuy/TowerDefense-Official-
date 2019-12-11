package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Stack;

public class FinalStage extends MyStage {
    Stack<MyStage> stageStack;
    private boolean isWon;
    int level;

    public FinalStage(boolean isWon, Stack<MyStage> stageStack, int level){
        this.isWon = isWon;
        this.stageStack = stageStack;
        this.level = level;
    }

    @Override
    public void render(GraphicsContext gc, Group root) {

        gc.drawImage(new Image("file:src/main/java/images/background.png"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/playagain.jpg"), Config.SCREEN_WIDTH/2 - 370, 450, 741, 190);
        if (isWon) {
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/win.png"), Config.SCREEN_WIDTH/2 - 187, 100, 375, 300);
        }
        else{
            gc.drawImage(new Image("file:src/main/java/TowerDefense/AssetsKit_3/lose.png"), Config.SCREEN_WIDTH/2 - 187, 100, 375, 300);
        }

    }
    @Override
    public void update() {
    }

    @Override
    public void event(Group group, Scene scene, GraphicsContext gc) {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();

            if(x >= 435 && y >= 585 && x <= 565 && y <= 631){
                //level += 1;
                //GameStage gameStage = new GameStage(level, stageStack);
                //stageStack.push(gameStage);
                MenuStage menu = new MenuStage(stageStack);
                stageStack.push(menu);
            }
            else if(x >= 689 && y >= 588 && x <= 773 && y <= 632){
                System.exit(0);
            }

        });
    }
}
