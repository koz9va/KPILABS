package sem2.lab4MMFWithSpring.service;

import java.util.List;

public interface ServiceRepository {
    double NewtonCotes(FunctionForNewton func,
                       int[] c,
                       double xMin,
                       double xMax,
                       int sum,
                       double E,
                       int nm);
}
