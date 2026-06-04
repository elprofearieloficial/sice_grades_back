package unpa.repository.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.seguridad.ReporteAccesoNoAutorizado;

import java.util.List;

@Repository
public interface ReporteAccesoNoAutorizadoRepository extends JpaRepository<ReporteAccesoNoAutorizado, Long> {

    List<ReporteAccesoNoAutorizado> findAllByOrderByFechaReporteDescIdDesc();

    List<ReporteAccesoNoAutorizado> findByEstatusOrderByFechaReporteDescIdDesc(Short estatus);

    @Query(value = "SELECT COUNT(1) FROM alumnos WHERE Id_Alu = :matricula", nativeQuery = true)
    long countAlumnoByMatricula(@Param("matricula") String matricula);
}
