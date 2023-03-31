package pacmangame.model.gameMode;

import javafx.scene.Node;
import pacmangame.model.GameModel;
import pacmangame.model.levels.Level;
import pacmangame.model.pacman.Pacman;

/**
 * In Normal mode, pacman eats cookies when it touches them, and dies when it touches a ghost
 */
public class NormalMode extends GameMode {

    @Override
    void checkGhostCollision(GameModel gameModel, Level level, Pacman pacman) {
        level.ghosts().forEach(ghost -> ghost.setScared(false));
        level.ghosts().stream().filter(pacman::checkCollision)
                .forEach(ghost -> gameModel.lifeLost());
    }

    @Override
    void checkCookieCollision(GameModel gameModel, Level level, Pacman pacman) {
        level.cookies().stream().filter(pacman::checkCollision)
                .filter(Node::isVisible).forEach(gameModel::cookieEaten);
    }
}
