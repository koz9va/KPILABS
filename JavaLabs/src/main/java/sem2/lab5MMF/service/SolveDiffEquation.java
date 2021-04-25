package sem2.lab5MMF.service;

public class SolveDiffEquation {
    private static final double[] a = {0, 1.0/4.0, 3.0/8.0, 12.0/13.0, 1, 0.5};
    private static final double[] c = {16.0/135.0, 0, 6656.0/12825.0, 28561.0/56430.0, -9.0/50.0, 2.0/55.0};
    private static final double[] c_s = {25.0/216.0, 0, 1408.0/2565.0, 2197.0/4104.0, -1.0/5.0, 0};
    private static final double[][] b = {
        {1.0/4.0},
        {3.0/32.0, 9.0/32.0},
        {1932.0/2197.0, -7200.0/2197.0, 7296.0/2197.0},
        {439.0/216.0, -8.0, 3680.0/513.0, -845.0/4104.0},
        {-8.0/27.0, 2.0, -3544.0/2565.0, 1859.0/4104.0, -11.0/40.0}
    };

    public static int euler(DiffFunction func,
                            double tend,
                            double y0,
                            double eps,
                            double[] t,
                            double[] y,
                            int nMax
    ) {
        int i;
        double h = tend;

        y[0] = y0;
        t[0] = 0;
        i = 0;
        do {
            double y1, y2, y3 = 0, R = 0;

            y2 = y[i] + h * func.calc(t[i], y[i]);

            do {
                if(h >= Math.ulp(1.0)) {
                    h /= 2;
                } else {
                    break;
                }
                y1 = y2;
                y2 = y[i] + h * func.calc(t[i], y[i]);
                y3 = y2 + h * func.calc(t[i] + h, y2);
                R = y3 - y1;
            } while(Math.abs(R) > eps * Math.abs(y3));
            ++i;
            if(i >= nMax) {
                return nMax;
            }

            h *= 2;

            y[i] = y3;
            t[i] = t[i - 1] + h;
            if(Math.abs(R) < eps * Math.abs(y[i])) {
                h *= 2;
            }
        }while(t[i] < tend);

        return i;
    }

    public static int imp_euler(DiffFunction func,
                            double tend,
                            double y0,
                            double eps,
                            double[] t,
                            double[] y,
                            int nMax
    ) {
        int i;
        double h = tend;

        y[0] = y0;
        t[0] = 0;
        i = 0;
        do {
            double y1, y2, y3 = 0, R, dx, eps_y1;

            y2 = y[i] + h * func.calc(t[i], y[i]);

            do {
                if(h >= Math.ulp(1)) {
                    h /= 2;
                }else {
                    break;
                }
                y1 = y2;
                y2 = y[i] + h * func.calc(t[i], y[i]);
                y3 = y2 + h * func.calc(t[i] + h, y2);
                R = y3 - y1;
            }while(Math.abs(R) > eps * Math.abs(y3));// && h >= h_min);

            do {
                y1 = y3;
                dx = Math.sqrt(Math.ulp(1)) * y3;
                y3 = y3 - (y3 * dx)/(func.calc(t[i], y3 + dx) - y3);
                eps_y1 = eps * Math.abs(y1);
            }while(Math.abs(y3 - y1) >= eps_y1);

            if(++i >= nMax) {
                return nMax;
            }

            h *= 2;

            y[i] = y3;
            t[i] = t[i - 1] + h;
        } while(t[i] < tend);
        return i;
    }

    public static int RK45 (DiffFunction func,
                            double tend,
                            double y0,
                            double eps,
                            double[] t,
                            double[] y,
                            int nMax
    ) {
        int i, j;
        double h;
        double R = 0;

        y[0] = y0;
        t[0] = 0;
        i = 0;

        h = tend;

        do {
            double yi = 0;
            double[] k = new double[6];

            do {
                if(h > 2 * Math.ulp(1)) {
                    h /= 2;
                } else {
                    break;
                }

                k[0] = func.calc(t[i], y[i]);
                k[1] = func.calc(t[i] + a[1] * h, y[i] + h * b[0][0] * k[0]);
                k[2] = func.calc(t[i] + a[2] * h, y[i] + h * (b[1][0] * k[0] + b[1][1] * k[1]));
                k[3] = func.calc(t[i] + a[3] * h, y[i] + h * (b[2][0] * k[0] + b[2][1] * k[1] + b[2][2] * k[2]));
                k[4] = func.calc(t[i] + a[4] * h, y[i] + h * (b[3][0] * k[0] + b[3][1] * k[1] + b[3][2] * k[2] + b[3][3] * k[3]));
                k[5] = func.calc(
                        t[i] + a[5] * h,
                        y[i] + h * (b[4][0] * k[0] + b[4][1] * k[1] + b[4][2] * k[2] + b[4][3] * k[3] + b[4][4] * k[4])
                );

                yi = 0;
                R = 0;
                for(j = 0; j < 6; ++j) {
                    yi += c[j] * k[j];
                    R += (c[j] - c_s[j]) * k[j];
                }
                yi = h * yi + y[i];
                R *= h;

            }while(Math.abs(R) > eps * Math.abs(yi));// && h >= h_min);

            if(++i >= nMax) {
                break;
            }

            y[i] = yi;
            t[i] = t[i - 1] + h;
            if((Math.abs(R) < eps * Math.abs(yi))) {
                h *= 2;
            }

        }while(t[i] < tend);

        return i;
    }
}
