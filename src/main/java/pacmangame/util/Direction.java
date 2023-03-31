package pacmangame.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A trivial Direction enum
 */
public enum Direction {
    UP, DOWN, RIGHT, LEFT;
    private static final List<Direction> VALUES =
            List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * @return a random Direction
     */
    public static Direction randomDirection() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Returns a random Direction excluding a specified direction
     *
     * @param excluded the directions to be excluded when choosing the random direction
     * @return the random direction
     */
    public static Direction randomDirection(Direction... excluded) {
        Direction dir;
        do {
            dir = VALUES.get(RANDOM.nextInt(SIZE));
        } while (Arrays.asList(excluded).contains(dir));
        return dir;
    }
}
