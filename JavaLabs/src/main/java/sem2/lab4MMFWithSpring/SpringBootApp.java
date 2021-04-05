package sem2.lab4MMFWithSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import sem2.lab4MMFWithSpring.service.FunctionForNewton;
import sem2.lab4MMFWithSpring.service.LabService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Kuchyn Dmytro
 */

@SpringBootApplication
@PropertySource(value={"classpath:lab4MMF.properties"})
public class пшеSpringBootApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootApp.class);
    private int countFunctionCall;
    private LabService labService;

    public int tMin = 0;
    public double tMax = 1.0 / 3 + 0.2, y;
    public double E = 1.0, c = 1.0, r3 = 3.0, r2 = 2.0, r1 = 2.0;
    public int numb = 30;
    public int t0 = 9;
    public double T, omega = 3 * Math.PI, phi = Math.PI / 4, A = 3, sigma = 3;
    public double Ee = E * (r2)/(r1 + r2);
    public double Ikz = ((E) / (r1 + (r2 * r3)/(r2 + r3))) * ((r2)/(r2 + r3));
    public double Re = E / Ikz;

    @Value("${const.xMin}")
    private double xMin;

    private double xMax = Math.PI/2.5;

    @Value("${const.epsilon}")
    private double eps;

    @Value("${const.sum4}")
    private int sum4;

    @Value("${const.sum5}")
    private int sum5;

    @Value("${const.sum8}")
    private int sum8;

    private final FunctionForNewton function = x -> {
        countFunctionCall++;
        if (x != 0) {
            return Math.log(2 - Math.sin(x)) / (0.1 + Math.tan(x) * Math.tan(x));
        }
        else return 0;
    };
