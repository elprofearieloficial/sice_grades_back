package unpa.entity.actas;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "oficios_asignaturas")
@IdClass(OficioAsignaturaId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OficioAsignatura {

    @Id
    @Column(name = "Id_Gpo_FK")
    private String idGpo;

    @Id
    @Column(name = "Id_Mat_FK")
    private String idMat;

    @Id
    @Column(name = "Id_Pla_FK")
    private String idPla;

    @Id
    @Column(name = "Id_Cic_FK")
    private String idCic;

    @Id
    @Column(name = "Tipo_Ofi")
    private Integer tipoOfi;

    @Id
    @Column(name = "Id_Per_FK")
    private String idPer;

    @Column(name = "Id_Ofi")
    private String idOfi;

    @Column(name = "Fecha_Ofi")
    private Date fechaOfi;
}

