package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoConstanciaDTO {
    // Nombre separado
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    // Campos derivados
    private String nombreCompleto;

    // Otros campos
    private String sexo;
    private String lugarNacimiento;
    private String estadoNacimiento;
    private String fechaNacimiento;
    private int edad;
    private String nacionalidad;
    private String curp;
    private String email;
    private String carrera;
    private String noFicha;
    private String anioFicha;
    private String nss;
    private String matricula;
    private int ultimoSemestre;
    private Double promedioGeneral;
    private int totalCreditos;
    private String nombreCarrera;
    private String planEstudio;

    // Método para generar el nombre completo automáticamente si no lo pasas
    public String getNombreCompleto() {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            return String.format("%s %s %s",
                    apellidoPaterno != null ? apellidoPaterno : "",
                    apellidoMaterno != null ? apellidoMaterno : "",
                    nombre != null ? nombre : ""
            ).trim();
        }
        return nombreCompleto;
    }
}


