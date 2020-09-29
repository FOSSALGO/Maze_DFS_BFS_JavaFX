package fosalgo;

import java.awt.Point;

public class Labirin {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    private int[][] grid = null;
    private Point start = null;
    private Point end = null;

    public Labirin(int[][] grid, Point startCell, Point endCell) {
        super();
        this.grid = grid;
        this.start = startCell;
        this.end = endCell;
    }

    public int[][] getGrid() {
        return grid;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public int getValueAt(Point at) {
        int value = -1;
        if (at != null && this.grid != null) {
            try {
                value = this.grid[at.x][at.y];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public boolean setValueAt(int value, Point at) {
        boolean result = false;
        if (at != null && this.grid != null) {
            try {
                this.grid[at.x][at.y] = value;
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Labirin clone() {
        Labirin l = null;
        if (this.grid != null && this.start != null && this.end != null) {
            int[][] n = this.grid.clone();
            Point s = new Point(this.start.x, this.start.y);
            Point e = new Point(this.end.x, this.end.y);
            l = new Labirin(n, s, e);
        }
        return l;
    }

    public void tampil() {
        if (grid != null) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            System.out.print("+");
                        } else {
                            if (grid[i][j] == -1) {
                                System.out.print("---");
                            } else {
                                System.out.print("   ");
                            }
                        }
                    } else {
                        if (j % 2 == 0) {
                            if (grid[i][j] == -1) {
                                System.out.print("|");
                            } else {
                                System.out.print(" ");
                            }
                        } else {
                            System.out.print("   ");
                        }
                    }
                }
                System.out.println();
            }
        }
    }
}
