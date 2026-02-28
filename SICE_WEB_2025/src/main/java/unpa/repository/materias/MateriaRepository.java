package unpa.repository.materias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unpa.entity.materias.Materia;
import unpa.entity.materias.MateriaId;
import unpa.dto.MateriaCursadaDTO;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, MateriaId> {
    // Puedes agregar consultas personalizadas aquí si las necesitas

    @Query("""
    SELECT c.nombre
    FROM Materia m
    JOIN m.plan p
    JOIN p.carrera c
    WHERE m.id.idMat = :idMateria AND m.id.idPlaFK = :idPlan
    """)
    String findNombreCarreraByMateria(
            @Param("idMateria") String idMateria,
            @Param("idPlan") String idPlan
    );

    @Query(value = """
    SELECT Id_Mat AS clave,
           Id_Pla_FK AS plan,
           Nombre_Mat AS nombre,
           Semestre_Mat AS semestre,
           Numerocred_Mat AS creditos,
           Tipo_Mat AS tipo
    FROM materias
    WHERE Id_Pla_FK = :plan
      AND Tipo_Mat NOT IN (0, 2)
    ORDER BY Semestre_Mat, Orden_Mat, Id_Mat
    """, nativeQuery = true)
    List<MateriaCursadaDTO> findMateriasDelPlan(@Param("plan") String plan);
}
