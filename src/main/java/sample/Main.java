package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    GraphicsContext gc;
    public static Stack<MyStage> stageStack = new Stack<>();

    @Override
    public void start(Stage primaryStage){
        Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();
        primaryStage.setTitle(Config.GAME_NAME);
        primaryStage.setResizable(false);
        MyStage menu = new MenuStage(stageStack);
        stageStack.push(menu);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                stageStack.peek().render(gc, root);
                stageStack.peek().update();
                stageStack.peek().event(root, scene, gc);
            }
        }.start();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
