#include "CallAndWrite.h"



int main() {

    std::string config;
    char outFileName[] = "out.txt";
    config = "params.xml";

    if(Calc(outFileName, config) != 0) {
    	printf("Something went wrong\n");
    }



	return 0;
}