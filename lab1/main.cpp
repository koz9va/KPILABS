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

	file.read((char*) Lds, sizeof(int));

	*Uds = new double [*Lds];

	file.read((char*) *Uds, *Lds * sizeof(double));

	file.read((char*) Lgs, sizeof(int));

	*Ugs = new double [*Lgs];

	file.read((char*) *Ugs, *Lgs * sizeof(double));

	return outMatrix;
}

double GaussInter(ublas::matrix<point> &input, double ugsx, double udsx,
				  const double *Uds, const double *Ugs, int Lds, int Lgs)
				  {
	double sum, product, *temp;
	int i, j, k;

	temp = new double [Lds];


	for(k = 0; k < Lds; ++k) {
		sum = 0;
		for(i = 0; i < Lds; ++i) {
			product = input(k, i).x;

			for(j = 0; j < Lds; ++j) {
				if(i != j)
					product *= (udsx - Uds[j]) / (Uds[i] - Uds[j]);
			}
			sum += product;
		}
		temp[k] = sum;
	}

	sum = 0;
	for(i = 0; i < Lgs; ++i) {
		product = temp[i];
		for(j = 0; j < Lgs; ++j) {
			if(i != j)
				product *= (ugsx - Ugs[j]) / (Ugs[i] - Ugs[j]);
		}
		sum += product;
	}

	delete [] temp;

	return sum;
}



ublas::matrix<point> InterpolateMatrix( ublas::matrix<point> &input, const double *Uds, int Lds,
										const double *Ugs, int Lgs)
										{
	ublas::matrix<point> out(Lds + 1, Lgs * 2 );
	int i, j;
	double dUds, dUgs, uds, ugs, deb;

	dUds = (Uds[0] + Uds[1]) / 2;
	dUgs = (Ugs[0] + Ugs[1]) / 2;
	uds = 0;
	ugs = 0;
	for(i = 0; i < out.size1(); ++i) {
		for(j = 0; j < out.size2(); ++j) {
			out(i, j).x = GaussInter(input, ugs, uds, Uds, Ugs, Lds, Lgs);
			out(i, j).y = Uds[i];
			uds += dUds;
			deb = out(i, j).x;
		}
		ugs += dUgs;
	}

	ugs = (Uds[0] + Uds[1]) / 2;

	for(i = 0; i < out.size2(); ++i) {
		out(out.size1() - 1, i).x = GaussInter(input, ugs, uds, Uds, Ugs, Lds, Lgs);
		out(out.size1() - 1, i).y = ugs;
		uds += dUds;
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
