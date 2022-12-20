package eliascregard.cells;

import java.awt.*;

public class Iron extends Cell {

    public static final Color COLOR = new Color(94, 94, 94);

    public Iron() {
        color = COLOR;
    }

    public Point update(Cell[][] neighbors) {
        super.update(neighbors);
        return new Point();
    }

}
