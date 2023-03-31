package pacmangame.model.levels;

import javafx.geometry.Point2D;
import pacmangame.model.obstacles.BarObstacle;
import pacmangame.model.items.Cookie;
import pacmangame.model.Maze;
import pacmangame.model.ghosts.Ghost;
import pacmangame.model.ghosts.GhostFactory;

import java.io.*;
import java.util.*;

import static java.util.Objects.*;
import static pacmangame.model.obstacles.BarObstacle.*;
import static pacmangame.model.obstacles.BarObstacle.Orientation.*;

/**
 * A simple Level
 */
public class Level1 extends Level {
    private static final double[][] ghostCoords =
            {
                    {18.5, 12.5},
                    {22.5, 12.5},
                    {28.5, 12.5},
                    {28.5, 9.5}
            };

    @Override
    protected Set<Cookie> generateCookies() {
        Set<Cookie> cookieSet = new HashSet<>();
        int[][] skips = {
                {5, 17},
                {1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
                {1, 21},
                {1, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 21},
                {1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
                {3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19},
                {1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
                {1, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 21},
                {1, 21},
                {1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
                {5, 17}
        };
        double xOff = 2.5;
        double yOff = 2.5;
        int nCols = 23;
        double unit = THICKNESS;
        for (int row = 0; row < skips.length; row++) {
            int[] skip = skips[row];
            for (int col = 0; col < nCols; col++) {
                boolean placeCookie = true;
                for (int skipCol : skip) {
                    if (skipCol == col) {
                        placeCookie = false;
                        break;
                    }
                }
                if (placeCookie) {
                    Cookie cookie = new Cookie(((2 * col) + xOff) * unit, (2 * row + yOff) * unit);
                    cookieSet.add(cookie);
                }
            }
        }

        return cookieSet;
    }

    @Override
    protected Maze createMaze() {
        int nCols = 49;
        int nRows = 24;
        double wallUnit = THICKNESS;
        Set<BarObstacle> obstacles = new HashSet<>();
        InputStream is = Level.class.getResourceAsStream("/levels/obstacles");
        InputStreamReader isr = new InputStreamReader(requireNonNull(is));
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
                String[] data = line.split(",");
                double col = Integer.parseInt(data[0]);
                int row = Integer.parseInt(data[1]);
                col = col >= 0 ? col : nCols + col;
                row = row >= 0 ? row : nRows + row;
                Orientation o = data[2].equals("H") ? HORIZONTAL : VERTICAL;
                int l = Integer.parseInt(data[3]);
                obstacles.add(new BarObstacle(col * wallUnit, row * wallUnit, o, l));

            } catch (Exception e) {
                System.out.println("Failed loading obstacles 1");
                System.out.println(e.getMessage());
            }

        }
        return new Maze(obstacles);
    }

    @Override
    public Point2D randomGhostHome() {
        int randomIndex = new Random().nextInt(ghostCoords.length);
        double[] homeCoord = ghostCoords[randomIndex];
        return new Point2D(homeCoord[0]*THICKNESS, homeCoord[1]*THICKNESS);
    }

    protected Set<Ghost> generateGhosts() {
        Set<Ghost> ghostSet = new HashSet<>();

        for (double[] coords : ghostCoords) {
            double x = coords[0] * THICKNESS;
            double y = coords[1] * THICKNESS;
            Ghost ghost = GhostFactory.createRandomGhost(x, y, maze());
            ghostSet.add(ghost);
        }
        return ghostSet;
    }
}
