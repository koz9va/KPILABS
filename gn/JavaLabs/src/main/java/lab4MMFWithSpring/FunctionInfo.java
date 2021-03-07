package lab4MMFWithSpring;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FunctionInfo {

    @Value("${userBucket.path}")
    private int someValue;

    public int getValue() {
        return someValue;
    }
}
