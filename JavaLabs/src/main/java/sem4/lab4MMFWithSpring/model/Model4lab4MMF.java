package sem4.lab4MMFWithSpring.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "model4lab4")
@Data
@NoArgsConstructor
public class Model4lab4MMF {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "somevalue")
    private Integer someValue;
}
