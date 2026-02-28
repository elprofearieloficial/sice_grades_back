package unpa.repository.universidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.materias.Materia;

@Repository
public interface EncabezadoReporteRepository extends JpaRepository<Materia, String> {

    @Query(value = """
        SELECT 
            u.Nombre_Uni as nombreUni,
            u.Leyenda_Uni as leyendaUni,
            u.Id_Uni as idUni,
            c.Nombre_Cam as nombreCam,
            c.Domicilio_Cam as domicilioCam,
            c.Ciudad_Cam as ciudadCam,
            c.Id_Cam as idCam,
            e.Nombre_Edo as nombreEdo,
            c.Codigop_Cam as codigopCam,
            c.Telefono_Cam as telefonoCam,
            c.Fax_Cam as faxCam
        FROM universidad u
        JOIN campus c ON c.Id_Cam = (SELECT ca.Id_Car FROM carreras ca JOIN planestudios p ON ca.Id_Car = p.Id_Car_FK WHERE p.Id_Pla = :idPlan LIMIT 1)
        JOIN estados e ON c.Id_Edo_FK = e.Id_Edo
        WHERE u.Id_Uni = 1
        """, nativeQuery = true)
    CampusProjection obtenerEncabezado(@Param("idPlan") String idPlan);
}
