package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the class used to create the snake that the player controls.
 */
public class Snake {

    /**
     * The length of the snake.
     */
    private int length;

    /**
     * A list that holds all the individual circles of the body of the snake.
     */
    private List<Circle> snakeBody;

    /**
     * A stack pane in which all the circles of the snake body as well as the snake's length will be displayed.
     */
    private StackPane body = new StackPane();

    /**
     * A label that stores and displays the length of the snake.
     */
    private Text lengthText = new Text();

    /**
     * A constructor that creates the initial snake.
     */
    Snake() {
        snakeBody = new ArrayList<>();
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
        length = 3;
        updateLengthText();
        body.getChildren().add(snakeBody.get(0));
        body.getChildren().add(snakeBody.get(1));
        body.getChildren().add(snakeBody.get(2));
        body.getChildren().add(lengthText);
        body.setAlignment(Pos.CENTER);
    }

    /**
     * A constructor that creates the initial snake with a specified length.
     * @param length Length of the snake.
     */
    Snake(int length) {
        snakeBody = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            snakeBody.add(new Circle(20, Paint.valueOf("BLUE")));
            body.getChildren().add(snakeBody.get(i));
        }
        this.length = length;
        updateLengthText();

        body.getChildren().add(lengthText);
        body.setAlignment(Pos.CENTER);
    }

    /**
     * A function that moves all the balls of the snake left.
     */
    void moveLeft() {
        if(snakeBody.get(0).getTranslateX()<=25)
            return;
        for (Circle c: snakeBody
        ) {
            c.setTranslateX(c.getTranslateX() -25);
        }
        lengthText.setTranslateX(snakeBody.get(0).getTranslateX()-5);
    }

    /**
     * A function that moves all the balls of the snake right.
     */
    void moveRight() {
        if(snakeBody.get(0).getTranslateX()>=475)
            return;
        for (Circle c: snakeBody
        ) {
            c.setTranslateX(c.getTranslateX() + 25);
        }
        lengthText.setTranslateX(snakeBody.get(0).getTranslateX()-5);
    }

    /**
     * A function used to update the value stored in the length label.
     */
    public void updateLengthText() {
        lengthText.setText(Integer.toString(length));
    }

    /**
     * A function used to get the value stored in the length label.
     * @return The value stored in the length label.
     */
    public Text getLengthText() {
        return lengthText;
    }

    /**
     * A function used to set the new length of the snake.
     * @param length The new length of the snake.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * A function to get the current length of the snake.
     * @return The current length of the snake.
     */
    public int getLength() {
        return length;
    }

    /**
     * A function to get the list of the individual circles that the snake is comprised of.
     * @return The list of the individual circles that the snake is comprised of.
     */
    public List<Circle> getSnakeBody() {
        return snakeBody;
    }

    /**
     * A function to get the stack pane in which all the circles of the snake body as well as the snake's length are be
     * displayed.
     * @return The stack pane in which all the circles of the snake body as well as the snake's length are be displayed.
     */
    public StackPane getSnakePane() { return body; }
}
