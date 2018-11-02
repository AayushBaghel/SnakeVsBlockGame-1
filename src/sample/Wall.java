package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Point2D;

public class Wall {
    private int length;
    private Point2D velocity;

    private Rectangle body = new Rectangle(5, 200, Color.WHITE);

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Rectangle getBody() {
        return body;
    }
}
