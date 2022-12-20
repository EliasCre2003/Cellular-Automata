package eliascregard.cells;

import java.awt.*;

public class Cell {

    protected Color color;

    public Point update(Cell[][] neighbors) {
        return new Point();
    }

    public void update(CellGrid grid, int x, int y) {
    }

    public Class<? extends Cell> getType() {
        return getClass();
    }

    public Cell copy() {
        return new Cell();
    }

    public Color getColor() {
        return this.color;
    }
    public Color getColor(int y) {
        return this.color;
    }

    protected boolean isBurning() {
        return false;
    }
}