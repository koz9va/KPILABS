//
// Created by koz9va on 26.10.20.
//

#include "CallAndWrite.h"

int cnt;

int Calc(char *fileName, std::string &configName) {

	double eps, result;
    pt::ptree params;
    FILE *outFile;
	std::string method, param0, param1;
    outFile = fopen(fileName, "w");
    if(!outFile) {
        return 21;
    }

    pt::read_xml(configName, params);

    eps = params.get<double>("params.eps");

    fprintf(outFile, "Precision: %e\n", eps);

    method = "Bisection";
    param0 = "a";
    param1 = "b";

    CallFunc(params, outFile, method, param0, param1, eps, Bisection, f4);

	method = "Newton";
	param0 = "x0";
	param1 = "xt";

	CallFunc(params, outFile, method, param0, param1, eps, Bisection, f4);method = "Bisection";

	method = "Secant";
	param0 = "x0";
	param1 = "x1";

	CallFunc(params, outFile, method, param0, param1, eps, Bisection, f4);

    fclose(outFile);
    return 0;
}


void CallFunc(pt::ptree &params,
			  FILE *file,
			  std::string &method,
			  std::string &param0,
			  std::string &param1,
			  double eps,
			  double func(double f(double), double, double, double, FILE*),
			  double fx(double)
			  )
{
	double result;
	cnt = 0;

	fprintf(file, "%s method:\n", method.c_str());

	result = func(
			fx,
			params.get<double>("params." + method + "." + param0),
			params.get<double>("params." + method + "." + param1),
			eps,
			file
	);

	fprintf(file, "Result: %e\nIterations count: %d\n", result, cnt);
}