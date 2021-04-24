#include "ode.h"


int euler(
	double f(double, double),
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
int imp_euler(
		double f(double, double),
		double tend,
		double y0,
		double eps,
		double *t,
		double *y,
		double h_min,
		int nmax
	)
{
	int i;
	double h = tend;

	y[0] = y0;
	t[0] = 0;
	i = 0;
	do {
		double y1, y2, y3, R, dx, eps_y1;

		y2 = y[i] + h * f(t[i], y[i]);

		do {
			if(h >= DBL_EPSILON) {
				h /= 2;
			}else {
				break;
			}
			y1 = y2;
			y2 = y[i] + h * f(t[i], y[i]);
			y3 = y2 + h * f(t[i] + h, y2);
			R = y3 - y1;
		}while(fabs(R) > eps * fabs(y3) && h >= h_min);

		do {
			y1 = y3;
			dx = sqrt(DBL_EPSILON) * y3;
			y3 = y3 - (y3 * dx)/(f(t[i], y3 + dx) - y3);
			eps_y1 = eps * fabs(y1);
		}while(fabs(y3 - y1) >= eps_y1);

		if(++i >= nmax) {
			return nmax;
		}

		h *= 2;

		y[i] = y3;
		t[i] = t[i - 1] + h;
	} while(t[i] < tend);
	return i;
}
int RK45::RK45(
		double f(double, double),
		double tend,
		double y0,
		double eps,
		double *t,
		double *y,
		double h_min,
		int nmax
		)
{
	int i, j;
	double h;
	double R;

	y[0] = y0;
	t[0] = 0;
	i = 0;

	h = h_min;

	do {
		double yi, k[6];

		do {
			if(h > 2 * DBL_EPSILON) {
				h /= 2;
			} else {
				break;
			}

			k[0] = f(t[i], y[i]);
			k[1] = f(t[i] + a[1] * h, y[i] + h * b[0][0] * k[0]);
			k[2] = f(t[i] + a[2] * h, y[i] + h * (b[1][0] * k[0] + b[1][1] * k[1]));
			k[3] = f(t[i] + a[3] * h, y[i] + h * (b[2][0] * k[0] + b[2][1] * k[1] + b[2][2] * k[2]));
			k[4] = f(t[i] + a[4] * h, y[i] + h * (b[3][0] * k[0] + b[3][1] * k[1] + b[3][2] * k[2] + b[3][3] * k[3]));
			k[5] = f(
					t[i] + a[5] * h,
					y[i] + h * (b[4][0] * k[0] + b[4][1] * k[1] + b[4][2] * k[2] + b[4][3] * k[3] + b[4][4] * k[4])
			);

			yi = 0;
			R = 0;
			for(j = 0; j < 6; ++j) {
				yi += c[j] * k[j];
				R += (c[j] - c_s[j]) * k[j];
			}
			yi = h * yi + y[i];
			R *= h;

		}while(fabs(R) > eps * fabs(yi) && h >= h_min);

		if(++i >= nmax) {
			break;
		}

		y[i] = yi;
		t[i] = t[i - 1] + h;
		if((fabs(R) < eps * fabs(yi))) {
			h *= 2;
		}

	}while(t[i] < tend);

	return i;
}
