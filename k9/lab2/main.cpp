#include <iostream>
#include <cstdio>

class matrix {
public:
	double **ptr;
	int n;
	matrix(int N): n(N) {
		ptr = new double *[n];
		for(int i = 0; i < n; ++i) {
			ptr[i] = new double [n];
		}
	}

	~matrix() {
		for(int i = 0; i < n; ++i) {
			delete [] ptr[i];
		}
		delete [] ptr;
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
		//present

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

int main() {

	matrix A(3);
	matrix test(3);

	A.ptr[0][0] = 9;
	A.ptr[0][1] = -2;
	A.ptr[0][2] = -6;
	A.ptr[1][0] = -2;
	A.ptr[1][1] = 16;
	A.ptr[1][2] = -12;
	A.ptr[2][0] = -6;
	A.ptr[2][1] = -12;
	A.ptr[2][2] = 18;
	test.ptr[0][0] = 9;
	test.ptr[0][1] = -2;
	test.ptr[0][2] = -6;
	test.ptr[1][0] = -2;
	test.ptr[1][1] = 16;
	test.ptr[1][2] = -12;
	test.ptr[2][0] = -6;
	test.ptr[2][1] = -12;
	test.ptr[2][2] = 18;

	double B[3] = {0, 20, -12};
	double X[3];
	double R[3];

//	for(int i = 0 ; i < 3; ++i) {
//		for(int j = 0; j < 3; ++j) {
//			std::cout << A.ptr[i][j] << " ";
//		}
//		std::cout << "\n";
//	}

	LU(A, B, X);

	MatrixByVector(test, X, R);

	SubtractVectors(R, B, A.n);

	for(int i = 0; i < A.n; ++i) {
		printf("%g\n", R[i]);
	}

	return 0;
}
