package sem2.lab5MMF.service;

public class SolveDiffEquation {
    private static final double[] a = {0.0, 1.0f/5.0, 3.0/10.0, 3.0/5.0, 1.0, 7.0/8.0};
    private static final double[] c = {37.0/378.0, 0.0, 250.0/621.0, 125.0/594.0, 0.0, 512.0/1771.0};
    private static final double[] c_z = {2825.0/27648.0, 0.0, 18575.0/48384.0, 13525.0/55296.0, 277.0/14336.0, 1.0/4.0};
    private static final double[][] b = {
        {1.0/5.0},
        {3.0/40.0, 9.0/40.0},
        {3.0/10.0, -9.0/10, 6.0/5.0},
        {-11.0/54.0, 5.0/2.0, -70.0/27.0, 35.0/27.0},
        {1631.0/55296.0, 175.0/512.0, 575.0/13824.0, 44275.0/110592.0, 253.0/4096.0}
    };

    public static int Eul(DiffFunction function,
                          double tEnd,
                          double firstValue,
                          double epsilon,
                          double[] argument,
                          double[] value,
                          int nMax,
                          double h_max
    ) {
        int i = 0;
        double h = tEnd;

        value[0] = firstValue;
        argument[0] = 0;

        do {
            double y1, y2, y3, R;

            y2 = value[i] + h * function.calc(argument[i], value[i]);
            do {
                h /= 2;
                y1 = y2;
                y2 = value[i] + h * function.calc(argument[i], value[i]);
                y3 = y2 + h * function.calc(argument[i] + h, y2);
                R = y3 - y1;
            } while(Math.abs(R) > epsilon * Math.abs(y3));
            ++i;
            if(i >= nMax)
                return nMax;
            h *= 2.0;
            value[i] = y3;
            argument[i] = argument[i - 1] + h;

            if(Math.abs(R) < epsilon * Math.abs(value[i]) && h < h_max / 2.0) h *= 2.0;

        } while(argument[i] < tEnd);
        return i;
    }

    public static int imEul(DiffFunction function,
                            double tEnd,
                            double firstValue,
                            double epsilon,
                            double[] argument,
                            double[] value,
                            int nMax
    ) {
        int i = 0;
        double h = tEnd/20.0;

        value[0] = firstValue;
        argument[0] = 0.0;

        do {
            double R, y2;

            do {
                double y1 = value[i] + h * function.calc(argument[i], value[i]), dy;

                y2 = y1;

                do {
                    double dt = Math.sqrt(Math.ulp(1.0)) * y2, fx = function.calc(argument[i] + h, y2);
                    dy = -(y2 - value[i] - h * fx) / (1.f - h * (function.calc(argument[i] + h, y2 + dt) - fx) / dt);
                    y2 += dy;
                } while(epsilon * Math.abs(y2) < Math.abs(dy));

                h /= 2.0;
                R = y2 - y1;

            } while(epsilon * Math.abs(y2) < Math.abs(R));
            h *= 2.0;
            ++i;
            if(i >= nMax) return nMax;

            if(epsilon * Math.abs(y2) / 4.0 > Math.abs(R)) h *= 2.0;

            value[i] = y2;
            argument[i] = argument[i - 1] + h;
        } while(argument[i] < tEnd);

        return i;
    }

    public static int RunKut(DiffFunction function,
                             double tEnd,
                             double firstValue,
                             double epsilon,
                             double[] argument,
                             double[] value,
                             int nMax,
                             double h_max
    ) {
        int i = 0;

        double h;
        value[0] = firstValue;
        argument[0] = 0;
        h = tEnd;
        do {
            double R = 0, y2 = 0;
            do {
                double[] k = new double[6];

                if(h > 2.0f * Math.ulp(1.0)) {
                    h /= 2.0f;
                } else break;

                k[0] = function.calc(argument[i], value[i]);
                k[1] = function.calc(argument[i] + a[1] * h, value[i] + h * b[0][0]);
                k[2] = function.calc(argument[i] + a[2] * h, value[i] + h * (b[1][0] * k[0] + b[1][1] * k[1]));
                k[3] = function.calc(argument[i] + a[3] * h, value[i] + h * (b[2][0] * k[0] + b[2][1] * k[1] + b[2][2] * k[2]));
                k[4] = function.calc(argument[i] + a[4] * h, value[i] + h * (b[3][0] * k[0] + b[3][1] * k[1] + b[3][2] * k[2] + b[3][3] * k[3]));
                k[5] = function.calc(argument[i] + a[5] * h, value[i] + h * (b[4][0] * k[0] + b[4][1] * k[1] + b[4][2] * k[2] + b[4][3] * k[3] + b[4][4] * k[4]));
                y2 = 0.0f;
                R = 0.0f;
                for(int j = 0; j < 6; j++) {
                    y2 += c[j] * k[j];
                    R += (c[j] - c_z[j]) * k[j];
                }

                R *= h;
                y2 = h * y2 + value[i];
            } while(epsilon * Math.abs(y2) < Math.abs(R));
            ++i;

            if(i >= nMax) break;

            value[i] = y2;
            argument[i] = argument[i - 1] + h;

            if(epsilon * Math.abs(y2) > Math.abs(R) && h < h_max / 2.f) h *= 2.f;

        } while(argument[i] < tEnd);

        return i;
    }
}
