package lab5;

import lab3.Matrix;
import lab3.Vector;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
public class Main {

    private static int countOfFuncCall = 0;
    private static int countIteration = 0;

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

            countOfFuncCall++;

            Rb.set(rb0 / (1 + X.getVector()[0] / iv));
            Y.getVector()[0] = X.getVector()[0] - i0 * (Math.exp((X.getVector()[1] - X.getVector()[0] * Rb.get()) / (m * Fi)) + 1.0);
            Y.getVector()[1] = E - X.getVector()[1] - X.getVector()[0] * R;
        };

        Vector X = new Vector(2);
        Vector tmpArr = new Vector(X.getVector().length);


        X.getVector()[0] = 0.001;
        X.getVector()[1] = 1.4;
        System.arraycopy(X.getVector(), 0, tmpArr.getVector(), 0, X.getVector().length);
        Broaden(function, X, 1e-7);

        System.out.println("Broaden: for " +
                Arrays.toString(tmpArr.getVector()) +
                " -> " + Arrays.toString(X.getVector()) +
                " Count of function call: " + countOfFuncCall);



        X.getVector()[0] = 0.001;
        X.getVector()[1] = 1.4;
        nullCount();
        System.arraycopy(X.getVector(), 0, tmpArr.getVector(), 0, X.getVector().length);
        Newton(function, X, 1e-7);

        System.out.println("Newton : for " +
                Arrays.toString(tmpArr.getVector()) +
                " -> " + Arrays.toString(X.getVector()) +
                " Count of function call: " + countOfFuncCall);
    }

    public static void Broaden(@NonNull Function function,
                               @NonNull Vector X,
                               double eps) {
        double nX, ndX;

        Matrix J = new Matrix(X.getVector().length);
        Matrix J1 = new Matrix(X.getVector().length);
        Vector Dx = new Vector(X.getVector().length);
        Vector Y = new Vector(X.getVector().length);
        Vector Yp = new Vector(X.getVector().length);

        calcJacobi(function, X, J, Y, Yp);

        do {
            Matrix.copyMatrix(J1, J);
            lab3.Main.QR(J1, Y, Dx.getVector());

            nX = 0.0;
            ndX = 0.0;

            for (int i = 0; i < X.getVector().length; i++) {
                X.setVector(i, X.getVector()[i] - Dx.getVector()[i]);
                nX += X.getVector()[i] * X.getVector()[i];
                ndX += Dx.getVector()[i] * Dx.getVector()[i];
            }

            function.calculateFunction(X, Y);

            for (int i = 0; i < X.getVector().length; i++) {
                for (int j = 0; j < X.getVector().length; j++) {
                    J.matrix[i][j] -= Y.getVector()[i] * Dx.getVector()[j] / ndX;
                }
            }
            countIteration++;
            System.out.println(countIteration + ": " + Arrays.toString(X.getVector()));
        }
        while(Math.sqrt(ndX) > eps * Math.sqrt(nX));
    }

    public static void Newton (@NonNull Function function,
                               @NonNull Vector X,
                               double eps) {

        Matrix J = new Matrix(X.getVector().length);
        Vector Dx = new Vector(X.getVector().length);
        Vector Y = new Vector(X.getVector().length);
        Vector Yp = new Vector(X.getVector().length);

        do {
            calcJacobi(function, X, J, Y, Yp);
            lab3.Main.QR(J, Y, Dx.getVector());
            subtractVectors(X, Dx);
            countIteration++;
            System.out.println(countIteration + ": " + Arrays.toString(X.getVector()));
        }
        while (norm(Dx) > eps * norm(X));
    }

    private static void calcJacobi(@NonNull Function function,
                                   @NonNull Vector X,
                                   @NonNull Matrix J,
                                   @NonNull Vector y,
                                   @NonNull Vector yp) {
        double h;
        function.calculateFunction(X, y);

        for (int j = 0; j < X.getVector().length; j++) {
            h = Math.sqrt(Math.ulp(1.0)) * X.getVector()[j];
            X.setVector(j, X.getVector()[j] + h);
            function.calculateFunction(X, yp);

            for (int i = 0; i < X.getVector().length; i++) {
                J.matrix[i][j] = (yp.getVector()[i] - y.getVector()[i]) / h;
            }
            X.setVector(j, X.getVector()[j] - h);
        }
    }

    public static void subtractVectors (@NonNull Vector a, @NonNull Vector d) {
        for (int i = 0; i < a.getVector().length; i++) {
            a.getVector()[i] -= d.getVector()[i];
        }
    }

    public static double norm (@NonNull Vector vector) {
        double sum = 0.0;
        for (double aDouble : vector.getVector()) {
            sum += aDouble * aDouble;
        }
        return Math.sqrt(sum);
    }

    public static void nullCount() {
        countIteration = 0;
        countOfFuncCall = 0;
    }
}
