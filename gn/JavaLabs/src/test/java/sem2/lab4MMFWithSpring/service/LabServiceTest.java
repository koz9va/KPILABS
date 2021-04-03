package sem2.lab4MMFWithSpring.service;

import junit.framework.TestCase;

public class LabServiceTest extends TestCase {

    private final FunctionForNewton function = x -> Math.pow(x, 9);

    public void testNewtonCotes() {
        LabService ls = new LabService();
        int [] M4 = {7, 32, 12, 32, 7};
        double actual = ls.NewtonCotes(function, M4, 0.0, 2.0, 90, 1e-6, 4);
        double expected = 102.40000000000873;
        assertEquals(expected, actual);
    }
}
//"commit testbranc