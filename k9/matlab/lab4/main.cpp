/*
 * =====================================================================================
 *
 *       Filename:  main.c
 *
 *    Description: Newton-Cottes integral method 
 *
 *        Created:  15.03.21 14:52:57
 *       Compiler:  Clang
 *         Author:  Serafim Honcharov 
 *
 * =====================================================================================
 */
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <float.h>
#include "Newton.h"

int cnt;
const double omg = M_PI * 2.0;
const double A = 1.0;
const double fi = M_PI / 4.0;
const double sigm = 1.0;
const double t0 = 5.0;
double tau = 4.0;


double v2(double x) {
	++cnt;
	return (exp(sin(x)))/(1.0 + cos(x));
}

double s1(double t) {
	if(t >= 0 && t <= M_PI/omg) {
		return A * sin(omg * t + fi);
	}else
		return 0;
}

double s2(double t) {
	return A * exp(-(((t - t0)*(t - t0))/(sigm*sigm)));
}

double s3(double t) {
	if(t < 0) {
		return 0;
	}
	else if(t < 1) {
		return -2.0 * t + 2.0;
	}
	else if(t < 3) {
		return t - 1.0;
	} else
		return 0;
}

double h(double t) {
	const double Re = 4.0;
	const double C = 1.0;

	if(t > 0) {
		return 0.5 * (1.0 - exp(-t/Re / C));
	} else{
		return 0.0;
	}

}

double funduam1(double t) {
	++cnt;	
	if(t < M_PI / omg) {
		return A * omg * cos(omg * t + fi) * h(tau - t);
	}else {
		return 0.0;
	}
}

double ds2(double t) {
	return -2.0 * exp(-((t - 5.0)*(t - 5.0))) * (t - 5.0) * h(tau - t);
}

double ds3(double t){
	if(t < 0) {
		return 0.0;
//<<<<<<< HEAD
	} else if(t < 1.0) {
//		return -2.0 * h(t - tau);
		return -2.0 * h(tau - t);
	} else if(t < 3.0) {
		return h(tau - t);
		//return h(t - tau);
	} else {
//=======
		return 0.0;
	}
}

int main() {
	constexpr double tmax[] = {0.6, 10.0, 4};
	constexpr int p_len = 3;
	constexpr int p[] = {4, 5, 8};
	double t, dt, tpp, tpm, y;
	int i;
	FILE *xFile[3], *yFile[3], *sFile[3];
	double (*s_arr[3])(double) = {&s1, &s2, &s3};
	double (*derr_arr[3])(double) = {&funduam1, &ds2, &ds3};
	char x_str[] = "data/x .txt";
	char y_str[] = "data/y .txt";
	char s_str[] = "data/s .txt";

	tpp = 0;


	for(i = 0; i < 3; ++i) {
		x_str[6] = 49 + i;
		y_str[6] = 49 + i;
		s_str[6] = 49 + i;

		xFile[i] = fopen(x_str, "w");
		yFile[i] = fopen(y_str, "w");
		sFile[i] = fopen(s_str, "w");

		if(!xFile[i] || !yFile[i] || !sFile[i]) {
			printf("Error in opening files\n");
			return -1;
		}

	}

	printf("Given function:\n");

	for(i = 0; i < p_len; ++i) {
		cnt = 0;
		y = Integral_calc(v2, -M_PI_2, M_PI_2, p[i], 1e-6);
		printf("p = %d, result = %g, calls to function = %d\n", p[i], y, cnt);
	}

	for(t = 0.0; t < tmax[0]; t += tmax[0]/100.0) {
		tau = t;
		if(t < 0.5) {
			y = s1(0) * h(t) + Integral_calc(funduam1, 0, t, 8, 1e-6);
		} else {
			y = s1(0) * h(t) + Integral_calc(funduam1, 0, 0.5 - 2 * DBL_EPSILON, 8, 1e-6)
			+ s1(0.5 + 2.0 * DBL_EPSILON) * h(t - 0.5 - 2 * DBL_EPSILON)
			+Integral_calc(funduam1, 0.5 + 2 * DBL_EPSILON, t, 8, 1e-6) - s1(0.5 - 2.0 * DBL_EPSILON) * h(t - 0.5 + 2.0 * DBL_EPSILON);
		}
		fprintf(xFile[0], "%g\n", t);
		fprintf(yFile[0], "%g\n", y);
		fprintf(sFile[0], "%g\n", s1(t));
	}


	for(t = 0.0; t < tmax[1]; t += tmax[1] / 100.0) {
		tau = t;

		y = s2(0) * h(t) + Integral_calc(ds2, 0, t, 8, 1e-6);

		fprintf(xFile[1], "%g\n", t);
		fprintf(yFile[1], "%g\n", y);
		fprintf(sFile[1], "%g\n", s2(t));
	}

	for(t = 0.0; t < tmax[2]; t += tmax[2] / 100.0) {
		tau = t;
		if(t < 1) {
			y = s3(0) * h(t) + Integral_calc(ds3, 0, t, 8, 1e-6) - s3(1.0 - 2 * DBL_EPSILON) * h(t - 1.0 - 2.0 * DBL_EPSILON);
		} else if(t <= 3) {
			y = s3(0) * h(t) + Integral_calc(ds3, 0, 1 - 2 * DBL_EPSILON, 8, 1e-6) - s3(1 - 2 * DBL_EPSILON) * h(t - 1.0 + 2.0 * DBL_EPSILON) 
			+ s3(1.0 + 2.0 * DBL_EPSILON) * h(t - 1.0 - 2.0 * DBL_EPSILON)
			+ Integral_calc(ds3, 1 + 2.0 * DBL_EPSILON, t, 8, 1e-6) - s3(3 - 2.0 * DBL_EPSILON) * h(t - 3.0 + 2.0 * DBL_EPSILON);

		} else {
			y = s3(0) * h(t) + Integral_calc(ds3, 0, 1.0 - 2.0 * DBL_EPSILON, 8, 1e-6)
			- s3(1.0 - 2.0 * DBL_EPSILON) * h(t - 1.0 + 2.0 * DBL_EPSILON)
			+ s3(1.0 + 2.0 * DBL_EPSILON) * h(t - 1.0 - 2.0 * DBL_EPSILON)
			+ Integral_calc(ds3, 1+2*DBL_EPSILON, 3-2*DBL_EPSILON, 8, 1e-6)
			- s3(3 - 2.0 * DBL_EPSILON) * h(t - 3 + 2.0 * DBL_EPSILON);
			tpp = s3(0) * h(t) + Integral_calc(ds3, 0, 1.0 - 2.0 * DBL_EPSILON, 8, 1e-6);
			tpm = s3(3 - 2.0 * DBL_EPSILON) * h(t - 3 + 2.0 * DBL_EPSILON);
		}
			fprintf(xFile[2], "%g\n", t);
			fprintf(yFile[2], "%g\n", y);
			fprintf(sFile[2], "%g\n", s3(t));
	}

	

	for(i = 0; i < 3; ++i) {
		fclose(xFile[i]);
		fclose(yFile[i]);
		fclose(sFile[i]);
	}

	printf("Dependence of voltage over time are written to the x.txt y.txt and s.txt files\n");

	return 0;                                         
}
