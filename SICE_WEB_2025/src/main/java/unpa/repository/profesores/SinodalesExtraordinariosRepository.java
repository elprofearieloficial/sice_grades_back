package unpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.SinodalProjection;
import unpa.entity.SinodalesExtraordinarios;

import java.util.List;

@Repository
public interface SinodalesExtraordinariosRepository extends JpaRepository<SinodalesExtraordinarios, Long> {
 // mandar el id exam
    /* String examen = "";
                switch (this.tipoExamen) {
                    case 5:
                        examen = "e1";
                        break;
                    case 6:
                        examen = "e2";
                        break;
                    case 7:
                        examen = "esp";
                        break;
                }
    * */
    @Query(value = """
        SELECT 
            m.Nombre_Mat AS nombreMat,
            m.Semestre_Mat AS semestreMat,
            t.Siglasgradomax_Tra AS siglasgradomaxTra,
            t.Nombre_Tra AS nombreTra,
            t.Apellidop_Tra AS apellidopTra,
            t.Apellidom_Tra AS apellidomTra
        FROM sinodales_extraordinarios s
        JOIN materias m ON m.Id_Mat = s.Id_Mat_FK AND m.Id_Pla_FK = s.Id_Pla_FK
        JOIN trabajadores t ON t.Id_Tra = s.Id_Pro_FK
        WHERE s.Id_Ext = :idExamen
          AND s.Id_Mat_FK = :idMateria
          AND s.Id_Pla_FK = :idPlan
          AND s.Id_Gpo_FK = :idGrupo
          AND s.Id_Cic_FK = :idCiclo
          AND s.Id_Per_FK = :idPeriodo
        ORDER BY s.Titular_Ext ASC
        """, nativeQuery = true)
    List<SinodalProjection> findSinodalesByExamen(
            @Param("idExamen") String idExamen,
            @Param("idMateria") String idMateria,
            @Param("idPlan") String idPlan,
            @Param("idGrupo") String idGrupo,
            @Param("idCiclo") String idCiclo,
            @Param("idPeriodo") Integer idPeriodo
    );
}

