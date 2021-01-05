void LDL (double **mat, int len, const double *b, double *x) {
	int j, i, k;

	for (j = 0; j < len; ++j) {
			for (k = 0; k < j; ++k) {
				mat[j][j] -= mat[j][k] * mat[j][k] * mat[k][k];
			}

		for (i = j + 1; i < len; ++i) {
			for (k = 0; k < j; ++k) {
				mat[i][j] -= mat[i][k] * mat[k][k] * mat[j][k];
			}

		mat[i][j] /= mat[j][j];
		}
	}

	for (i = 0; i < len; ++i) {
		x[i] = b[i];

		for (j = 0; j < i; ++j) {
			x[i] -= mat[i][j] * x[j];
		}
	}

	for (i = 0; i < len; ++i) {
	x[i] = x[i] / mat[i][i];
	}

	for (i = len - 1; i >= 0; --i) {
		for (j = i + 1; j < len; ++j) {
			x[i] -= mat[j][i] * x[j];
		}
	}
}