//    private final FunctionForNewton function = x -> {
//        countFunctionCall++;
//        return Math.pow(x, 4);
//    };

    public static void main(String[] args) throws IOException {
        var ctx = SpringApplication.run(SpringBootApp.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) throws IOException {


        log.info("App Start");
        log.info("Point 5 start");

        int[] M4 = {7, 32, 12, 32, 7};
        int[] M5 = {19, 75, 50, 50, 75, 19};
        int[] M8 = {989, 5888, -928, 10496, -4540, 10496, -928, 5888, 989};
        System.out.println("Integral solved by M4 :" + labService.NewtonCotes(function, M4, xMin, xMax, sum4, eps, 4) + "\ncountFunctionCall :" + countFunctionCall);
        countFunctionCall = 0;
        System.out.println("Integral solved by M5 :" + labService.NewtonCotes(function, M5, xMin, xMax, sum5, eps, 5) + "\ncountFunctionCall :" + countFunctionCall);
        countFunctionCall = 0;
        System.out.println("Integral solved by M8 :" + labService.NewtonCotes(function, M8, xMin, xMax, sum8, eps, 8) + "\ncountFunctionCall :" + countFunctionCall);

        log.info("Point 5 done");
        log.info("Point 6 start");

        double[] t = new double[numb + 1];
        t[0] = 0;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("t1.txt"))) {

            for (int i = 1; i < numb + 1; i++) {
                t[i] = t[i - 1] + ((tMax + 0.2) / numb);
                writer.write(t[i - 1] + "\n");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("y1.txt"))) {
            for (int i = 0; i < numb; i++) {
                T = t[i];
                if (t[i] < tMax)
                    y = signal1(tMin, omega, phi, A) * h(t[i] - tMin) + labService.NewtonCotes(Vx1, M8, tMin, t[i], sum8, E, 8) - signal1(tMax, omega, phi, A) * h(t[i] - tMax);
                else
                    y = signal1(tMin, omega, phi, A) * h(t[i] - tMin) + labService.NewtonCotes(Vx1, M8, tMin, tMax, sum8, E, 8) - signal1(tMax, omega, phi, A) * h(t[i] - tMax);
                writer.write(y + "\n");
            }
        }
        tMax = 20.0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("t2.txt"))) {
            for (int i = 1; i < numb + 1; i++) {
                t[i] = t[i - 1] + (tMax / numb);
                writer.write(t[i - 1] + "\n");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("y2.txt"))) {
            for (int i = 0; i < numb; i++) {
                T = t[i];
                y = signal2(tMin, A, t0, sigma) * h(t[i] - tMin) + labService.NewtonCotes(Vx2, M8, tMin, t[i], sum8, E, 8) - signal2(tMax, A, t0, sigma) * h(t[i] - tMax);
                writer.write(y + "\n");
            }
        }
        tMax = 5.0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("t3.txt"))) {
            for (int i = 1; i < numb + 1; i++) {
                t[i] = t[i - 1] + (tMax / numb);
                writer.write(t[i - 1] + "\n");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("y3.txt"))) {

            for (int i = 0; i < numb; i++) {
                T = t[i];
                double t1 = t[i] - (1 + Math.ulp(1.0));
                if (t[i] < 1)
                    y = signal3(tMin) * h(t[i] - tMin) + labService.NewtonCotes(Vx3, M8, tMin, t[i], sum8, E, 8) - signal3(1 - Math.ulp(1.0)) * h(t1);
                else if (t[i] <= 3) {
                    y = signal3(tMin) * h(t[i] - tMin) + labService.NewtonCotes(Vx3, M8, tMin, 1, sum8, E, 8) - signal3(1 - Math.ulp(1.0)) * h(t1);
                    y += signal3(1) * h(t[i] - 1) + labService.NewtonCotes(Vx3, M8, 1, t[i], sum8, E, 8) - signal3(3) * h(t[i] - 3);
                } else {
                    y = signal3(tMin) * h(t[i] - tMin) + labService.NewtonCotes(Vx3, M8, tMin, 1, sum8, E, 8) - signal3(1 - Math.ulp(1.0)) * h(t1);
                    y += signal3(1) * h(t[i] - 1) + labService.NewtonCotes(Vx3, M8, 1, 3, sum8, E, 8) - signal3(3) * h(t[i] - 3);
                    y += signal3(3 + Math.ulp(1.0)) * h(t[i] - (3 + Math.ulp(1.0))) + labService.NewtonCotes(Vx3, M8, 3, t[i], sum8, E, 8) - signal3(tMax) * h(t[i] - tMax);
                }
                writer.write(y + "\n");
            }
        }
    }

    public double signal1(double t, double omega, double phi, double A) {
        if (t >= 0 && t <= 0.5)
            return (A * Math.sin(omega * t + phi));
        return 0;
    }

    public double signal2(double t, double A, double t0, double sigma) {
        return A * Math.exp(-(t - t0) * (t - t0) / (sigma * sigma));
    }

    public double signal3(double t) {
        if (t < 1)
            return 2 - 2 * t;
        else if (t >= 1 && t <= 3)
            return 0.5*t - 1.5;
        else
            return 0;
    }

    public static double SSignal3(double t) {
        if (t < 1)
            return -2;
        else if (t >= 1 && t <= 3)
            return 0.5;
        else
            return 0;
    }

    public static double SSignal1(double t, double omega, double phi, double A) {
        if (t >= 0 && t <= 0.5)
            return (A * omega * Math.cos(omega * t + phi));
        return 0;
    }

    private static double SSignal2(double t, double A, double t0, double sigma) {
        return -A * (t - t0) * Math.exp(-(t - t0) * (t - t0) / (sigma * sigma)) * 2 / sigma / sigma;
    }

    public FunctionForNewton Vx1 = x -> h(T - x) * SSignal1(x, omega, phi, A);
    public FunctionForNewton Vx2 = x -> h(T - x) * SSignal2(x, A, t0, sigma);
    public FunctionForNewton Vx3 = x -> h(T - x) * SSignal3(x);

    public double h(double t) {
        if (t < 0)
            return 0;
        return Ee * (1 - Math.exp(t / (Re * c)));
    }

    @Autowired
    public void setLabService(LabService labService) {
        this.labService = labService;
    }
}
