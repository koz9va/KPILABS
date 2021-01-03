#include <stdio.h>
#include <stdlib.h>
#include <float.h>
#include <math.h>
#include <string.h>

#include "QR.h"

int count;


void func(double *U, double *Y) {
	double g221, b1, u01, R1, R2, E, uvh, id1, id2, ugs2, uds2, g222, b2, u02;
	R1 = 1e3, R2 = 0.6e3, E = 5, u01 = -5, b1 = 0.8e-4, g221 = 10e-5, uvh = 3, u02 = -2.0, b2 = 5e-4, g222 = 2e-5;

	if(U[0] <= u01) {
		id1 = g221 * U[1];
	} else if(U[0] > u01 && U[1] < U[0] - u01) {
		id1 = b1 * (2.0 * (U[0] - u01) - U[1]) + g221 * U[1];
	} else {
		id1 = b1 * (U[0] - u01) * (U[0] - u01) + g221 * U[1];
	}
	ugs2 = -id1 * R2;
	uds2 = E - U[1] - id1 * ( R1 + R2);

	if(ugs2 <= u02) {
		id2 = g222 * U[1];
	} else if(ugs2 > u02 && uds2 < ugs2 - u02) {
		id2 = b2 * (2.0 * (ugs2 - u02) - uds2) + g222 * uds2;
	} else {
		id2 = b2 * (ugs2 - u02) * (ugs2 - u02) + g222 * uds2;
	}

	Y[0] = uvh - U[0] + U[1] - E;
	Y[1] = id2 - id1;

	++count;

}


void Brdn(void f(double*, double*), double *x, double eps, int n, FILE *file) {
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

	f(x, y);

	for(j = 0; j < n; ++j) {
		h = sqrt(DBL_EPSILON) * x[j];
		x[j] += h;
		f(x, yp);
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

		f(x, y);

		for(i = 0; i < n; ++i) {
			for(j = 0; j < n; ++j) {
				J[i][j] -= (y[i] * dX[j])/ndX;
			}
		}

	}while(sqrt(ndX) >= eps * sqrt(nx));

	free(dX);
	free(y);
	if(yp) {
		free(yp);
	}
	for(i = 0; i < n; ++i) {
		free(J[i]);
		free(Jt[i]);
	}
	free(J);
	if(Jt) {
		free(Jt);
	}


}

void Newt(void f(double*, double*), double* x, double eps, int n, FILE* file) {
	int i, j, cnt;
	double h, nx, ndX;

	double** J;
	double* dX;
	double* y;
	double* yp;

	J = (double**)malloc(sizeof(double*) * n);
	for (i = 0; i < n; ++i) {
		J[i] = (double*)malloc(sizeof(double) * n);
	}
	dX = (double*)malloc(sizeof(double) * n);
	y = (double*)malloc(sizeof(double) * n);
	yp = (double*)malloc(sizeof(double) * n);

	cnt = 0;

	do {
		f(x, y);
		for (j = 0; j < n; ++j) {
			h = sqrt(DBL_EPSILON) * x[j];
			x[j] += h;
			f(x, yp);
			for (i = 0; i < n; ++i) {
				J[i][j] = (yp[i] - y[i]) / h;
			}
			x[j] -= h;
		}

		QR(J, n, y, dX);

		nx = ndX = 0;
		for (i = 0; i < n; ++i) {
			x[i] -= dX[i];
			nx += x[i] * x[i];
			ndX += dX[i] * dX[i];
		}
		++cnt;
		fprintf(file, "%d:   x = %lf  y = %lf  fx = %lf fy = %lf\n", cnt, x[0], x[1], y[0], y[1]);
	} while (sqrt(ndX) >= eps * sqrt(nx));

	for (i = 0; i < n; ++i) {
		free(J[i]);
	}
	free(J);
	free(y);
	if (dX) {
		free(dX);
	}
	if (yp) {
		free(yp);
	}
}

int main() {
	double x0[2] = {0.5, 2.5};
	FILE *file;

	file = fopen("data.txt", "w");

	count = 0;
	fprintf(file, "Newton:\n");
	Newt(func, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\nBroiden\n", count);
	x0[0] = 0.5;
	x0[1] = 2.5;

	count = 0;

	Brdn(func, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\n", count);

	fclose(file);

	return 0;
}