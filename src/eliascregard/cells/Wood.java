package eliascregard.cells;

import java.awt.*;

public class Wood extends Cell {

    public static final Color COLOR = new Color(139, 69, 19);

    private static final Color[] COLORS = {
            new Color(139, 69, 19),
            new Color(59, 40, 0)
    };

    public Wood(){}

    public Color getColor(int y) {
        return COLORS[y % 2];
    }

}
