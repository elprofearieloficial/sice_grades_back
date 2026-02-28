package unpa.entity.universidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "carreras")
@Data
public class Carrera {

    @Id
    @Column(name = "Id_Car")
    private String id;

    @Column(name = "Nombre_Car")
    private String nombre;
}

