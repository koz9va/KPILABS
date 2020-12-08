package lab5;


import java.util.Arrays;

public class Main {



    public static void main(String[] args) {
        double[] X = new double[2];
        X[0] = 2.5;
        X[1] = 0.5;
        IsaakSLAU(Main::function, X, 1e-7);
        System.out.println(Arrays.toString(X));
        X[0] = 2.5;
        X[1] = 0.5;
        BroidenSLAU(Main::function, X, 1e-7);
        System.out.println(Arrays.toString(X));

    }

    public static void IsaakSLAU(Func func, double[] X, double eps) {
        double[][] J = new double[X.length][X.length];
        double[] Dx = new double[X.length];
        double[] Y = new double[X.length];
        double[] Ypr = new double[X.length];

        do {
            double h;
            func.func(X, Y);

            for (int j = 0; j < X.length; j++) {
                h = Math.sqrt(Math.ulp(1.0)) * X[j];
                X[j] += h;
                func.func(X, Ypr);

                for (int i = 0; i < X.length; i++) {
                    J[i][j] = (Ypr[i] - Y[i]) / h;
                }
                X[j] -= h;
            }

            QR(J, Y, Dx);

            vectorDifference(X, Dx);
        }
        while (norm(Dx) > eps * norm(X));
    }

    public static void BroidenSLAU(Func func, double[] X, double eps) {
        double nX, ndX;
        double[][] J = new double[X.length][X.length];
        double[][] J1 = new double[X.length][X.length];
        double[] Dx = new double[X.length];
        double[] Y = new double[X.length];
        double[] Ypr = new double[X.length];

        func.func(X, Y);

        for(int j = 0; j < X.length; ++j) {
           double h = Math.sqrt(Math.ulp(1.0)) * X[j];
            X[j] += h;
            func.func(X, Ypr);
            for(int i = 0; i < X.length; ++i) {
                J[i][j] = (Ypr[i] - Y[i])/h;
            }

            X[j] -= h;
        }

        do{
            copy(J1,J);
            QR(J1,Y,Dx);

            nX = 0.0;
            ndX = 0.0;

            for (int i = 0; i < X.length; i++) {
                X[i] -= Dx[i];
                nX += X[i] * X[i];
                ndX += Dx[i] * Dx[i];
            }
            func.func(X,Y);

            for (int i = 0; i < X.length; i++) {
                for (int j = 0; j < X.length; j++) {
                    J[i][j] -= (Y[i] * Dx[i]) / ndX;
                }
            }
        }
        while (Math.sqrt(ndX) > eps * Math.sqrt(nX));
    }

    public static void function (double[] X, double[] Y) {
        Y[0] = Math.sin(X[0]) * Math.sin(X[0]) + Math.sqrt(X[1]) - 1.0;
        Y[1] = Math.sin(X[0]) - 2.0 * Math.sqrt(X[1]) + 1.0;
    }

    public static void vectorDifference (double[] a, double[] b ) {
        for (int i = 0; i < a.length; i++) {
            a[i] -= b[i];
        }
    }

    public static void copy(double[][] A, double[][] B) {
        for (int i = 0; i < A.length; i++) {
            System.arraycopy(B[i], 0, A[i], 0, A.length);
        }

    }

    public static double norm (double[] a) {
        double sum = 0.0;
        for (double aDouble : a) {
            sum += aDouble * aDouble;
        }
        return Math.sqrt(sum);
    }


    public static void QR(double [][] A, double[] B, double[] x) {
        Arrays.fill(x, 0);

        for (int j = 0; j < A.length - 1; j++) {
            double a = 0;
            for (int i = j; i < A.length; i++) {
                a += A[i][j] * A[i][j];
            }
            if (A[j][j] < 0)
                a = Math.sqrt(a);
            else
                a = -Math.sqrt(a);
            double k = 1.0 / (a * a - a * A[j][j]);
            A[j][j] -= a;
            double t;
            for (int i = j + 1; i < A.length; i++) {
                t = 0;
                for (int l = j; l < A.length; l++) {
                    t += A[l][j] * A[l][i];
                }
                for (int l = j; l < A.length; l++) {
                    A[l][i] -= k * A[l][j] * t;
                }
            }
            t = 0;
            for (int i = j; i <A.length; i++) {
                t += A[i][j] * B[i];
            }
            for (int i = j; i < A.length; i++) {
                B[i] -= k * A[i][j] * t;
            }
            A[j][j] = a;
        }

        for (int i = A.length - 1; i >= 0; i--) {
            x[i] = B[i];
            for (int j = i + 1; j < A.length; j++) {
                x[i] -= A[i][j] * x[j];
            }
            x[i] /= A[i][i];
        }
    }



}
