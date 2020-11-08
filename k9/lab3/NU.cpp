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
        xi = (a+b)/2.0;

        fprintf(file, "%e\n", xi);

        if(aSign * f(xi) < 0) {
            b = xi;
        }else {
            a = xi;
        }

        if(cnt > 100)
			break;

    } while (fabs(b - a) >= eps);

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


    } while (fabs((x1 - x0) / x0) > eps * fabs(x1));


    return x1;
}

double Secant(double f(double), double x0, double x1, double eps, FILE *file) {
    double fx0, fx1, fx2, x2, h;

	if(!file) {
		exit(21);
	}

	h = sqrt(DBL_EPSILON) * x0;
	fx1 = f(x1);
	x2 = x1 - (h * fx1) / (f(x0 + h) - fx1);
	fx2 = f(x2);
    do {
		x0 = x1;
		x1 = x2;
		fx0 = fx1;
		fx1 = fx2;
		x2 = x1 - (((x1 - x0) * fx1) / (fx1 - fx0));
		fx2 = f(x2);

    } while(fabs(x2 - x1) > eps * fabs(x1));
	return x2;

}


double f4(double ud) {
    ++cnt;
    double egs = 1.5,
    eds = 10.0,
    R = 3e3,
    u0 = -3.0,
    Beta = 2e-3,
    g22 = 1e-4,
    id;

    if(egs <= u0) {
    	id = g22 * ud;
    } else if(fabs(ud) < -(egs + u0)) {
    	id = Beta * (-2.0 * (egs + u0) - ud) * ud + g22 * ud;
    } else {
    	id = Beta * (egs + u0) * (egs + u0) + g22 * ud;
    }
	return eds - ud - R * id;
}

