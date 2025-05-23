import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @brief A class that represents the view for the Dragon Curve fractal.
 * 
 * This class contains the UI components for displaying the Dragon Curve fractal,
 * including a canvas for drawing, a slider for adjusting the depth, and a progress
 * indicator for showing the generation progress.
 */
public class DragonCurveView {
    public final BorderPane root = new BorderPane();
    public final Pane canvas = new Pane();
    public final Slider slider = new Slider(0, 14, 10);
    public final ProgressIndicator progressIndicator = new ProgressIndicator();
    final double[] dragDelta = new double[2];
    
    /**
     * @brief Constructor for the DragonCurveView class.
     * 
     * This constructor initializes the UI components and sets up event handlers
     * for mouse dragging and scrolling on the canvas.
     */
    public DragonCurveView() {
        root.setCenter(canvas);
        root.setBottom(slider);
        root.setTop(progressIndicator);
        progressIndicator.setVisible(false);

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(2);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);

        canvas.setOnScroll(e -> {
            double scale = e.getDeltaY() > 0 ? 1.1 : 0.9;
            canvas.setScaleX(canvas.getScaleX() * scale);
            canvas.setScaleY(canvas.getScaleY() * scale);
        });

        canvas.setOnMousePressed(e -> {
            dragDelta[0] = e.getSceneX() - canvas.getTranslateX();
            dragDelta[1] = e.getSceneY() - canvas.getTranslateY();
        });

        canvas.setOnMouseDragged(e -> {
            canvas.setTranslateX(e.getSceneX() - dragDelta[0]);
            canvas.setTranslateY(e.getSceneY() - dragDelta[1]);
        });
    }
}