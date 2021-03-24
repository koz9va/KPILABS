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
#include <cstdlib>
#include <cstdio>
#include <cmath>
#include "Newton.h"
int cnt;

double v2(double x) {
	++cnt;
	return (exp(sin(x)))/(1.0 + cos(x));
}
double test(double x) {
	++cnt;
	return pow(x, 4);
}
double func(double x) {
	return 0.5 * x + x * log10(x);
}

int main() {
	cnt = 0;
	printf("Integral value: %lf\nFunction calls: %d", Integral_calc(test, 0, 2, 4, 1e-6), cnt);
	return 0;
}
