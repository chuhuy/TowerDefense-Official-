package sample;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static sample.Main.stageStack;

public class MenuStage extends MyStage{
    private final int startButton_x = 500;
    private final int startButton_y = 377;
    private final int startButton_width = 198;
    private final int startButton_height = 56;

    public void render(GraphicsContext gc, Group root){
        gc.drawImage(
            new Image("file:src/images/menu.png"),
                0,0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT
        );
        //start button
        Button startButton = new Button();
        startButton.setLayoutX(startButton_x);
        startButton.setLayoutY(startButton_y);
        startButton.setMinSize(startButton_width, startButton_height);
        startButton.setMaxSize(startButton_width, startButton_height);
        startButton.setStyle("-fx-background-color: transparent;");

        root.getChildren().add(startButton);

        startButton.setOnMouseReleased(new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent some) {
                //MyStage game = new GameStage(2);

                //stageStack.push(game);
            }
        });
    }

    @Override
    public void update() {

    }

}
