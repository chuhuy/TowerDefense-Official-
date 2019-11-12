package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Stack;

public class MenuStage extends MyStage{
    private Stack<MyStage> stageStack;

    MenuStage(Stack<MyStage> stageStack){
        this.stageStack = stageStack;
    }

    public void render(GraphicsContext gc, Group root){
        gc.drawImage(
            new Image("file:src/main/java/images/menu.png"),
                0,0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT
        );
        //start button
        Button startButton = new Button();
        int startButton_x = 500;
        startButton.setLayoutX(startButton_x);
        int startButton_y = 377;
        startButton.setLayoutY(startButton_y);
        int startButton_height = 56;
        int startButton_width = 198;
        startButton.setMinSize(startButton_width, startButton_height);
        startButton.setMaxSize(startButton_width, startButton_height);
        startButton.setStyle("-fx-background-color: transparent;");

        root.getChildren().add(startButton);

        startButton.setOnMouseReleased(some -> {
            TransitionStage trans = new TransitionStage(stageStack);

            stageStack.push(trans);
        });
    }

    @Override
    public void update() {

    }

    @Override
    public void event(Scene scene) {

    }

}
