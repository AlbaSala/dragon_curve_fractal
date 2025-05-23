import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

/**
 * @brief A JavaFX application that generates and displays a Dragon Curve fractal.
 */
public class DragonCurveApp extends Application {
    private final DragonCurveView view = new DragonCurveView();
    private final DragonCurveGenerator generator = new DragonCurveGenerator();
    
    /**
     * @brief Initializes the JavaFX application.
     * 
     * @param stage The primary stage for this application.
     * 
     * @pre stage != null
     * @post Initializes the JavaFX application with a slider and canvas for drawing the Dragon Curve.
     */
    @Override
    public void start(Stage stage) {
        view.slider.valueProperty().addListener((_, _, newVal) -> drawAnimatedCurve(newVal.intValue()));
        drawAnimatedCurve((int) view.slider.getValue());

        Scene scene = new Scene(view.root, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("Dragon Curve Fractal");
        stage.show();
    }
    
    /**
     * @brief Draws the Dragon Curve fractal 
     * 
     * @param depth The depth of recursion for the Dragon Curve.
     * 
     * @pre depth >= 0
     * @post Draws the Dragon Curve fractal on the canvas.
     */
    private void drawAnimatedCurve(int depth) {
        view.canvas.getChildren().clear();
        view.progressIndicator.setVisible(true);

        Task<List<Line>> task = new Task<>() {
            @Override
            protected List<Line> call() {
                return generator.generate(depth, 400, 400, 600, 400);
            }

            @Override
            protected void succeeded() {
                List<Line> lines = getValue();
                Timeline timeline = new Timeline();

                for (int i = 0; i < lines.size(); i++) {
                    Line line = lines.get(i);
                    line.setStroke(Color.hsb(i * 360.0 / lines.size(), 1.0, 1.0));
                    line.setStrokeWidth(1.0);
                    KeyFrame frame = new KeyFrame(Duration.millis(i * 10), _ -> view.canvas.getChildren().add(line));
                    timeline.getKeyFrames().add(frame);
                }

                timeline.setOnFinished(e -> view.progressIndicator.setVisible(false));
                timeline.play();
            }

            @Override
            protected void failed() {
                view.progressIndicator.setVisible(false);
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * @brief The main method to launch the JavaFX application.
     * 
     * @param args Command-line arguments (not used).
     * 
     * @pre --
     * @post Launches the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}