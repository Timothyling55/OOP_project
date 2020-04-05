package libs;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameText {
    private String text;

    private Color fillColor;
    private Color strokeColor;

    public GameText(Color fillColor, Color strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    public String getText() {
        return text;
    }

    public void setText(GraphicsContext gc, String text, int fontSize, int xPos, int yPos) {
        gc.setFill(this.fillColor);
        gc.setStroke(this.strokeColor);
        gc.setLineWidth(2);

        Font font = Font.font("Times New Roman", FontWeight.BOLD, fontSize);
        gc.setFont(font);

        gc.fillText(text, xPos, yPos);
        gc.strokeText(text, xPos, yPos);

        this.text = text;
    }
}
