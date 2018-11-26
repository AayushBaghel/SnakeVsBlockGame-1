package sample;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.geom.Point2D;

public class Magnet {
    private Point2D velocity;
    private boolean alive = true;

    private StackPane body = new StackPane();
    //private Circle body = new Circle(10, Color.RED);

    public Magnet(){
        ImageView img = new ImageView(Main.class.getResource("/Magnet.png").toString());
        img.setFitHeight(35);
        img.setFitWidth(35);
        body.getChildren().add(img);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive (boolean alive) {
        this.alive = alive;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public StackPane getBody() {
        return body;
    }
}
