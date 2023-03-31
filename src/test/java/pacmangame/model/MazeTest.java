package pacmangame.model;

import org.junit.jupiter.api.Test;
import pacmangame.model.obstacles.BarObstacle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pacmangame.model.obstacles.BarObstacle.Orientation.HORIZONTAL;
import static pacmangame.model.obstacles.BarObstacle.THICKNESS;

public class MazeTest {

    @Test
    public void testAddObstacles() {
        Maze maze = new Maze();//empty
        BarObstacle obs1 = new BarObstacle(0, 0, HORIZONTAL, 10);
        BarObstacle obs2 = new BarObstacle(0, 20, HORIZONTAL, 10);
        BarObstacle obs3 = new BarObstacle(0, 40, HORIZONTAL, 10);
        maze.addObstacles(List.of(obs1, obs2, obs3));
        assertEquals(maze.obstacles().size(), 3);

        assertTrue(maze.hasObstacle(0, 10 * THICKNESS, 0, THICKNESS));
        assertTrue(maze.hasObstacle(0, 10 * THICKNESS, 20, 20 + THICKNESS));
        assertTrue(maze.hasObstacle(0, 10 * THICKNESS, 40, 40 + THICKNESS));

        assertFalse(maze.hasObstacle(0, 10 * THICKNESS, 60, THICKNESS));

    }
}
