package pacmangame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import pacmangame.model.GameModel;
import pacmangame.view.GameView;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartController implements Initializable {
    @FXML
    private ComboBox<Color> bgColorComboBox;
    @FXML
    private ComboBox<Color> wallColorComboBox;
    @FXML
    private CheckBox musicCheckBox;
    private final String startMusicPath = "/audio/start_music.mp3";
    private MediaPlayer player;

    @FXML
    private void loadGameScene(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Group root = new Group();

        GameModel model = new GameModel();
        GameView view = new GameView(root);
        GameController controller = new GameController(model, view);

        model.startGame();
        if (wallColorComboBox.getValue() != null)
            model.setObstaclesFill(wallColorComboBox.getValue());

        Scene gameScene = new Scene(root);
        if (bgColorComboBox.getValue() != null)
            gameScene.setFill(bgColorComboBox.getValue());
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, controller::keyPressed);
        gameScene.addEventHandler(KeyEvent.KEY_RELEASED, controller::keyReleased);
        stage.setScene(gameScene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(startMusicPath)).toExternalForm());
        player = new MediaPlayer(sound);
        player.play();
        musicCheckBox.setSelected(true);
        final String[] colorHexes = {"#633795", "#DB393D", "#EE6D2D",
                "#F0D20D", "#45AFC5", "#601545", "#F3F5E8", "#2D4E57"};

        for (ComboBox<Color> colorComboBox : List.of(bgColorComboBox, wallColorComboBox)) {
            colorComboBox.getItems().addAll(Arrays.stream(colorHexes).map(Color::valueOf).toList());
            colorComboBox.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Color color, boolean empty) {
                    super.updateItem(color, empty);
                    setTextFill(Color.WHITE);
                    if (empty || color == null) {
                        setBackground(Background.EMPTY);
                        colorComboBox.setBackground(Background.EMPTY);
                    } else {
                        Background bg = new Background(new BackgroundFill(
                                color, CornerRadii.EMPTY, Insets.EMPTY));
                        setBackground(bg);
                        colorComboBox.setBackground(bg);
                    }
                    setText("");
                }
            });

            colorComboBox.setButtonCell(colorComboBox.getCellFactory().call(null));
        }
        bgColorComboBox.setPromptText("BG Color");
        wallColorComboBox.setPromptText("Wall Color");
    }

    @FXML
    private void musicOptionChanged(ActionEvent e) {
        if (musicCheckBox.isSelected()) {
            player.play();
        } else {
            player.stop();
        }
    }
}
