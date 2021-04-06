package sem2.lab4MMFWithSpring.service;

import org.springframework.stereotype.Service;

@Service
public class LabService implements ServiceRepository {

    @Override
    public double NewtonCotes(FunctionForNewton func, int[] C, double a, double b, int sum, double eps, int m) {
        double I1, I2, R, h, x;
        int i, j, k, n, p;

        if(Math.abs(a - b) < Math.ulp(1.0) * 2)
            return 0;


        n = 1;
        I1 = func.calculateFunction(a) * C[0];
        h = (b - a) / n;
        x = a;
        p = ( 1 << (m + 2)) - 1;
        for(i = 1; i < m + 1; ++i) {
            x += h / m;
            I1 += C[i] * func.calculateFunction(x);
        }
        I1 *= h;

        for(k = 0; k < 30; ++k) {
            n *= 2;
            h = (b - a)/n;
            I2 = I1;
            if(h < (Math.ulp(1.0) * 2)) {
                I1 /= sum;
                break;
            }
            x = a;
            I1 = (func.calculateFunction(a) - func.calculateFunction(b)) * C[0];
            for(i = 0; i < n; ++i) {
                for(j = 1; j < m; ++j) {
                    x += h / m;
                    I1 += C[j] * func.calculateFunction(x);
                }
                x += h / m;
                I1 += 2.0 * C[0] * func.calculateFunction(x);
            }
            I1 *= h;
            R = Math.abs((I1 - I2) ) / p;
            if(Math.abs(R) < Math.abs(eps * I1)) {
                I1 += R;
                I1 /= sum;
                break;
            }
        }
        return I1;
    }
}
