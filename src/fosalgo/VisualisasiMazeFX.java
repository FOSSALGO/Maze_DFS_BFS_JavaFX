package fosalgo;

import java.awt.Point;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VisualisasiMazeFX extends Application {
    
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    //Atribut kelas	
    private Labirin labirin = new GeneratorMaze(5, 5).getLabirin();
    private ArrayList<History> history = null;

    private ProgressBox[][] progressBox = null;
    private Point[] lintasan = null;
    double T = 2;
    int t, head = 0;
    boolean isSet = false;
    double size;
    Color color1 = Color.rgb(243, 156, 18, 0.9);
    Color color2 = Color.RED;

    @Override
    public void start(Stage stage) {
        inisialisasiUI(stage);
    }

    private void inisialisasiUI(Stage stage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, Color.WHITESMOKE);
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                release(e);
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                click(e);
            }
        });

        Canvas canvas = new Canvas(500, 500);
        root.setCenter(canvas);

        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 6, 2);
        flowPane.setPadding(new Insets(4));
        flowPane.setAlignment(Pos.BASELINE_CENTER);

        Label lbl1 = new Label("_Baris:");
        Label lbl2 = new Label("_Kolom:");
        TextField field1 = new TextField();
        TextField field2 = new TextField();
        field1.setText("5");
        field2.setText("5");
        field1.setPrefColumnCount(4);
        field2.setPrefColumnCount(4);
        lbl1.setLabelFor(field1);
        lbl1.setMnemonicParsing(true);
        lbl2.setLabelFor(field2);
        lbl2.setMnemonicParsing(true);

        Button tombolGenerate = new Button("_Generate");
        tombolGenerate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int baris = Integer.parseInt(field1.getText().trim());
                int kolom = Integer.parseInt(field2.getText().trim());
                labirin = new GeneratorMaze(baris, kolom).getLabirin();
                history = null;
                progressBox = null;
                lintasan = null;
            }
        });

        Button tombolDFS = new Button("Solusi _DFS");
        tombolDFS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (labirin != null) {
                    initializeProgressBox(labirin.getGrid().length, labirin.getGrid()[0].length);
                    isSet = false;
                    DFS dfs = new DFS(labirin);
                    history = dfs.getHistory();
                    lintasan = dfs.getLintasan();
                    t = 0;
                    head = 0;
                }
            }
        });

        Button tombolBFS = new Button("Solusi _BFS");
        tombolBFS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (labirin != null) {
                    initializeProgressBox(labirin.getGrid().length, labirin.getGrid()[0].length);
                    isSet = false;
                    BFS bfs = new BFS(labirin);
                    history = bfs.getHistory();
                    lintasan = bfs.getLintasan();
                    t = 0;
                    head = 0;
                }
            }
        });

        flowPane.getChildren().addAll(lbl1, field1, lbl2, field2, tombolGenerate, tombolDFS, tombolBFS);
        root.setTop(flowPane);

        new AnimationTimer() {

            @Override
            public void handle(long currentNanoTime) {
                update();
                draw(canvas);
            }

        }.start();
        stage.setTitle("La Byrinth-DFS-BFS");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    private void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = gc.getCanvas().getWidth();
        double height = gc.getCanvas().getHeight();
        gc.clearRect(0, 0, width, height);
        //Menggambar objek di atas canvas
        if (this.labirin != null) {
            int[][] grid = this.labirin.getGrid();
            //gambar gridnya
            //inisialisasi titik awal
            double s1 = height / (double) grid.length;
            double s2 = width / (double) grid[0].length;
            double size = Math.min(s1, s2);
            this.size = size;
            double s3 = size * grid.length;
            double s4 = size * grid[0].length;
            int gxo = (int) ((width - s4) / 2.0);
            int gyo = (int) ((height - s3) / 2.0);
            setSizeOfProgressBox(size, gxo, gyo);
            //buat warna latar Gray
            gc.setFill(Color.SILVER);
            gc.fillRect(gxo, gyo, s4, s3);

            //gambar cell-cell grid
            gc.beginPath();
            gc.setLineWidth(0.15);
            gc.setStroke(Color.rgb(236, 240, 241));
            for (int i = 0; i <= grid.length; i++) {
                int yt = (int) (gyo + i * size);
                gc.moveTo(gxo, yt);
                gc.lineTo(gxo + s4, yt);
            }
            for (int j = 0; j <= grid[0].length; j++) {
                int xt = (int) (gxo + j * size);
                gc.moveTo(xt, gyo);
                gc.lineTo(xt, gyo + s3);
            }
            gc.stroke();
            //gambar dinding penghalang
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == -1) {
                        gc.setFill(Color.rgb(52, 152, 219));
                        gc.fillRect(gxo + j * size, gyo + i * size, size, size);
                    }
                }
            }
            //gambar sel awal dan akhir
            gc.setFill(color1);
            Point start = labirin.getStart();
            Point end = labirin.getEnd();
            gc.fillRect(gxo + start.y * size, gyo + start.x * size, size, size);
            gc.fillRect(gxo + end.y * size, gyo + end.x * size, size, size);

            //gambar progressBox
            if (progressBox != null) {
                for (int i = 0; i < progressBox.length; i++) {
                    for (int j = 0; j < progressBox[i].length; j++) {
                        progressBox[i][j].draw(gc);
                    }
                }
            }

            //gambar lintasan terbaik (Shortest Path)
            if (lintasan != null && lintasan.length > 1 && head >= history.size()) {
                for (int i = 1; i < lintasan.length; i++) {
                    double x0 = gxo + size * lintasan[i - 1].y;
                    double y0 = gyo + size * lintasan[i - 1].x;
                    double x1 = gxo + size * lintasan[i].y;
                    double y1 = gyo + size * lintasan[i].x;

                    double setengahSize = size / 2.0;
                    double cx0 = x0 + setengahSize;
                    double cy0 = y0 + setengahSize;
                    double cx1 = x1 + setengahSize;
                    double cy1 = y1 + setengahSize;

                    //gc.setStroke(Color.rgb(52, 152, 219));
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(size / 10.0);
                    gc.strokeLine(cx0, cy0, cx1, cy1);
                }
            }

        }
    }

    private void initializeProgressBox(int nBaris, int nKolom) {
        this.progressBox = new ProgressBox[nBaris][nKolom];
        for (int i = 0; i < this.progressBox.length; i++) {
            for (int j = 0; j < this.progressBox[i].length; j++) {
                this.progressBox[i][j] = new ProgressBox(i, j, T, color1);
            }
        }
    }

    private void setSizeOfProgressBox(double size, double gxo, double gyo) {
        if (!this.isSet && this.progressBox != null) {
            for (int i = 0; i < this.progressBox.length; i++) {
                for (int j = 0; j < this.progressBox[i].length; j++) {
                    this.progressBox[i][j].setSize(size, gxo, gyo);
                }
            }
        }
    }

    private void setSpeedOfProgressBox(double speed) {
        if (!this.isSet && this.progressBox != null) {
            for (int i = 0; i < this.progressBox.length; i++) {
                for (int j = 0; j < this.progressBox[i].length; j++) {
                    this.progressBox[i][j].setSpeed(speed);
                }
            }
        }
    }

    private void update() {
        if (T > 0) {
            t++;
            if (t > T && history != null && !history.isEmpty() && head < history.size() && progressBox != null) {
                t = 0;
                History h = history.get(head);
                Point ori = h.getOriginal();
                Point des = h.getDestination();
                Gerakan gr = h.getGerakan();
                Arah ar = h.getArah();
                if (gr == Gerakan.MAJU) {
                    int I = des.x, J = des.y;
                    System.out.println("TRACE[" + (I) + "," + (J) + "]");
                    progressBox[I][J].aktifkan(gr, ar);
                } else if (gr == Gerakan.MUNDUR) {
                    int I = ori.x, J = ori.y;
                    progressBox[I][J].aktifkan(gr, ar);
                }
                head++;
            }
        }

    }

    private void release(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.RIGHT) {
            //Aksi untuk tombol RIGHT
        } else if (code == KeyCode.DOWN) {
            //Aksi untuk tombol DOWN
        }
    }

    private void click(MouseEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        //Aksi untuk klik Mouse
    }

    public static void main(String[] args) {
        launch(args);
    }
}
