package eliascregard.cells;

import java.awt.*;

public class Water extends Cell {

    public static final Color COLOR = new Color(18, 102, 255);

    public Water() {
        color = COLOR;
    }

    public void update(CellGrid grid, int x, int y) {
        Cell[][] neighbors = grid.findNeighbors(x, y);
        Point direction = new Point();

        if (CellTypes.isGas(neighbors[1][2].getType())) {
            direction.y++;
        } else if (CellTypes.isGas(neighbors[0][1].getType()) && CellTypes.isGas(neighbors[2][1].getType())) {
            direction.x += Math.random() < 0.5 ? -1 : 1;
        } else if (CellTypes.isGas(neighbors[2][1].getType())) {
            direction.x++;
        } else if (CellTypes.isGas(neighbors[0][1].getType())) {
            direction.x--;
        }

        if (direction.x != 0 || direction.y != 0) {
            grid.switchCells(x, y, x + direction.x, y + direction.y);
        }
    }

}