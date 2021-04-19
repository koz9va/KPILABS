#include <cmath>
#include <cfloat>

namespace RK45 {

	constexpr double a[] = {0, 1.0/4.0, 3.0/8.0, 12.0/13.0, 1, 0.5};
	constexpr double c[] = {16.0/135.0, 0, 6656.0/12825.0, 28561.0/56430.0, -9.0/50.0, 2.0/55.0};
	constexpr double c_s[] = {25.0/216.0, 0, 1408.0/2565.0, 2197.0/4104.0, -1.0/5.0, 0};
	constexpr double b[][5] = {
			{1.0/4.0},
			{3.0/32.0, 9.0/32.0},
			{1932.0/2197.0, -7200.0/2197.0, 7296.0/2197.0},
			{439.0/216.0, -8.0, 3680.0/513.0, -845.0/4104.0},
			{-8.0/27.0, 2.0, -3544.0/2565.0, 1859.0/4104.0, -11.0/40.0}
	};
	int RK45(
			double f(double t, double y),
			double tend,
			double y0,
			double eps,
			double *t,
			double *y,
			double h_max,
			int nmax
	);
}

int euler(
	double f(double t, double y),
	double tend,
	double y0,
	double eps,
       	double *t,	
	double *y,
	int nmax
	);

