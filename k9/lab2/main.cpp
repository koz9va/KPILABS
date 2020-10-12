#include <iostream>
#include <cmath>
#include <cstdio>
#include <cstring>

class matrix {
public:
	double **ptr;
	int n;
	explicit matrix(int N): n(N) {
		ptr = new double *[n];
		for(int i = 0; i < n; ++i) {
			ptr[i] = new double [n];
		}
	}

	matrix(char* filename, int N): n(N) {
		int i, j;
		FILE *file;
		file = fopen(filename, "r");
		if(!file) {
			std::cout << "wrong file name :(\n";
			exit(21);
		}
		ptr = new double *[n];
		for(i = 0; i < n; ++i) {
			ptr[i] = new double [n];
		}

		for(i = 0; i < n; ++i) {
			for(j = 0; j < n; ++j) {
				fscanf(file, "%lf", ptr[i] + j);
			}
		}
		fclose(file);
	}

	~matrix() {
		for(int i = 0; i < n; ++i) {
			delete [] ptr[i];
		}
		delete [] ptr;
	}

	void operator = (const matrix& A) {
		if(A.n != n) {
			exit(31);
		}
		int i;
		for(i = 0; i < n; ++i) {
			memcpy(ptr[i], A.ptr[i], sizeof(double) * n);
		}
	}


	void printToFile(char *filename, char *description = nullptr) const {
		int i, j;
		FILE *file;
		file = fopen(filename, "a");

		if(description) {
			fprintf(file, "%s\n", description);
		}
		for(i = 0; i < n; ++i) {
			for(j = 0; j < n; ++j) {
				fprintf(file, "%lf ", ptr[i][j]);
			}
			fprintf(file, "\n");
		}


	}

	

};

void LU(matrix &A, const double *B, double *X) {
	int j, i, k;

	for(j = 0; j < A.n; ++j) {
		for (i = j; i < A.n; ++i) {
			for (k = 0; k <= j - 1; ++k) {
				A.ptr[i][j] -= A.ptr[i][k] * A.ptr[k][j];
			}
		}

		for (i = j + 1; i < A.n; ++i) {
			for (k = 0; k <= j - 1; ++k) {
				A.ptr[j][i] -= A.ptr[j][k] * A.ptr[k][i];
			}
			A.ptr[j][i] /= A.ptr[j][j];
		}
	}
	//	memcpy(X, B, sizeof(double) * n);
	for(i = 0; i < A.n; ++i) {
		X[i] = B[i];
		for(j = 0; j <= i - 1; ++j) {
			X[i] -= A.ptr[i][j] * X[j];
		}
		X[i] /= A.ptr[i][i];
	}

	for(i = A.n - 1; i >= 0; --i) {
		for(j = i + 1; j < A.n; ++j) {
			X[i] -= A.ptr[i][j] * X[j];
		}
	}


}

void QR(matrix &A, double *B, double *X) {

	if(!B || !X) {
		std::cout << "nullptr passed to QR :(\n";
		exit(30);
	}
	double alpha, k, t;
	int i, j, l;

	for (j = 0; j < A.n - 1; ++j) {
		alpha = 0;
		for (i = j; i < A.n; ++i) {
			alpha += A.ptr[i][j] * A.ptr[i][j];
		}

		if (A.ptr[j][j] < 0)
			alpha = sqrt(alpha);
		else
			alpha = -sqrt(alpha);
		k = 1.0 / (alpha * alpha - alpha * A.ptr[j][j]);
		A.ptr[j][j] -= alpha;

		for (i = j + 1; i < A.n; ++i) {
			t = 0;
			for (l = j; l < A.n; ++l) {
				t += A.ptr[l][j] * A.ptr[l][i];
			}
			for (l = j; l < A.n; ++l) {
				A.ptr[l][i] -= k * A.ptr[l][j] * t;
			}
		}
		t = 0;
		for (i = j; i < A.n; ++i) {
			t += A.ptr[i][j] * B[i];
		}
		for (i = j; i < A.n; ++i) {
			B[i] -= k * A.ptr[i][j] * t;
		}
		A.ptr[j][j] = alpha;
	}

	for (i = A.n - 1; i >= 0; --i) {
		X[i] = B[i];
		for (j = i + 1; j < A.n; ++j) {
			X[i] -= A.ptr[i][j] * X[j];
		}
		X[i] /= A.ptr[i][i];
	}
}


