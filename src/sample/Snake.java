package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private int length;
    private boolean alive = true;
    private Point2D velocity;

    private List<Circle> snakeBody;

    private StackPane body = new StackPane();

    Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        length = 3;
        Text txt = new Text(Integer.toString(length));
        txt.setFont(Font.font("Courier New Bold"));
        body.getChildren().add(txt);
        body.getChildren().add(snakeBody.get(0));
        body.getChildren().add(snakeBody.get(1));
        body.getChildren().add(snakeBody.get(2));
        body.setAlignment(Pos.CENTER);
    }

    boolean encounterBlock(Block block) {return true;}

    boolean encounterBall(Ball ball) {return true;}

    boolean encounterDestroyBlock(DestroyBlock block) {return true;}

    boolean encounterShield(Shield shield) {return true;}

    boolean encounterMagnet(Magnet magnet) {return true;}

    void moveLeft() {
        //System.out.println("Left - "+snakeBody.get(0).getTranslateX());
        if(snakeBody.get(0).getTranslateX()<=25)
            return;
        for (Circle c: snakeBody
        ) {
            c.setTranslateX(c.getTranslateX() -25);
        }
    }

    void moveRight() {
        //System.out.println("Right - "+snakeBody.get(0).getTranslateX());
        if(snakeBody.get(0).getTranslateX()>=475)
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

    public StackPane getSnakePane() { return body; }
}
