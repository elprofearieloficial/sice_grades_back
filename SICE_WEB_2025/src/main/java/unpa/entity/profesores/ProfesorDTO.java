package unpa.entity.profesores;

import lombok.Data;

@Data
public class ProfesorDTO {
    private String idProfesor;
    private String idCarrera;
    private String categoria;
    private String nivel;
    private String campus;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String sexo;
    private String siglasGradoMax;
    private String cargo;

    // Constructor
    public ProfesorDTO(String idProfesor, String idCarrera, String categoria, String nivel,
                       String campus, String nombre, String apellidoPaterno, String apellidoMaterno,
                       String sexo, String siglasGradoMax, String cargo) {
        this.idProfesor = idProfesor;
        this.idCarrera = idCarrera;
        this.categoria = categoria;
        this.nivel = nivel;
        this.campus = campus;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.sexo = sexo;
        this.siglasGradoMax = siglasGradoMax;
        this.cargo = cargo;
    }

    // Getters y setters opcionales si usas lombok puedes usar @Data
}

