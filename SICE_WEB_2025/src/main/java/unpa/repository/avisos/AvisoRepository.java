package unpa.repository.avisos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import unpa.entity.avisos.Aviso;
import unpa.entity.avisos.AvisoId;

@Repository
public interface AvisoRepository extends JpaRepository<Aviso, AvisoId> {
    @Query(value = """
            SELECT a.*
            FROM avisos a
            WHERE a.Status_Avi = 1
              AND a.Dirigir_Avi IN (2, 3)
              AND NOT EXISTS (
                  SELECT 1
                  FROM avisos_alumno_status s
                  WHERE s.Id_Avi_FK = a.Id_Avi
                    AND s.Id_Cic_FK = a.Id_Cic_FK
                    AND s.Id_Per_FK = a.Id_Per_FK
                    AND s.Id_Alu_FK = :matricula
                    AND s.Status_Avi_Alu = 1
              )
            ORDER BY a.Fecha_Avi DESC, a.Id_Avi DESC
            """, nativeQuery = true)
    List<Aviso> findNoLeidosByMatricula(@Param("matricula") String matricula);

    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO avisos_alumno_status (
                Id_Avi_FK,
                Id_Cic_FK,
                Id_Per_FK,
                Id_Alu_FK,
                Status_Avi_Alu,
                Fecha_Lectura
            ) VALUES (
                :idAvi,
                :cicloId,
                :periodoId,
                :matricula,
                1,
                NOW()
            )
            ON DUPLICATE KEY UPDATE
                Status_Avi_Alu = 1,
                Fecha_Lectura = NOW()
            """, nativeQuery = true)
    int marcarAvisoLeido(
            @Param("idAvi") Integer idAvi,
            @Param("cicloId") String cicloId,
            @Param("periodoId") String periodoId,
            @Param("matricula") String matricula
    );
}


