package eliascregard.cells;

import eliascregard.physics.Vector2D;

import java.awt.*;

public class Air extends Cell {

    public static final Color COLOR = new Color(255, 255, 255, 0);
    public Air() {
        color = COLOR;
    }
    public Color getColor() {
        return color;
    }

    public Air copy() {
        return new Air();
    }

}
