package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ball {
    private int value;
    private Point2D velocity;
    private boolean alive = true;

    StackPane body = new StackPane();

    Ball () {

        // get random value no.
        Random r = new Random();
        value = r.nextInt(8) + 1;

        body.getChildren().add(new Circle(15, Color.GOLD));
        Text txt = new Text(Integer.toString(value));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);
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

    public StackPane getBody() {
        return body;
    }
}
