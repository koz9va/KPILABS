#include <iostream>
#include <fstream>
#include <cstdlib>
using namespace std;
constexpr int DS = 7;
constexpr int GS = 3;

class MOSFET {
public:
    double U0;
    double Beta;
    double G22;
    double Ugs[GS];
    double Uds[DS];



    MOSFET(double beta, double g22, double u0, double Ugs0, double Ugsl, double Uds0, double Udsl)
    : U0(u0), Beta(beta), G22(g22)  {
        Ugs[0] = Ugs0;
        Uds[0] = Uds0;
        Uds[DS - 1] = Udsl;
        Ugs[GS - 1] = Ugsl;
        double step = (Ugsl - Ugs0) / (GS -1);
        for(int i = 1; i < GS - 1; ++i) {
            Ugs[i] = Ugs[i - 1] + step;

        }
        step = (Udsl - Uds0) / (DS -1);
        for(int i = 1; i < DS - 1; ++i) {
            Uds[i] = Uds[i - 1] + step;

        }

    }
    double FindIc(double Ags, double Ads) {
        if (Ags <= U0) {
            return G22 * Ads;
        }
        else {
            if (Ads < (Ags - U0)) {
                return Beta * (2 * (Ags - U0) - Ads) * Ads + G22 * Ads;
            }
            else {
                return Beta * (Ags - U0) * (Ags - U0) + G22 * Ads;
            }
        }
    }




};






int main(int argc, char **argv) {

    int i, j;

    ofstream Ugs, Uds, current;

    if(argc != 8){
        cout << "BOOM" << endl;
    }
    Ugs.open("Ugs.txt");
    Uds.open("Uds.txt");
    current.open("Current.txt");
    MOSFET P(
            atof(argv[1]),
            atof(argv[2]),
            atof(argv[3]),
            atof(argv[4]),
            atof(argv[5]),
            atof(argv[6]),
            atof(argv[7])
            );

    for( i = 0; i < DS; ++i) {


     for(j = 0; j < GS; ++j) {
         current << P.FindIc(P.Ugs[i], P.Uds[j]) << " ";



     }
    current << endl;

    }

    for(i = 0; i < GS; ++i) {
        Ugs << P.Ugs[i] << endl;

        }


    for(j = 0; j < DS; ++j) {
        Uds << P.Uds[j] << endl;

    }
    current.close();
    Ugs.close();
    Uds.close();
    return 0;

}
