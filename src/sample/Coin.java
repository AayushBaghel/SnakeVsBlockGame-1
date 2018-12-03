package sample;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.util.Random;

/**
 * This is the class used to create the individual coin objects in the game.
 */
class Coin {

    /**
     * The number by which the score increases when it eats this coin.
     */
    private int value;

    /**
     * The status of the coin.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the coin.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the coin.
     */
    Coin () {

        // get random value no.
        Random r = new Random();
        value = r.nextInt(8) + 1;
        ImageView img = new ImageView(Main.class.getResource("/Bitcoin.png").toString());
        img.setFitHeight(30);
        img.setFitWidth(30);
        body.getChildren().add(img);
        Text txt = new Text(Integer.toString(value));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);
    }

    /**
     * Gives the number by which the score increases when it eats this coin.
     * @return The number by which the score increases when it eats this coin.
     */
    int getValue() {
        return value;
    }

    /**
     * Gives the status of the coin.
     * @return The status of the coin.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the coin.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the coin.
     * @return The (GUI) body of the coin.
     */
    StackPane getBody() {
        return body;
    }
}
