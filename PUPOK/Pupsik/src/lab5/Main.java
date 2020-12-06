package lab5;


public class Main {


    public static void main(String[] args) {

    }

    public static void IsaakSLAU(Func func, double[] X, double eps) {
        double[][] J = new double[X.length][X.length];
        double[] Dx = new double[X.length];
        double[] Y = new double[X.length];
        double[] Ypr = new double[X.length];

        do {
            double h;
            func.func(X, Y);

            for (int j = 0; j < X.length; j++) {
                h = Math.sqrt(Math.ulp(1.0)) * X[j];
                X[j] += h;
                func.func(X, Ypr);

                for (int i = 0; i < X.length; i++) {
                    J[i][j] = Ypr[i] - Y[i] / h;
                }
                X[j] -= h;
            }
            //QR
            /*substract vectors*/
        }
        while (/*norm*/);
    }

    public static void asd(Driveble driveble) {
        driveble.sitZaRul();
        driveble.drive();
    }

    public static void function (double[] X, double[] Y) {
        Y[0] = Math.sin(X[0]) * Math.sin(X[0]) + Math.sqrt(X[1]) - 1.0;
        Y[1] = Math.sin(X[0]) - 2.0 * Math.sqrt(X[1]) + 1.0;
    }

    public static void function2 (double[] X, double[] Y) {

    }

}
