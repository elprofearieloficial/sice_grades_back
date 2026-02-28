package unpa.service.materias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.dto.CalificacionMateriaDTO;
import unpa.repository.materias.CalificacionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository repository;

    public List<CalificacionMateriaDTO> obtenerCalificaciones(String idExt, String matricula, String ciclo, String periodo) {
        List<Object[]> resultados = repository.findCalificaciones(idExt, matricula, ciclo, periodo);

        return resultados.stream()
                .map(obj -> new CalificacionMateriaDTO(
                        (String) obj[0],        // nombreMateria
                        (String) obj[1],        // grupo
                        (float) obj[2]         // calificacion
                ))
                .collect(Collectors.toList());
    }

    public List<CalificacionMateriaDTO> obtenerCalificacionesParcial1(String matricula, String ciclo, String periodo) {
        List<Object[]> resultados = repository.findCalificacionesParcial1(matricula, ciclo, periodo);
        return resultados.stream()
                .map(obj -> new CalificacionMateriaDTO(
                        (String) obj[0], // nombreMateria
                        (String) obj[1], // grupo
                        (float) obj[2]  // calificacion
                ))
                .collect(Collectors.toList());
    }
}