#include <iostream>

class matrix {
	double **ptr;
	int n;
	matrix(int N) {
		n = N;
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
	double operator() (int i, int j) {
		return ptr[i][j];
	}

};

void LU(int n, double **A, double *B, double *X) {
	int j, i, k;

	for(j = 0; j < n; ++j) {
		for(i = j; i < n; ++i) {
			for(k = 0; k <= j - 1; ++k) {
				A[i][j] -= A[i][k] * A[k][j];
			}
		}
		//present

		for(i = j + 1; j < n; ++j) {
			for(k = 0; k <= j - 1; ++k) {
				A[j][i] -= A[j][k] * A[k][i];
			}
			A[j][i] /= A[j][j];
		}
	//	memcpy(X, B, sizeof(double) * n);
		for(i = 0; i < n; ++i) {
			X[i] = B[i];
			for(j = 0; j <= i - 1; ++j) {
				X[i] -= A[i][j] * X[j];
			}
			X[i] /= A[i][i];
		}

		for(i = n - 1; i >= 0; --i) {
			for(j = i + 1; j < n; ++j) {
				X[i] -= A[i][j] * X[j];
			}
		}

	}

}


int main() {





	return 0;
}
