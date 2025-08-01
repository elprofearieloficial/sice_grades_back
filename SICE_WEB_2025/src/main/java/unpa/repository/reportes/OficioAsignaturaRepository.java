package unpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unpa.entity.OficioAsignatura;
import unpa.entity.OficioAsignaturaId;

public interface OficioAsignaturaRepository extends JpaRepository<OficioAsignatura, OficioAsignaturaId> {

    // Por ejemplo: verificar si ya existe un oficio generado
    boolean existsByIdGpoAndIdMatAndIdPlaAndIdCicAndTipoOfiAndIdPer(
            String idGpo, String idMat, String idPla, String idCic, Integer tipoOfi, String idPer
    );
}
