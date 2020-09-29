package fosalgo;

public class Cloning {
    /**
     * Author : Sugiarto Cokrowibowo
     * Youtube: https://www.youtube.com/c/FOSALGO
     * FOSALGO
     */

    public static int[][] clone(int[][] A) {
        int[][] result = null;
        if (A != null) {
            result = new int[A.length][A[0].length];
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[0].length; j++) {
                    result[i][j] = A[i][j];
                }
            }
        }
        return result;
    }
}
