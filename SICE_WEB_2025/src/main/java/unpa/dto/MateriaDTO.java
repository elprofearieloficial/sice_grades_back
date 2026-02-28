package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaDTO {
    private String clave;
    private String materia;
    private Integer semestre;
    private Boolean activo;
    private String ciclo;
    private CalificacionDTO calificaciones;
    private CalendarioDTO calendarioExamenes;
}
