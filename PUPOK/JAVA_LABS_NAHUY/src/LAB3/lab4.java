
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;
import java.util.Scanner;

public class lab4 {
    public static double count;
    public static double fun(double x){
        count++;
          return ((200 - x)/80e3)-0.05*Math.pow(x,1.5);

    }
    public static double bis(Function<Double, Double> func, double a, double b, double eps, FileWriter outFile) throws IOException {
        double aSign, xi, fx;
        aSign = func.apply(a)/Math.abs(func.apply(a));

        do {


            xi = (a + b) / 2;
            fx = func.apply(xi);
            outFile.write("x = " + xi + "  f(x) = " + fx + "\n");
            if (aSign * fx < 0)
                b = xi;
            else
                a = xi;
        }
        while (Math.abs(b - a) >= eps);
        return xi;
    }

    private static double isaak(Function<Double, Double> func, double x0, double xt, double eps, FileWriter outFile) throws IOException {
        double h = Math.sqrt(Math.ulp(1.0)) * Math.max(Math.abs(x0), Math.abs(xt));
        double fx = func.apply(x0);
        double x1 = x0 - (fx * h) / (func.apply(x0 + h) - fx);
        do {
            x0 = x1;
            h = Math.sqrt(Math.ulp(1.0)) * Math.max(Math.abs(x0), Math.abs(xt));
            fx = func.apply(x0);
            x1 = x0 - (fx * h) / (func.apply(x0 + h) - fx);
            outFile.write("x = " + x0 + "  f(x) = " + fx + "\n");
        }
        while (Math.abs((x1 - x0)) > eps*Math.abs(x0));
        return x1;
    }

    private static double secular(Function<Double, Double> func, double x0, double x1, double eps, FileWriter outFile) throws IOException {
        double fx0, fx1, fx2, x2, h;

        fx1 = func.apply(x1);
        h = Math.sqrt(Math.ulp(1.0)) * x0;
        x2 = x1 - (h * fx1) / (func.apply(x0+h) - fx1);
        fx2 = func.apply(x2);
        outFile.write("x = " + x2 +  "  f(x) = " + fx2 +"\n");
        do {
            x0 = x1;
            x1 = x2;
            fx0 = fx1;
            fx1 = fx2;
            x2 = x1 - (((x1 - x0) * fx1) / (fx1 - fx0));
            fx2 = func.apply(x2);
            outFile.write("x = " + x2 +  "  f(x) = " + fx2 +"\n");

        } while(Math.abs(x2 - x1) > eps * Math.abs(x1));
        return x2;
    }

    public static void main(String[] args) {
        double [] options = new double[7];
    try(  FileWriter file = new FileWriter("OUT.txt")){
    Scanner scnr = new Scanner(System.in);


       for(int i = 0; i < options.length; i++){
          options[i] = scnr.nextDouble();
       }
        count = 0;
       file.write("Pression: "+options[0]+"\nBissection Method:\n");
       bis(lab4::fun, options[1],options[2],options[0],file);
       file.write("Counts: "+ count);
        count = 0;
        file.write("\nNewton:\n");
        isaak(lab4::fun, options[3],options[4], options[0],file);
        file.write("Counts " + count);
        count = 0;
        file.write("\nSecular:\n");
        secular(lab4::fun, options[5],options[6], options[0],file);
        file.write("Counts " + count);


    }
    catch (IOException ignored){};

    }
}
