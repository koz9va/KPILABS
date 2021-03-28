/*
 * =====================================================================================
 *
 *       Filename:  main.cpp
 *
 *    Description: Newton-Cottes integral method 
 *
 *        Version:  1.0
 *        Created:  15.03.21 14:52:57
 *       Revision:  none
 *       Compiler:  Clang
 *
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
double (*s)(double);

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
//		return 2 * (1.0 - exp(t / Re / C));
	} else{
		return 0.0;
	}

}

double funduam1(double t) 
{
	++cnt;	
	if(t < M_PI / omg) {
		return A * omg * cos(omg * tau + fi) * h(t - tau);
	}else {
		return 0.0;
	}
}

int main() {
	double tmax, t, dt, tpp, tpm, y;

	s = &s1;
	tmax = M_PI / omg;
//	tmax = 0.5;
	dt = tmax / 100;
	tpp = 0;
	tpm = M_PI / omg;
	FILE *xFile, *yFile, *sFile;

	xFile = fopen("x.txt", "w");
	yFile = fopen("y.txt", "w");
	sFile = fopen("s.txt", "w");

	printf("omg %g fi %g\n", omg, fi);

	for(t = 0.0; t <= tmax; t += dt) {
		cnt = 0;
		if(t < M_PI /omg) {
			y = s(tpp) * h(t - tpp)
			+ Integral_calc(funduam1, tpp, t - s(tpm), 8, 1e-6)
		       	* h(t) - s(tpm) * h(t - tmax);
		}else {
			y = s(tpp) * h(t - tpp)
			+ Integral_calc(funduam1, tpp, tpm - s(tpm), 8, 1e-6)
			* h(t - tpm) - s(tpm) * h(t - tmax);	
		}
		printf("%g, %g, %d\n", t, y, cnt);
		fprintf(xFile, "%g\n", t);
		fprintf(yFile, "%g\n", y);
		fprintf(sFile, "%g\n", s(t));
	}
	fclose(xFile);
	fclose(yFile);
	fclose(sFile);

	

	return 0;                                         
}
