package unpa.entity.materias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaResultadoDTO {
    private List<MateriaCursadaDTO> materiasAprobadas;
    private List<MateriaCursadaDTO> materiasReprobadas;
    private List<MateriaCursadaDTO> materiasSinCalificacion;
}

