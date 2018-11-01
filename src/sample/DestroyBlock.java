package sample;

import java.awt.geom.Point2D;

public class DestroyBlock {
    private Point2D velocity;
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
