package sample;


import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.awt.geom.Point2D;

public class Shield {
    private Point2D velocity;
    private boolean alive = true;

    private StackPane body = new StackPane();

    public Shield(){
        ImageView img = new ImageView(Main.class.getResource("/Shield.png").toString());
        img.setFitHeight(40);
        img.setFitWidth(40);
        body.getChildren().add(img);
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
