package eliascregard.cells;

import java.awt.*;

public class CellGrid {

    private final Cell[][] cells;
    private final PixelGrid pixelGrid;
    private final int width, height;
    private double temperature;

    public CellGrid(int width, int height) {
        cells = new Cell[width][height];
        pixelGrid = new PixelGrid(width, height);
        this.width = width;
        this.height = height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setCell(x, y, new Air());
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) return Edge.EDGE;
        return cells[x][y];
    }

    public void setCell(int x, int y, Cell cell) {
        if (x < 0 || x >= width || y < 0 || y >= height) return;
        cells[x][y] = cell;
        if (cell.getType() == CellTypes.WOOD) {
            pixelGrid.set(x, y, cell.getColor(y));
        } else {
            pixelGrid.set(x, y, cell.getColor());
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void update() {
        CellGrid tempCells = this.copy();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
//                if (tempCells.cells[i][j].getType() == CellTypes.AIR) continue;
                tempCells.cells[i][j].update(this, i, j);
            }
        }
    }

    public Cell[][] findNeighbors(int x, int y) {
        Cell[][] neighbors = new Cell[3][3];
        neighbors[1][1] = Edge.EDGE;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (i + x < 0 || i + x >= width || j + y < 0 || j + y >= height) {
                    neighbors[i + 1][j + 1] = Edge.EDGE;
                } else {
                    neighbors[i + 1][j + 1] = cells[i + x][j + y];
                }
            }
        }
        return neighbors;
    }

    public static Cell[][] findNeighbors(Cell[][] cells, int x, int y) {
        Cell[][] neighbors = new Cell[3][3];
        neighbors[1][1] = Edge.EDGE;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (i + x < 0 || i + x >= cells.length || j + y < 0 || j + y >= cells[0].length) {
                    neighbors[i + 1][j + 1] = Edge.EDGE;
                } else {
                    neighbors[i + 1][j + 1] = cells[i + x][j + y];
                }
            }
        }
        return neighbors;
    }

    public void switchCells(int x1, int y1, int x2, int y2) {
        if (x1 < 0 || x1 >= width || x2 < 0 || x2 >= width || y1 < 0 || y1 >= height || y2 < 0 || y2 >= height) return;
        if (x1 == x2 && y1 == y2) return;
        Cell temp = cells[x1][y1];
        setCell(x1, y1, cells[x2][y2]);
        setCell(x2, y2, temp);
    }

    public void fillCircle(int centerX, int centerY, double radius, Cell cell) {
        for (int x = (int) (centerX - radius); x <= centerX + radius; x++) {
            for (int y = (int) (centerY - radius); y <= centerY + radius; y++) {
                if (Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= radius) {
                    setCell(x, y, cell);
                }
            }
        }
    }

    public void updatePixel(int x, int y) {
        if (cells[x][y].getType() == CellTypes.WOOD) {
            pixelGrid.set(x, y, cells[x][y].getColor(y));
        } else {
            pixelGrid.set(x, y, cells[x][y].getColor());
        }
    }

    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setCell(x, y, new Air());
            }
        }
    }

    public CellGrid copy() {
        CellGrid copy = new CellGrid(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                copy.setCell(i, j, cells[i][j]);
            }
        }
        return copy;
    }

    public void render(Graphics2D g2, double scale) {
        pixelGrid.draw(g2, 0, 0, scale);
    }

}
