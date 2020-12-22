//
// Created by koz9va on 15.12.20.
//
#include "include/SLAU.h"
#include <cstdio>

void Recalculate_table(lin::vector &U1, lin::vector &C1, lin::vector &U2, lin::vector &C2, double sense) {
	int i, j;

	j = 0;
	for(i = 0; i < U1.n; ++i) {
		if(U1[i] >= sense) {
			j = i;
			break;
		}
	}

	U2.resize(U1.n - j, false);
	C2.resize(C1.n - j, false);

	for(i = 0; i < U2.n; ++i) {
		U2[i] = log(U1[i + j]);
		C2[i] = log(C1[0] / C1[i + j]);
	}

}

void Polynomial_approximation(lin::vector &U, lin::vector &C, lin::vector &X, int n) {
	int i, j, k, p;
	double pow;
	lin::matrix m(++n);
	lin::vector B(n);

	for(i = 0; i < m.n; ++i) {
		for(k = 0; k < U.n; ++k) {
			pow = 1.0;
			for(p = 0; p < i; ++p) {
				pow *= C[k];
			}
			B[i] += pow * U[k];
		}

		for(j = 0; j <= i; ++j) {
			for(k = 0; k < U.n; ++k) {
				pow = 1.0;
				for(p = 0; p < i + j; ++p) {
					pow *= C[k];
				}
				m.ptr[i][j] += pow;
			}
		}
	}

	m.LDL(B, X);

}

int main() {

	int n, i;
	FILE *input, *output;

	input = fopen("input.txt", "r");
	output = fopen("out.txt", "w");
	if(!input || !output) {
		printf("error in opening file :(\n");
	}
	fscanf(input, "%d", &n);

	lin::vector U1(n);
	lin::vector C1(n);
	lin::vector U2(1);
	lin::vector C2(1);

	for(i = 0; i < n; ++i) {
		fscanf(input, "%lf", &U1[i]);
	}
	for(i = 0; i < n; ++i) {
		fscanf(input, "%lf", &C1[i]);
	}
	fclose(input);
	Recalculate_table(U1, C1, U2, C2, 6.0);

	lin::vector out(U2.n);

	Polynomial_approximation(U2, C2, out, 1);

	out[0] = exp(out[0]);
	out[1] = 1.0 / out[1];

	fprintf(output, "fi = %e\tN = %e\n", out[0], out[1]);

	fprintf(output, "\n");

	fclose(output);
	return 0;
}
