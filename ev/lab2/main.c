#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "algorithms/LU.h"
#include "algorithms/QR.h"
#include "algorithms/LDL.h"

double getVectorR(double **mat, size_t len, double *b, double *x, double *r) {
	int i, j, wasNULL;
	double sum;

	sum = 0;
	if(r == NULL) {
		r = calloc(len, sizeof(double));
		wasNULL = 1;
	} else {
		memset(r, 0, len * sizeof(double));
		wasNULL = 0;
	}

	for(i = 0; i < len; ++i) {
		for(j = 0; j < len; ++j) {
			r[i] += mat[i][j] * x[j];
		}
	}

	for(i = 0; i < len; ++i) {
		r[i] -= b[i];
		sum += r[i] * r[i];
		printf("%e\n", r[i]);
	}


	if(wasNULL == 1) {
		free(r);
	}
	return sqrt(sum);
}

double **ReadMatrix(char *filename, int *len) {
	FILE *file;
	int i, j;
	double **mat;
	if(!len) {
		printf("Wrong pointer: len must NOT be null\n");
		return NULL;
	}
	file = fopen(filename, "r");

	if(!file) {
		printf("Wrong file\n");
		return NULL;
	}

	fscanf(file, "%d", len);

	mat = (double**)malloc(*len * sizeof(double*));

	for(i = 0; i < *len; ++i) {
		mat[i] = (double*)malloc(*len * sizeof(double));
	}

	for(i = 0; i < *len; ++i) {
		for(j = 0; j < *len; ++j) {
			fscanf(file, "%lf", &mat[i][j]);
		}
	}
	fclose(file);
	return mat;
}
double **CopyAndAllocMatrix(double **inMat, int len) {
	int i;
	double **mat;
	if(!inMat) {
		return NULL;
	}

	mat = (double**)malloc(len * sizeof(double*));
	for(i = 0; i < len; ++i) {
		mat[i] = (double*)malloc(len * sizeof(double));
		memcpy(mat[i], inMat[i], len * sizeof(double));
	}
	return mat;
}

void CopyMatrix(double **dst, double **src, int len) {
	int i;
	if(!dst || !src)
		return;

	for(i = 0; i < len; ++i) {
		memcpy(dst[i], src[i], len * sizeof(double));
	}
}

void freeMat(double **mat, int len) {
	int i;
	for(i = 0; i < len; ++i) {
		free(mat[i]);
	}
	free(mat);
}
int main() {
	double **matrix, **copyMat;
	int matLen, i, j;
	double B[3] = {-10, 20, 9};
	double BCp[3];
	double X[3];
	FILE *file;

	file = fopen("out.txt", "w");

	matrix = ReadMatrix("matrix.txt", &matLen);
	copyMat = CopyAndAllocMatrix(matrix, matLen);


	fprintf(file, "matrix:\n");
	for(i = 0; i < matLen; ++i) {
		for(j = 0; j < matLen; ++j) {
			fprintf(file, "%lf ", matrix[i][j]);
		}
		fprintf(file, "\n");
	}
	LU(copyMat, matLen, B, X);
	fprintf(file, "LU decomposition:\n");
	for(i = 0; i < matLen; ++i) {
		for(j = 0; j < matLen; ++j) {
			fprintf(file, "%lf ", copyMat[i][j]);
		}
		fprintf(file, "\n");
	}

	fprintf(file, "x:\n");

	for(i = 0; i < matLen; ++i) {
		fprintf(file, "%e ", X[i]);
	}
	fprintf(file, "\nnormal of R: %e\n", getVectorR(matrix, matLen, B, X, NULL));

	CopyMatrix(copyMat, matrix, matLen);
	memcpy(BCp, B, matLen * sizeof(double));
	QR(copyMat, matLen, BCp, X);
	fprintf(file, "QR decomposition:\n");
	for(i = 0; i < matLen; ++i) {
		for(j = 0; j < matLen; ++j) {
			fprintf(file, "%lf ", copyMat[i][j]);
		}
		fprintf(file, "\n");
	}

	fprintf(file, "x:\n");

	for(i = 0; i < matLen; ++i) {
		fprintf(file, "%e ", X[i]);
	}
	fprintf(file, "\nnormal of R: %e\n", getVectorR(matrix, matLen, B, X, NULL));

	CopyMatrix(copyMat, matrix, matLen);
	LDL(copyMat, matLen, B, X);
	fprintf(file, "LDL decomposition:\n");
	for(i = 0; i < matLen; ++i) {
		for(j = 0; j < matLen; ++j) {
			fprintf(file, "%lf ", copyMat[i][j]);
		}
		fprintf(file, "\n");
	}

	fprintf(file, "x:\n");

	for(i = 0; i < matLen; ++i) {
		fprintf(file, "%e ", X[i]);
	}
	fprintf(file, "\nnormal of R: %e\n", getVectorR(matrix, matLen, B, X, NULL));


	fclose(file);
	freeMat(matrix, matLen);
	freeMat(copyMat, matLen);
	return 0;
}