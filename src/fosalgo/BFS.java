package fosalgo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    //INPUT
    private Labirin labirin = null;

    //OUTPUT
    private Point[] lintasan = null;
    private ArrayList<History> history = null;

    public Point[] getLintasan() {
        return lintasan;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public BFS(Labirin labirin) {
        super();
        this.labirin = labirin;
        this.run();
    }

    public void run() {
        if (this.labirin != null) {
            this.history = new ArrayList<History>();

            int[][] grid = Cloning.clone(this.labirin.getGrid());
            Point start = this.labirin.getStart();
            Point end = this.labirin.getEnd();

            Queue<Point> queue = new LinkedList<Point>();
            queue.offer(start);

            //Set Step 1
            int MAX_STEP = 0;
            int cx = 0, cy = 0;
            grid[start.x][start.y] = 1;

            Boolean next = true;
            while (next) {
                Point center = queue.peek();
                int[] status = this.getStatusSelTetangga(center, grid);
                //LAKUKAN PROSES PENYEBARAN KE SEL TETANGGA
                for (int i = 0; i < status.length; i++) {
                    if (status[i] == 1) {
                        Point selBaru = getTetangga(center, i);
                        grid[selBaru.x][selBaru.y] = grid[center.x][center.y] + 1;;
                        queue.offer(selBaru);
                        this.history.add(new History(center, selBaru, Gerakan.MAJU, Arah.getArah(center, selBaru)));
                        if (selBaru.equals(end)) {
                            next = false;
                            MAX_STEP = grid[center.x][center.y] + 1;
                            cx = selBaru.x;
                            cy = selBaru.y;
                            break;
                        }
                    }
                }
                //Keluarkan Center dari Queue
                queue.poll();
                if (queue.isEmpty()) {
                    next = false;
                }
            }
            //set litasan
            if (MAX_STEP > 0) {
                this.lintasan = new Point[MAX_STEP];
                int step = MAX_STEP;
                for (int i = MAX_STEP - 1; i >= 0; i--) {
                    lintasan[i] = new Point(cx, cy);
                    step--;
                    //cek UTARA
                    if (cy - 1 >= 0 && grid[cx][cy - 1] == step) {
                        cy--;
                    } else if (cy + 1 < grid[cx].length && grid[cx][cy + 1] == step) {
                        cy++;
                    }
                    if (cx - 1 >= 0 && grid[cx - 1][cy] == step) {
                        cx--;
                    }
                    if (cx + 1 < grid.length && grid[cx + 1][cy] == step) {
                        cx++;
                    }
                }
            }
            cetakArray(grid);
        }
    }

    private int[] getStatusSelTetangga(Point selPusat, int[][] grid) {
        int x = selPusat.x;
        int y = selPusat.y;
        int[] status = new int[4];
        //UTARA
        int xn = x - 1;
        int yn = y;
        if (xn >= 0 && xn < grid.length && yn >= 0 && yn < grid[0].length && grid[xn][yn] == 0) {
            status[0] = 1;
        }
        //TIMUR
        int xe = x;
        int ye = y + 1;
        if (xe >= 0 && xe < grid.length && ye >= 0 && ye < grid[0].length && grid[xe][ye] == 0) {
            status[1] = 1;
        }

        //SELATAN
        int xs = x + 1;
        int ys = y;
        if (xs >= 0 && xs < grid.length && ys >= 0 && ys < grid[0].length && grid[xs][ys] == 0) {
            status[2] = 1;
        }

        //BARAT
        int xw = x;
        int yw = y - 1;
        if (xw >= 0 && xw < grid.length && yw >= 0 && yw < grid[0].length && grid[xw][yw] == 0) {
            status[3] = 1;
        }
        return status;
    }

    private Point getTetangga(Point center, int nextNode) {
        if (nextNode == 0) {
            return new Point(center.x - 1, center.y);
        } else if (nextNode == 1) {
            return new Point(center.x, center.y + 1);
        } else if (nextNode == 2) {
            return new Point(center.x + 1, center.y);
        } else if (nextNode == 3) {
            return new Point(center.x, center.y - 1);
        } else {
            return null;
        }
    }

    public void cetakArray(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

}