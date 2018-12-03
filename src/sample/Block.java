package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Random;

/**
 * This is the class used to create the individual block objects in the game.
 */
class Block {

    /**
     * The number by which the score increases and the length of the snake deceases when it eats this ball.
     */
    private int value;

    /**
     * The status of the block.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the destroy block.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the block.
     */
    Block() {
        Random r = new Random();
        value = r.nextInt(8) + 1;

        Rectangle rect = new Rectangle(100, 100);
        defineBody(rect);

        body.getChildren().add(rect);
        Text txt = new Text(Integer.toString(value));
        txt.setFont(Font.font("Courier New Bold"));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);

    }

    /**
     * The constructor used to create the block given a certain upper limit for the block's value.
     * @param i The upper limit of the block's value.
     */
    Block(int i) {
        Random r = new Random();
        value = r.nextInt(i-1) + 1;

        Rectangle rect = new Rectangle(100, 100);
        defineBody(rect);

        body.getChildren().add(rect);
        Text txt = new Text(Integer.toString(value));
        txt.setFont(Font.font("Courier New Bold"));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);
    }

    /**
     * A function That sets the size and color of the block.
     * @param body The body of the block.
     */
    private void defineBody(Rectangle body) {
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

//        return body;
    }

    /**
     * Gives the number by which the score increases and the length of the snake decreases when it eats this block.
     * @return The number by which the score increases and the length of the snake decreases when it eats this block.
     */
    int getValue() {
        return value;
    }

    /**
     * Gives the status of the block.
     * @return The status of the block.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the block.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the block.
     * @return The (GUI) body of the block.
     */
    StackPane getBody() {
        return body;
    }
}
