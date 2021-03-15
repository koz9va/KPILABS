package sem2.lab4MMFWithSpring;

import sem2.lab4MMFWithSpring.service.FunctionForNewton;
import sem2.lab4MMFWithSpring.service.HelloWorld;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class SpringBootApp implements CommandLineRunner {

    private final HelloWorld helloWorld;

    public static void main(String[] args) throws IOException {
        var ctx = SpringApplication.run(SpringBootApp.class, args);
        ctx.close();
    }

    @Override
    public void run(String... args) {
        FunctionForNewton function = x -> {
            if (x != 0) {
                return Math.sin(x * x * x * x) / (1 - Math.cos(Math.log(1 + x)));
            }
            else return 0;
        };
        int [] M4 = {7, 32, 12, 32, 7};
        double a = helloWorld.NewtonCotes(function, M4, 1.0, 0.0, 90, 1e-6, 4);
        System.out.println(a);
    }
}
