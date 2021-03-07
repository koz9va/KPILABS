package lab4MMFWithSpring.repository;

import lab4MMFWithSpring.FunctionInfo;
import lab4MMFWithSpring.model.Model4lab4MMF;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Model4labRepository extends JpaRepository<Model4lab4MMF, Integer> {
    FunctionInfo getFunctionData();
}
