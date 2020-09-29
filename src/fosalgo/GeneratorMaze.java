package fosalgo;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

public class GeneratorMaze {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    //atribut kelas
    private Labirin labirin = null;

    public Labirin getLabirin() {
        return labirin;
    }

    public void setLabirin(Labirin labirin) {
        this.labirin = labirin;
    }

    public GeneratorMaze(int baris, int kolom) {
        super();
        this.generate(baris, kolom);
    }

    private Labirin generate(int baris, int kolom) {
        int[][] grid = new int[2 * baris + 1][2 * kolom + 1];

        //INISIALISASI grid
        for (int i = 0; i < grid.length; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = -1;
                }
            } else {
                for (int j = 0; j < grid[i].length; j += 2) {
                    grid[i][j] = -1;
                }
            }
        }

        //TENTUKAN TITIK AWAL DAN TITIK AKHIR SECARA RANDOM
        int iAwal, iAkhir;
        iAwal = randomBetweenInt(1, grid.length - 2);
        while (iAwal % 2 == 0) {
            iAwal = randomBetweenInt(1, grid.length - 2);
        }
        iAkhir = randomBetweenInt(1, grid.length - 2);
        while (iAkhir % 2 == 0) {
            iAkhir = randomBetweenInt(1, grid.length - 2);
        }
        //tandai titik awal dan akhir dengan mengubah nilai elemen gridnya menjadi = 1
        //tandai titik awal
        grid[iAwal][0] = 1;
        //node[iAwal][1]=1;
        Point titikAwal = new Point(iAwal, 0);
        //tandai titik akhir
        grid[iAkhir][grid[iAkhir].length - 1] = 1;
        grid[iAkhir][grid[iAkhir].length - 2] = 1;
        Point titikAkhir = new Point(iAkhir, grid[iAkhir].length - 1);

        //MEMULAI PROSES PEMBANGKITAN MAZE
        //proses akan dimulai dari titik akhir (* ini opsional, boleh juga dari titik awal)
        //proses pembangkitan maze akan dilakukan menggunakan algoritma Backtracking
        //lintasan/path Backtracking akan disimpan ke dalam struktur data stack
        Stack<Point> path = new Stack<Point>();
        path.push(new Point(iAkhir, grid[iAkhir].length - 2));
        while (!path.isEmpty()) {
            //melakukan operasi peek untuk memanggil titik teratas di stack path
            //titik ini teratas ini kemudian ditetapkan sebagai center
            Point center = path.peek();
            int cx = center.x;
            int cy = center.y;

            //mengidentifikasi cell-cell tetangga
            ArrayList<Point> tetangga = this.getTetangga(cx, cy, grid);

            //jika titik center tidak memiliki tetangga maka keluarkan titik center dari stack path			
            if (tetangga.isEmpty()) {
                path.pop();
            } else {
                //jika titik center memiliki tetangga, maka lakukan operasi random untuk memilih tetangga yg akan menjadi titik center berikutnya
                int index = randomBetweenInt(0, tetangga.size() - 1);
                Point destination = tetangga.get(index);
                //update grid untuk tetangga yang baru terpilih
                grid[destination.x][destination.y] = 1;
                //hapus pagar pembatas
                if (destination.x == cx) {
                    if (destination.y < cy) {
                        grid[cx][cy - 1] = 1;
                    } else if (destination.y > cy) {
                        grid[cx][cy + 1] = 1;
                    }
                } else if (destination.y == cy) {
                    if (destination.x < cx) {
                        grid[cx - 1][cy] = 1;
                    } else if (destination.x > cx) {
                        grid[cx + 1][cy] = 1;
                    }
                }
                //update path
                path.push(destination);
            }
        }

        //replace nilai grid (ganti nilai 1 menjadi 0)
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    grid[i][j] = 0;
                }
            }
        }

        if (grid != null && titikAwal != null && titikAkhir != null) {
            this.labirin = new Labirin(grid, titikAwal, titikAkhir);
        }

        return this.labirin;
    }

    private ArrayList<Point> getTetangga(int cx, int cy, int[][] grid) {
        int nBaris = grid.length;
        int nKolom = grid[0].length;
        ArrayList<Point> tetangga = new ArrayList<Point>();
        //Nort
        int nx = cx - 2;
        int ny = cy;
        Point pn = new Point(nx, ny);
        if (isValid(pn, nBaris, nKolom) && valueAt(pn, grid) == 0) {
            tetangga.add(pn);
        }

        //East
        int ex = cx;
        int ey = cy + 2;
        Point pe = new Point(ex, ey);
        if (isValid(pe, nBaris, nKolom) && valueAt(pe, grid) == 0) {
            tetangga.add(pe);
        }

        //South
        int sx = cx + 2;
        int sy = cy;
        Point ps = new Point(sx, sy);
        if (isValid(ps, nBaris, nKolom) && valueAt(ps, grid) == 0) {
            tetangga.add(ps);
        }

        //West
        int wx = cx;
        int wy = cy - 2;
        Point pw = new Point(wx, wy);
        if (isValid(pw, nBaris, nKolom) && valueAt(pw, grid) == 0) {
            tetangga.add(pw);
        }
        return tetangga;
    }

    private boolean isValid(Point p, int nBaris, int nKolom) {
        boolean valid = false;
        if (p.x > 0 && p.y > 0 && p.x < nBaris - 1 && p.y < nKolom - 1) {
            valid = true;
        }
        return valid;
    }

    private int valueAt(Point p, int[][] grid) {
        if (isValid(p, grid.length, grid[0].length)) {
            return grid[p.x][p.y];
        } else {
            return -1;
        }
    }

    private int randomBetweenInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }

    public static void main(String[] scw) {
        GeneratorMaze gm = new GeneratorMaze(4, 11);
        Labirin labirin = gm.labirin;
        labirin.tampil();
    }

}
