#include <iostream>
#include <fstream>
#include <cstdlib>

using namespace std;

const int N = 7;
const int M = 3;
const double U0 = 4.5;
const double beta = 0.25e-3;
const double g22 = 3e-6;

void calcArray(double *array, int len) {
	double step = ((array[len - 1] - array[0]) / (len - 1));

	for(int i = 1; i < len - 1; i++) {
		array[i] = array[i - 1] + step;
	}

}

double CompCurr(double gs, double ds) {
	if(gs <= U0)
		return g22 * ds;
	else {
		if(ds < (gs - U0))
			return  beta * (2 * (gs - U0) - ds) * ds + g22 * ds;
		else
			return  beta * (gs - U0) * (gs - U0) + g22 *ds;
	}
}

int main(int argc, char **argv) {
	auto *Uds = new double[N];
	auto *Ugs = new double[M];

	ofstream I, Fds, Fgs;

	if(argc != 5)
		cout << "Wrong number of arguments\n";

	I.open("I.txt");
	Fds.open("DS.txt");
	Fgs.open("GS.txt");

	Ugs[0]     = atof(argv[1]);
	Ugs[M - 1] = atof(argv[2]);
	Uds[0]     = atof(argv[3]);
	Uds[N - 1] = atof(argv[4]);

	calcArray(Ugs, M);
	calcArray(Uds, N);

	for (int i = 0; i < M; i++) {
		for (int j = 0; j < N; j++) {
			I << CompCurr(Ugs[i], Uds[j]) << " ";
		}
		I << endl;
	}

	for(int i = 0; i < M; i++) {
		Fgs << Ugs[i] << endl;
	}

	for(int i = 0 ; i < N; i++) {
		Fds << Uds[i] << endl;
	}


	I.close();
	Fds.close();
	Fgs.close();

	delete [] Uds;
	delete [] Ugs;

	return 0;
}
