package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.geom.Point2D;

public class DestroyBlock {
    private Point2D velocity;
    private boolean alive = true;

    private StackPane body = new StackPane();
//    private Rectangle body = new Rectangle(30 ,30, Color.BLACK);

    public DestroyBlock(){
        ImageView img = new ImageView(Main.class.getResource("/DestroyBlocks.png").toString());
        img.setFitHeight(35);
        img.setFitWidth(35);
        body.getChildren().add(img);
        //pane.getChildren().add(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public StackPane getBody() {
        return body;
    }
}
