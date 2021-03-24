package sem2.lab4MMFWithSpring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sem2.lab4MMFWithSpring.repository.Model4lab4MMFRepository;

@Service
public class LabService implements ServiceRepository {


    @Override
    public double NewtonCotes(FunctionForNewton func, int[] c, double xMin, double xMax, int sum, double E, int nm) {
        int j,k = 0, step = 1, p;
        double min, max;
        if (xMax < xMin) {
            min = xMax;
            max = xMin;
        }
        else {
            max = xMax;
            min = xMin;
        }
        double I = 0, x, hod, IPre, R, I0 = c[0] * (func.calculateFunction(min) - func.calculateFunction(max));
        double h = (max - min) / (double) 2;
        if (nm % 2 != 0)
            p = nm + 1;
        else
            p = nm + 2;
        for (int i = 0; i < p;i++)
            step *= 2;
        do {
            k++;
            IPre = I;
            I = I0;
            hod = h / nm;
            if (h == 0) return IPre;
            for (int i = 0;i * h < max; i++) {
                for (j = 1, x = min + i * h + hod; j < nm; j++, x += hod)
                    I += func.calculateFunction(x) * c[j];
                I += 2 * func.calculateFunction(x) * c[0];
            }
            I = I * h;
            h /= 2;
            R = (I - IPre) / (step - 1);
            if (k > 20)
                if (xMax < xMin)
                    return -(I + R) / sum;
                else return (I + R) / sum;
        }
        while (Math.abs(R) > Math.abs(E));
        if (xMax < xMin)
            return -(I + R) / sum;
        else return (I + R) / sum;
    }

    private static final Logger logger = LoggerFactory.getLogger(LabService.class);

    private Model4lab4MMFRepository model4lab4MMFRepository;

    @Autowired
    public void setModel4lab4MMFRepository(Model4lab4MMFRepository model4lab4MMFRepository) {
        this.model4lab4MMFRepository = model4lab4MMFRepository;
    }
}
