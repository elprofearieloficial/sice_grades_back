package unpa.repository.profesores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.materias.MateriaImpartida;
import unpa.entity.profesores.ProfesorReporteProjection;

@Repository
public interface ProfesorReporteRepository extends JpaRepository<MateriaImpartida, Long> {

    @Query(value = """
        SELECT 
            i.Id_Pro_FK as clave,
            i.Id_Cic_FK  as idCicFk,
            m.Nombre_Mat AS nombreMat,
            m.Semestre_Mat AS semestreMat,
            t.Nombre_Tra AS nombreTra,
            t.Apellidop_Tra AS apellidopTra,
            t.Apellidom_Tra AS apellidomTra,
            t.Siglasgradomax_Tra AS siglasgradomaxTra
        FROM imparten i
        JOIN materias m ON m.Id_Mat = i.Id_Mat_FK AND m.Id_Pla_FK = i.Id_Pla_FK
        JOIN trabajadores t ON t.Id_Tra = i.Id_Pro_FK
        WHERE i.Id_Mat_FK = :idMateria
          AND i.Id_Pla_FK = :idPlan
          AND i.Id_Gpo_FK = :idGrupo
          AND i.Id_Cic_FK = :idCiclo
          AND i.Id_Per_FK = :idPeriodo
        LIMIT 1
        """, nativeQuery = true)
    ProfesorReporteProjection obtenerDatosProfesor(
            @Param("idMateria") String idMateria,
            @Param("idPlan") String idPlan,
            @Param("idGrupo") String idGrupo,
            @Param("idCiclo") String idCiclo,
            @Param("idPeriodo") String idPeriodo
    );
}
