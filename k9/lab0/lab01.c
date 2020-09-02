#define M 3
#define N 7
#define u0 0.25e-3
#define betta 3e-6
#define g22 4.5
#include <stdio.h>
#include <stdlib.h>

typedef struct {
	int len;
	double *ptr;
} DArr;

void initArr(DArr *in, int len) {
	in->len = len;

	in->ptr = (double*)malloc(len * sizeof(double));

}

void FillArr(DArr *in) {
	double step;
	int i;
	step = ((in->ptr[in->len - 1] - in->ptr[0]) / (in->len - 1));

	for(i = 1; i < in->len - 1; ++i) {
		in->ptr[i] = in->ptr[i - 1] + step;
	}
}

double CalcIc(double Ugs, double Uds) {
	if (Ugs <= u0) {
		return g22 * Uds;
	}
	else {
		if (Uds < (Ugs - u0)) {
			return betta * (2 * (Ugs - u0) - Uds) * Uds + g22 * Uds;
		}
		else {
			return betta * (Ugs - u0) * (Ugs - u0) + g22 * Uds;
		}
	}
}



int main(int argc, char **argv) {

	int i, j;

	double deb;

	Transistor out;
	DArr data3;
	DArr data7;
	FILE *DataFile;

	if(argc != 10)
		printf("There are wrong number of arguments :(\n");
	else if(argv[1][1] != 100)
		printf("There is wrong key :(\n");

	DataFile = fopen(argv[9], "w");

	if(!DataFile) {
		printf("Can't find this file :(\n");
		return 21;
	}

	sscanf(argv[2], "%lf", &out.beta);
	sscanf(argv[3], "%lf", &out.G22);
	sscanf(argv[4], "%lf", &out.U0);

	initArr(&data3, N);
	initArr(&data7, M);

	sscanf(argv[5], "%lf", data3.ptr);
	sscanf(argv[6], "%lf", data3.ptr + data3.len - 1);
	sscanf(argv[7], "%lf", data7.ptr);
	sscanf(argv[8], "%lf", data7.ptr + data7.len - 1);

	FillArr(&data3);
	FillArr(&data7);

	fprintf(DataFile, "---Currents---\n");

	for (i = 0; i < N; ++i) {
		for (j = 0; j < M; ++j) {
			fprintf(DataFile, "%lf ", CalcIc(&out, data3.ptr[i], data7.ptr[j]));
		}
		fprintf(DataFile, "\n");
	}

	fprintf(DataFile, "---data3---\n");

	for (i = 0; i < N; ++i) {
		fprintf(DataFile, "%.3lf ", data3.ptr[i]);
	}

	fprintf(DataFile ,"\n---data7---\n");

	for (i = 0; i < M; ++i) {
		fprintf(DataFile, "%lf ", data7.ptr[i]);
	}

	fclose(DataFile);

	return 0;
}
