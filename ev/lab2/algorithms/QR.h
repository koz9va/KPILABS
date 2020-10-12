#include <math.h>
void QR(double **mat, size_t len, double *b, double *x) {
	int i, j, l;
	double a, k, t;
	for (j = 0; j < len - 1; ++j) {
		a = 0;
	for (i = j; i < len; ++i) {
		a += mat[i][j] * mat[i][j];
	}

	if (mat[j][j] < 0) {
		a = sqrt(a);
	}
	else {
		a = -sqrt(a);
	}

	k = 1.0 / (a * a - a * mat[j][j]);
	mat[j][j] -= a;

	for (i = j + 1; i < len; ++i) {
		t = 0;
		for (l = j; l < len; ++l) {
			t += mat[l][j] * mat[l][i];
		}
		for (l = j; l < len; ++l) {
			mat[l][i] -= k * mat[l][j] * t;
		}
	}
	t = 0;
	for (i = j; i < len; ++i) {
	t += mat[i][j] * b[i];
	}
	for (i = j; i < len; ++i) {
		b[i] -= k * mat[i][j] * t;
	}
		mat[j][j] = a;
	}

	for (i = len - 1; i >= 0; --i) {
		x[i] = b[i];
		for (j = i + 1; j < len; ++j) {
			x[i] -= mat[i][j] * x[j];
		}
	x[i] /= mat[i][i];
	}
}