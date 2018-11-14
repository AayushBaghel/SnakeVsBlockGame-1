package sample;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int length;
    private boolean alive = true;
    private Point2D velocity;

    private List<Circle> snakeBody;

    Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        length = 1;
    }

    boolean encounterBlock(Block block) {return true;}

    boolean encounterBall(Ball ball) {return true;}

    boolean encounterDestroyBlock(DestroyBlock block) {return true;}

    boolean encounterShield(Shield shield) {return true;}

    boolean encounterMagnet(Magnet magnet) {return true;}

    void moveLeft() {
        System.out.println("Left - "+snakeBody.get(0).getTranslateX());
        if(snakeBody.get(0).getTranslateX()<=20)
            return;
        for (Circle c: snakeBody
        ) {
            c.setTranslateX(c.getTranslateX() -25);
        }
    }

    void moveRight() {
        System.out.println("Right - "+snakeBody.get(0).getTranslateX());
        if(snakeBody.get(0).getTranslateX()>=480)
            return;
        for (Circle c: snakeBody
        ) {
            c.setTranslateX(c.getTranslateX() + 25);
        }
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<Circle> getSnakeBody() {
        return snakeBody;
    }
}
