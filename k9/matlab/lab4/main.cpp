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
#include "Newton.h"

int cnt;
const double omg = M_PI * 2.0;
const double A = 10.0;
const double fi = M_PI / 4.0;
const double sigm = 1.0;
const double t0 = 4.0;
const double tau = 4.0;


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
	else {
		if(t >= 0 && t <= 1) {
			return -2.0 * t + 2.0;
		}
		else if(t > 1 && t < 3) {
			return t - 1.0;
		}else
			return 0;
	}

}
double h(double t) {
	const double Re = 4.0;
	const double C = 1.0;

	if(t >= 0) {
		return 0.5 * (1.0 - exp(t/Re / C));
	} else{
		return 0.0;
	}

}

double funduam1(double t) {
	++cnt;	
	if(t < M_PI / omg) {
		return A * omg * cos(omg * tau + fi) * h(t - tau);
	}else {
		return 0.0;
	}
}

int main() {
	constexpr double tmax[] = {1.0, 10.0, 5};
	constexpr int p_len = 3;
	constexpr int p[] = {4, 5, 8};
	double t, dt, tpp, tpm, y;
	int i;
	FILE *xFile[3], *yFile[3], *sFile[3];
	double (*s_arr[3])(double) = {&s1, &s2, &s3};

	char x_str[] = "data/x .txt";
	char y_str[] = "data/y .txt";
	char s_str[] = "data/s .txt";

	tpp = 0;
	tpm = M_PI / omg;

	for(i = 0; i < 3; ++i) {
		x_str[6] = 49 + i;
		y_str[6] = 49 + i;
		s_str[6] = 49 + i;

		xFile[i] = fopen(x_str, "w");
		yFile[i] = fopen(y_str, "w");
		sFile[i] = fopen(s_str, "w");
	}

	printf("Given function:\n");

	for(i = 0; i < p_len; ++i) {
		cnt = 0;
		y = Integral_calc(v2, -M_PI_2, M_PI_2, p[i], 1e-6);
		printf("p = %d, result = %g, calls to function = %d\n", p[i], y, cnt);
	}
	for(i = 0; i < 3; ++i) {
		dt = tmax[i] / 100.0;
		for(t = 0.0; t <= tmax[i]; t += dt) {
			cnt = 0;
			if(t < M_PI /omg) {
				y = s_arr[i](tpp) * h(t - tpp)
				+ Integral_calc(funduam1, tpp, t - s_arr[i](tpm), 8, 1e-6)
			       	* h(t) - s_arr[i](tpm) * h(t - tmax[i]);
			}else {
				y = s_arr[i](tpp) * h(t - tpp)
				+ Integral_calc(funduam1, tpp, tpm - s_arr[i](tpm), 8, 1e-6)
				* h(t - tpm) - s_arr[i](tpm) * h(t - tmax[i]);	
			}
//			printf("t: %g U: %g,func calls: %d\n", t, y, cnt);
			fprintf(xFile[i], "%g\n", t);
			fprintf(yFile[i], "%g\n", y);
			fprintf(sFile[i], "%g\n", s_arr[i](t));
		}
	}
	for(i = 0; i < 3; ++i) {
		fclose(xFile[i]);
		fclose(yFile[i]);
		fclose(sFile[i]);
	}

	printf("Dependence of voltage over time are written to the x.txt y.txt and s.txt files\n");

	return 0;                                         
}
