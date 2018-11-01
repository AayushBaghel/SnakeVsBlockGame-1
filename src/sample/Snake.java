package sample;

import java.awt.geom.Point2D;

public class Snake {
    private int length;
    private boolean alive = true;
    private Point2D velocity;

    boolean encounterBlock(Block block) {return true;}

    boolean encounterBall(Ball ball) {return true;}

    boolean encounterDestroyBlock(DestroyBlock block) {return true;}

    boolean encounterShield(Shield shield) {return true;}

    boolean encounterMagnet(Magnet magnet) {return true;}

    void moveLeft() {}

    void moveRight() {}

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
