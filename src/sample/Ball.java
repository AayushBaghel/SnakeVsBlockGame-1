package sample;

import java.awt.geom.Point2D;

public class Ball {
    private int value;
    private Point2D velocity;
    private boolean alive = true;

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
}
