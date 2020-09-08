#include <iostream>
#include <fstream>
#include <boost/numeric/ublas/matrix.hpp>
#include <future>
#include <vector>

typedef struct {
	double x;
	double y;
} point;

using namespace boost::numeric;

template<typename T>
ublas::matrix<T> readMatrix(char *filename) {

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

	return outMatrix;
}

double GaussInter(ublas::matrix<point> input, double x, int row) {
	double sum, product, deb;
	size_t i, j;

	if(row > input.size1() - 1)
		exit(-1);

	sum = 0;
	for(i = 0; i < input.size2(); ++i) {
		product = 1;
		for(j = 0; j < input.size2(); ++j) {
			if(i != j) {
				product *= (x - input(row, j).x ) / (input(row, i).x - input(row, j).x);
			}
		}
		deb = input(row, i).y;
		sum += product * input(row, i).y;
	}

	return sum;
}



ublas::matrix<point> InterpolateMatrix( ublas::matrix<point> input) {
	ublas::matrix<point> out;
	std::vector< std::future<double> > results;
	int i, j;

	out = input;
	out.resize(input.size1(), input.size2() * 2);

	for(i = 0; i < out.size1(); ++i) {
		results.reserve(input.size2());

		for(j = 0; j < out.size2() - 2; ++j) {
				out(i, j).x = (input(i, j/2).x + input(i, j/2 + 1).x) / 2;

				results.emplace_back(std::async(std::launch::any, GaussInter, input, out(i, j).x, i));
		}

		for(j = 0; j < out.size2() - 1; ++j) {
			if(j % 2) {
				out(i, j).y = results[j].get();
			}
		}

		results.clear();

	}


	return out;
}


int main() {

	ublas::matrix<point> ReadData = readMatrix<point>("data.bin");

	ublas::matrix<point> big = InterpolateMatrix(ReadData);

	for(int i = 0; i < big.size1(); ++i) {
		for(int j = 0; j < big.size2(); ++j) {
			std::cout << big(i, j).x << " ";
		}
		std::cout << "\n";
	}


	return 0;
}
