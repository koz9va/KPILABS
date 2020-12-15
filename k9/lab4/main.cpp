#include <cstdio>
#include <cfloat>
#include <cmath>
#include "include/SLAU.h"

#include <string>

int cnt;

void F5(lin::vector &X, lin::vector &Y) {
	//0 - uce 1 - ube
	double Rc = 1e2, ie0 = 1e-9, a = 0.98, h12 = 2e-3, mc = 1.5, ft = 26e-3, eb = 4.0, me = 1.01, Rb = 5e3, ec = 10.0,
	ic0 = 1.5e-9;

	Y[0] = X[0] - ec + Rc * ie0 * a * (exp( (X[1] + h12*X[0])/(me * ft) ) - 1) -
			Rc * ic0 * (exp( (X[1] - X[0])/(me * ft) ) - 1.0);

	Y[1] = X[1] - eb + Rb * ic0 * (exp( (X[1] - X[0]) / (me * ft) ) - 1.0) + Rb * (1.0 - a) *
			ie0 * (exp( (X[1] + h12 * X[0])/(me * ft) ) - 1.0);
	++cnt;
}

void NewtonSNE(void FUN(lin::vector&, lin::vector&), lin::vector &X,  double eps, FILE *out = nullptr) {
	int i, j, d;
	double h;
	lin::matrix J(X.n);
	lin::vector dX(X.n);
	lin::vector Y(X.n);
	lin::vector Yp(X.n);


	d = 0;
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

		if(out) {
			fprintf(out, "%d:\tx1: %e x2: %e fx1: %e\t fx2: %e\n", d, X[0], X[1], Y[0], Y[1]);
			++d;
		}

	}while (dX.normalize() > eps * X.normalize());

}

void BroadenSNE(void FUN(lin::vector&, lin::vector&), lin::vector &X, double eps, FILE *out = nullptr) {
	int i, j, d;
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

	d = 0;
	do {

		J1 = J;
		J1.QR(Y, dX);

		X -= dX;

		if(out) {
			fprintf(out, "%d:\tx1: %e x2: %e fx1: %e\t fx2: %e\n", d, X[0], X[1], Y[0], Y[1]);
			++d;
		}

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

constexpr int num = 2;

int main() {

	lin::vector X(num);

	std::string x;
	FILE *outFile;

	fopen_s(&outFile, "outData.txt", "w");

	if(outFile == nullptr) {
		printf("There is an error with file occurred :(\n");
	}

	for(int i = 0; i < num; ++i) {
		std::cout << "Enter x" << i << ":" << std::endl;
		std::getline(std::cin, x);
		X[i] = std::stod(x);
	}

	lin::vector Xc(X);

	cnt = 0;

	fprintf(outFile, "Newton's method:\n");
	NewtonSNE(F5, X, 1e-6, outFile);

	for(int i = 0; i < X.n; ++i) {
		fprintf(outFile, "%e ", X[i]);
	}

	fprintf(outFile, "\nfunction calls: %d\n", cnt);

	cnt = 0;

	fprintf(outFile, "Broyden method:\n");
	BroadenSNE(F5, Xc, 1e-6, outFile);

	for(int i = 0; i < X.n; ++i) {
		fprintf(outFile,"%e ", X[i]);
	}
	fprintf(outFile, "\n");

	fprintf(outFile, "\nfunction calls: %d\n", cnt);

	fclose(outFile);
	return 0;
}
