package pacmangame.model.obstacles;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A simple rectangular obstacle
 */
public class BarObstacle extends Rectangle {
    private static final Color DEFAULT_FILL = Color.CADETBLUE;

    public enum Orientation {VERTICAL, HORIZONTAL}

    public static final double THICKNESS = 25;

    /**
     * Initializes the obstacle at the specified location with the specified properties
     *
     * @param x           x coordinate of the location
     * @param y           y coordinate of the location
     * @param orientation - horizontal or vertical
     * @param length      - the length of the bar (1 == 100px)
     */
    public BarObstacle(double x, double y, Orientation orientation, double length) {
        this.setX(x);
        this.setY(y);
        switch (orientation) {
            case VERTICAL -> {
                this.setHeight(length * BarObstacle.THICKNESS);
                this.setWidth(BarObstacle.THICKNESS);
            }
            case HORIZONTAL -> {
                this.setHeight(BarObstacle.THICKNESS);
                this.setWidth(length * BarObstacle.THICKNESS);
            }
        }
        this.setFill(DEFAULT_FILL);
    }

    /**
     * Checks whether this obstacle touches a specified point
     *
     * @param x       x coordinate of the point
     * @param y       y coordinate of the point
     * @param padding layout padding
     * @return whether the obstacle touches the specified point(x,y)
     */
    public boolean isTouching(double x, double y, double padding) {
        return x >= this.getX() - padding &&
                x <= this.getX() + padding + this.getWidth() &&
                y >= this.getY() - padding &&
                y <= this.getY() + padding + this.getHeight();
    }
}
