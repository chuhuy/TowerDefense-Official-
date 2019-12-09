package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

abstract public class MyStage {

    abstract public void render(GraphicsContext gc, Group root);
    abstract public void update();
    abstract public void event(Group group, Scene scene, GraphicsContext gc);
}
