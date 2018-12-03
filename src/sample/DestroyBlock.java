package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * This is the class used to create the individual destroy block objects in the game.
 */
class DestroyBlock {
    /**
     * The status of the destroy block.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the destroy block.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the destroy block.
     */
    DestroyBlock(){
        ImageView img = new ImageView(Main.class.getResource("/DestroyBlocks.png").toString());
        img.setFitHeight(35);
        img.setFitWidth(35);
        body.getChildren().add(img);
    }

    /**
     * Gives the status of the destroy block.
     * @return The status of the destroy block.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the destroy block.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the destroy block.
     * @return The (GUI) body of the destroy block.
     */
    StackPane getBody() {
        return body;
    }
}
