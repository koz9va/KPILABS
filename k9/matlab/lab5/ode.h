#include <cmath>
#include <cfloat>


int euler(
	double f(double t, double y),
	double tend,
	double y0,
	double eps,
       	double *t,	
	double *y,
	int nmax
	);

int RK(
		double f(double t, double y),
		double tend,
		double y0,
		int m,
		double eps,
		double *t,
		double *y,
		int nmax
);
