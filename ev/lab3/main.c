#include <stdio.h>
#include <math.h>
#include <float.h>

int counter;

double f2(double ua) {
	double g = 1e-4;
	double ea = 180;
	double es = 1.5;
	double D = 0.05;
	double R = 40e3;
	double X = 3.5;
	double ia;
	counter++;
	ia = g * pow((-es + D * ua)/(1.f + X*D), 1.5);
	return ea - ua - R * ia;
}

double test(double x) {
	counter++;
	return x * x - 3;
}

double NEBis(double a, double b, double eps, FILE *out, double f(double)) {
	double aDiff;
	double x;
	double fx;
	aDiff = f(a)/fabs(a);
	do {
		x = (a + b)/2;
		fx = f(x);
		if(aDiff * fx >= 0) {
			a = x;
		} else {
			b = x;
		}
		fprintf(out, "x = %lf f(x) = %lf\n", x, fx);
	} while(fabs(b - a) >= eps * fabs(a));
	return x;
}

double NENew(double x0, double eps, FILE *out, double f(double)) {
	double x1;
	double fx;
	double h;

	x1 = x0;

	do {
		x0 = x1;
		h = sqrt(DBL_EPSILON) * fabs(x0);
		fx = f(x0);
		fprintf(out, "x = %lf fx = %lf\n", x0, fx);
		x1 = x0 - (fx * h) / (f(x0 + h) - fx);
	}while(fabs(x1 - x0) >= eps * fabs(x0));
	return x1;
}

double NESec(double x0, double x1, double eps, FILE *out, double f(double)) {
	double fx0;
	double fx1;
	double fx2;
	double x2;

	fx0 = f(x0);
	fx1 = f(x1);
	while(1) {
		x2 = x1 - (((x1 - x0) * fx1) / (fx1 - fx0));
		fx2 = f(x2);
		fprintf(out, "x = %lf fx = %lf\n", x2, fx2);
		if(fabs(x2 - x1) < eps * fabs(x1))
			break;
		x0 = x1;
		x1 = x2;
		fx0 = fx1;
		fx1 = fx2;
	}
	return x2;
}

int main() {
	FILE *outFile;

	outFile = fopen("data.txt", "w");

	counter = 0;
	fprintf(outFile, "Метод Бісекції:\n");
	NEBis(100, 200, 1e-6, outFile, f2);
	fprintf(outFile, "Виклики функції: %d\n", counter);

	counter = 0;
	fprintf(outFile, "Метод Ньютона\n");
	NENew(120, 1e-6, outFile, f2);
	fprintf(outFile, "Виклики функції: %d\n", counter);

	counter = 0;
	fprintf(outFile, "Метод Січних\n");
	NESec(120, 140, 1e-6, outFile, f2);
	fprintf(outFile, "Виклики функції: %d\n", counter);

	fclose(outFile);
	return 0;
}
