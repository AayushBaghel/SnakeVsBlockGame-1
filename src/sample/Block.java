package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block {
    private int value;
    private Point2D velocity;
    private boolean alive = true;
//    private Rectangle body = new Rectangle(100, 100);

    StackPane body = new StackPane();

    Block() {
//        body = defineBody(body);

        Random r = new Random();
        value = r.nextInt(8) + 1;

        Rectangle rect = new Rectangle(100, 100);
        rect = defineBody(rect);

        body.getChildren().add(rect);
        Text txt = new Text(Integer.toString(value));
        txt.setFont(Font.font("Courier New Bold"));
        body.getChildren().add(txt);
        body.setAlignment(Pos.CENTER);

    }

    private Rectangle defineBody(Rectangle body) {
        body.setArcHeight(40);
        body.setArcWidth(40);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BEIGE);
        colors.add(Color.PINK);
        colors.add(Color.DARKSALMON);
        colors.add(Color.SANDYBROWN);
        colors.add(Color.LIGHTSLATEGRAY);

        Random random = new Random();
        int index = random.nextInt(colors.size());

        Color color = colors.get(index);

        body.setFill(color);

        return body;
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
