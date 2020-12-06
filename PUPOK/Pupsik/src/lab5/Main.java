package lab5;

import java.util.function.Consumer;
import java.util.function.Function;



public class Main {


    public static void main(String[] args) {
        Consumer<Bus> func = Main::Func;


    }

    public static void Func(Bus unite) {
        unite.Y[0] = Math.sin(unite.X[0]) * Math.sin(unite.X[0]) + Math.sqrt(unite.X[1]) - 1.0;
        unite.Y[1] = Math.sin(unite.X[0]) - 2.0 * Math.sqrt(unite.X[1]) + 1.0;

    }

    public static void IsaakSLAU(Consumer<Bus> func, double[] X, double eps) {
        double[][] J = new double[X.length][X.length];
        double[] Dx = new double[X.length];
        double[] Y = new double[X.length];
        double[] Ypr = new double[X.length];

        do {
            double h;
            func.accept();


        }



    }
}
