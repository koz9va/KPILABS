#include <cstdio>
#include <cfloat>
#include <cmath>
#include "include/SLAU.h"


void test(lin::vector &X, lin::vector &Y) {
	Y[0] = sin(X[0]) + sqrt(X[1]) - 2;
	Y[1] = 2 * cos(X[0]) + sqrt(X[1]) - 3;
}

void gnida(lin::vector &X, lin::vector &Y) {
	Y[0] = pow(sin(X[0]), 2) + sqrt(X[1]) - 1;
	Y[1] = sin(X[0]) - 2 * sqrt(X[1]) + 1;
}

void NewtonSNE(void FUN(lin::vector&, lin::vector&), lin::vector &X,  double eps) {
	int i, j;
	double h;
	lin::matrix J(X.n);
	lin::vector dX(X.n);
	lin::vector Y(X.n);
	lin::vector Yp(X.n);

	do {
		FUN(X, Y);
		for(j = 0; j < X.n; ++j) {
			h = sqrt(DBL_EPSILON) * X[j];
			X[j] += h;
			FUN(X, Yp);
			for(i = 0; i < X.n; ++i) {
				J.ptr[i][j] = (Yp[i] - Y[i])/h;
			}
			X[j] -= h;
		}
		J.QR(Y, dX);

		X -= dX;

	}while (dX.normalize() > eps * X.normalize());

}

void BroadenSNE(void FUN(lin::vector&, lin::vector&), lin::vector &X, double eps) {
	int i, j;
	double h, ndX;
	lin::matrix J(X.n);
	lin::matrix J1(X.n);
	lin::vector dX(X.n);
	lin::vector Y(X.n);
	lin::vector Yp(X.n);

	FUN(X, Y);

	for(j = 0; j < X.n; ++j) {
		h = sqrt(DBL_EPSILON) * X[j];
		X[j] += h;
		FUN(X, Yp);
		for(i = 0; i < X.n; ++i) {
			J.ptr[i][j] = (Yp[i] - Y[i])/h;
		}

		X[j] -= h;
	}


	do {

		J1 = J;
		J1.QR(Y, dX);

		X -= dX;

		ndX = dX.normalize();
		ndX = ndX * ndX;

		FUN(X, Y);


		for(i = 0; i < X.n; ++i) {
			for(j = 0; j < X.n; ++j) {
				J.ptr[i][j] -= (Y[i] * dX[j])/ndX;
			}
		}
	} while(sqrt(ndX) > eps * X.normalize());


}


int main() {

	lin::vector X(2);

	X[0] = 5.0;
	X[1] = 10.0;

	BroadenSNE(test, X, 1e-6);


	for(int i = 0; i < X.n; ++i) {
		printf("%e ", X[i]);
	}
	printf("\n");

	return 0;
}
