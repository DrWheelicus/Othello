package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.Serializable;

public class Piece implements Serializable {
    protected boolean isPlaced = false;
    protected int isWhite = 0;
    private Circle circle = new Circle(20, 20, 30);

    public void toggle() {
        if (isWhite == 0) {
            isWhite = 1;
            circle.setFill(Color.BLUE);
        }
        else if (isWhite == 1) {
            isWhite = 0;
            circle.setFill(Color.BLACK);
        }
    }

    public void place(int player) {
        if (player == 1) {
            toggle();
        }
        isPlaced = true;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "Piece [isPlaced=" + isPlaced + ", isWhite=" + isWhite + "]";
    }
}