package lab4;

import javax.imageio.ImageTranscoder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        try(FileWriter writer = new FileWriter("Output.txt")) {
            Function<Double, Double> func = Main::function;

        }
        catch (IOException ignored){}
    }

    private static double Bis(Function<Double, Double> func,
                              double a,
                              double b,
                              double eps,
                              FileWriter writer) throws IOException {
        double aSign, xi;
        aSign = func.apply(a)/Math.abs(func.apply(a));
        do {
            xi = (a + b) / 2;
            writer.write(xi + "\n");
            if (aSign * func.apply(xi) < 0)
                b = xi;
            else
                a = xi;
        }
        while (Math.abs(b - a) >= eps);
        return xi;
    }

    private static double Newton(Function<Double, Double> func,
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
            writer.write(x1 + "\n");
        }
        while (Math.abs((x1 - x0) / x0) > eps);
        return x1;
    }

    private static double Secant(Function<Double, Double> func,
                                 double x0,
                                 double x1,
                                 double eps,
                                 FileWriter writer) throws IOException {
        double fx0, fx1, fx2, x2;
        fx0 = func.apply(x0);
        fx1 = func.apply(x1);
        while (true) {
            x2 = (x0 - (((x1 - x0) / (fx1 - fx0)) * fx0));
            writer.write(x2 + "\n");
            fx2 = func.apply(x2);
            if (fx2 == 0)
                return x2;
            if (Math.abs(x0 - x2) <= eps)
                return x2;
            if ((fx0) / Math.abs(fx0) == fx2 / Math.abs(fx2))
                x0 = x2;
            else
                x1 = x2;
            fx0 = fx1;
            fx1 = fx2;
        }
    }

    private static Double function(Double a){

    }

}
