package unpa.entity.Alumnos;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@Access(AccessType.FIELD) // Especifica que las anotaciones se leen directamente desde los campos
public class DatosPersonalesId implements Serializable {

    @Column(name = "Id_Dat")
    private int idDat;

    @Column(name = "Ano_Dat")
    private int anoDat;
}
