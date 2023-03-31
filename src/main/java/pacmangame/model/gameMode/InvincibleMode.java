package pacmangame.model.gameMode;

import javafx.scene.Node;
import pacmangame.model.GameModel;
import pacmangame.model.levels.Level;
import pacmangame.model.pacman.Pacman;

/**
 * In invincible, ghosts are scared and die(respawn) when they touch the pacman.
 * Pacman eats cookies when it touches them.
 */
public class InvincibleMode extends GameMode {
    @Override
    void checkGhostCollision(GameModel gameModel, Level level, Pacman pacman) {
        level.ghosts().forEach(ghost -> ghost.setScared(true));
        level.ghosts().stream().filter(pacman::checkCollision)
                .forEach(gameModel::ghostRespawn);
    }

    @Override
    void checkCookieCollision(GameModel gameModel, Level level, Pacman pacman) {
        level.cookies().stream().filter(pacman::checkCollision)
                .filter(Node::isVisible).forEach(gameModel::cookieEaten);
    }
}
