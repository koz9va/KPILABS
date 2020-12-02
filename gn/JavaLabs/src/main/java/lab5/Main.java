package lab5;

import lab3.Matrix;
import lab3.Vector;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class Main {

    private static final double
            i0 = 10e-9,
            iv = 1e-3,
            rb0 = 2e3,
            E = 5.0,
            R = 4e3,
            Fi = 26e-3,
            m = 1.5;

    public static void main(String[] args) {
        var Rb = new AtomicReference<>((double) 0);

        Function function = (X, Y) -> {
            //X[0] = Id, X[1] = Ud

            Rb.set(rb0 / (1 + X.getVector()[0] / iv));
            Y.getVector()[0] = X.getVector()[0] - i0 * Math.exp((X.getVector()[1] - X.getVector()[0] * Rb.get()) / (m * Fi)) + 1.0;
            Y.getVector()[1] = E - X.getVector()[1] - X.getVector()[0] * R;

        };

    }

    public static void Newton (@NonNull Function function, @NonNull Vector X, double eps) {
        double h;

        Matrix J = new Matrix(X.getVector().length);

        Vector Dx = new Vector(X.getVector().length);
        Vector Y = new Vector(X.getVector().length);
        Vector Yp = new Vector(X.getVector().length);
        Vector F = new Vector(X.getVector().length);

        do {
            function.calculateFunction(X, Y);

            for (int j = 0; j < X.getVector().length; j++) {
                h = Math.sqrt(Math.ulp(1.0) * X.getVector()[j]);
                X.getVector()[j] += h;
                function.calculateFunction(X, Yp);

                for (int i = 0; i < X.getVector().length; i++) {
                    J.matrix[i][j] = (Yp.getVector()[i] - Y.getVector()[i]) / h;
                }
                X.getVector()[j] -= h;
            }
            lab3.Main.QR(J, Y, Dx.getVector());

            subtractVectors(X, Dx);
        }
        while (norm(Dx) >= eps * norm(X));
    }

    public static void subtractVectors (@NonNull Vector a, @NonNull Vector d) {
        for (int i = 0; i < a.getVector().length; i++) {
            a.getVector()[i] -= d.getVector()[i];
        }
    }

    public static double norm (@NonNull Vector vector) {
        double sum = 0.0;
        for (double aDouble : vector.getVector()) {
            sum += aDouble;
        }
        return Math.sqrt(sum);
    }


//    public static void function (double[] x, double[] returned) {
//        if (x == null) {
//            System.out.println("NullPointer");
//        }
//        else {
//            returned[0] = x[0] + x[1] - 3;
//            returned[1] = x[0] * x[0] + x[1] * x[1] - 9;
//        }
//    }
}
