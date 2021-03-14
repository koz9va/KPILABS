package sem2.lab4MMFWithSpring.service;

import sem2.lab4MMFWithSpring.repository.Model4lab4MMFRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorld implements ServiceRepository {
    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    private Model4lab4MMFRepository model4lab4MMFRepository;

    @Autowired
    public void setModel4lab4MMFRepository(Model4lab4MMFRepository model4lab4MMFRepository) {
        this.model4lab4MMFRepository = model4lab4MMFRepository;
    }

    @Override
    public void getValue(Integer id) {
        logger.info(model4lab4MMFRepository.getSomeValue(id).toString());
    }
}
