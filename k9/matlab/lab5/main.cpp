#include <cstring>
#include <cstdio>
#include <cmath>
#include "ode.h"

namespace diff {
	constexpr double R = 1e3, C = 2e-9, i0 = 5000e-12, m = 1.7, ft = 26e-3, Rb0 = 1e3, Iv = 0.3e-3;
	constexpr double E0 = 5, Tp = 1e-6;
	double e(double t) {
		if(t < Tp) {
			return E0;
		} else {
			return 0;
		}
	}
	double equation(double t, double upn) {
		double arg = upn/m/ft, exp_value, j, Rb;

		if(arg > 80.0) {
			exp_value = exp(80);
		} else if(arg < -80.0) {
			exp_value = 0;
		} else {
			exp_value = exp(arg);
		}
		j = i0 * (exp_value - 1.0);
		Rb = Rb0/(1 + (j/Iv));

		return ((e(t) - upn)/(Rb + R) - j)/C;
	}

	void find_voltage(double *t, double *y, double *ud, int len) {
		for(int i = 0; i < len; ++i) {
			double Rb, j, arg, exp_value;
			arg = y[i]/m/ft;

			if(arg > 80) {
				exp_value = exp(80);
			}else if(arg < -80) {
				exp_value = 0;
			} else {
				exp_value = exp(arg);
			}

			j = i0 * (exp_value - 1);
			Rb = Rb0/(1.0 + (j/Iv));

			ud[i] = ((e(t[i]) - y[i])/(Rb + R))*Rb + y[i];
		}
	}

}

double test(double t, double y) {
	return -(t + 1) * y * y;
}

int main() {
	int nmax = 1u << 16;
	int i, n;
	double tend, *t, *y, *Ud, *tmp;
	FILE *y_file, *t_file;

	t = new double[nmax];
	y = new double[nmax];
	tend = 5e-6;
	

	n = euler(&diff::equation, tend, 0, 1e-6, t, y, nmax);
//	n = RK45::RK45(&diff::equation, tend, 0, 1e-6, t, y, tend, nmax);
	printf("Points were found: %d\n", n);
	if(n == nmax) {
		printf("Quantity of allocated points might not be sufficient\n");
	}else {
		tmp = new double[n];
		memmove(tmp, t, sizeof(double) * n);
		delete [] t;
		t = new double[n];
		memmove(t, tmp, sizeof(double) * n);
		memmove(tmp, y, sizeof(double) * n);
		delete [] y;
		y = new double[n];
		memmove(y, tmp, sizeof(double) * n);
		delete [] tmp;
	}

	Ud = new double [n];

	diff::find_voltage(t, y, Ud, n);

	y_file = fopen("../y.txt", "w");
	t_file = fopen("../t.txt", "w");

	for(i = 0; i < n; ++i) {
		fprintf(y_file, "%g\n", Ud[i]);
		fprintf(t_file, "%g\n", t[i]);
	}

	fclose(y_file);
	fclose(t_file);

	delete [] y;
	delete [] t;
	delete [] Ud;

	return 0;
}
