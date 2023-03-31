package pacmangame.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pacmangame.model.pacman.Pacman;
import pacmangame.model.levels.Level;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pacmangame.model.obstacles.BarObstacle;
import pacmangame.observers.GameObserver;
import pacmangame.util.HighScoresManager;

import java.util.List;
import java.util.stream.IntStream;

/**
 * View component of our Game
 */
public class GameView implements GameObserver {
    private ScoreBoard scoreBoard;
    private Group root;

    /**
     * Constructor
     *
     * @param root the game UI is added to this group
     */
    public GameView(Group root) {
        this.root = root;
        scoreBoard = new ScoreBoard(root);
    }

    /**
     * Shows the game over UI, and removes the in-game elements
     *
     * @param pacman
     * @param currentLevel
     */
    @Override
    public void gameOver(Pacman pacman, Level currentLevel, int score) {
        root.getChildren().remove(pacman);
        scoreBoard.removeFrom(root);
        currentLevel.ghosts().forEach(ghost -> root.getChildren().remove(ghost));
        HighScoresManager hsManager = new HighScoresManager();
        hsManager.loadHighScores();
        Text endGame = new Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Arial", 40));
        endGame.setFill(Color.ROYALBLUE);
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setHeaderText("Game Over");
        gameOverAlert.setContentText(endGame.getText());

        if (hsManager.calculateRank(score) == -1) {
            gameOverAlert.show();
            root.getChildren().add(endGame);
            return;
        }
        TextInputDialog td = new TextInputDialog("Enter your name ");
        td.setHeaderText("You are a top scorer !!!!");
        td.show();
        td.setOnCloseRequest(e -> {
            root.getChildren().clear();
            hsManager.addHighScore(
                    td.getResult(), score);
            GridPane entries = new GridPane();
            entries.setLayoutX(root.getScene().getWidth() / 2 - BarObstacle.THICKNESS * 5);
            entries.setLayoutY(root.getScene().getHeight() / 2 - BarObstacle.THICKNESS * 2);
            entries.setGridLinesVisible(true);
            Text nameHeader = new Text("Player names");
            Text scoreHeader = new Text("High Scores");
            nameHeader.setFont(Font.font("Arial", 20));
            scoreHeader.setFont(Font.font("Arial", 20));
            entries.addRow(0, nameHeader, scoreHeader);

            int bound = hsManager.getHighScores().length;
            for (int i = 0; i < bound; i++) {
                Text highNameText = new Text("" + hsManager.getHighNames()[i]);
                Text highScoreText = new Text("" + hsManager.getHighScores()[i]);
                highNameText.setFont(Font.font("Arial", 18));
                highScoreText.setFont(Font.font("Arial", 18));
                entries.addRow(i + 1, highNameText, highScoreText);
            }
            hsManager.saveHighScores();

            gameOverAlert.show();
            root.getChildren().add(entries);
            root.getChildren().add(endGame);
        });
    }

    /**
     * @return the score board UI
     */
    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    /**
     * removes the previous UI elements and calls the gameStarted method
     *
     * @param pacman
     * @param currentLevel
     */
    @Override
    public void gameRestarted(Pacman pacman, Level currentLevel) {
        root.getChildren().clear();
        gameStarted(pacman, currentLevel);
    }

    /**
     * Adds the in-game UI elements to the root
     *
     * @param pacman
     * @param currentLevel
     */
    @Override
    public void gameStarted(Pacman pacman, Level currentLevel) {
        root.getChildren().addAll(currentLevel.cookies());
        root.getChildren().addAll(currentLevel.ghosts());
        root.getChildren().add(pacman);
        root.getChildren().addAll(currentLevel.maze().obstacles());
        scoreBoard.addTo(root);
    }
}
