package unpa.repository.materias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.materias.CalificacionMateria;

import java.util.List;

// repository/CalificacionRepository.java
@Repository
public interface CalificacionRepository extends JpaRepository<CalificacionMateria, String> {

    @Query(
            value = """
                SELECT m.Nombre_Mat AS nombreMateria, 
                       a.Id_Gpo_FK AS grupo, 
                       a.Calificacion_Ext AS calificacion
                FROM actasextraordinarias a
                JOIN materias m 
                     ON a.Id_Mat_FK = m.Id_Mat 
                    AND a.Id_Pla_FK = m.Id_Pla_FK
                WHERE a.Id_Ext = :idExt
                  AND a.Id_Alu_FK = :matricula
                  AND a.Id_Cic_FK = :ciclo
                  AND a.Id_Per_FK = :periodo
                ORDER BY a.Id_Gpo_FK, m.Nombre_Mat
                """,
            nativeQuery = true
    )
    List<Object[]> findCalificaciones(
            @Param("idExt") String idExt,
            @Param("matricula") String matricula,
            @Param("ciclo") String ciclo,
            @Param("periodo") String periodo
    );

    // Nuevo método para calificaciones parciales (ejemplo: Parcial1)
    @Query(value = """
        SELECT m.Nombre_Mat AS nombreMateria,
               a.Id_Gpo_FK AS grupo,
               a.Calificacionp1_Gra AS calificacion
        FROM actasordinarias a
        JOIN materias m ON a.Id_Mat_FK = m.Id_Mat AND a.Id_Pla_FK = m.Id_Pla_FK
        WHERE a.Id_Alu_FK = :matricula
          AND a.Id_Cic_FK = :ciclo
          AND a.Id_Per_FK = :periodo
        ORDER BY a.Id_Gpo_FK, m.Nombre_Mat
    """, nativeQuery = true)
    List<Object[]> findCalificacionesParcial1(
            @Param("matricula") String matricula,
            @Param("ciclo") String ciclo,
            @Param("periodo") String periodo
    );

    // Nuevo método para calificaciones parciales (ejemplo: Parcial1)
    @Query(value = """
        SELECT m.Nombre_Mat AS nombreMateria,
               a.Id_Gpo_FK AS grupo,
               a.Calificacionp2_Gra AS calificacion
        FROM actasordinarias a
        JOIN materias m ON a.Id_Mat_FK = m.Id_Mat AND a.Id_Pla_FK = m.Id_Pla_FK
        WHERE a.Id_Alu_FK = :matricula
          AND a.Id_Cic_FK = :ciclo
          AND a.Id_Per_FK = :periodo
        ORDER BY a.Id_Gpo_FK, m.Nombre_Mat
    """, nativeQuery = true)
    List<Object[]> findCalificacionesParcial2(
            @Param("matricula") String matricula,
            @Param("ciclo") String ciclo,
            @Param("periodo") String periodo
    );

}
