package unpa.entity.avisos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "avisos")
public class Aviso {

    @EmbeddedId
    private AvisoId id;

    @Column(name = "Aviso_Avi", nullable = false, columnDefinition = "TEXT")
    private String aviso;

    @Column(name = "Fecha_Avi", nullable = false)
    private LocalDate fecha;

    @Column(name = "Status_Avi", nullable = false)
    private Boolean status = true;

    @Column(name = "Dirigir_Avi", nullable = false)
    private Integer dirigir = 3;
}
