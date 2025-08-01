package unpa.entity.Alumnos;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "datospersonales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosPersonales {

    @EmbeddedId
    private DatosPersonalesId id;

    @Column(name = "Nombre_Dat")
    private String nombre;

    @Column(name = "Apellidop_Dat")
    private String apPaterno;

    @Column(name = "Apellidom_Dat")
    private String apMaterno;

    @Column(name = "Sexo_Dat")
    private String sexo;

    @Column(name = "Ciudadnac_Dat")
    private String lugarNacimiento;

    @Column(name = "Id_Edonac_FK")
    private String edoNacimiento;

    @Column(name = "Fechanac_Dat")
    private Date fechaNacimiento;

    @Column(name = "Nacionalidad_Dat")
    private String nacionalidad;

    @Column(name = "Curp_Dat")
    private String curp;

    @Column(name = "Email_Dat")
    private String email;

    // Agrega otros campos si los necesitas
}

