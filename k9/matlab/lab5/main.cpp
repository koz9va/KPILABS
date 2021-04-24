#include <cstring>
#include <cstdio>
#include <cmath>
#include "ode.h"
#include "include/SLAU.h"

int cnt;

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

		++cnt;

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

	void find_voltage(lin::vector &t, lin::vector &y, lin::vector &ud) {

		for(int i = 0; i < t.n; ++i) {
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


int main() {
	int nmax = 1u << 16;
	int i, n;
	double tend;
	lin::vector t(nmax), y(nmax), Ud(nmax);
	FILE *y_file, *t_file;

	tend = 5e-6;

	cnt = 0;
	n = euler(&diff::equation, tend, 0, 1e-6, t.ptr, y.ptr, nmax);
	printf("Euler's method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
	cnt = 0;
	n = imp_euler(&diff::equation, tend, 0, 1e-6, t.ptr, y.ptr, tend/10000,nmax);
	printf("Implicit Euler's method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
	cnt = 0;
	n = RK45::RK45(&diff::equation, tend, 0, 1e-6, t.ptr, y.ptr, tend/10000, nmax);
	printf("Runge-Kutta method:\nPoints were found: %d; calls to function: %d\n", n, cnt);
	if(n == nmax) {
		printf("Quantity of allocated points might not be sufficient\n");
	}else {
		t.resize(n, true);
		y.resize(n, true);
	}
	diff::find_voltage(t, y, Ud);

	y_file = fopen("../y.txt", "w");
	t_file = fopen("../t.txt", "w");

	for(i = 0; i < n; ++i) {
		fprintf(y_file, "%g\n", Ud[i]);
		fprintf(t_file, "%g\n", t[i]);
	}

	fclose(y_file);
	fclose(t_file);

	return 0;
}
