#include "Newton.h"


double Integral_calc(double (*fun)(double), double a, double b, int m, double eps) {
	double I1, I2, R, h, x;
	int i, j, k, n, p;

	if(fabs(a - b) < DBL_EPSILON * 2)
		return 0;
	

	n = 1;
	I1 = fun(a) * C[m - 1][0];
	h = (b - a) / n;
	x = a;
	p = (int)( 1u << (m + 2)) - 1;
  	for(i = 1; i < m + 1; ++i) {
		x += h / m;
		I1 += C[m-1][i] * fun(x);
	}
    I1 *= h;

	for(k = 0; k < MAX_ITER; ++k) {
		n *= 2;
		h = (b - a)/n;
		I2 = I1;
		if(h < (DBL_EPSILON * 2))
			break;
		x = a;
		I1 = (fun(a) - fun(b)) * C[m - 1][0];
		for(i = 0; i < n; ++i) {
			for(j = 1; j < m; ++j) {
				x += h / m;
				I1 += C[m-1][j] * fun(x);
			}
			x += h / m;
			I1 += 2.0 * C[m-1][0] * fun(x);
		}
		I1 *= h;
		R = fabs((I1 - I2) ) / p;
		if(fabs(R) < fabs(eps * I1)) {
            I1 += R;
			I1 /= C[m-1][m+1];
			break;
		}
	}
	return I1;
}
