#include <cstdio>
#include <cmath>
#include <cfloat>

int cnt;

double f1(double x) {
	++cnt;
	return x*x-3;
}

double f2(double x) {
	++cnt;
	return 3e-10*(exp(x/(1.7*26e-3)) - 1);
}

double f3(double x) {
	++cnt;
	return x*x*x - sqrt(19);
}



double Bisection(double f(double), double a, double b, double eps) {
	double aSign, xi;
	aSign = f(a)/(fabs(f(a)));

	do {
		xi = (a+b)/2;
		if(aSign * f(xi) < 0) {
			b = xi;
		}else {
			a = xi;
		}
	}while (fabs(b - a) > eps * fabs(a));

	return xi;
}

double Newton(double f(double), double x0, double xt, double eps, FILE* file) {
	double x1, fx, h;

	if(!file) {
		printf("Wrong file pointer :(\n");
		exit(21);
	}

	h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));

	fx = f(x0);

	x1 = x0 - (fx * h)/(f(x0+h) - fx);

	do {
		fprintf(file, "%e\n", x0);
		x0 = x1;
		h = sqrt(DBL_EPSILON) * fmax(fabs(x0), fabs(xt));

		fx = f(x0);

		x1 = x0 - (fx * h)/(f(x0+h) - fx);


	} while (fabs((x1 - x0) / x0) > eps);


	return x1;
}

double Secant(double f(double), double x0, double x1, double eps) {
	double fx0, fx1, fx2, x2;
	fx0 = f(x0);
	fx1 = f(x1);
	while(true) {

		x2 = (x0 - (((x1-x0)/(fx1 - fx0)) * fx0));
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

int main() {

	double fx;
	FILE* file;

	file = fopen("out.txt", "w");
	if(!file) {
		printf("Can't open file:(\n");
		return 21;
	}
	cnt = 0;
	fx = Bisection(f1, 1.0, 2.0, 1e-6);
	fprintf(file, "Bisection method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	cnt = 0;

	fx = Newton(f3, 1.0, 3.0, 1e-6, file);

	fprintf(file, "Newton method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	cnt = 0;
	fx = Secant(f3, -1.0, 3.0, 1e-6);

	fprintf(file, "Secant method:\nThe root is: %e\nIterations count: %d\n", fx, cnt);

	fclose(file);

	return 0;
}