package unpa.repository.materias;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unpa.dto.CalendarioDTO;
import unpa.entity.materias.CalendarioProjection;
import unpa.entity.materias.ImpartenId;
import unpa.entity.materias.MateriaAsignadaDetails;

import java.util.List;
@Repository
public interface CalendarioExamenRepository extends JpaRepository<MateriaAsignadaDetails, ImpartenId> {

    @Query(value = """
    SELECT m.Nombre_Mat AS materia,
           DATE_FORMAT(i.Fechae1_Imp, '%Y-%m-%d') AS e1,
           DATE_FORMAT(i.Fechae2_Imp, '%Y-%m-%d') AS e2,
           DATE_FORMAT(i.Fechaesp_Imp, '%Y-%m-%d') AS esp,
           DATE_FORMAT(i.Fechaf_Imp, '%Y-%m-%d') AS f,
           DATE_FORMAT(i.Fechap1_Imp, '%Y-%m-%d') AS p1,
           DATE_FORMAT(i.Fechap2_Imp, '%Y-%m-%d') AS p2,
           DATE_FORMAT(i.Fechap3_Imp, '%Y-%m-%d') AS p3,
          CONCAT(i.Id_Cic_FK, '-', i.Id_Per_FK) AS ciclo
    FROM imparten i
    JOIN materias m
         ON i.Id_Mat_FK = m.Id_Mat
        AND i.Id_Pla_FK = m.Id_Pla_FK
    JOIN grupos_alumnos ga
         ON ga.Id_Mat_FK = m.Id_Mat
        AND ga.Id_Pla_FK = m.Id_Pla_FK
        AND ga.Id_Cic_FK = i.Id_Cic_FK
        AND ga.Id_Per_FK = i.Id_Per_FK
        AND ga.Id_Gpo_FK = i.Id_Gpo_FK
    WHERE ga.Id_Alu_FK = :alumnoId      
    ORDER BY ga.Id_Gpo_FK, m.Nombre_Mat
    """, nativeQuery = true)
    List<CalendarioDTO> findCalendarioExamenes(
            @Param("alumnoId") String alumnoId
            );


    @Query(value = """
    SELECT m.Nombre_Mat AS materia,
           DATE_FORMAT(i.Fechae1_Imp, '%Y-%m-%d') AS e1,
           DATE_FORMAT(i.Fechae2_Imp, '%Y-%m-%d') AS e2,
           DATE_FORMAT(i.Fechaesp_Imp, '%Y-%m-%d') AS esp,
           DATE_FORMAT(i.Fechaf_Imp, '%Y-%m-%d') AS f,
           DATE_FORMAT(i.Fechap1_Imp, '%Y-%m-%d') AS p1,
           DATE_FORMAT(i.Fechap2_Imp, '%Y-%m-%d') AS p2,
           DATE_FORMAT(i.Fechap3_Imp, '%Y-%m-%d') AS p3,
           CONCAT(i.Id_Cic_FK, '-', i.Id_Per_FK) AS ciclo
    FROM imparten i
    JOIN materias m
         ON i.Id_Mat_FK = m.Id_Mat
        AND i.Id_Pla_FK = m.Id_Pla_FK
    JOIN grupos_alumnos ga
         ON ga.Id_Mat_FK = m.Id_Mat
        AND ga.Id_Pla_FK = m.Id_Pla_FK
        AND ga.Id_Cic_FK = i.Id_Cic_FK
        AND ga.Id_Per_FK = i.Id_Per_FK
        AND ga.Id_Gpo_FK = i.Id_Gpo_FK
    WHERE ga.Id_Alu_FK = :alumnoId
      AND ga.Id_Cic_FK = :ciclo
      AND ga.Id_Per_FK = :periodo
    ORDER BY ga.Id_Gpo_FK, m.Nombre_Mat
    """, nativeQuery = true)
    List<CalendarioDTO> findCalendarioExamenesByCicloAndPeriodo(
            @Param("alumnoId") String alumnoId,
            @Param("ciclo") String ciclo,
            @Param("periodo") String periodo);
}
