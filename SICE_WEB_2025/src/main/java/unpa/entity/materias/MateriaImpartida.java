package unpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "imparten")
public class MateriaImpartida {

    @EmbeddedId
    private MateriaImpartidaId id;

    @Column(name = "Id_Pro_FK")
    private String idProfesor;

    @Column(name = "Fechap1_Imp")
    private Date fechap1;

    @Column(name = "Fechap2_Imp")
    private Date fechap2;

    @Column(name = "Fechap3_Imp")
    private Date fechap3;

    @Column(name = "Fechaf_Imp")
    private Date fechaf;

    @Column(name = "Fechae1_Imp")
    private Date fechae1;

    @Column(name = "Fechae2_Imp")
    private Date fechae2;

    @Column(name = "Fechaesp_Imp")
    private Date fechaesp;

    @Column(name = "Id_Car_FK")
    private String idCarrera;

    @Column(name = "Fechaclases_Imp")
    private Integer fechaClases;
}