void Hales (matrix &A, const double *B, double *X) {

	if(!B || !X) {
		std::cout << "nullptr passed to Hales :(\n";
		exit(30);
	}

	int j, i, k;

	for (j = 0; j < A.n; ++j) {

		for (k = 0; k < j; ++k) {
			A.ptr[j][j] -= A.ptr[j][k] * A.ptr[j][k] * A.ptr[k][k];
		}

		for (i = j + 1; i < A.n; ++i) {

			for (k = 0; k < j; ++k) {
				A.ptr[i][j] -= A.ptr[i][k] * A.ptr[k][k] * A.ptr[j][k];
			}

			A.ptr[i][j] /= A.ptr[j][j];
		}
	}

	for (i = 0; i < A.n; ++i) {

		X[i] = B[i];

		for (j = 0; j < i; ++j) {
			X[i] -= A.ptr[i][j] * X[j];
		}
	}

	for (i = 0; i < A.n; ++i) {
		X[i] = X[i] / A.ptr[i][i];
	}

	for (i = A.n - 1; i >= 0; --i) {
		for (j = i + 1; j < A.n; ++j) {
			X[i] -= A.ptr[j][i] * X[j];
		}
	}
}


void MatrixByVector(matrix &A, const double* B, double *R) {
	int i, j;

	if(!B || !R) {
		std::cout << "nullptr is passed to function :(\n";
		exit(30);
	}

	memset(R, 0, sizeof(double) * A.n);

	for(i = 0; i < A.n; ++i) {
		for(j = 0; j < A.n; ++j) {
			R[i] += A.ptr[i][j] * B[j];
		}
	}

}



void SubtractVectors(double *A, const double *B, int n) {
	int i;

	if(!B || !A) {
		std::cout << "nullptr is passed to function :(\n";
		exit(30);
	}

	for(i = 0; i < n; ++i) {
		A[i] -= B[i];
	}
}

double Normalize(const double *Vec, int n) {
	int i;
	double sum;

	sum = 0;

	for(i = 0; i < n; ++i) {
		sum += Vec[i] * Vec[i];
	}

	return sqrt(sum);

}


void PrintArray(char *filename, double *arr, int n, char *description = nullptr, double after = -1) {
	int i;
	FILE *file;
	file = fopen(filename, "a");

	if(description) {
		fprintf(file, "%s", description);
	}

	for(i = 0; i < n; ++i) {
		fprintf(file, "%e ", arr[i]);
	}
	fprintf(file, "\n");
	if(after != -1) {
		fprintf(file, "Normal %e\n", after);
	}
	fclose(file);
}


int main() {

	char filename[] = "../input.txt";
	char outFile[] = "../out.txt";

	matrix A(filename, 3);
	matrix test(3);


	double B[3] = {-14, 5, 12};
	double X[3];
	double R[3];

	test = A;

	LU(A, B, X);

	MatrixByVector(test, X, R);

	SubtractVectors(R, B, A.n);

	//std::cout << "<--------LU-------->\n" << Normalize(R, A.n) << "\n\n";
	test.printToFile(outFile, "INPUT MATRIX:");
	A.printToFile(outFile, "<-----LU----->");
	PrintArray(outFile, X, A.n, "X:\n");
	PrintArray(outFile, R, A.n, "R:\n", Normalize(R, A.n));

	A = test;

	memcpy(R, B, sizeof(double) * A.n);

	QR(A, B, X);

	memcpy(B, R, sizeof(double) * A.n);

	MatrixByVector(test, X, R);

	SubtractVectors(R, B, A.n);

	A.printToFile(outFile, "<-----QR----->");
	PrintArray(outFile, X, A.n, "X:\n");
	PrintArray(outFile, R, A.n, "R:\n", Normalize(R, A.n));
	PrintArray(outFile, B, A.n, "B:\n");

//	std::cout << "<--------QR-------->\n" << Normalize(R, A.n) << "\n\n";

	A = test;

	Hales(A, B, X);

	MatrixByVector(test, X, R);

	SubtractVectors(R, B, A.n);
	A.printToFile(outFile, "<-----LUL----->");
	PrintArray(outFile, X, A.n, "X:\n");
	PrintArray(outFile, R, A.n, "R:\n", Normalize(R, A.n));
	//std::cout << "<--------Hales-------->\n" << Normalize(R, A.n) << "\n";

	return 0;
}
