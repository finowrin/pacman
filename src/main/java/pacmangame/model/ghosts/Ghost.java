package pacmangame.model.ghosts;


import pacmangame.model.Maze;
import pacmangame.util.Direction;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Typical ghost npc in our pacman game
 */
public class Ghost extends Rectangle {

    private Direction direction;
    private final Maze maze;
    private int timesWalked;
    private boolean moving;
    private boolean scared;

    private final Paint normalFill;
    private static final Paint scaredFill = GhostFactory.getScaredPattern();

    private final static double PADDING = 12;

    /**
     * Spawns a ghost at the given coordinates
     *
     * @param x    x coordinate of the ghost
     * @param y    y coordinate of the ghost
     * @param fill fill(color, pattern, etc.) of the ghost
     * @param maze maze for reference of obstacles when moving the ghost
     */
    public Ghost(double x, double y, Paint fill, Maze maze) {
        this(x,y,Direction.DOWN, fill, maze);
    }

    /**
     * Spawns a ghost at the given coordinates
     *
     * @param x    x coordinate of the ghost
     * @param y    y coordinate of the ghost
     * @param fill fill(color, pattern, etc.) of the ghost
     * @param maze maze for reference of obstacles when moving the ghost
     */
    public Ghost(double x, double y, Direction direction, Paint fill, Maze maze) {
        this.setX(x);
        this.setY(y);
        this.maze = maze;
        this.setHeight(50);
        this.setWidth(50);
        this.normalFill = fill;
        setFill(fill);
        this.timesWalked = 0;
        this.direction = direction;
        moving = true;
    }


    /**
     * Checks whether there's a path to go in the given direction
     *
     * @param direction the direction to move
     */
    private void checkPath(Direction direction) {
        double rightEdge, leftEdge, topEdge, bottomEdge;
        switch (direction) {
            case DOWN -> {
                leftEdge = getX() - 10;
                bottomEdge = getY() + getHeight() + 15;
                rightEdge = getX() + getWidth() + 10;
                if (!maze.hasObstacle(leftEdge, rightEdge, bottomEdge - 1, bottomEdge)) {
                    this.direction = direction;
                }
            }
            case UP -> {
                leftEdge = getX() - 10;
                rightEdge = getX() + getWidth() + 10;
                topEdge = getY() - 15;
                if (!maze.hasObstacle(leftEdge, rightEdge, topEdge - 1, topEdge)) {
                    this.direction = direction;
                }
            }
            case LEFT -> {
                leftEdge = getX() - 15;
                bottomEdge = getY() + getHeight() + 10;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(leftEdge - 1, leftEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
            }
            case RIGHT -> {
                bottomEdge = getY() + getHeight() + 10;
                rightEdge = getX() + getWidth() + 15;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(rightEdge - 1, rightEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
            }
        }
    }

    /**
     * @param whereToGo
     * @param whereToChangeTo
     * @param padding
     */

    /**
     * Moves the ghost in a given direction, and changes the direction when it can't move further
     *
     * @param whereToGo       the direction to move in initially
     * @param whereToChangeTo the direction to move in when stuck
     * @param padding         layout padding
     */
    private void moveUntilYouCant(Direction whereToGo, Direction whereToChangeTo, double padding) {
        double step = 5;
        double leftEdge = getX();
        double topEdge = getY();
        double rightEdge = getX() + getWidth();
        double bottomEdge = getY() + getHeight();
        switch (whereToGo) {
            case LEFT:
                if (!maze.isTouchingObstacles(leftEdge, topEdge, padding)) {
                    setX(leftEdge - step);
                } else {
                    while (maze.isTouchingObstacles(getX(), getY(), padding)) {
                        setX(getX() + 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case RIGHT:
                if (!maze.isTouchingObstacles(rightEdge, topEdge, padding)) {
                    setX(leftEdge + step);
                } else {
                    while (maze.isTouchingObstacles(getX() + getWidth(), getY(), padding)) {
                        setX(getX() - 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case UP:
                if (!maze.isTouchingObstacles(leftEdge, topEdge, padding)) {
                    setY(topEdge - step);
                } else {
                    while (maze.isTouchingObstacles(getX(), getY(), padding)) {
                        setY(getY() + 1);
                    }
                    direction = Direction.LEFT;
                }
                break;
            case DOWN:
                if (!maze.isTouchingObstacles(leftEdge, bottomEdge, padding)) {
                    setY(topEdge + step);
                } else {
                    while (maze.isTouchingObstacles(getX(), getY() + getHeight(), padding)) {
                        setY(getY() - 1);
                    }
                    direction = Direction.RIGHT;
                }
                break;
        }
    }

    /**
     * Updates the ghost according to its current moving direction
     */
    public void update() {
        if (!moving) return;
        timesWalked++;
        int walkAtLeast = 4;
        switch (direction) {
            case LEFT -> {
                moveUntilYouCant(Direction.LEFT, Direction.DOWN, PADDING);
                if (timesWalked > walkAtLeast) {
                    checkPath(Direction.randomDirection(Direction.LEFT, Direction.RIGHT));
                    timesWalked = 0;
                }
            }
            case RIGHT -> {
                moveUntilYouCant(Direction.RIGHT, Direction.UP, PADDING);
                if (timesWalked > walkAtLeast) {
                    checkPath(Direction.randomDirection(Direction.LEFT, Direction.RIGHT));
                    timesWalked = 0;
                }
            }
            case UP -> {
                moveUntilYouCant(Direction.UP, Direction.LEFT, PADDING);
                if (timesWalked > walkAtLeast) {
                    checkPath(Direction.randomDirection(Direction.UP, Direction.DOWN));
                    timesWalked = 0;
                }
            }
            case DOWN -> {
                moveUntilYouCant(Direction.DOWN, Direction.RIGHT, PADDING);
                if (timesWalked > walkAtLeast) {
                    checkPath(Direction.randomDirection(Direction.UP, Direction.DOWN));
                    timesWalked = 0;
                }
            }
        }
    }

    /**
     * Set whether the ghost is scared or not
     *
     * @param scared whether the ghost is scared
     */
    public void setScared(boolean scared) {
        if (scared == this.scared) return;
        this.scared = scared;
        setFill(scared ? scaredFill : normalFill);
    }

    /**
     * Set whether the ghost is moving or not
     */
    public void startMoving() {
        this.moving = true;
    }

    /**
     * Stop the ghost
     */
    public void stopMoving() {
        this.moving = false;
    }

    /**
     * returns whether the ghost is scared
     *
     * @return whether the ghost is scared
     */
    public boolean isScared() {
        return scared;
    }
}
