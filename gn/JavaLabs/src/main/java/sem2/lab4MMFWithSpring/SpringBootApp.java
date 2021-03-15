package sem2.lab4MMFWithSpring;

import sem2.lab4MMFWithSpring.service.FunctionForNewton;
import sem2.lab4MMFWithSpring.service.LabService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@AllArgsConstructor
public class SpringBootApp implements CommandLineRunner {

    private final LabService labService;
//    private final FunctionForNewton function = x -> {
//        if (x != 0) {
//            return Math.sin(x * x * x * x) / (1 - Math.cos(Math.log(1 + x)));
//        }
//        else return 0;
//    };
    private final FunctionForNewton function = x -> Math.pow(x, 9);

    public static void main(String[] args) throws IOException {
        var ctx = SpringApplication.run(SpringBootApp.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) {
        int [] M4 = {7, 32, 12, 32, 7};
        System.out.println(labService.NewtonCotes(function, M4, 0.0, 2.0, 90, 1e-6, 4));
    }
}
