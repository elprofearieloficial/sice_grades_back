package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.Alumnos.Reinscripcion;
import unpa.entity.Alumnos.ReinscripcionId;

import java.util.Optional;
import java.util.List;

@Repository
public interface ReinscripcionRepository extends JpaRepository<Reinscripcion, ReinscripcionId> {

    // Obtiene la última reinscripción de un alumno (por fecha más reciente)
    Optional<Reinscripcion> findFirstById_IdAluFkOrderByFechaReiDesc(String idAluFk);

    // Obtiene solo el semestre de la última reinscripción de un alumno
    default Optional<Integer> findUltimoSemestreByAlumno(String idAluFk) {
        return findFirstById_IdAluFkOrderByFechaReiDesc(idAluFk)
                .map(r -> r.getId().getSemestreNumRei());
    }

    // Lista todas las reinscripciones de un alumno ordenadas por fecha descendente
    List<Reinscripcion> findById_IdAluFkOrderByFechaReiDesc(String idAluFk);
}
