void LU(double **mat, size_t len, double *b, double *x) {
	int j, i, k;

	for(j = 0; j < len; ++j) {
		for (i = j; i < len; ++i) {
			for (k = 0; k <= j - 1; ++k) {
				mat[i][j] -= mat[i][k] * mat[k][j];
			}
		}

		for (i = j + 1; i < len; ++i) {
			for (k = 0; k <= j - 1; ++k) {
				mat[j][i] -= mat[j][k] * mat[k][i];
			}
			mat[j][i] /= mat[j][j];
		}
	}

	for(i = 0; i < len; ++i) {
		x[i] = b[i];
		for(j = 0; j <= i - 1; ++j) {
			x[i] -= mat[i][j] * x[j];
		}
		x[i] /= mat[i][i];
	}

	for(i = len - 1; i >= 0; --i) {
		for(j = i + 1; j < len; ++j) {
			x[i] -= mat[i][j] * x[j];
		}
	}

}
