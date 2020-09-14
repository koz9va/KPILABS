#include <stdlib.h>
#include <stdio.h>

#define M 3
#define N 7


float inter(int m, int n, const float* arrayOfUgs, const float* arrayOfUds, float** matrix, float UgsX, float UdsX) {

    int i, j, k;
    float sum;
    float multiply;
    float tmpArray[M];

    for (k = 0; k < m; k++) {

        sum = 0;
        for (i = 0; i < n; i++) {

            multiply = matrix[k][i];
            for (j = 0; j < n; j++) {
                if (i != j)
                    multiply *= (UdsX - arrayOfUds[j]) / (arrayOfUds[i] - arrayOfUds[j]);
            }

            sum += multiply;
        }
        tmpArray[k] = sum;
    }

    sum = 0;
    for (i = 0; i < m; ++i) {
        multiply = tmpArray[i];
        for (j = 0; j < m; j++) {
            if (j != i) {
                multiply *= (UgsX - arrayOfUgs[j]) / (arrayOfUgs[i] - arrayOfUgs[j]);
            }
        }
        sum += multiply;
    }
    return sum;
}


int main() {
    int i, j;
    float UgsStep, UdsStep;
    float ** matrix;
    float * arrOfUgs;
    float * arrOfUds;
    FILE * Ugs;
    FILE * Uds;
    FILE * I;
    FILE * final;


    matrix = (float**)malloc(M * sizeof(float* ));
    if (!matrix){
        printf("666\n");
        return -1;
    }
    for (i = 0; i < M; i++) {
        matrix[i] = (float*) malloc(N * sizeof(float));
        if (!matrix[i]) {
            printf("666\n");
            return -1;
        }
    }

    arrOfUds = (float *)malloc(N * sizeof(float ));
    if (!arrOfUds) {
        printf("666\n");
        return -1;
    }

    arrOfUgs = (float *)malloc(M * sizeof(float ));
    if (!arrOfUgs) {
        printf("666");
        return -1;
    }

    Ugs = fopen("GS.txt", "r");
    if (!Ugs){
        printf("777");
        return -1;
    }
    Uds = fopen("DS.txt", "r");
    if (!Uds){
        printf("777");
        return -1;
    }
    I = fopen("I.txt", "r");
    if (!I){
        printf("777");
        return -1;
    }
    final = fopen("final.txt", "w");
    if (!final){
        printf("777");
        return -1;
    }

    for (i = 0; i < M; i++) {
        for (j = 0; j < N; j++) {
            fscanf(I, "%f", &matrix[i][j]);
        }
    }

    for (i = 0; i < M; i++) {
        fscanf(Ugs, "%f", &arrOfUgs[i]);
    }

    for (i = 0; i < N; i++) {
        fscanf(Uds, "%f", &arrOfUds[i]);
    }

    UdsStep = (arrOfUds[0] + arrOfUds[1]) / 2;
    UgsStep = (arrOfUgs[0] + arrOfUgs[1]) / 2;

    for (i = 0; i < M; i++) {
        float value = 0;
        for (j = 0; j < N * 2 - 1; j++) {
            fprintf(final, "%f ", inter(M, N, arrOfUgs, arrOfUds, matrix, arrOfUgs[i], value));
            value += UdsStep;
        }
        fprintf(final, "\n");
    }

    float value = 0;
    for(i = 0; i < N * 2 - 1; i++) {
        fprintf(final, "%f ", inter(M, N, arrOfUgs, arrOfUds, matrix, UgsStep, value));
        value += UdsStep;
    }

    free(arrOfUds);
    free(arrOfUgs);
    for (i = 0; i < M; i++) {
        free(matrix[i]);
    }
    free(matrix);

    fclose(Ugs);
    fclose(Uds);
    fclose(I);
    fclose(final);

    return 0;
}
