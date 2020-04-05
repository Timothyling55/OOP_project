package libs;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public interface CoreFunc {
    public void init(GraphicsContext gc);
    public void animate(GraphicsContext gc, int time,  ArrayList input);
    public void mouseClick(MouseEvent e);
    public void mouseMoved(MouseEvent e);
}
