package lab4MMFWithSpring;


import lab4MMFWithSpring.model.Model4lab4MMF;
import lab4MMFWithSpring.repository.Model4labRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FunctionInfo {

    Model4labRepository model4labRepository;

    private Integer id;
    private Integer somevalue;

    public int getValue() {
        return somevalue;
    }

    public FunctionInfo returnData() {
        return model4labRepository.getFunctionData();
    }

}
