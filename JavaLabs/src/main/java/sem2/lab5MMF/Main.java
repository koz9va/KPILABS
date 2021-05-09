package sem2.lab5MMF;

import sem2.lab5MMF.service.*;
import java.io.*;

public class Main {
    public static final double E = 50, f = 1e3, fi = Math.PI / 4, R = 4e3, C = 1e-6, g = 0.8e-3, tEnd = 4e-3;
    private static int count = 0;

    private static final DiffFunction FUNCTION = (t, u) -> {
        double e, ud, id;
        count++;
        e = E * Math.sin(2.0 * Math.PI * f * t + fi);
        ud = e - u;
        id = 0;
        if (ud > 0) id = g * ud * Math.sqrt(ud);
        return (id - u / R) / C;
    };

    public static void main(String[] args) throws IOException {
        int nMax = 1 << 15, value;
        double[] t = new double[nMax];
        double[] y = new double[nMax];

        count = 0;
        value = SolveDiffEquation.imEul(FUNCTION, tEnd, 0, 1e-6, t, y, nMax);
        System.out.format("Implicit Euler's method:\nPoints were found: %d; calls to function: %d\n", value, count);
        count = 0;
        value = SolveDiffEquation.Eul(FUNCTION, tEnd, 0, 1e-6, t, y, nMax, tEnd);
        System.out.format("Runge-Kutta method:\nPoints were found: %d; calls to function: %d\n", value, count);
        count = 0;
        value = SolveDiffEquation.RunKut(FUNCTION, tEnd, 0, 1e-6, t, y, nMax, tEnd);
        System.out.format("Euler's method:\nPoints were found: %d; calls to function: %d\n", value, count);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("y.txt"))) {
            for (int i = 0; i < value; i++) {
                writer.write(y[i] + "\n");
            }
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("t.txt"))) {
            for (int i = 0; i < value; i++) {
                writer.write(t[i] + "\n");
            }
        }
    }
}
