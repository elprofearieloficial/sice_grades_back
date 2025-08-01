package unpa.entity.materias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MateriaCursadaDTO {
    private String clave;
    private String plan;
    private String nombre;
    private String grupo;
    private int semestre;
    private int creditos;
    private Float calificacion; // puede ser null si no está calificada
}


