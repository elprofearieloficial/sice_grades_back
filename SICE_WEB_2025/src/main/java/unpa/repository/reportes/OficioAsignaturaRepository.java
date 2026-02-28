package unpa.repository.reportes;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.actas.OficioAsignatura;
import unpa.entity.actas.OficioAsignaturaId;

public interface OficioAsignaturaRepository extends JpaRepository<OficioAsignatura, OficioAsignaturaId> {

    // Por ejemplo: verificar si ya existe un oficio generado
    boolean existsByIdGpoAndIdMatAndIdPlaAndIdCicAndTipoOfiAndIdPer(
            String idGpo, String idMat, String idPla, String idCic, Integer tipoOfi, String idPer
    );
}

