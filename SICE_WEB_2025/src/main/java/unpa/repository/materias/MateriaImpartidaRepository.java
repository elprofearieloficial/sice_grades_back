package unpa.repository.materias;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unpa.entity.materias.MateriaImpartida;
import unpa.entity.materias.MateriaImpartidaData;
import unpa.entity.materias.MateriaImpartidaId;

public interface MateriaImpartidaRepository extends JpaRepository<MateriaImpartida, MateriaImpartidaId> {
    @Query(value = """
    SELECT 
        m.Nombre_Mat AS nombreMat,
        m.Semestre_Mat AS semestreMat,
        t.Nombre_Tra AS nombreTra,
        t.Apellidop_Tra AS apellidopTra,
        t.Apellidom_Tra AS apellidomTra,
        t.Siglasgradomax_Tra AS siglasgradomaxTra,
        i.Id_Cic_FK AS idCiclo,
        i.Id_Gpo_FK AS idGrupo,
        i.Id_Mat_FK AS idMateria,  
        i.Id_Per_FK AS idPeriodo,
        i.Id_Pla_FK AS idPlan,
        i.Fechaclases_Imp AS fechaClases,
        i.Fechae1_Imp AS fechae1,
        i.Fechae2_Imp AS fechae2,
        i.Fechaesp_Imp AS fechaesp,
        i.Fechaf_Imp AS fechaf,
        i.Fechap1_Imp AS fechap1,
        i.Fechap2_Imp AS fechap2,
        i.Fechap3_Imp AS fechap3,
        i.Id_Car_FK AS idCarrera,
        i.Id_Pro_FK AS idProfesor
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
    MateriaImpartidaData findMateriaImpartidaBy(
            @Param("idMateria") String idMateria,
            @Param("idPlan") String idPlan,
            @Param("idGrupo") String idGrupo,
            @Param("idCiclo") String idCiclo,
            @Param("idPeriodo") String idPeriodo
    );

}
