module com.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens pacmangame to javafx.fxml;
    exports pacmangame;
    exports pacmangame.model;
    opens pacmangame.model to javafx.fxml;
    exports pacmangame.controller;
    opens pacmangame.controller to javafx.fxml;
    exports pacmangame.view;
    opens pacmangame.view to javafx.fxml;
    exports pacmangame.model.levels;
    opens pacmangame.model.levels to javafx.fxml;
    exports pacmangame.model.ghosts;
    opens pacmangame.model.ghosts to javafx.fxml;
    exports pacmangame.model.obstacles;
    opens pacmangame.model.obstacles to javafx.fxml;
    exports pacmangame.model.items;
    opens pacmangame.model.items to javafx.fxml;
    exports pacmangame.model.pacman;
    opens pacmangame.model.pacman to javafx.fxml;
    exports pacmangame.util;
    opens pacmangame.util to javafx.fxml;
    exports pacmangame.model.gameMode;
    opens pacmangame.model.gameMode to javafx.fxml;
    exports pacmangame.observers;
    opens pacmangame.observers to javafx.fxml;
}