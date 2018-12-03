package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * This is the class used to create the individual magnet objects in the game.
 */
class Magnet {
    /**
     * The status of the magnet.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the magnet.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the magnet.
     */
    Magnet(){
        ImageView img = new ImageView(Main.class.getResource("/Magnet.png").toString());
        img.setFitHeight(35);
        img.setFitWidth(35);
        body.getChildren().add(img);
    }

    /**
     * Gives the status of the magnet.
     * @return The status of the magnet.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the magnet.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the magnet.
     * @return The (GUI) body of the magnet.
     */
    StackPane getBody() {
        return body;
    }
}
