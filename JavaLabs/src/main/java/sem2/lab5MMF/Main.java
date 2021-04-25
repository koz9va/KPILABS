package sem2.lab5MMF;

import sem2.lab5MMF.service.*;
import java.io.*;
import java.util.Arrays;

public class Main {
    public static final double R = 1e3, C = 2e-9, i0 = 5000e-12, m = 1.7, ft = 26e-3, Rb0 = 1e3, Iv = 0.3e-3, E0 = 5, Tp = 1e-6;
    private static int cnt = 0;

    private static double e(double t) {
        if(t < Tp) {
            return E0;
        } else {
            return 0;
        }
    }

    private static final DiffFunction equation = (t, upn) -> {
        double arg = upn/m/ft, exp_value, j, Rb;

        ++cnt;

        if(arg > 80.0) {
            exp_value = Math.exp(80);
        } else if(arg < -80.0) {
            exp_value = 0;
        } else {
            exp_value = Math.exp(arg);
        }
        j = i0 * (exp_value - 1.0);
        Rb = Rb0/(1 + (j/Iv));

        return ((e(t) - upn)/(Rb + R) - j)/C;
    };

    private static void find_voltage(double[] t, double[] y, double[] ud) {

        for(int i = 0; i < t.length; i++) {
            double Rb, j, arg, exp_value;
            arg = y[i]/m/ft;

            if(arg > 80) {
                exp_value = Math.exp(80);
            }else if(arg < -80) {
                exp_value = 0;
            } else {
                exp_value = Math.exp(arg);
            }

            j = i0 * (exp_value - 1);
            Rb = Rb0/(1.0 + (j/Iv));

            ud[i] = ((e(t[i]) - y[i])/(Rb + R))*Rb + y[i];
        }
    }

    public static void main(String[] args) throws IOException {
        int nMax = 1 << 20;
        int n;
        double tend;
        double[] t = new double[nMax];
        double[] y = new double[nMax];
        double[] Ud = new double[nMax];

        tend = 5e-6;

        cnt = 0;
        n = SolveDiffEquation.euler(equation, tend, 0, 1e-6, t, y, nMax);
        System.out.format("Euler's method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
        cnt = 0;
        n = SolveDiffEquation.imp_euler(equation, tend, 0, 1e-6, t, y, nMax);
        System.out.format("Implicit Euler's method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
        cnt = 0;
        n = SolveDiffEquation.RK45(equation, tend, 0, 1e-6, t, y, nMax);
        System.out.format("Runge-Kutta method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
        if(n == nMax) {
            System.out.format("Quantity of allocated points might not be sufficient\n");
        }
        find_voltage(t, y, Ud);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("y.txt"))) {
            Arrays.stream(Ud).forEachOrdered(element -> {
                try {
                    writer.write(element + "\n");
                } catch (IOException ignored) {}
            });
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("t.txt"))) {
            Arrays.stream(Ud).forEachOrdered(element -> {
                try {
                    writer.write(element + "\n");
                } catch (IOException ignored) {}
            });
        }
    }
}
