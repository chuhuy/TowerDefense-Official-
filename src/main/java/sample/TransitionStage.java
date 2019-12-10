package sample;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Stack;

public class TransitionStage extends MyStage{
    private Stack<MyStage> stageStack;

    TransitionStage(Stack<MyStage> stageStack){
        this.stageStack = stageStack;
    }

    @Override
    public void render(GraphicsContext gc, Group root) {


        gc.drawImage(
            new Image("file:src/main/java/images/intothewood.jpg"), 0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT
        );
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                GameStage game = new GameStage(1, stageStack);
                stageStack.push(game);
            }
        });
        new Thread(sleeper).start();
    }

    @Override
    public void update() {

    }

    @Override
    //public void event(Scene scene) {
    public void event(Group group, Scene scene, GraphicsContext gc) {

    }
}
