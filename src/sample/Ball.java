package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.Random;

/**
 * This is the class used to create the individual ball objects in the game.
 */
class Ball {

    /**
     * The number by which the length of the snake increases when it eats this ball.
     */
    private int value;

    /**
     * The status of the ball.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the ball.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the ball.
     */
    Ball () {

        // get random value no.
        Random r = new Random();
        value = r.nextInt(8) + 1;
        body.getChildren().add(new Circle(15, Color.BLUE));
        Text txt = new Text(Integer.toString(value));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);
    }


    /**
     * Gives the number by which the length of the snake increases when it eats this ball.
     * @return The number by which the length of the snake increases when it eats this ball.
     */
    int getValue() {
        return value;
    }

    /**
     * Gives the status of the ball.
     * @return The status of the ball.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the ball.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the ball.
     * @return The (GUI) body of the ball.
     */
    StackPane getBody() {
        return body;
    }
}
