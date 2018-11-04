package sample;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Point2D;

public class Shield {
    private Point2D velocity;
    private boolean alive = true;

    private Rectangle body = new Rectangle(25, 30, Color.ORANGERED);

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Rectangle getBody() {
        return body;
    }
}
