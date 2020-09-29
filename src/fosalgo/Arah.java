package fosalgo;

import java.awt.Point;

public enum Arah {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */
    UTARA, TIMUR, SELATAN, BARAT, NULL;

    public static Arah getArah(Point original, Point destination) {
        Arah arah = Arah.NULL;
        if (original.x == destination.x) {
            if (original.y > destination.y) {
                arah = Arah.BARAT;
            } else if (original.y < destination.y) {
                arah = Arah.TIMUR;
            }
        } else if (original.y == destination.y) {
            if (original.x < destination.x) {
                arah = Arah.SELATAN;
            } else if (original.x > destination.x) {
                arah = Arah.UTARA;
            }
        }
        return arah;
    }
}
