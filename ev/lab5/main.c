#include "LDL.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define maxLen 1000000

void Aprox(const double *U, const double *I, double *X, int len, int n) {
	int i, j, l, z;
	double mult, **matrix, *B;
	n++;
	B = (double*)calloc(n, sizeof(double));
	matrix = (double**)malloc(n * sizeof(double*));

	if(!B || !matrix)
		return;

	for(i = 0; i < n; ++i) {
		matrix[i] = (double*)calloc(n, sizeof(double));
		if(!matrix[i])
			return;
	}

	for(i = 0; i < n; i++) {
		for(l = 0; l < len; l++) {
			mult = 1.0;
			for(z = 0; z < i; z++) {
				mult *= I[l];
			}
			B[i] += mult * U[l];
		}

		for(j = 0; j <= i; j++) {
			for(l = 0; l < len; l++) {
				mult = 1.0;
				for(z = 0; z < i + j; z++) {
					mult *= I[l];
				}
				matrix[i][j] += mult;
			}
		}
	}

	LDL(matrix, n, B, X);

	free(B);
	for (i = 0; i < n; ++i) {
		free(matrix[i]);
	}
	free(matrix);

}


int main(int argc, char *argv[]) {
	int i, len;
	double *U, *I, X[2], b;
	FILE *data;

	if(argc != 2) {
		printf("Incorrect number of arguments\n");
		return -1;
	}
	data = fopen(argv[1], "r");
	if(!data) {
		printf("Can't open file\n");
		return -2;
	}

	fscanf(data, "%d", &len);

	if(len > 0 && len < maxLen) {
		U = (double*)malloc(sizeof(double) * len);
		I = (double*)malloc(sizeof(double) * len);
		if(!U || !I) {
			return -4;
		}
	} else {
		return -3;
	}

	for(i = 0; i < len; i++) {
		fscanf(data, "%lf", U + i);

	}
	for(i = 0; i < len; i++) {
		fscanf(data, "%lf", I + i);
		I[i] = (26e-3*log(I[i]))/U[i];
		U[i] = 26e-3/U[i];
	}
	fclose(data);

	Aprox(U, I, X, len, 1);



	printf("I0 = %lf\tm = %lf\n", exp(X[0]), X[0]/X[1]);

	free(U);
	free(I);


	return 0;
}
