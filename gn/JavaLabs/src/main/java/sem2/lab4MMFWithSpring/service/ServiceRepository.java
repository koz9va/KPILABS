package sem2.lab4MMFWithSpring.service;

import java.util.List;

public interface ServiceRepository {
    void getValue(Integer id);
    double NewtonCotes(FunctionForNewton func,
                       int[] c,
                       double xMin,
                       double xMax,
                       int sum,
                       double E,
                       int nm);
}
