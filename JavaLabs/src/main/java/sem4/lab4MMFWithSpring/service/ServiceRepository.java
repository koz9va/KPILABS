package sem4.lab4MMFWithSpring.service;

public interface ServiceRepository {
    double NewtonCotes(FunctionForNewton func,
                       int[] c,
                       double xMin,
                       double xMax,
                       int sum,
                       double E,
                       int nm);
}
