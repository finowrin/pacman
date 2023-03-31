package pacmangame.model;


import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import pacmangame.model.gameMode.GameMode;
import pacmangame.model.gameMode.InvincibleMode;
import pacmangame.model.gameMode.NormalMode;
import pacmangame.model.ghosts.Ghost;
import pacmangame.model.items.Cookie;
import pacmangame.model.levels.Level;
import pacmangame.model.levels.LevelFactory;
import pacmangame.model.pacman.Pacman;
import pacmangame.util.Direction;
import pacmangame.observers.GameObserver;
import pacmangame.observers.ScoreObserver;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static pacmangame.model.obstacles.BarObstacle.THICKNESS;

public class GameModel {

    private static final long COOLDOWN_TIME = 5 * 1000;
    private Pacman pacman;
    private AnimationTimer moveAnim;
    private Level currentLevel;
    private int lives;
    private int score;
    private boolean gameEnded;
    private int cookiesEaten;

    private GameMode mode;

    private List<GameObserver> gameObservers;
    private List<ScoreObserver> scoreObservers;
    private boolean hasUsedInvincibleMode;
    private boolean paused;


    /**
     * Constructor
     */
    public GameModel() {
        gameObservers = new ArrayList<>();
        scoreObservers = new ArrayList<>();
        currentLevel = LevelFactory.createLevel1();
        this.pacman = new Pacman(2.5 * THICKNESS, 2.5 * THICKNESS);
        this.lives = 3;
        this.score = 0;
        this.cookiesEaten = 0;
        activateNormalMode();
        moveAnim = new AnimationTimer() {
            @Override
            public void handle(long l) {
                currentLevel.ghosts().forEach(Ghost::update);
                int step = 5;
                pacman.update(currentLevel.maze(), step);
                mode.update(GameModel.this, currentLevel, pacman);
            }
        };
    }

    /**
     * Set one life less
     */
    public void lifeLost() {
        int delay = 1000; // delay before starting again
        pacman.stop();
        currentLevel.ghosts().forEach(Ghost::stopMoving);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.pacman.setCenterX(2.5 * THICKNESS);
        this.pacman.setCenterY(2.5 * THICKNESS);
        lives--;
        score -= 10;

        scoreObservers.forEach(
                scoreObserver -> {
                    scoreObserver.updateScore(score);
                    scoreObserver.updateLives(lives);
                });
        if (lives <= 0) {
            this.endGame();
        }
    }

    /**
     * Set the fill of the obstacles of the current level.
     *
     * @param fill
     */
    public void setObstaclesFill(Paint fill) {
        currentLevel.maze().obstacles().forEach(obstacle -> obstacle.setFill(fill));
    }

    /**
     * Start the game
     */
    public void startGame() {
        gameObservers.forEach(gameObserver ->
                gameObserver.gameStarted(pacman, currentLevel));
        scoreObservers.forEach(scoreObserver -> {
            scoreObserver.updateScore(score);
            scoreObserver.updateLives(lives);
        });
    }

    /**
     * End the game
     */
    public void endGame() {
        this.gameEnded = true;
        gameObservers.forEach(gameObserver -> gameObserver.gameOver(pacman, currentLevel, score));
    }

    /**
     * Restart the game
     */
    public void restartGame() {
        if (gameEnded) {
            currentLevel = LevelFactory.createLevel1();
            gameObservers.forEach(gameObserver ->
                    gameObserver.gameRestarted(pacman, currentLevel));

            this.pacman.setCenterX(2.5 * THICKNESS);
            this.pacman.setCenterY(2.5 * THICKNESS);
            this.lives = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
            activateNormalMode();
            scoreObservers.forEach(scoreObserver -> {
                scoreObserver.updateScore(score);
                scoreObserver.updateLives(lives);
            });
        }
    }

    /**
     * Moves the pacman
     *
     * @param direction direction to move
     */
    public void movePacman(Direction direction) {
        if (!pacman.isMoving()) {
            moveAnim.start();
        }
        currentLevel.ghosts().forEach(Ghost::startMoving);
        pacman.setMoving(direction);
    }

    /**
     * Stops the pacman
     */
    public void stopPacman() {
        pacman.stop();
    }

    /**
     * Add a game observer
     *
     * @param gameObserver game observer to be notified of game changes
     */
    public void addGameObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    /**
     * Add a score observer
     *
     * @param scoreObserver score observer
     */
    public void addScoreObserver(ScoreObserver scoreObserver) {
        scoreObservers.add(scoreObserver);
    }

    /**
     * Called when a cookie is eaten; hides the cookie and updates the score
     *
     * @param cookie
     */
    public void cookieEaten(Cookie cookie) {
        this.score += cookie.getValue();
        this.cookiesEaten++;
        scoreObservers.forEach(scoreObserver -> scoreObserver.updateScore(score));
        cookie.hide();
        if (this.cookiesEaten == currentLevel.cookies().size()) {
            this.endGame();
        }
    }

    /**
     * Respawns the ghost at a random location chosen from the starting locations of this level's ghosts
     *
     * @param ghost the ghost to be respawned
     */
    public void ghostRespawn(Ghost ghost) {
        if (!currentLevel.ghosts().contains(ghost))
            return;
        Point2D homePoint = currentLevel.randomGhostHome();
        ghost.setX(homePoint.getX());
        ghost.setY(homePoint.getY());
    }

    /**
     * Activate the invincible mode
     */
    public void activateInvincibleMode() {
        if (hasUsedInvincibleMode)
            return;
        mode = new InvincibleMode();
        Timer timer = new Timer();
        TimerTask cooldown = new TimerTask() {
            @Override
            public void run() {
                activateNormalMode();
                cancel();
            }
        };
        hasUsedInvincibleMode = true;
        timer.schedule(cooldown, COOLDOWN_TIME);
    }

    /**
     * Activate the normal mode
     */
    public void activateNormalMode() {
        mode = new NormalMode();
    }

    /**
     * Pause/Resume the game
     */
    public void pause() {
        if (moveAnim == null || currentLevel == null || currentLevel.ghosts() == null)
            return;
        if (paused) {
            moveAnim.start();
            currentLevel.ghosts().forEach(Ghost::startMoving);
        } else {
            moveAnim.stop();
            currentLevel.ghosts().forEach(Ghost::stopMoving);
        }
        paused = !paused;
    }
}
