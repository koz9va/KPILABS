//
// Created by koz9va on 26.10.20.
//

#ifndef LAB3_NU_H
#define LAB3_NU_H
#include <cmath>
#include <cfloat>
#include <cstdio>
extern int cnt;

double Bisection(double f(double), double a, double b, double eps, FILE *file);
double Newton(double f(double), double x0, double xt, double eps, FILE *file);
double Secant(double f(double), double x0, double x1, double eps, FILE *file);


double f4(double ud);
double rgr(double x);
#endif //LAB3_NU_H
