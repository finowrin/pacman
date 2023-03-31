package pacmangame.view;


import pacmangame.model.obstacles.BarObstacle;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pacmangame.observers.ScoreObserver;

/**
 * Score board UI
 */
public class ScoreBoard implements ScoreObserver {

    private Text score;
    private Text lives;

    /**
     * Creates a blank score board, and adds it to the root
     *
     * @param root the root
     */
    public ScoreBoard(Group root) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: ");
        this.lives = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28, "Lifes: ");
        score.setFill(Color.MAGENTA);
        score.setFont(Font.font("Arial", 30));

        lives.setFill(Color.MAROON);
        lives.setFont(Font.font("Arial", 30));

        root.getChildren().add(score);
        root.getChildren().add(lives);
    }

    /**
     * Remove this score board from the root
     *
     * @param root the root
     */
    public void removeFrom(Group root) {
        root.getChildren().remove(this.score);
        root.getChildren().remove(this.lives);
    }

    /**
     * adds this scoreboard to the root
     *
     * @param root the root
     */
    public void addTo(Group root) {
        if (root.getChildren().contains(this.score))
            return;
        root.getChildren().add(this.score);
        root.getChildren().add(this.lives);
    }

    @Override
    public void updateScore(int score) {
        this.score.setText("Score : " + score);
    }

    @Override
    public void updateLives(int lives) {
        this.lives.setText("Lives : " + lives);
    }
}
