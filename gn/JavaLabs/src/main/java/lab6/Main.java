package lab6;

public class Main {
    public static int lengthToSkip = 6, powerOfInputFunction = 1;
    public static void main(String[] args) {
        double[] fakeArrU = new double[]{0.0, 0.5, 1.0, 3.0, 4.5, 6.0, 7.5, 9.5, 12.0, 16.0, 20.0, 25.0, 30.0};
        double[] fakeArrC = new double[]{15.5e-12, 14.0e-12, 12.2e-12, 10.5e-12, 9.7e-12, 9.0e-12, 8.6e-12, 8.1e-12, 7.6e-12, 7.1e-12, 6.7e-12, 6.4e-12, 6.1e-12};

        double[] realArrayU = new double[fakeArrU.length - lengthToSkip];
        double[] realArrayC = new double[fakeArrC.length - lengthToSkip];

        createTable(fakeArrU, fakeArrC, realArrayU, realArrayC,lengthToSkip);

        double[] src = func(realArrayU, realArrayC, powerOfInputFunction);

        getValues(src);

        System.out.println("fi = [" + src[0] + "], n = [" + src[1] + "]");
    }

    public static double[] func (double[] arrayOfC, double[] arrayOfU, int powerOfPol) {
        powerOfPol++;
        double [] B = new double[powerOfPol];
        double[][] matrix = new double[powerOfPol][powerOfPol];

        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < arrayOfC.length; k++) {
                B[i] += pow(i, arrayOfU[k]) * arrayOfC[k];
            }
            for (int j = 0; j < matrix.length; j++) {
                for (int k = 0; k < arrayOfC.length; k++) {
                    matrix[i][j] += pow(i + j, arrayOfU[k]);
                }
            }
        }
        double[] src = new double[powerOfPol];
        lab3.Main.Hales(matrix, B, src);
        return src;
    }

    public static double pow(int i, double value) {
        double pow = 1.0;
        for (int j = 0; j < i; j++) {
            pow *= value;
        }
        return pow;
    }

    public static void createTable(double[] src1, double[] src2, double[] dest1, double[] dest2, int lengthToSkip) {
        for (int i = lengthToSkip; i < src1.length; i++) {
            dest1[i - lengthToSkip] = Math.log(src1[i]);
            dest2[i - lengthToSkip] = Math.log(src2[0] / src2[i]);
        }
    }

    public static void getValues(double[] src) {
        src[1] = 1 / src[1];
        src[0] = Math.exp(src[0]);
    }
}
