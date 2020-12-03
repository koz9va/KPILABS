//
// Created by koz9va on 26.10.20.
//
#include "NU.h"


double Bisection(double f(double), double a, double b, double eps, FILE *file) {
    double aSign, xi, fx;
    int i;
    aSign = f(a)/(fabs(f(a)));
	if(!file) {
		exit(21);
	}
	i = 0;
    do {
    	++i;
        xi = (a+b)/2.0;
		fx = f(xi);
		fprintf(file, "x: %e\t fx: %e\n", xi, fx);
        if(aSign * fx < 0) {
            b = xi;
        }else {
            a = xi;
        }



    } while (fabs(b - a) >= eps * fabs((a+b)/2));
	fprintf(file, "Iterations count: %d\n", i);
    return xi;
}

double Newton(double f(double), double x0, double xt, double eps, FILE *file) {
    double x1, fx, h;
    int i;
    	if(!file) {
		exit(21);
	}
    i = 0;
    x1 = x0;

	do {
		x0 = x1;
		h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));
		fx = f(x0);
		fprintf(file, "x: %e\tfx: %e\n", x0, fx);
		x1 = x0 - (fx * h) / (f(x0 + h) - fx);
		if(i++ > 8)
			break;
	}while(fabs(x1 - x0) >= eps * fabs(x0));

	fprintf(file, "Iterations count: %d\n", i);

	return x1;
}

double Secant(double f(double), double x0, double x1, double eps, FILE *file) {
    double fx0, fx1, fx2, x2, h;
	int i;
	if(!file) {
		exit(21);
	}
	i = 0;
	x2 = x1;
	fx1 = f(x1);
	do {
		x0 = x1;
		x1 = x2;
		h = sqrt(DBL_EPSILON) * fabs(x0);
		x2 = x1 - (fx1 * h) / (f(x1 + h) - fx1);
		fx2 = f(x2);
		fprintf(file, "x: %e\tfx: %e\n", x2, fx2);
		fx1 = fx2;

	} while(fabs(x2 - x1) >= eps * fabs(x1));

    fprintf(file, "Iterations count: %d\n", i);

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
double rgr(double x) {
	++cnt;
	return (x * x * x) - (2 * (x * x)) - (3 * x) + 5;
}


