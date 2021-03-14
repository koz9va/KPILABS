package sem2.lab4MMFWithSpring;

import sem2.lab4MMFWithSpring.service.HelloWorld;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

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
        helloWorld.getValue(3);
    }
}
