#include "ode.h"


int euler(
	double f(double t, double y),
	double tend,
	double y0,
	double eps,
	double *t,
	double *y,
	int nmax
	)
{
	int i;
	double h = tend;

	y[0] = y0;
	t[0] = 0;
	i = 0;
	do {
		double y1, y2, y3, R;

		y2 = y[i] + h * f(t[i], y[i]);

		do {
			if(h >= DBL_EPSILON) {
				h /= 2;
			} else {
				break;
			}
			y1 = y2;
			y2 = y[i] + h * f(t[i], y[i]);
			y3 = y2 + h * f(t[i] + h, y2);
			R = y3 - y1;
		} while(fabs(R) > eps * fabs(y3));
		++i;
		if(i >= nmax) {
			return nmax;
		}

		h *= 2;

		y[i] = y3;
		t[i] = t[i - 1] + h;
		if(fabs(R) < eps * fabs(y[i])) {
			h *= 2;
		}
	}while(t[i] < tend);

	return i;
}

int RK(
		double f(double t, double y),
		double tend,
		double y0,
		int m,
		double eps,
		double *t,
		double *y,
		int nmax
		)
{
	int i;
	double h = tend;

	y[0] = y0;
	t[0] = 0;
	i = 0;

	do {
		double y1, y2, y3, R, k[6];


	}while();

}
