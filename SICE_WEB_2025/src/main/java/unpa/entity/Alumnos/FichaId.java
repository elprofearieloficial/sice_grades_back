package unpa.entity.Alumnos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FichaId implements Serializable {
    @Column(name = "Id_Dat_FK")
    private int idDat;

    @Column(name = "Ano_Dat_FK")
    private int anoDat;
}