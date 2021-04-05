package sem2.lab4MMFWithSpring.repository;

import sem2.lab4MMFWithSpring.model.Model4lab4MMF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Model4lab4MMFRepository extends JpaRepository<Model4lab4MMF, Integer> {

    @Query(
            value = "select somevalue from model4lab4 where id = :id",
            nativeQuery = true
    )
    Integer getSomeValue(@Param("id") Integer id);
}
