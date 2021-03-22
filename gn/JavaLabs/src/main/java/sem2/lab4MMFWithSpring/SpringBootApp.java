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

import java.io.IOException;

/**
 * @author Kuchyn Dmytro
 */

@SpringBootApplication
@PropertySource(value={"classpath:lab4MMF.properties"})
public class SpringBootApp implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SpringBootApp.class);
    private int countFunctionCall;
    private LabService labService;

    @Value("${const.xMin}")
    private double xMin;

    @Value("${const.xMax}")
    private double xMax;

    @Value("${const.epsilon}")
    private double eps;

    @Value("${const.sum4}")
    private int sum4;

    @Value("${const.sum5}")
    private int sum5;

    @Value("${const.sum8}")
    private int sum8;

//    private final FunctionForNewton function = x -> {
//        countFunctionCall++;
//        if (x != 0) {
//            return Math.sin(x * x * x * x) / (1 - Math.cos(Math.log(1 + x)));
//        }
//        else return 0;
//    };
    private final FunctionForNewton function = x -> {
        countFunctionCall++;
        return Math.pow(x, 9);
    };

    public static void main(String[] args) throws IOException {
        var ctx = SpringApplication.run(SpringBootApp.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) {
        log.info("App Start");
        log.info("Point 5 start");
        int [] M4 = {7, 32, 12, 32, 7};
        int [] M5 = {19, 75, 50, 50, 75, 19};
        int [] M8 = {989, 5888, -928, 10496, -4540, 10496, -928, 5888, 989};
        System.out.println("Integral solved by M4 :" + labService.NewtonCotes(function, M4, xMin, xMax, sum4, eps, 4) + "\ncountFunctionCall :" + countFunctionCall);
        countFunctionCall = 0;
        System.out.println("Integral solved by M5 :" + labService.NewtonCotes(function, M5, xMin, xMax, sum5, eps, 5) + "\ncountFunctionCall :" + countFunctionCall);
        countFunctionCall = 0;
        System.out.println("Integral solved by M8 :" + labService.NewtonCotes(function, M8, xMin, xMax, sum8, eps, 8) + "\ncountFunctionCall :" + countFunctionCall);

        log.info("Point 5 done");
        log.info("Point 6 start");

        //TODO:Point 6

    }

    @Autowired
    public void setLabService(LabService labService) {
        this.labService = labService;
    }
}
