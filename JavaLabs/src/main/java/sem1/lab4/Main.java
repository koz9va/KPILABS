package sem1.lab4;

import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class Main {

    private static int count = 0;

    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("Output.txt")) {
            double eps = Double.parseDouble(args[0]);
            double a = Double.parseDouble(args[1]);
            double b = Double.parseDouble(args[2]);
            double x0n = Double.parseDouble(args[3]);
            double xt = Double.parseDouble(args[4]);
            double x0s = Double.parseDouble(args[5]);
            double x1 = Double.parseDouble(args[6]);

            Function<Double, Double> func = Main::function;

            writer.write("Bisection\n");
            writer.write("Solution: " + Bis(func, a, b, eps, writer) + " Count is: " + count + "\n");
            count = 0;
            writer.write("Newton\n");
            writer.write("Solution: " + Newton(func, x0n, xt, eps, writer) + " Count is: " + count +"\n");
            count = 0;
            writer.write("Secant\n");
            writer.write("Solution: " + Secant(func, x0s, x1, eps, writer) + " Count is: " + count +"\n");
        }
        catch (IOException ignored){}
    }

    public static double Bis(Function<Double, Double> func,
                              double a,
                              double b,
                              double eps,
                              FileWriter writer) throws IOException {
        double aSign, xi, fx;
        aSign = func.apply(a)/Math.abs(func.apply(a));
        do {
            xi = (a + b) / 2;
            fx = func.apply(xi);
            writer.write("x = " + xi + " f(x) = " + fx + "\n");
            if (aSign * fx < 0)
                b = xi;
            else
                a = xi;
        }
        while (Math.abs(b - a) >= eps * Math.abs(a));

        return xi;
    }

    public static double Newton(Function<Double, Double> func,
                              double x0,
                              double xt,
                              double eps,
                              FileWriter writer) throws IOException {
        double h = Math.sqrt(Math.ulp(1.0)) * Math.max(Math.abs(x0), Math.abs(xt));
        double fx = func.apply(x0);
        double x1 = x0 - (fx * h) / (func.apply(x0 + h) - fx);
        do {
            x0 = x1;
            h = Math.sqrt(Math.ulp(1.0)) * Math.max(Math.abs(x0), Math.abs(xt));
            fx = func.apply(x0);
            x1 = x0 - (fx * h) / (func.apply(x0 + h) - fx);
            writer.write("x = " + x0 + " f(x) = " + fx + "\n");
        }
        while (Math.abs((x1 - x0) / x0) >= eps);
        return x1;
    }

    public static double Secant(Function<Double, Double> func,
                                 double x0,
                                 double x1,
                                 double eps,
                                 FileWriter writer) throws IOException {
        double fx0, fx1, fx2, x2, h;
        h = Math.sqrt(Math.ulp(1.0)) * x0;
        fx1 = func.apply(x1);
        x2 = x1 - (h * fx1) / (func.apply(x0 + h) - fx1);
        fx2 = func.apply(x2);
        do {
            writer.write("x = " + x2 + " f(x) = " + fx2 + "\n");
            x0 = x1;
            x1 = x2;
            fx0 = fx1;
            fx1 = fx2;
            x2 = x1 - (((x1 - x0) * fx1) / (fx1 - fx0));
            fx2 = func.apply(x2);
        }
        while (Math.abs((x2 - x1) / x1) >= eps);
        return x2;
    }


    private static Double function(Double Ube) {
//        count++;
//        double Ie, Eb = 4, Ec = 10, R = 5e3, Ie0 = 1e-9, Me = 1.01, Fit = 26e-3, h21 = 2e-3;
//        Ie = Ie0 * (Math.exp((Ube + h21 * (Ec - Eb + Ube))/(Me * Fit)) - 1);
//        return Eb - Ube - R * Ie;
        return 4 - 3 * Ube - Math.exp(Ube) + 1;
    }
}
