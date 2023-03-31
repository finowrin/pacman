package pacmangame.model.ghosts;

import pacmangame.model.Maze;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Ghost Factory helps spawn various types of ghosts without knowing the details.
 * This class implements the Factory Pattern (currently it uses various images to create ghosts,
 * but in future if there are many types of ghosts, we can add corresponding 'create' functions)
 * It will be helpful if in the future we have subclasses for each type of Ghost.
 */
public class GhostFactory {
    private static HashMap<Color, ImagePattern> imgMap;
    private static ImagePattern scared;

    // load the image map before anything else
    static {
        loadImageMap();
    }

    /**
     * Create a random type(or color) ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createRandomGhost(double x, double y, Maze maze) {
        Color[] colors = imgMap.keySet().toArray(new Color[0]);
        Color randomColor = colors[new Random().nextInt(colors.length)];
        return new Ghost(x, y, imgMap.get(randomColor), maze);
    }

    /**
     * Create a pink ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createPinkGhost(double x, double y, Maze maze) {
        return new Ghost(x, y, imgMap.get(Color.PINK), maze);
    }

    /**
     * Create a green ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createGreenGhost(double x, double y, Maze maze) {
        return new Ghost(x, y, imgMap.get(Color.GREEN), maze);
    }

    /**
     * Create a yellow ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createYellowGhost(double x, double y, Maze maze) {
        return new Ghost(x, y, imgMap.get(Color.YELLOW), maze);
    }

    /**
     * Create a blue ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createBlueGhost(double x, double y, Maze maze) {
        return new Ghost(x, y, imgMap.get(Color.BLUE), maze);
    }

    /**
     * Create a purple ghost at the specified location
     *
     * @param x    x coordinate of the location
     * @param y    y coordinate of the location
     * @param maze the maze in which the ghost will move
     * @return the created ghost
     */
    public static Ghost createPurpleGhost(double x, double y, Maze maze) {
        return new Ghost(x, y, imgMap.get(Color.PURPLE), maze);
    }


    /**
     * Loads the image map
     */
    private static void loadImageMap() {
        imgMap = new HashMap<>();
        imgMap.put(Color.PINK, loadImagePattern("/images/ghosts/pink.png"));
        imgMap.put(Color.GREEN, loadImagePattern("/images/ghosts/green.png"));
        imgMap.put(Color.YELLOW, loadImagePattern("/images/ghosts/yellow.png"));
        imgMap.put(Color.BLUE, loadImagePattern("/images/ghosts/blue.png"));
        imgMap.put(Color.PURPLE, loadImagePattern("/images/ghosts/purple.png"));
        scared = loadImagePattern("/images/ghosts/scared.png");
    }

    /**
     * Returns the image pattern for scared ghosts.
     *
     * @return Image Pattern for scared ghosts
     */
    protected static ImagePattern getScaredPattern() {
        return scared;
    }

    /**
     * utility function to make an image pattern with the image at the given path
     *
     * @param path file path of the image to be used for creating the image pattern
     * @return the Image pattern with the specified image
     */
    private static ImagePattern loadImagePattern(String path) {
        InputStream imgStream = GhostFactory.class.getResourceAsStream(path);
        Image image = new Image(Objects.requireNonNull(imgStream));
        return new ImagePattern(image);
    }
}