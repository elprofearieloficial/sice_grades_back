package unpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "universidad")
@Data
public class Universidad {

    @Id
    @Column(name = "Id_Uni")
    private String id;

    @Column(name = "Nombre_Uni")
    private String nombre;

    @Column(name = "Leyenda_Uni")
    private String leyenda;

    // Getters y setters (o usa @Data de Lombok si quieres)
}
