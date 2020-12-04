//
// Created by koz9va on 27.11.20.
//
#include "SLAU.h"


lin::matrix::matrix(int N) : n(N) {
	ptr = new double *[n];
	for(int i = 0; i < n; ++i) {
		ptr[i] = new double [n];
	}
}

lin::matrix::~matrix() {
	for(int i = 0; i < n; ++i) {
		delete [] ptr[i];
	}
	delete [] ptr;
}

lin::vector::vector(int N) : n(N) {
	ptr = new double [n];
}

lin::vector::~vector() {
	delete [] ptr;
}

lin::vector& lin::vector::operator -= (const  lin::vector& rhs) {
	if(this->n != rhs.n) {
		std::cout << "Different length at subtraction!\n";
		return *this;
	}
	for(int i = 0; i < this->n; ++i) {
		this->ptr[i] -= rhs.ptr[i];
	}
	return *this;
}

double& lin::vector::operator [] (int index) const {
	return this->ptr[index];
}

double lin::vector::normalize() const {
	double sum;
	sum = 0;
	for(int i = 0; i < n; ++i) {
		sum += ptr[i] * ptr[i];
	}
	return sqrt(sum);
}

lin::vector::vector(lin::vector &ref) : n(ref.n) {
	ptr = new double [n];
	memcpy(ptr, ref.ptr, sizeof(double) * n);
}

void lin::matrix::QR(lin::vector &B, lin::vector &X) const {
	double alpha, k, t;
	int i, j, l;


	for (j = 0; j < n - 1; ++j) {
		alpha = 0;
		for (i = j; i < n; ++i) {
			alpha += ptr[i][j] * ptr[i][j];
		}

		if (ptr[j][j] < 0)
			alpha = sqrt(alpha);
		else
			alpha = -sqrt(alpha);
		k = 1.0 / (alpha * alpha - alpha * ptr[j][j]);
		ptr[j][j] -= alpha;

		for (i = j + 1; i < n; ++i) {
			t = 0;
			for (l = j; l < n; ++l) {
				t += ptr[l][j] * ptr[l][i];
			}
			for (l = j; l < n; ++l) {
				ptr[l][i] -= k * ptr[l][j] * t;
			}
		}
		t = 0;
		for (i = j; i < n; ++i) {
			t += ptr[i][j] * B[i];
		}
		for (i = j; i < n; ++i) {
			B[i] -= k * ptr[i][j] * t;
		}
		ptr[j][j] = alpha;

		for (i = n - 1; i >= 0; --i) {
			X[i] = B[i];
			for (j = i + 1; j < n; ++j) {
				X[i] -= ptr[i][j] * X[j];
			}
			X[i] /= ptr[i][i];
		}
	}



}

lin::matrix &lin::matrix::operator = (const lin::matrix &ref) {
	if(&ref == this)
		return *this;

	if(ref.n != this->n) {
		printf("Not the same sizes of matrixes:(\n");
		return *this;
	}

	for(int i = 0; i < ref.n; ++i) {
		memcpy(this->ptr[i], ref.ptr[i], sizeof(double ) * this->n);
	}
	return *this;
}

