package pacmangame.model.levels;

import javafx.geometry.Point2D;
import pacmangame.model.items.Cookie;
import pacmangame.model.Maze;
import pacmangame.model.ghosts.Ghost;

import java.util.Set;

/**
 * Level ADT comprises the game items other than the player
 */
public abstract class Level {
    private Maze maze;
    private Set<Ghost> ghosts;
    private Set<Cookie> cookies;

    /**
     * @return the maze in this level
     */
    public Maze maze() {
        return maze;
    }

    /**
     * @return the ghosts in this level
     */
    public Set<Ghost> ghosts() {
        return ghosts;
    }

    /**
     * @return the cookies in this level
     */
    public Set<Cookie> cookies() {
        return cookies;
    }

    /**
     * Default constructor
     */
    public Level() {
        maze = createMaze();
        cookies = generateCookies();
        ghosts = generateGhosts();
    }

    /**
     * Generates the ghosts
     *
     * @return the generated ghosts
     */
    protected abstract Set<Ghost> generateGhosts();


    /**
     * Generates the cookies
     *
     * @return the generated cookies
     */
    protected abstract Set<Cookie> generateCookies();


    /**
     * Generates the maze
     *
     * @return the generated maze
     */
    protected abstract Maze createMaze();

    /**
     * @return a random point chosen from the starting point of ghosts in this level
     */
    public abstract Point2D randomGhostHome();
}
