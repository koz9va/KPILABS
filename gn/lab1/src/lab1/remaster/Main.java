package lab1.remaster;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    private final static int UgsLengthOfArray = 3;
    private final static int UdsLengthOfArray = 7;

    private static double g22;
    private static double betta;
    private static double U0;

    public static void main(String[] args) {
        if (args.length == 7) {

            try(BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Enter file to write Ugs");
                String UgsFileName = consoleReader.readLine();

                System.out.println("Enter file to write Uds");
                String UdsFileName = consoleReader.readLine();

                System.out.println("Enter file to write Current");
                String CurrentFileName = consoleReader.readLine();

                double[] arrayOfUgs = new double[UgsLengthOfArray];
                double[] arrayOfUds = new double[UdsLengthOfArray];

                Main.betta = Double.parseDouble(args[0]);
                Main.g22 = Double.parseDouble(args[1]);
                Main.U0 = Double.parseDouble(args[2]);
                arrayOfUgs[0] = Double.parseDouble(args[3]);
                arrayOfUgs[arrayOfUgs.length - 1] = Double.parseDouble(args[4]);
                arrayOfUds[0] = Double.parseDouble(args[5]);
                arrayOfUds[arrayOfUds.length - 1] = Double.parseDouble(args[6]);

                getFullArray(arrayOfUds);
                getFullArray(arrayOfUgs);

                try(BufferedWriter writer = new BufferedWriter(new FileWriter(UgsFileName))){
                    for (double arrayOfUg : arrayOfUgs) {
                        writer.write(arrayOfUg + "\n");
                    }
                    writer.flush();
                }

                try(BufferedWriter writer = new BufferedWriter(new FileWriter(UdsFileName))){
                    for (double arrayOfUd : arrayOfUds) {
                        writer.write(arrayOfUd + "\n");
                    }
                    writer.flush();
                }

                try(BufferedWriter writer = new BufferedWriter(new FileWriter(CurrentFileName))) {
                    MathContext context = new MathContext(4, RoundingMode.HALF_UP);
                    for (double arrayOfUg : arrayOfUgs) {
                        for (double arrayOfUd : arrayOfUds) {
                            writer.write(new BigDecimal(getCurrent(arrayOfUg, arrayOfUd), context)  + " ");
                        }
                        writer.write("\n");
                    }
                    writer.flush();
                }
            }
            catch (IOException ignored) {
                System.out.println("E0");
            }
        }
        else System.out.println("Enter 7 arguments");
    }


    private static double getCurrent(double Ugs, double Uds) {
        if (Ugs <= U0) {
            return g22 * Uds;
        }
        else {
            if (Uds < (Ugs - U0)){
                return betta * (2 * (Ugs - U0) - Uds) * Uds + g22 * Uds;
            }
            else {
                return betta * (Ugs - U0) * (Ugs - U0) + g22 * Uds;
            }
        }
    }

    private static void getFullArray(double[] arr) {
        double step = (arr[arr.length - 1] - arr[0]) / (arr.length - 1);
        for (int i = 1; i < arr.length - 1; i++) {
            arr[i] = arr[i - 1] + step;
        }
    }
}
