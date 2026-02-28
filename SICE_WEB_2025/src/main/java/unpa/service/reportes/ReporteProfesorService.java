package unpa.service.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.profesores.ProfesorReporteProjection;
import unpa.repository.profesores.ProfesorReporteRepository;

@Service
public class ReporteProfesorService {

    @Autowired
    private ProfesorReporteRepository profesorRepo;

    /*public Map<String, Object> obtenerDatosProfesor(String idMateria, String idPlan, String idGrupo, String idCiclo, Integer idPeriodo) {
        ProfesorReporteProjection prof = profesorRepo.obtenerDatosProfesor(idMateria, idPlan, idGrupo, idCiclo, idPeriodo);

        if (prof == null) return Collections.emptyMap();

        Map<String, Object> params = new HashMap<>();
        params.put("Nombre_Mat", prof.getNombreMat());
        params.put("Semestre_Mat", prof.getSemestreMat());
        String nombreProfesor = String.format("%s %s %s %s",
                prof.getSiglasgradomaxTra(),
                prof.getNombreTra(),
                prof.getApellidopTra(),
                prof.getApellidomTra()
        );
        params.put("Nombre_Profesor", nombreProfesor.trim());
        return params;
    }*/

    public ProfesorReporteProjection obtenerDatosProfesor(String idMateria, String idPlan, String idGrupo, String idCiclo, String idPeriodo) {
        return  profesorRepo.obtenerDatosProfesor(idMateria, idPlan, idGrupo, idCiclo, idPeriodo);
    }
}

