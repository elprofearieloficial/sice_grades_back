package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.Alumnos.AlumnoMatriculado;
import unpa.entity.materias.MateriaCursadaDTO;

import java.util.List;

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

}
