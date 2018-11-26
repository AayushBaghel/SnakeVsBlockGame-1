package sample;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.geom.Point2D;
import java.util.Random;

public class Coin {
    private int value;
    private Point2D velocity;
    private boolean alive = true;

    StackPane body = new StackPane();

    Coin () {

        // get random value no.
        Random r = new Random();
        value = r.nextInt(8) + 1;
        ImageView img = new ImageView(Main.class.getResource("/Bitcoin.png").toString());
        img.setFitHeight(30);
        img.setFitWidth(30);
        body.getChildren().add(img);
        //body.getChildren().add(new Circle(15, Color.BLUE));
        Text txt = new Text(Integer.toString(value));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);
    }



    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
