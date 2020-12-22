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

void f3(double *U, double *Y) { // U[0] = ugs, U[1] = uds
	double g22, b, u0, R1, R2, E, uvh, id1, id2, ugs2, uds2;
	R1 = 1e3, R2 = 0.6e3, E = 5, u0 = -5, b = 0.8e-4, g22 = 10e-5, uvh = 3;

	if(U[0] <= u0) {
		id1 = g22 * U[1];
	} else if(U[0] > u0 && U[1] < U[0] - u0) {
		id1 = b * (2.0 * (U[0] - u0) - U[1]) + g22 * U[1];
	} else {
		id1 = b * (U[0] - u0) * (U[0] - u0) + g22 * U[1];
	}
	ugs2 = -id1 * R2;
	uds2 = E - U[1] - id1 * ( R1 + R2);

	if(ugs2 <= u0) {
		id2 = g22 * U[1];
	} else if(ugs2 > u0 && uds2 < ugs2 - u0) {
		id2 = b * (2.0 * (ugs2 - u0) - uds2) + g22 * uds2;
	} else {
		id2 = b * (ugs2 - u0) * (ugs2 - u0) + g22 * uds2;
	}

	Y[0] = uvh - U[0] + U[1] - E;
	Y[1] = id2 - id1;

	++counter;

}
void Newton(void f(double *, double *), double *x, double eps, int n, FILE *file) {
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
	free(y);
	if(dX) {
		free(dX);
	}
	if(yp) {
		free(yp);
	}
}

void Broiden(void f(double*, double*), double *x, double eps, int n, FILE *file) {
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

int main() {
	double x0[2] = {1, 2};
	FILE *file;

	file = fopen("data.txt", "w");

	counter = 0;
	fprintf(file, "Newton:\n");
	Newton(f3, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\nBroiden\n", counter);
	x0[0] = 1;
	x0[1] = 2;

	counter = 0;

	Broiden(f3, x0, 1e-6, 2, file);
	fprintf(file, "function calls = %d\n", counter);

	fclose(file);

	return 0;
}