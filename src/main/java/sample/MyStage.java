package sample;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

abstract public class MyStage {

    abstract public void render(GraphicsContext gc, Group root);
    abstract public void update();
}
