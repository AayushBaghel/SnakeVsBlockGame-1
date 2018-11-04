package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.geom.Point2D;

public class Ball {
    private int value;
    private Point2D velocity;
    private boolean alive = true;

    private Circle body = new Circle(15, Color.GOLD);

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Circle getBody() {
        return body;
    }
}
