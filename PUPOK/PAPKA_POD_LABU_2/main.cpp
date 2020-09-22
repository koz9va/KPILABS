#include <iostream>
#include <cstdio>

using namespace std;

void ReadFile(double **matrix, int rows, int columns,
              double *Uds, double *Ugs, int Udsl, int Ugsl, char *filename)
              {

    FILE *openfile = fopen(filename, "r");

    if(!openfile) {
        cout<<"Incorrect filename\n";
        return;

    }

    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < columns; ++j) {

          fscanf(openfile, "%lf", & matrix[i][j]);

        }

    }

      for (int i = 0; i < Udsl; ++i) {

          fscanf(openfile, "%lf", Uds + i);

      }

      for (int i = 0; i < Ugsl; ++i) {

          fscanf(openfile, "%lf", Ugs + i);


      }

      fclose(openfile);

}

double InterPol (int Ugsl, int Udsl,
                 double *Ugs, double *Uds, double **matrix, double Ugsx, double Udsx)
                 {
    double sum;
    double product;
    double temp[Ugsl];

     for (int k = 0; k < Ugsl; ++k) {
         sum = 0;
         for (int i = 0; i < Udsl ; ++i) {
             product = matrix[k][i];

             for (int j = 0; j < Udsl; ++j) {
                 if (i != j) {
					 product *= (Udsx - Uds[j]) / (Uds[i] - Uds[j]);
                 }
             }
             sum += product;
         }
         temp[k] = sum;
     }

     sum = 0;
     for (int i = 0; i < Ugsl ; ++i) {
         product = temp[i];
         for (int j = 0; j < Ugsl; ++j) {
             if (j != i) {
                 product *= (Ugsx - Ugs[j]) / (Ugs[i] - Ugs[j]);
             }
         }
         sum += product;
     }
    return sum;

}

void InterPolmatrix (int Ugsl, int Udsl, double *Ugs, double *Uds, double **matrix)
{
    double UdsStep = (Uds[0] + Uds[1]) / 2;
    double UgsStep = (Ugs[0] + Ugs[1]) / 2;

	double value;

    for (int i = 0; i < Ugsl; ++i) {
		value = 0;
        for (int j = 0; j < Udsl * 2 - 1 ; ++j) {

        cout << InterPol(Ugsl, Udsl, Ugs, Uds, matrix, Ugs[i], value) << " ";

        value += UdsStep;
        }

        cout << "\n";

    }

    value = 0;

    for (int i = 0; i < Udsl * 2 - 1; ++i) {
        cout << InterPol(Ugsl, Udsl, Ugs, Uds, matrix, UgsStep, value) << " ";
        value += UdsStep;
    }

}

int main() {
    double **matrix;
    double Uds[7];
    double Ugs[3];

    matrix = new double *[3];

    for (int i = 0; i < 3; ++i) {
        matrix[i] = new double [7];

    }

    ReadFile(matrix, 3, 7, Uds, Ugs, 7, 3, "current.txt");

    InterPolmatrix(3, 7, Ugs, Uds, matrix);


    for (int i = 0; i < 3; ++i) {
        delete [] matrix[i];

    }
    delete [] matrix;
    return 0;
}
