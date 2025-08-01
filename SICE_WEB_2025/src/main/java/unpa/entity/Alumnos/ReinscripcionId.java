package unpa.entity.Alumnos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReinscripcionId implements Serializable {

    @Column(name = "Id_Alu_FK")
    private String idAluFk;

    @Column(name = "Id_Per_FK")
    private String idPerFk;

    @Column(name = "Id_Cic_FK")
    private String idCicFk;

    @Column(name = "SemestreNum_Rei")
    private int semestreNumRei;

    // equals y hashCode

    // getters y setters
}

