package eliascregard.cells;

import java.awt.*;

public class Lava extends Cell {
    public static final Color COLOR = new Color(255, 0, 0);
    private static final Color[] COLORS = {
            new Color(255, 0, 0),
            new Color(255, 154, 0, 255),
    };
    public Lava() {
        if (Math.random() < 0.1) {
            color = COLORS[1];
        } else {
            color = COLORS[0];
        }
        heatPoints = 50;
    }
    public Color getColor() {
        return color;
    }
    public Lava copy() {
        return new Lava();
    }

    public void update(CellGrid grid, int x, int y) {
        if (heatPoints <= 0) {
            grid.setCell(x, y, new Obsidian());
            return;
        }
        Cell[][] neighbors = grid.findNeighbors(x, y);
        Point direction = new Point();
        if (!CellTypes.isSolid(neighbors[1][2].getType())) {
            direction.y++;
        }
        else if (neighbors[1][2].getType() == CellTypes.LAVA && Math.random() < 0.1) {
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

    public boolean isBurning() {
        return true;
    }
}
