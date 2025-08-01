package unpa.entity.Alumnos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "fichas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ficha {

    @EmbeddedId
    private FichaId id;

    @Column(name = "Id_Car_FK")
    private String carrera;

    @Column(name = "Fechasol_Fic")
    private Date fechaSolicitud;

    @Column(name = "Fechaexam_Fic")
    private Date fechaExamen;

    @Column(name = "Aprobado_Fic")
    private Boolean aprobado;

    @Column(name = "Promedioexam_Fic")
    private Float promedio;

    @Column(name = "Tipo_Fic")
    private Integer tipo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Id_Dat_FK", referencedColumnName = "Id_Dat", insertable = false, updatable = false),
            @JoinColumn(name = "Ano_Dat_FK", referencedColumnName = "Ano_Dat", insertable = false, updatable = false)
    })
    private DatosPersonales datosPersonales;
}

