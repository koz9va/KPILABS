package lab4MMFWithSpring;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorld {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    private FunctionInfo functionInfo;


    public void hello() {
        System.out.println(functionInfo.getValue());
    }


    @Autowired
    public void setFunctionInfo(FunctionInfo functionInfo) {
        this.functionInfo = functionInfo;
    }
}
