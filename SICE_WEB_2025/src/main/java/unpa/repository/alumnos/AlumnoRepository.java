package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.dto.AlumnoProjection;
import unpa.entity.Alumnos.AlumnoMatriculado;
import unpa.entity.Alumnos.ReinscripcionPeriodoProjection;
import unpa.dto.MateriaCursadaDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoMatriculado, String> {

    @Query(value = """
    SELECT 
        ga.Id_Mat_FK AS clave,
        ga.Id_Pla_FK AS plan,
        m.Nombre_Mat AS nombre,
        ga.Id_Gpo_FK AS grupo,
        m.Semestre_Mat AS semestre,
        m.Numerocred_Mat AS creditos,
        ga.Calificacionmateria_Gra AS calificacion
    FROM grupos_alumnos ga
    JOIN materias m ON ga.Id_Mat_FK = m.Id_Mat AND ga.Id_Pla_FK = m.Id_Pla_FK
    WHERE ga.Id_Alu_FK = :matricula
    ORDER BY m.Semestre_Mat, m.Orden_Mat
    """, nativeQuery = true)
    List<MateriaCursadaDTO> findMateriasPorAlumno(@Param("matricula") String matricula);




    @Query(value = """
        SELECT 
            t1.SemestreNum_Rei AS PrimerSemestreNum,
            t1.Id_Cic_FK AS PrimerCiclo,
            t1.Id_Per_FK AS PrimerPeriodo,
            t2.SemestreNum_Rei AS UltimoSemestreNum,
            t2.Id_Cic_FK AS UltimoCiclo,
            t2.Id_Per_FK AS UltimoPeriodo
        FROM reinscripcion t1
        JOIN reinscripcion t2 
            ON t1.Id_Alu_FK = t2.Id_Alu_FK AND t1.Id_Alu_FK = :matricula
        WHERE 
            t1.SemestreNum_Rei = (
                SELECT MIN(SemestreNum_Rei) FROM reinscripcion WHERE Id_Alu_FK = :matricula
            )
            AND t2.SemestreNum_Rei = (
                SELECT MAX(SemestreNum_Rei) FROM reinscripcion WHERE Id_Alu_FK = :matricula
            )
        ORDER BY PrimerCiclo, PrimerPeriodo
        LIMIT 1
        """, nativeQuery = true)
    Optional<ReinscripcionPeriodoProjection> obtenerPeriodosDeReinscripcion(@Param("matricula") String matricula);

    @Query(value = """
        SELECT SUM(m.Numerocred_Mat)
        FROM grupos_alumnos ga
        JOIN materias m 
          ON m.Id_Mat = ga.Id_Mat_FK 
         AND m.Id_Pla_FK = ga.Id_Pla_FK
        WHERE ga.Id_Alu_FK = :matricula
          AND ga.Calificacionmateria_Gra BETWEEN 6 AND 10
        """, nativeQuery = true)
    Integer obtenerCreditosAprobados(@Param("matricula") String matricula);

    @Query(value = """
        SELECT ga.Id_Pla_FK as IdPlaFk
        FROM grupos_alumnos ga
        INNER JOIN planestudios pe ON pe.Id_Pla = ga.Id_Pla_FK
        WHERE ga.Id_Alu_FK = :matricula
        LIMIT 1
        """, nativeQuery = true)
    String obtenerPlanEstudios(@Param("matricula") String matricula);


        @Query(value = """
        SELECT 
            c.matricula AS matricula,
            c.Nombre_Dat AS nombre,
            c.Apellidop_Dat AS apPaterno,
            c.Apellidom_Dat AS apMaterno,
            c.Id_Mat_FK AS Id_Mat_FK,
            c.Nombre_Mat AS materia,
            c.Semestre_Mat AS semestre,
            c.Id_Pla AS Id_Pla,
            c.Id_Car AS Id_Car,
            c.Nombre_Car AS Nombre_Car,
            c.Id_Pla_FK AS Id_Pla_FK,
            c.Tipocurso_Gra AS Tipocurso_Gra,
            CONCAT(c.Id_Cic_FK, '-', c.Id_Per_FK) AS ciclo,
            c.Calificacionp1_Gra AS parcial1,
            c.Calificacionp2_Gra AS parcial2,
            c.Calificacionp3_Gra AS parcial3,
            c.Promedioparcial_Gra AS Promedioparcial_Gra,
            c.Calificacionf_Gra AS ordinario,
            c.Calificaciondef_Gra AS pFinal,
            c.Tipocalificacion_Gra AS Tipocalificacion_Gra,
            e.extra1 AS extra1,
            e.extra2 AS extra2,
            e.especial AS especial
        FROM calificaciones_unpa c
        LEFT JOIN extras_unpa e 
            ON c.matricula = e.Id_Alu_FK
           AND c.Id_Mat_FK = e.Id_Mat_FK
        WHERE c.matricula = :matricula
        """, nativeQuery = true)
        List<AlumnoProjection> findAlumnoConMaterias(@Param("matricula") String matricula);



}
