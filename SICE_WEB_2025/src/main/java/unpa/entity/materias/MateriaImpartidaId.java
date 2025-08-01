package unpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class MateriaImpartidaId implements Serializable {
    @Column(name = "Id_Gpo_FK")
    private String idGrupo;

    @Column(name = "Id_Mat_FK")
    private String idMateria;

    @Column(name = "Id_Cic_FK")
    private String idCiclo;

    @Column(name = "Id_Per_FK")
    private String idPeriodo;

    @Column(name = "Id_Pla_FK")
    private String idPlan;
}
