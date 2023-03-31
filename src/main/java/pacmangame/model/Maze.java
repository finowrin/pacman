package pacmangame.model;


import pacmangame.model.obstacles.BarObstacle;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Maze is simply an encapsulation of obstacles
 */
public class Maze {

    private final Set<BarObstacle> obstacles;

    /**
     * Default Constructor
     */
    public Maze() {
        obstacles = new HashSet<>();
    }

    /**
     * Initializes a maze with the given obstacles
     * @param obstacles obstacles which should compose this maze
     */
    public Maze(Collection<BarObstacle> obstacles) {
        this();
        this.obstacles.addAll(obstacles);
    }

    /**
     * @return all the obstacles in this maze
     */
    public Collection<BarObstacle> obstacles() {
        return obstacles;
    }

    /**
     * Add obstacles to this maze
     * @param obstacles obstacles to be added
     */
    public void addObstacles(Collection<BarObstacle> obstacles) {
        this.obstacles.addAll(obstacles);
    }

    /**
     * Checks if point is touching obstacles
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchingObstacles(double x, double y, double padding) {
        return obstacles.stream()
                .anyMatch(barObstacle -> barObstacle.isTouching(x, y, padding));
    }

    /**
     * lets you know if there's an obstacle in the current coordinate
     *
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @return
     */
    public Boolean hasObstacle(double fromX, double toX, double fromY, double toY) {
        boolean isTouching = false;
        for (double x = fromX; x < toX; x++) {
            for (double y = fromY; y < toY; y++) {
                if (this.isTouchingObstacles(x, y, 0)) isTouching = true;
            }
        }
        return isTouching;
    }
}
