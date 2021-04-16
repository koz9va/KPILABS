#include <cstdio>

#include "ode.h"

int cnt;

double test(double t, double y) {
	++cnt;
	return -(t + 1) * y * y;
}

int main() {
	const int nmax = 1024;
	int i, n;
	double tend, *t, *y;
	FILE *y_file, *t_file;

	t = new double[nmax];
	y = new double[nmax];
	tend = 1;
	
	cnt = 0;

	n = euler(&test, tend, 1, 1e-6, t, y, nmax);

	printf("Calls to the function: %d\nPoints were found: %d\n"
			, cnt, n);
	if(n == nmax) {
		printf("Quantity of allocated points might not be sufficient\n");
	}

	y_file = fopen("../y.txt", "w");
	t_file = fopen("../t.txt", "w");

	for(i = 0; i < n; ++i) {
		fprintf(y_file, "%g\n", y[i]);
		fprintf(t_file, "%g\n", t[i]);
	}
	fclose(y_file);
	fclose(t_file);

	delete [] y;
	delete [] t;

	return 0;
}
