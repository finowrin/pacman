package pacmangame.controller;


import pacmangame.model.GameModel;
import pacmangame.util.Direction;
import pacmangame.view.GameView;
import javafx.scene.input.KeyEvent;

/**
 * Controller component of our pacman game.
 * Handles the user inputs
 */
public class GameController {
    private GameModel model;
    private GameView view;

    /**
     * Initializes the controller with the view and the model
     *
     * @param model the game model
     * @param view  the game view
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        model.addGameObserver(view);
        model.addScoreObserver(view.getScoreBoard());
    }

    /**
     * When the user presses a key,
     *
     * @param keyEvent
     */
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT -> model.movePacman(Direction.LEFT);
            case RIGHT -> model.movePacman(Direction.RIGHT);
            case UP -> model.movePacman(Direction.UP);
            case DOWN -> model.movePacman(Direction.DOWN);
            case ESCAPE -> model.restartGame();
            case SPACE -> model.activateInvincibleMode();
            case P -> model.pause();
        }
    }

    /**
     * When the user releases the key
     *
     * @param keyEvent
     */
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT, RIGHT, UP, DOWN -> model.stopPacman();
        }
    }
}
