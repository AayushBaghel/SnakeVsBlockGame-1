package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the class used to create the individual wall objects in the game.
 */
class Wall {

    /**
     * The body (GUI part) of the wall.
     */
    private Rectangle body = new Rectangle(5, 200, Color.WHITE);

    /**
     * The constructor used to create the shield.
     */
    Rectangle getBody() {
        return body;
    }
}
