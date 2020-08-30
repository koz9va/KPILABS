package com.company;

public class Transistor {
    private static double betta;
    private static double g22;
    private static double U0;
    private final double[] Voltaic = new double[3];
    private final double[] Usv = new double[7];

    public Transistor(double betta, double g22, double u0, double uzv1, double uzv3, double usv1, double usv7) {
        Transistor.betta = betta;
        Transistor.g22 = g22;
        U0 = u0;
        Voltaic[0] = uzv1;
        Voltaic[1] = (uzv1 + uzv3) / 2;
        Voltaic[2] = uzv3;

        Usv[0] = usv1;
        Usv[6] = usv7;
        double deltaUsv = usv7 / 6;
        for (int i = 1; i < 6; i++) {
            Usv[i] += i * deltaUsv;
        }

}

    public static double getCurrent(double Uzv, double Usv) {
        if (Uzv <= U0) {
            return g22 * Usv;
        }
        else{
            if (Usv < (Uzv - U0))
                return betta * ( 2 * (Uzv - U0) - Usv) + g22 * Usv;
            else
                return betta * Math.pow((Uzv - U0), 2) + g22 * Usv;
        }
    }
    public double getVoltaic(int i) {
        return Voltaic[i];
    }

    public double getUsv(int i) {
        return Usv[i];
    }

    public double[] getVoltaic() {
        return Voltaic;
    }

    public double[] getUsv() {
        return Usv;
    }
}
