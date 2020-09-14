#include <iostream>
#include <fstream>
#include <boost/numeric/ublas/matrix.hpp>

typedef struct {
	double x;
	double y;
} point;

using namespace boost::numeric;

template<typename T>
ublas::matrix<T> readMatrix(char *filename, double **Uds, int *Lds, double **Ugs, int *Lgs) {

	int colls, rows, i, j;
	size_t elementSize;
	std::fstream file(filename, std::ios::in | std::ios::binary);

	if(!file.is_open()) {
		std::cout << "Wrong file name :(\n";
		exit(21);
	}

	file.read((char*) &elementSize, sizeof(size_t));
	file.read((char*) &rows, sizeof(int));
	file.read((char*) &colls, sizeof(int));

	ublas::matrix<T> outMatrix(rows, colls);

	for(i = 0; i < rows; ++i) {
		for(j = 0; j < colls; ++j) {
			file.read((char*) & outMatrix(i, j), elementSize);
		}
	}

	file.read((char*) Lgs, sizeof(int));

	*Ugs = new double [*Lgs];

	file.read((char*) *Ugs, *Lgs * sizeof(double));

	file.read((char*) Lds, sizeof(int));

	*Uds = new double [*Lds];

	file.read((char*) *Uds, *Lds * sizeof(double));

	return outMatrix;
}

double GaussInter(double *Ugs, double *Uds, int Lgs, int Lds, ublas::matrix<point> &input,
				  double ugsx, double udsx)
				  {
	double sum, product, value;
	int i, j, k;

 value = 0;

	for(k = 0; k < Lgs; ++k) {
		sum = 0;
		for (i = 0; i < Lds; ++i) {
			product = input(k, i).x;

			for (j = 0; j < Lds; ++j) {
				if (i != j)
					product *= (udsx - Uds[j]) / (Uds[i] - Uds[j]);
			}
			sum += product;
		}


		for (i = 0; i < Lgs; ++i) {
			if (i != k) {
				sum *= (ugsx - Ugs[i]) / (Ugs[k] - Ugs[i]);
			}
			value += sum;
		}

	}

	return value;
}



ublas::matrix<point> InterpolateMatrix( ublas::matrix<point> &input, double *Uds, int Lds,
										double *Ugs, int Lgs)
										{
	ublas::matrix<point> out(Lgs + 1, Lds * 2 - 1);
	int i, j;
	double dUds, dUgs, value;

	dUds = (Uds[0] + Uds[1]) / 2;
	dUgs = (Ugs[0] + Ugs[1]) / 2;

	for(i = 0; i < Lgs; ++i) {
		value = 0;
		for(j = 0; j < Lds * 2 - 1; ++j) {
			out(i, j).x = GaussInter(Ugs, Uds, Lgs, Lds, input, Ugs[i], value);
			value += dUds;
		}
	}
	value = 0;
	for(i = 0; i < Lds * 2 - 1; ++i) {
		out(input.size1(), i).x = GaussInter(Ugs, Uds, Lgs, Lds, input, dUgs, value);
		value += dUds;
	}



	return out;
}


int main() {

	int Lds, Lgs, i, j;
	double *Uds, *Ugs;

	Uds = Ugs = nullptr;

	ublas::matrix<point> ReadData = readMatrix<point>("data.bin", &Uds, &Lds, &Ugs, &Lgs);

	ublas::matrix<point> big = InterpolateMatrix(ReadData, Uds, Lds, Ugs, Lgs);

	for(i = 0; i < big.size1(); ++i) {
		for(j = 0; j < big.size2(); ++j) {
			std::cout << big(i, j).x << " ";
		}
		std::cout << "\n";
	}


	delete [] Uds;
	delete [] Ugs;

	return 0;
}
