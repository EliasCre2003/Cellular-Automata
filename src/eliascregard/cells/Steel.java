package eliascregard.cells;

import java.awt.*;

public class Steel extends Cell {

    public static final Color COLOR = new Color(152, 152, 152);

    public Steel() {
        color = COLOR;
    }

    public Point update(Cell[][] neighbors) {
        super.update(neighbors);
        return new Point();
    }

}
