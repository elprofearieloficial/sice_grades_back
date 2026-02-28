package unpa.entity.Alumnos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "alumnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoMatriculado {

    @Id
    @Column(name = "Id_Alu")
    private String id;

    @Column(name = "Id_Car_FK")
    private String carrera;

    @Column(name = "Id_Tutor_FK")
    private Integer idTutor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "idDat", column = @Column(name = "Id_Dat_FK")),
            @AttributeOverride(name = "anoDat", column = @Column(name = "Ano_Dat_FK"))
    })
    private DatosPersonalesId datosId;

    @Column(name = "Status_Alu")
    private int estatus;

    @Column(name = "Nss_Alu")
    private String nss;

    @Column(name = "Fecharegistro_Alu")
    private Date fechaRegistro;

    @Column(name = "Fechaegresobaja_Alu")
    private Date fechaEgresoBaja;

    // Relaciones
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Id_Dat_FK", referencedColumnName = "Id_Dat", insertable = false, updatable = false),
            @JoinColumn(name = "Ano_Dat_FK", referencedColumnName = "Ano_Dat", insertable = false, updatable = false)
    })
    private DatosPersonales datosPersonales;
}

