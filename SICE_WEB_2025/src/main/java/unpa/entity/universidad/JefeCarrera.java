package unpa.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "jefescarreras")
public class JefeCarrera {
    @Id
    @Column(name = "Id_Car_FK")
    private String idCarFk;

    @Column(name = "Id_Tra_FK")
    private String idTraFk;

    // getters, setters (puedes usar Lombok si deseas)
}