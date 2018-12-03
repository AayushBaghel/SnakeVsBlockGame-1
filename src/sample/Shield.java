package sample;


import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * This is the class used to create the individual shield objects in the game.
 */
class Shield {

    /**
     * The status of the shield.
     */
    private boolean alive = true;

    /**
     * The body (GUI part) of the shield.
     */
    private StackPane body = new StackPane();

    /**
     * The constructor used to create the shield.
     */
    Shield(){
        ImageView img = new ImageView(Main.class.getResource("/Shield.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        body.getChildren().add(img);
    }

    /**
     * Gives the status of the shield.
     * @return The status of the sheild.
     */
    boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the shield.
     */
    void setAlive() {
        this.alive = false;
    }

    /**
     * Gives the (GUI) body of the shield.
     * @return The (GUI) body of the shield.
     */
    StackPane getBody() {
        return body;
    }
}
