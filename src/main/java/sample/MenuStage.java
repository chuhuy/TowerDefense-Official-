package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
        /*
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
            GameStage game = new GameStage(1);
            stageStack.pop();
            stageStack.push(game);
        });

        Button loadSaveGame = new Button();
        int loadSaveGame_x = 610;
        loadSaveGame.setLayoutX(loadSaveGame_x);
        int loadSaveGame_y = 476;
        loadSaveGame.setLayoutY(loadSaveGame_y);
        int loadSaveGame_height = 47;
        int loadSaveGame_width = 160;
        loadSaveGame.setMinSize(loadSaveGame_width, loadSaveGame_height);
        loadSaveGame.setMaxSize(loadSaveGame_width, loadSaveGame_height);
        //startButton.setStyle("-fx-background-color: transparent;");
        loadSaveGame.setText("Load Save Game");
        root.getChildren().add(loadSaveGame);

        loadSaveGame.setOnMouseReleased(some -> {
            GameStage game = null;
            try {
                root.getChildren().remove(loadSaveGame);
                root.getChildren().remove(startButton);
                game = new GameStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            stageStack.pop();
            stageStack.push(game);
        });

         */
    }

    @Override
    public void update() {

    }

    @Override
    public void event(Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {

            double X = mouseEvent.getX();
            double Y = mouseEvent.getY();

            if(X >= 610 && X <= 770 && Y >= 476 && Y < (476 + 47)){
                MyStage game = null;
                try {
                    game = new GameStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    game = new GameStage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stageStack.pop();
                stageStack.push(game);
            }
            else if(X >= 500 && X <= 698 && Y >= 377 && Y <= (377+56)){
                GameStage game = new GameStage(1);
                stageStack.pop();
                stageStack.push(game);
            }
        });
    }

}
