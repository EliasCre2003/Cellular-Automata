package eliascregard.cells;

import java.awt.*;

public class Thermite extends Cell {

    public static final Color COLOR = new Color(129, 48, 47);
    private boolean isBurning = false;
    private static final Color[] COLORS = {
            new Color(129, 48, 47),
            new Color(255, 144, 0)
    };


    public Thermite() {
        color = COLOR;
    }

    public void update(CellGrid grid, int x, int y) {
        Cell[][] neighbors = grid.findNeighbors(x, y);
        Point direction = new Point();
        if (isBurning && Math.random() < 0.002) {
            grid.setCell(x, y, new Smoke());
        }
        if (neighbors[1][2].isBurning() || neighbors[1][0].isBurning() || neighbors[0][1].isBurning() ||
            neighbors[2][1].isBurning()) {
            setBurning(true);
        }
        if (!CellTypes.isSolid(neighbors[1][2].getType())) {
            direction.y++;
        }
        else {
            if (neighbors[1][2].getType() == CellTypes.IRON && !isBurning) {
                setBurning(true);
            }
            else if (neighbors[1][2].getType() == CellTypes.IRON && isBurning) {
                if (Math.random() < 0.01) {
                    grid.setCell(x, y + 1, new Air());
                    direction.y++;
                }
            }
            else if (!CellTypes.isSolid(neighbors[0][1].getType()) && !CellTypes.isSolid(neighbors[0][2].getType()) &&
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

    private void setBurning(boolean burning) {
        isBurning = burning;
        if (burning) {
            color = COLORS[1];
        } else {
            color = COLORS[0];
        }
    }

    public boolean isBurning() {
        return isBurning;
    }

}
