package pacmangame.model.gameMode;

import pacmangame.model.GameModel;
import pacmangame.model.levels.Level;
import pacmangame.model.pacman.Pacman;

/**
 * Game Mode ADT for our pacman game. This class exists to implement various modes for the pacman using State Pattern
 */
public abstract class GameMode {

    /**
     * Updates the interaction(e.g. collisions) of pacman and other objects in the level
     *
     * @param game   The game model
     * @param level  The current level
     * @param pacman The pacman
     */
    public void update(GameModel game, Level level, Pacman pacman) {
        checkGhostCollision(game, level, pacman);
        checkCookieCollision(game, level, pacman);
    }

    /**
     * Checks for ghost collisions with other objects in the level
     *
     * @param gameModel the game model
     * @param level     the current level
     * @param pacman    the pacman
     */
    abstract void checkGhostCollision(GameModel gameModel, Level level, Pacman pacman);

    /**
     * Checks for cookie collisions with other objects in the level
     *
     * @param gameModel the game model
     * @param level     the current level
     * @param pacman    the pacman
     */
    abstract void checkCookieCollision(GameModel gameModel, Level level, Pacman pacman);
}
