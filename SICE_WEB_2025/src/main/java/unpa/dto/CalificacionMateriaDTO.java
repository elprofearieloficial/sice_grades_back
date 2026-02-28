package unpa.dto;

import lombok.Data;

@Data
public class CalificacionMateriaDTO {

    private String nombreMateria;
    private String grupo;
    private float calificacion;

    public CalificacionMateriaDTO(String nombreMateria, String grupo, float calificacion) {
        this.nombreMateria = nombreMateria;
        this.grupo = grupo;
        this.calificacion = calificacion;
    }
}