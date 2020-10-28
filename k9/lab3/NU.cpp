//
// Created by koz9va on 26.10.20.
//
#include "NU.h"


double Bisection(double f(double), double a, double b, double eps, FILE *file) {
    double aSign, xi;
    aSign = f(a)/(fabs(f(a)));
	if(!file) {
		exit(21);
	}
    do {
        xi = (a+b)/2;

        fprintf(file, "%e\n", xi);

        if(aSign * f(xi) < 0) {
            b = xi;
        }else {
            a = xi;
        }
    }while (fabs(b - a) > eps * fabs(a));

    return xi;
}

double Newton(double f(double), double x0, double xt, double eps, FILE *file) {
    double x1, fx, h;
	if(!file) {
		exit(21);
	}
    h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));

    fx = f(x0);

    x1 = x0 - (fx * h)/(f(x0+h) - fx);

    do {

        x0 = x1;
        h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));

        fx = f(x0);

        x1 = x0 - (fx * h)/(f(x0+h) - fx);
		fprintf(file, "%e\n", x1);

    } while (fabs((x1 - x0) / x0) > eps);


    return x1;
}

double Secant(double f(double), double x0, double x1, double eps, FILE *file) {
    double fx0, fx1, fx2, x2;
	if(!file) {
		exit(21);
	}
    fx0 = f(x0);
    fx1 = f(x1);
    while(true) {

        x2 = (x0 - (((x1-x0)/(fx1 - fx0)) * fx0));

		fprintf(file, "%e\n", x2);

        fx2 = f(x2);
        if(fx2 == 0)
            return x2;
        if(fabs(x0 - x2) <= eps)
            return x2;

        if((fx0)/fabs(fx0) == fx2/fabs(fx2))
            x0 = x2;
        else
            x1 = x2;



        fx0 = fx1;
        fx1 = fx2;

    }

}


double f4(double x) {
    ++cnt;
    if(fabs(x) < 1.5) {
        return 2e-3 * (-6.0 - x ) * x + 1e-4*x;
    } else {
        return 45e-4 + 1e-4 * x;
    }
}

