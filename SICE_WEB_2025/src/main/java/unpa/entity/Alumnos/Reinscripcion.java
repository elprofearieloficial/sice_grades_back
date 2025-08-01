package unpa.entity.Alumnos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "reinscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reinscripcion {

    @EmbeddedId
    private ReinscripcionId id;

    @Column(name = "Fecha_Rei")
    private Date fechaRei;

    @Column(name = "Semestre_Rei")
    private String semestreRei;

    @Column(name = "observacion_Rei")
    private String observacionRei;

    // getters y setters
}