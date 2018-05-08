package util.calculation;

public class Calculation {

    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static int[] multiplyMatrixByVector(int[][] F, int[] Y) {
        int[] result = new int[Y.length];
        for (int i = 0; i < F.length; i++) {
            int sum = 0;
            for (int j = 0; j < F.length; j++) {
                sum ^= F[i][j] & Y[j];
            }
            result[i] = sum;
        }
        return result;
    }

    public static int convertBinaryArrayToInteger(int[] Y) {
        int result=0;
        for (int i = 0; i < Y.length ; i++) {
            result+=Y[i] * Math.pow(2,Y.length-1-i);
        }
        return result;
    }



}
