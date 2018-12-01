package sample;


import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * This is the class used to create the individual shield objects in the game.
 */
public class Shield {

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
    public Shield(){
        ImageView img = new ImageView(Main.class.getResource("/Shield.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        body.getChildren().add(img);
    }

    /**
     * Gives the status of the shield.
     * @return The status of the sheild.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the status of the shield.
     * @param alive The new status of the shield.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Gives the (GUI) body of the shield.
     * @return The (GUI) body of the shield.
     */
    public StackPane getBody() {
        return body;
    }
}
