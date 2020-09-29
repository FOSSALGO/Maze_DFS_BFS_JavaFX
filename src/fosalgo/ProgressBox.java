package fosalgo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ProgressBox {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    double size, size1, t, T, gxo, gyo, I, J;
    Color color;
    Gerakan gerakan = Gerakan.DIAM;
    Arah arah = Arah.NULL;

    public ProgressBox(int I, int J, double size, double gxo, double gyo, double T, Color color) {
        super();
        this.size = size;
        this.T = T;
        this.color = color;
        this.I = I;
        this.J = J;
        this.gxo = gxo;
        this.gyo = gyo;
    }

    public ProgressBox(int I, int J, double T, Color color) {
        super();
        this.T = T;
        this.color = color;
        this.I = I;
        this.J = J;
    }

    public void setSpeed(double speed) {
        T = speed;
    }

    public void setSize(double size, double gxo, double gyo) {
        this.size = size;
        this.gxo = gxo;
        this.gyo = gyo;
    }

    public void aktifkan(Gerakan gerakan, Arah arah) {
        this.gerakan = gerakan;
        this.arah = arah;
        t = 0;
        size1 = 0;
    }

    private void update() {
        if (gerakan != Gerakan.DIAM && t < T) {
            t++;
            size1 = size * (t / T);
            if (size1 > size) {
                size1 = size;
            }
        }
    }

    public void draw(GraphicsContext gc) {
        if (gerakan != Gerakan.DIAM) {
            gc.setFill(color);
            double xo = gxo + J * size, yo = gyo + I * size, sizeX = 0, sizeY = 0;
            if (gerakan == Gerakan.MAJU) {
                if (arah == Arah.UTARA) {
                    yo += (size - size1);
                    sizeX = size;
                    sizeY = size1;
                } else if (arah == Arah.TIMUR) {
                    sizeX = size1;
                    sizeY = size;
                } else if (arah == Arah.SELATAN) {
                    sizeX = size;
                    sizeY = size1;
                } else if (arah == Arah.BARAT) {
                    xo += (size - size1);
                    sizeX = size1;
                    sizeY = size;
                }
            } else if (gerakan == Gerakan.MUNDUR) {
                if (arah == Arah.UTARA) {
                    sizeX = size;
                    sizeY = size - size1;
                } else if (arah == Arah.TIMUR) {
                    xo += (size1);
                    sizeX = size - size1;
                    sizeY = size;
                } else if (arah == Arah.SELATAN) {
                    yo += (size1);
                    sizeX = size;
                    sizeY = size - size1;
                } else if (arah == Arah.BARAT) {
                    sizeX = size - size1;
                    sizeY = size;
                }
            }
            gc.fillRect(xo, yo, sizeX, sizeY);
            update();
        }
    }
}
