package eliascregard.cells;

import java.awt.*;

public class Sand extends Cell {
    public static final Color COLOR = new Color(238, 163, 0);
     private static final Color[] COLORS = {
            new Color(238, 163, 0),
            new Color(203, 88, 0),
     };
     private final Color color;

    public Sand() {
        color = COLORS[(int) (Math.random() * 2)];
    }

    public Color getColor() {
        return color;
    }

    public void update(CellGrid grid, int x, int y) {
        Cell[][] neighbors = grid.findNeighbors(x, y);
        Point direction = new Point();
        if (!CellTypes.isSolid(neighbors[1][2].getType())) {
            direction.y++;
        }
        else {
            if (!CellTypes.isSolid(neighbors[0][1].getType()) && !CellTypes.isSolid(neighbors[0][2].getType()) &&
                    !CellTypes.isSolid(neighbors[2][1].getType()) && !CellTypes.isSolid(neighbors[2][2].getType())) {
                direction.x += Math.random() < 0.5 ? -1 : 1;
            }
            else if (!CellTypes.isSolid(neighbors[0][1].getType()) && !CellTypes.isSolid(neighbors[0][2].getType())) {
                direction.x--;
            }
            else if (!CellTypes.isSolid(neighbors[2][1].getType()) && !CellTypes.isSolid(neighbors[2][2].getType())) {
                direction.x++;
            }
        }
        grid.switchCells(x, y, x + direction.x, y + direction.y);
    }

}
