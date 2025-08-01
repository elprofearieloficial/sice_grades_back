package unpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "periodosescolares")
@IdClass(PeriodoEscolarId.class)
@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoEscolar {

    @Id
    @Column(name = "Id_Per")
    private String idPer;

    @Id
    @Column(name = "Id_Cic_FK")
    private String idCicFk;

    @Column(name = "Fechainicio_Per")
    private Date fechainicio;

    @Column(name = "Fechafin_Per")
    private Date fechafin;

    @Column(name = "Iniciop1_Per")
    private Date iniciop1;

    @Column(name = "Finp1_Per")
    private Date finp1;

    @Column(name = "Iniciop2_Per")
    private Date iniciop2;

    @Column(name = "Finp2_Per")
    private Date finp2;

    @Column(name = "Iniciop3_Per")
    private Date iniciop3;

    @Column(name = "Finp3_Per")
    private Date finp3;

    @Column(name = "Iniciof_Per")
    private Date inicioFinal;

    @Column(name = "Finf_Per")
    private Date finFinal;

    @Column(name = "Inicioe1_Per")
    private Date inicioExtra1;

    @Column(name = "Fine1_Per")
    private Date finExtra1;

    @Column(name = "Inicioe2_Per")
    private Date inicioExtra2;

    @Column(name = "Fine2_Per")
    private Date finExtra2;

    @Column(name = "Inicioesp_Per")
    private Date inicioEspecial;

    @Column(name = "Finesp_Per")
    private Date finEspecial;
}
