package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;

public class Magnet {
    private Point2D velocity;
    private boolean alive = true;

    private Circle body = new Circle(10, Color.RED);

    public boolean isAlive() {
        return alive;
    }

    public void setAlive (boolean alive) {
        this.alive = alive;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Circle getBody() {
        return body;
    }
}
