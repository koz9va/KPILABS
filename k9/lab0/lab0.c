#define N 3
#define M 7

#include <stdio.h>
#include <stdlib.h>

typedef struct {
	double x;
	double y;
} point;

typedef struct {
	double U0;
	double beta;
	double G22;
} Transistor;

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

double CalcIc(Transistor *in, double Ugs, double Uds) {
	if (Ugs <= in->U0) {
		return in->G22 * Uds;
	}
	else {
		if (Uds < (Ugs - in->U0)) {
			return in->beta * (2 * (Ugs - in->U0) - Uds) * Uds + in->G22 * Uds;
		}
		else {
			return in->beta * (Ugs - in->U0) * (Ugs - in->U0) + in->G22 * Uds;
		}
	}
}



int main(int argc, char **argv) {

	int i, j;

	double temp[2];
	int tempi[2];
	const size_t temp_size = sizeof(double)*2;

	Transistor out;
	DArr data3;
	DArr data7;
	FILE *DataFile;
	FILE *BinFile;

	if(argc != 10)
		printf("There are wrong number of arguments :(\n");
	else if(argv[1][1] != 100)
		printf("There is wrong key :(\n");

	DataFile = fopen(argv[9], "w");
	BinFile = fopen("data.bin", "wb");

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

	tempi[0] = N;
	tempi[1] = M;

	fwrite(&temp_size, sizeof(size_t), 1, BinFile);
	fwrite(tempi, sizeof(int), 2, BinFile);


	for (i = 0; i < N; ++i) {
		for (j = 0; j < M; ++j) {
			temp[0] =  CalcIc(&out, data3.ptr[i], data7.ptr[j]);
			fprintf(DataFile, "%lf ", temp[0]);

			temp[1] = (double)data3.ptr[i];
			fwrite(temp, sizeof(double), 2, BinFile);
		}
		fprintf(DataFile, "\n");
	}

	fprintf(DataFile, "---data3---\n");

	i = N;
	fwrite(&i, sizeof(int), 1, BinFile);

	for (i = 0; i < N; ++i) {
		fprintf(DataFile, "%.3lf ", data3.ptr[i]);
		fwrite(data3.ptr + i, sizeof(double), 1, BinFile);
	}

	fprintf(DataFile ,"\n---data7---\n");

	i = M;

	fwrite(&i, sizeof(int), 1, BinFile);

	for (i = 0; i < M; ++i) {
		fprintf(DataFile, "%lf ", data7.ptr[i]);
		fwrite(data7.ptr + i, sizeof(double), 1, BinFile);
	}

	fclose(DataFile);
	fclose(BinFile);


	return 0;
}
