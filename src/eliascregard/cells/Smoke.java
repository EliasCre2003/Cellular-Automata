package eliascregard.cells;

import java.awt.*;

public class Smoke extends Cell {

public static final Color COLOR = new Color(66, 66, 66);

    public Smoke() {
        color = new Color(66, 66, 66);
    }
    public Color getColor() {
        return color;
    }
    public Smoke copy() {
        return new Smoke();
    }

    public void update(CellGrid grid, int x, int y) {
        if (Math.random() < 0.001) {
            grid.setCell(x, y, new Air());
        }
        Cell[][] neighbors = grid.findNeighbors(x, y);
        Point direction = new Point();
        if (neighbors[1][0].getType() == CellTypes.AIR && Math.random() < 0.1) {
            direction.y--;
        }
        else if (neighbors[1][0].getType() == CellTypes.SMOKE) {
            if (neighbors[0][1].getType() == CellTypes.AIR && neighbors[2][1].getType() == CellTypes.AIR) {
                direction.x += Math.random() < 0.5 ? -1 : 1;
            }
            else if (neighbors[0][1].getType() == CellTypes.AIR) {
                direction.x--;
            }
            else if (neighbors[2][1].getType() == CellTypes.AIR) {
                direction.x++;
            }
        }
        grid.switchCells(x, y, x + direction.x, y + direction.y);
    }
}
