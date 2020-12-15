#include <stdio.h>
#include <stdlib.h>
#include <float.h>
#include <math.h>
#include <string.h>

#include "QR.h"

int counter;

void test(double *x, double *y, int n) {
	y[0] = sin(x[0]) + sqrt(x[1]) - 2.0;
	y[1] = 2.0 * cos(x[0]) + sqrt(x[1]) - 3.0;
	++counter;
}

void Newton(void f(double *, double *, int), double *x, double eps, int n, FILE *file) {
	int i, j, cnt;
	double h, nx, ndX;

	double **J;
	double *dX;
	double *y;
	double *yp;

	J = (double**)malloc(sizeof(double*) * n);
	for(i = 0; i < n; ++i) {
		J[i] = (double*)malloc(sizeof(double) * n);
	}
	dX = (double*)malloc(sizeof(double) * n);
	y = (double*)malloc(sizeof(double) * n);
	yp = (double*)malloc(sizeof(double) * n);

	cnt = 0;

	do {
		f(x, y, n);
		for(j = 0; j < n; ++j) {
			h = sqrt(DBL_EPSILON) * x[j];
			x[j] += h;
			f(x, yp, n);
			for(i = 0; i < n; ++i) {
				J[i][j] = (yp[i] - y[i])/h;
			}
			x[j] -= h;
		}

		QR(J, (size_t)n, y, dX);

		nx = ndX = 0;
		for(i = 0; i < n; ++i) {
			x[i] -= dX[i];
			nx += x[i] * x[i];
			ndX += dX[i] * dX[i];
		}
		++cnt;
		fprintf(file, "%d:   x = %lf  y = %lf  fx = %lf fy = %lf\n", cnt, x[0], x[1], y[0], y[1]);
	}while(sqrt(ndX) >= eps * sqrt(nx));

	for(i = 0; i < n; ++i) {
		free(J[i]);
	}
	free(J);
	free(dX);
	free(y);
	free(yp);
}

void Broiden(void f(double*, double*, int), double *x, double eps, int n, FILE *file) {
	int i, j, cnt;
	double h, ndX, nx;
	double **J, **Jt;
	double *dX, *y, *yp;

	J = (double**)malloc(sizeof(double*) * n);
	Jt = (double**)malloc(sizeof(double*) * n);

	for(i = 0; i < n; ++i) {
		J[i] = (double*)malloc(sizeof(double) * n);
		Jt[i] = (double*)malloc(sizeof(double) * n);
	}
	dX = (double*)malloc(sizeof(double) * n);
	y = (double*)malloc(sizeof(double) * n);
	yp = (double*)malloc(sizeof(double) * n);

	f(x, y, n);

	for(j = 0; j < n; ++j) {
		h = sqrt(DBL_EPSILON) * x[j];
		x[j] += h;
		f(x, yp, n);
		for(i = 0; i < n; ++i) {
			J[i][j] = (yp[i] - y[i])/h;
		}

		x[j] -= h;
	}

	cnt = 0;

	do {
		for(i = 0; i < n; ++i) {
			memcpy(Jt[i], J[i], sizeof(double) * n);
		}
		QR(Jt, n, y, dX);
		nx = ndX = 0;
		for(i = 0; i < n; ++i) {
			x[i] -= dX[i];
			nx += x[i] * x[i];
			ndX += dX[i] * dX[i];
		}

		++cnt;
		fprintf(file, "%d:   x = %lf  y = %lf  fx = %lf fy = %lf\n", cnt, x[0], x[1], y[0], y[1]);

		f(x, y, n);

		for(i = 0; i < n; ++i) {
			for(j = 0; j < n; ++j) {
				J[i][j] -= (y[i] * dX[j])/ndX;
			}
		}

	}while(sqrt(ndX) >= eps * sqrt(nx));

	free(dX);
	free(y);
	free(yp);

	for(i = 0; i < n; ++i) {
		free(J[i]);
		free(Jt[i]);
	}
	free(J);
	free(Jt);

}

int main() {
	double x0[2] = {5.0, 10.0};
	FILE *file;

	file = fopen("data.txt", "w");

	counter = 0;
	fprintf(file, "Newton:\n");
	Newton(test, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\nBroiden\n", counter);
	x0[0] = 5.0;
	x0[1] = 10.0;

	counter = 0;

	Broiden(test, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\n", counter);

	fclose(file);

	return 0;
}