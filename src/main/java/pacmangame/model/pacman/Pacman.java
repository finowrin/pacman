package pacmangame.model.pacman;

import pacmangame.model.Maze;
import pacmangame.util.Direction;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Pacman is the playable character of our game.
 */
public class Pacman extends Circle {
    private static final double DEFAULT_RADIUS = 25;
    private static final int PADDING = 15;
    private Direction moveDirection;
    private boolean moving;
    private static final String
            upURL = "/images/pacman/pacman-up.png",
            downURL = "/images/pacman/pacman-down.png",
            leftURL = "/images/pacman/pacman-left.png",
            rightURL = "/images/pacman/pacman-right.png";
    private static ImagePattern up, down, left, right;

    /**
     * Creates the pacman at the specified location
     *
     * @param x x coordinate of the pacman location
     * @param y y coordinate of the pacman location
     */
    public Pacman(double x, double y) {
        this(x, y, DEFAULT_RADIUS);
    }

    /**
     * Creates the pacman of specified radius, at the specified location
     *
     * @param radius radius of the pacman
     * @param x      x coordinate of the pacman location
     * @param y      y coordinate of the pacman location
     */
    public Pacman(double x, double y, double radius) {
        loadImages();
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(radius);
        moveDirection = Direction.RIGHT;
        setFill(decidePattern(moveDirection));
    }

    /**
     * check whether pacman collides with the specified circle
     *
     * @param circle the circle
     * @return whether the pacman collides with the specified circle
     */
    public boolean checkCollision(Circle circle) {
        return intersects(circle.getLayoutBounds());
    }

    /**
     * sets the pacman as moving in the specified direction
     *
     * @param direction the pacman moving direction
     */
    public void setMoving(Direction direction) {
        moving = true;
        if (direction == moveDirection)
            return;
        moveDirection = direction;
        setFill(decidePattern(moveDirection));
    }



    /**
     * Updates the pacman according to its moving direction
     *
     * @param maze maze on which the pacman moves
     * @param step minimum move step of the pacman
     */
    public void update(Maze maze, int step) {
        if (!moving) return;
        switch (moveDirection) {
            case LEFT:
                if (!maze.isTouchingObstacles(getCenterX() - getRadius(), getCenterY(), PADDING)) {
                    setCenterX(getCenterX() - step);
                }
                break;
            case RIGHT:
                if (!maze.isTouchingObstacles(getCenterX() + getRadius(), getCenterY(), PADDING)) {
                    setCenterX(getCenterX() + step);
                }
                break;
            case UP:
                if (!maze.isTouchingObstacles(getCenterX(), getCenterY() - getRadius(), PADDING)) {
                    setCenterY(getCenterY() - step);
                }
                break;
            case DOWN:
                if (!maze.isTouchingObstacles(getCenterX(), getCenterY() + getRadius(), PADDING)) {
                    setCenterY(getCenterY() + step);
                }
                break;
        }
    }

    /**
     * Stops the pacman
     */
    public void stop() {
        moving = false;
    }

    /**
     * check whether pacman collides with the specified circle
     *
     * @param other the other circle
     * @return whether the pacman collides with the other circle
     */
    public boolean checkCollision(Rectangle other) {
        return other.intersects(this.getLayoutBounds());
    }

    /**
     * @return Is the pacman moving
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Decide the image pattern according to direction
     * Instead of rotating the pacman image procedurally through code, I have used separate images for each direction
     * @param direction pacman direction
     * @return the corresponding image pattern
     */
    private static ImagePattern decidePattern(Direction direction) {
        if (direction == Direction.UP) {
            return up;
        } else if (direction == Direction.DOWN) {
            return down;
        } else if (direction == Direction.RIGHT) {
            return right;
        } else if (direction == Direction.LEFT) {
            return left;
        } else {
            return null;
        }
    }

    /**
     * load pacman sprites/images
     */
    private static void loadImages() {
        up = new ImagePattern(new Image(Objects.requireNonNull(
                Pacman.class.getResourceAsStream(upURL))));
        down = new ImagePattern(new Image(Objects.requireNonNull(
                Pacman.class.getResourceAsStream(downURL))));
        left = new ImagePattern(new Image(Objects.requireNonNull(
                Pacman.class.getResourceAsStream(leftURL))));
        right = new ImagePattern(new Image(Objects.requireNonNull(
                Pacman.class.getResourceAsStream(rightURL))));
    }
}
