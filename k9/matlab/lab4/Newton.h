/*
 * =====================================================================================
 *
 *       Filename:  Newton.h
 *
 *    Description: integral header 
 *
 *        Version:  1.0
 *        Created:  23.03.21 21:36:58
 *       Revision:  none
 *       Compiler:  clang
 *
 *         Author:  Serafim, 
 *
 * =====================================================================================
 */
#include <cmath>
#include <cfloat>
#define MAX_ITER 20

int const C[][10] = {
	{1, 1, 2},
	{1, 4, 1, 6},
	{1, 3, 3, 1, 8},
	{7, 32, 12, 32, 7, 90},
	{19, 75, 50, 50, 75, 19, 288},
	{41, 216, 27, 272, 27, 216, 41, 840},
	{751, 3577, 1323, 2989, 2989, 1323, 3577, 751},
	{989, 5888, -928, 10496, -4540, 10496, -928, 5888, 989, 28350}
};

double Integral_calc(double (*fun)(double), double a, double b, int m, double eps);
