#include <stdio.h>
#include <stdlib.h>
#include "LDL.h"
#include <math.h>

void approx(double *U, double *C, double *X, int len, int n) {
    int i, j, k, l;
    double step;
    double **M;
    double *N;

    ++n;

    M = (double **)malloc(sizeof(double*) * n);
    for(i = 0; i < n; ++i) {
        M[i] = (double *)malloc(sizeof(double) * n);
    }
    N = (double*)malloc(sizeof(double)*n);

    for(i = 0; i < n; ++i){
        for(k = 0; k < len; ++k){
            step = 1.0;
            for(l = 0; l < i; ++l){
                step *= C[k];
            }
            N[i] += step * U[k];
        }

        for(j = 0; j < n; ++j) {
            for (k = 0; k < len; ++k) {
                step = 1.0;
                for (l = 0; l < i + j; ++l) {
                    step *= C[k];
                }
                M[i][j] += step;
            }
        }
    }
    LDL(M, n, N, X);
    free(N);
    for(i = 0; i < n; ++i){
        free(M[i]);
    }
    free(M);
}

int main() {
    double *U1, *U2, *C1, *C2, *qq;
    int i, lenin, lenout;

    FILE *in, *out;
    in = fopen("in.txt", "r");
    out = fopen("out.txt", "w");

    if(in == NULL || out == NULL){
        printf("File error!");
        return -1;
    }
    fscanf(in, "%d", &lenin);
    U1 = malloc(sizeof(double) * lenin);
    C1 = malloc(sizeof(double) * lenin);

    for(i = 0; i < lenin; ++i){
        fscanf(in, "%lf", U1 + i);
    }
    for(i = 0; i < lenin; ++i){
        fscanf(in, "%lf", C1 + i);
    }

    for(i = 0; U1[i] <= 6.0; ++i);
    lenout = lenin - i;

    U2 = malloc(sizeof(double) * lenout);
    C2 = malloc(sizeof(double) * lenout);

    for(i = 0; i < lenout; ++i){
        U2[i] = log(U1[i + lenin - lenout]);
        C2[i] = log(C1[0] / C1[i + lenin - lenout]);
    }
    qq = malloc(sizeof(double) * lenout);

    approx(U2, C2, qq, lenout, 1);

    qq[0] = exp(qq[0]);
    qq[1] = 1.0 / qq[1];

    fprintf(out, "phi = %lf\tN = %lf\n", qq[0], qq[1]);

    fclose(in);
    fclose(out);

    free(U1);
    free(C1);
    free(U2);
    free(C2);
    free(qq);

    return 0;
}
