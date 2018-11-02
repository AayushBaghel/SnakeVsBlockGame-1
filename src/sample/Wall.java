package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Point2D;

public class Wall {
    private int length;
    private Point2D velocity;

    private Rectangle body = new Rectangle(3, 10, Color.WHITE);

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Rectangle getBody() {
        return body;
    }
}
