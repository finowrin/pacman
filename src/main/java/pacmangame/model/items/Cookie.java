package pacmangame.model.items;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A simple game item - the pacman can eat this and increase score
 */
public class Cookie extends Circle {

    private int value;
    private static final double DEFAULT_RADIUS = 12.5;

    /**
     * Initializes a default size cookie at the specified location
     *
     * @param x x coordinate of the cookie location
     * @param y y coordinate of the cookie location
     */
    public Cookie(double x, double y) {
        this(x, y, DEFAULT_RADIUS);
    }

    /**
     * Initializes the cookie of a specified radius, at the specified location
     *
     * @param x      x coordinate of the cookie location
     * @param y      y coordinate of the cookie location
     * @param radius radius of the cookie
     */
    public Cookie(double x, double y, double radius) {
        this.value = 5;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(radius);
        this.setFill(Color.SADDLEBROWN);
    }

    /**
     * @return the cookie's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Hides the cookie
     */
    public void hide() {
        this.setVisible(false);
    }

    /**
     * Shows the cookie
     */
    public void show() {
        this.setVisible(true);
    }

}