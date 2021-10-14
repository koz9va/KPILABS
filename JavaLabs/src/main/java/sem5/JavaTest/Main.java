package sem5.JavaTest;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<double[]> U = new ArrayList<>();
        ArrayList<double[]> I = new ArrayList<>();
        U.add(new double[]{0, 0.04, 0.083, 0.125, 0.17, 0.22, 0.27, 0.32, 0.38, 0.44});
        U.add(new double[]{0, 0.04, 0.08, 0.12, 0.165, 0.22, 0.28, 0.33, 0.4});
        U.add(new double[]{0, 0.04, 0.09, 0.135, 0.185, 0.26, 0.32, 0.38, 0.46, 0.56});
        U.add(new double[]{0, 0.045, 0.1, 0.15, 0.21, 0.28, 0.36, 0.44, 0.52, 0.65});
        U.add(new double[]{0, 0.05, 0.1, 0.17, 0.24, 0.32, 0.41, 0.52, 0.64, 0.8});
        U.add(new double[]{0, 0.06, 0.12, 0.21, 0.3, 0.42, 0.58, 0.75, 1.1, 1.45});
        U.add(new double[]{0, 0.07, 0.15, 0.25, 0.365, 0.58, 0.88, 1.3, 1.9, 2.75});
        U.add(new double[]{0, 0.08, 0.18, 0.3, 0.5, 0.82, 1.4, 2.1, 2.8, 3.62});
        U.add(new double[]{0, 0.09, 0.22, 0.42, 0.76, 1.3, 2, 2.75, 3.6, 4.4});
        U.add(new double[]{0, 0.11, 0.3, 0.68, 1.35, 2.1, 2.8, 3.6, 4.6, 5.1});
        U.add(new double[]{0, 0.18, 0.52, 1.2, 1.8, 2.6, 3.6, 4.6, 5.4, 6.4});
        U.add(new double[]{0, 0.32, 1, 1.8, 2.6, 3.4, 4.4, 5.2, 6.2, 7.2});
        U.add(new double[]{0, 0.54, 1.3, 2.2, 3, 4, 4.9, 5.8, 6.8, 7.8});
        U.add(new double[]{0, 0.76, 1.65, 2.5, 3.4, 4.4, 5.4, 6.3, 7.2, 8.2});
        U.add(new double[]{0, 0.9, 1.8, 2.7, 3.7, 4.6, 5.6, 7.6, 8.5});
        U.add(new double[]{0, 0.9, 1.8, 2.8, 3.8, 4.8, 5.8, 6.7, 7.8, 8.8});
        I.add(new double[]{0, 0.35, 0.7, 1.05, 1.4, 1.75, 2.1, 2.45, 2.8, 3.15});
        I.add(new double[]{0, 0.31, 0.68, 0.97, 1.29, 1.68, 2.02, 2.34, 2.68});
        I.add(new double[]{0, 0.295, 0.6, 0.96, 1.27, 1.68, 2.02, 2.34, 2.68, 3});
        I.add(new double[]{0, 0.31, 0.62, 0.97, 1.27, 1.66, 2, 2.3, 2.68, 3.05});
        I.add(new double[]{0, 0.305, 0.61, 0.95, 1.27, 1.64, 1.98, 2.3, 2.6, 2.9});
        I.add(new double[]{0, 0.305, 0.61, 0.95, 1.26, 1.64, 1.95, 2.22, 2.48, 2.66});
        I.add(new double[]{0, 0.4425, 0.885, 0.925, 1.23, 1.56, 1.82, 2.02, 2.14, 2.28});
        I.add(new double[]{0, 0.31, 0.61, 0.91, 1.19, 1.42, 1.61, 1.73, 1.82, 1.9});
        I.add(new double[]{0, 0.295, 0.575, 0.875, 1.09, 1.245, 1.35, 1.43, 1.5, 1.56});
        I.add(new double[]{0, 0.285, 0.545, 0.74, 0.88, 0.96, 1.02, 1.07, 1.12, 1.15});
        I.add(new double[]{0, 0.282, 0.465, 0.57, 0.635, 0.685, 0.73, 0.77, 0.8, 0.83});
        I.add(new double[]{0, 0.232, 0.315, 0.365, 0.4, 0.43, 0.455, 0.48, 0.5, 0.52});
        I.add(new double[]{0, 0.16, 0.208, 0.238, 0.26, 0.28, 0.3, 0.31, 0.33, 0.345});
        I.add(new double[]{0, 0.076, 0.098, 0.114, 0.128, 0.138, 0.15, 0.16, 0.168, 0.178});
        I.add(new double[]{0, 0.028, 0.038, 0.046, 0.052, 0.058, 0.064, 0.074, 0.08});
        I.add(new double[]{0, 0.006, 0.009, 0.012, 0.015, 0.018, 0.02, 0.022, 0.024, 0.026});
        for (int i = 0; i < U.size(); i++) {
            System.out.println(Arrays.toString(apacheApprox(I.get(i), U.get(i), 4)));
        }
    }

    public static double[] apacheApprox(double[] arrayOfValue, double[] arrayOfTime, int powerOfPol) {
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        for (int i = 0; i < arrayOfValue.length; i++)
            obs.add(arrayOfTime[i], arrayOfValue[i]);
        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(powerOfPol);
        return fitter.fit(obs.toList());
    }
}
