package pacmangame.model.pacman;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Test;
import pacmangame.model.Maze;
import pacmangame.util.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class PacmanTest {

    @Test
    public void testMoveLeft() {
        Maze maze = new Maze(); // Empty maze;
        Pacman pacman = new Pacman(0, 0);
        pacman.setMoving(Direction.LEFT);

        assertEquals(pacman.getCenterX(), 0); // The pacman shouldn't move just by setMoving
        assertNotNull(pacman.getFill()); // The pacman sprite shouldn't be null

        pacman.update(maze, 1); // Move one step towards left
        assertEquals(pacman.getCenterX(), -1); // The pacman moves one step towards left
        assertEquals(pacman.getCenterY(), 0); // No change in y
    }

    @Test
    public void testMoveRight() {
        Maze maze = new Maze(); // Empty maze;
        Pacman pacman = new Pacman(0, 0);
        pacman.setMoving(Direction.RIGHT);

        assertEquals(pacman.getCenterX(), 0); // The pacman shouldn't move just by setMoving
        assertNotNull(pacman.getFill()); // The pacman sprite shouldn't be null

        pacman.update(maze, 1); // Move one step towards left
        assertEquals(pacman.getCenterX(), 1); // The pacman moves one step towards right
        assertEquals(pacman.getCenterY(), 0); // No change in y
    }

    @Test
    public void testMoveUp() {
        Maze maze = new Maze(); // Empty maze;
        Pacman pacman = new Pacman(0, 0);
        pacman.setMoving(Direction.UP);

        assertEquals(pacman.getCenterX(), 0); // The pacman shouldn't move just by setMoving
        assertNotNull(pacman.getFill()); // The pacman sprite shouldn't be null

        pacman.update(maze, 1); // Move one step towards left
        assertEquals(pacman.getCenterY(), -1); // The pacman moves one step towards up
        assertEquals(pacman.getCenterX(), 0); // No change in x
    }

    @Test
    public void testMoveDown() {
        Maze maze = new Maze(); // Empty maze;
        Pacman pacman = new Pacman(0, 0);
        pacman.setMoving(Direction.DOWN);

        assertEquals(pacman.getCenterX(), 0); // The pacman shouldn't move just by setMoving
        assertNotNull(pacman.getFill()); // The pacman sprite shouldn't be null

        pacman.update(maze, 1); // Move one step towards left
        assertEquals(pacman.getCenterY(), 1); // The pacman moves one step towards down
        assertEquals(pacman.getCenterX(), 0); // No change in x
    }

    @Test
    public void testStop() {
        Maze maze = new Maze(); // Empty maze;
        Pacman pacman = new Pacman(0, 0);
        pacman.setMoving(Direction.LEFT);

        pacman.update(maze, 1); //move one step left
        pacman.stop(); // stop, so no further updates should move the pacman
        assertFalse(pacman.isMoving()); // the pacman must not be moving now

        pacman.update(maze, 1); // this should not move the pacman, as it is stopped`
        assertEquals(pacman.getCenterX(), -1); // two updates, but we stopped pacman after first update,
        // so this should be -1
        assertEquals(pacman.getCenterY(), 0); // no movement in y direction anyway

    }

    @Test
    public void testCollision() {
        Pacman pacman = new Pacman(0, 0, 10);
        Circle collider = new Circle(10, 0, 5); // collider touching pacman
        assertTrue(pacman.checkCollision(collider));

        collider = new Circle(20, 0, 5); // collider not touching pacman
        assertFalse(pacman.checkCollision(collider));
    }
}
