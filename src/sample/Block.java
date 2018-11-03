package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Block {
    private int value;
    private Point2D velocity;
    private boolean alive = true;
    private Rectangle body = new Rectangle(100, 100);

    Block() {
        body = defineBody(body);
    }

    private Rectangle defineBody(Rectangle body) {
        body.setArcHeight(40);
        body.setArcWidth(40);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BEIGE);
        colors.add(Color.PINK);
        colors.add(Color.DARKSALMON);
        colors.add(Color.SANDYBROWN);
        colors.add(Color.LIGHTSLATEGRAY);

        Random random = new Random();
        int index = random.nextInt(colors.size());

        Color color = colors.get(index);

        body.setFill(color);

        return body;
    }

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

    public Rectangle getBody() {
        return body;
    }
}
