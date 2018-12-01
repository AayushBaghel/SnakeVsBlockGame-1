package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * This is the class used to create the individual magnet objects in the game.
 */
public class Magnet {
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
    public Magnet(){
        ImageView img = new ImageView(Main.class.getResource("/Magnet.png").toString());
        img.setFitHeight(35);
        img.setFitWidth(35);
        body.getChildren().add(img);
    }

    /**
     * Gives the status of the magnet.
     * @return The status of the magnet.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the magnet.
     * @param alive The new status of the magnet.
     */
    public void setAlive (boolean alive) {
        this.alive = alive;
    }

    /**
     * Gives the (GUI) body of the magnet.
     * @return The (GUI) body of the magnet.
     */
    public StackPane getBody() {
        return body;
    }
}
