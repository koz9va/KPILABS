//
// Created by koz9va on 26.10.20.
//

#include "CallAndWrite.h"

int cnt;

int Calc(char *fileName, std::string &configName) {

	double eps, result;
    pt::ptree params;
    FILE *outFile;

    outFile = fopen(fileName, "w");
    if(!outFile) {
        return 21;
    }

    pt::read_xml(configName, params);

    eps = params.get<double>("params.eps");

    fprintf(outFile, "Precision: %e\nBisection method:\n", eps);
    cnt = 0;

    result = Bisection(
    		f4,
    		params.get<double>("params.Bisection.a"),
    		params.get<double>("params.Bisection.b"),
    		eps,
    		outFile
    		);

	fprintf(outFile, "Result: %e\nIterations count: %d\n", result, cnt);

	fprintf(outFile, "Precision: %e\nNewton method:\n", eps);
	cnt = 0;
	result = Newton(
			f4,
			params.get<double>("params.Newton.x0"),
			params.get<double>("params.Newton.xt"),
			eps,
			outFile
			);
	fprintf(outFile, "Result: %e\nIterations count: %d\n", result, cnt);

	fprintf(outFile, "Precision: %e\nSecant method:\n", eps);
	cnt = 0;

	result = Secant(
			f4,
			params.get<double>("params.Secant.x0"),
			params.get<double>("params.Secant.x1"),
			eps,
			outFile
			);
	fprintf(outFile, "Result: %e\nIterations count: %d\n", result, cnt);

    fclose(outFile);
    return 0;
}

