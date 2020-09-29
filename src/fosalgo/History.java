package fosalgo;

import java.awt.Point;

public class History {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    private Point original = null;
    private Point destination = null;
    private Gerakan gerakan = Gerakan.DIAM;
    private Arah arah = Arah.NULL;

    public History(Point original, Point destination, Gerakan gerakan, Arah arah) {
        super();
        this.original = original;
        this.destination = destination;
        this.gerakan = gerakan;
        this.arah = arah;
    }

    public Point getOriginal() {
        return original;
    }

    public void setOriginal(Point original) {
        this.original = original;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Gerakan getGerakan() {
        return gerakan;
    }

    public void setGerakan(Gerakan gerakan) {
        this.gerakan = gerakan;
    }

    public Arah getArah() {
        return arah;
    }

    public void setArah(Arah arah) {
        this.arah = arah;
    }
}
