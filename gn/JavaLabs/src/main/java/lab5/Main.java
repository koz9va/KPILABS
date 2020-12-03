package lab5;

import lab3.Matrix;
import lab3.Vector;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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

//            Rb.set(rb0 / (1 + X.getVector()[0] / iv));
//            Y.getVector()[0] = X.getVector()[0] - i0 * Math.exp((X.getVector()[1] - X.getVector()[0] * Rb.get()) / (m * Fi)) + 1.0;
//            Y.getVector()[1] = E - X.getVector()[1] - X.getVector()[0] * R;

            Y.getVector()[0] = Math.sin(X.getVector()[0]) * Math.sin(X.getVector()[0]) + Math.sqrt(X.getVector()[1]) - 1.0;
            Y.getVector()[1] = Math.sin(X.getVector()[0]) - 2.0 * Math.sqrt(X.getVector()[1]) + 1.0;

        };

        Vector X = new Vector(2);
        X.getVector()[0] = 2.5;
        X.getVector()[1] = 0.5;

        Broiden(function, X, 1e-7);

        System.out.println("Broiden : " + Arrays.toString(X.getVector()));

        X.getVector()[0] = 2.5;
        X.getVector()[1] = 0.5;
        Newton(function, X, 1e-7);

        System.out.println("Newton : " + Arrays.toString(X.getVector()));
    }

    public static void Broiden (Function function, Vector X, double eps) {
        double h, nX, ndX;

        Matrix J = new Matrix(X.getVector().length);

        Vector Dx = new Vector(X.getVector().length);
        Vector Y = new Vector(X.getVector().length);
        Vector Yp = new Vector(X.getVector().length);
        Vector F = new Vector(X.getVector().length);


        function.calculateFunction(X, Y);

        for (int j = 0; j < X.getVector().length; j++) {
            h = Math.sqrt(Math.ulp(1.0)) * X.getVector()[j];
            X.getVector()[j] += h;
            function.calculateFunction(X, Yp);

            for (int i = 0; i < X.getVector().length; i++) {
                J.matrix[i][j] = (Yp.getVector()[i] - Y.getVector()[i]) / h;
            }
            X.getVector()[j] -= h;
        }

        do {

            lab3.Main.QR(J, Y, Dx.getVector());

            nX = 0.0;
            ndX = 0.0;

            for (int i = 0; i < X.getVector().length; i++) {
                X.getVector()[i] -= Dx.getVector()[i];
                nX += X.getVector()[i] * X.getVector()[i];
                ndX += Dx.getVector()[i] * Dx.getVector()[i];
            }

            function.calculateFunction(X, Y);

            for (int i = 0; i < X.getVector().length; i++) {
                for (int j = 0; j < X.getVector().length; j++) {
                    J.matrix[i][j] -= Y.getVector()[i] * Dx.getVector()[j] / ndX;
                }
            }
        }
        while(Math.sqrt(ndX) > eps * Math.sqrt(nX));

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
                h = Math.sqrt(Math.ulp(1.0)) * X.getVector()[j];
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
}