import javafx.scene.shape.Line;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief A class that generates the Dragon Curve fractal.
 */
public class DragonCurveGenerator {
    private final List<Line> lines = new ArrayList<>();
    
    /**
     * @brief Generates the Dragon Curve fractal lines.
     * 
     * @param depth The depth of recursion for the Dragon Curve.
     * @param x1 The starting x-coordinate.
     * @param y1 The starting y-coordinate.
     * @param x2 The ending x-coordinate.
     * @param y2 The ending y-coordinate.
     * 
     * @pre depth >= 0
     * @post Generates the Dragon Curve fractal lines and stores them in the lines list.
     */
    public List<Line> generate(int depth, double x1, double y1, double x2, double y2) {
        lines.clear();
        generateDragonLines(depth, x1, y1, x2, y2, true);
        return lines;
    }
    
    /**
     * @brief Recursively generates the Dragon Curve lines.
     * 
     * @param level The current recursion level.
     * @param x1 The starting x-coordinate.
     * @param y1 The starting y-coordinate.
     * @param x2 The ending x-coordinate.
     * @param y2 The ending y-coordinate.
     * @param turnLeft Indicates whether to turn left or right.
     * 
     * @pre level >= 0
     * @post Generates the Dragon Curve lines recursively.
     */
    private void generateDragonLines(int level, double x1, double y1, double x2, double y2, boolean turnLeft) {
        if (level == 0) {
            lines.add(new Line(x1, y1, x2, y2));
        } else {
            double midX = (x1 + x2) / 2;
            double midY = (y1 + y2) / 2;
            double dx = x2 - x1;
            double dy = y2 - y1;
            double newX, newY;

            if (turnLeft) {
                newX = midX - dy / 2;
                newY = midY + dx / 2;
            } else {
                newX = midX + dy / 2;
                newY = midY - dx / 2;
            }

            generateDragonLines(level - 1, x1, y1, newX, newY, true);
            generateDragonLines(level - 1, newX, newY, x2, y2, false);
        }
    }
}