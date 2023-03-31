package pacmangame.observers;

import pacmangame.model.pacman.Pacman;
import pacmangame.model.levels.Level;

/**
 * Should be implemented by a class which needs to be notified of any changes in our pacman game
 */
public interface GameObserver {
    /**
     * called when the game ends
     * @param pacman
     * @param currentLevel
     */
    void gameOver(Pacman pacman, Level currentLevel, int score);

    /**
     * called when the game restarts
     * @param pacman
     * @param currentLevel
     */
    void gameRestarted(Pacman pacman, Level currentLevel);

    /**
     * calls when the game starts
     * @param pacman
     * @param currentLevel
     */
    void gameStarted(Pacman pacman, Level currentLevel);
}
