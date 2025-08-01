package unpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "planestudios")
@Data
public class PlanEstudios {

    @Id
    @Column(name = "Id_Pla")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Car_FK", insertable = false, updatable = false)
    private Carrera carrera;
}
