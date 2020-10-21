#include <cstdio>
#include <cmath>
#include <cfloat>
double f1(double x, int *cnt) {
	++(*cnt);
	return x*x-3;
}

double f2(double x, int *cnt) {
	++(*cnt);
	return atan(x);
}


double Bisection(double f(double, int*), double a, double b, double eps, int *cnt) {
	double aSign, xi;
	aSign = f(a, cnt)/(fabs(f(a, cnt)));
	*cnt = 0;

	do {
		xi = (a+b)/2;
		if(aSign * f(xi, cnt) < 0) {
			b = xi;
		}else {
			a = xi;
		}
	}while (fabs(b - a) > eps * fabs(a));

	return xi;
}

double Newton(double f(double, int*), double x0, double xt, double eps, int *cnt) {
	double x1, fx, h;

	h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));

	fx = f(x0, cnt);

	x1 = x0 - (fx * h)/(f(x0+h, cnt) - fx);

	while(fabs((x1 - x0) / x0) > eps) {
		x0 = x1;

		fx = f(x0, cnt);

		x1 = x0 - (fx * h)/(f(x0+h, cnt) - fx);

		while(fabs(f(x1, cnt)) > fabs(f(x0, cnt))) {
			x1 = (x1 + x0) / 2;
		}

	}

	return x1;
}

double Secant(double f(double, int*), double x0, double x1, double eps, int *cnt) {
	double fx0, fx1, fx2, x2;

	while(true) {
		fx0 = f(x0, cnt);
		fx1 = f(x1, cnt);

		x2 = (x0 - (((x1-x0)/(fx1 - fx0)) * fx0));
		fx2 = f(x2, cnt);
		if(fx2 == 0)
			return x2;
		if(fabs(x0 - x2) <= eps)
			return x2;

		if((fx0)/fabs(fx0) == fx2/fabs(fx2))
			x0 = x2;
		else
			x1 = x2;

	}

}

int main() {

	int cnt;
	double fx;
	FILE* file;

	file = fopen("out.txt", "w");
	if(!file) {
		printf("Can't open file:(\n");
		return 21;
	}
	cnt = 0;
	fx = Bisection(f1, 1.0, 2.0, 1e-6, &cnt);
	fprintf(file, "Bisection method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	cnt = 0;
	fx = Newton(f1, 2.0, 2.0, 1e-6, &cnt);

	fprintf(file, "Newton method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	cnt = 0;
	fx = Secant(f1, 1.0, 2.0, 1e-6, &cnt);

	fprintf(file, "Secant method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	fclose(file);

	return 0;
}