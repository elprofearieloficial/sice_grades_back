package unpa.repository.universidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unpa.entity.universidad.PeriodoEscolar;
import unpa.entity.universidad.PeriodoEscolarId;

import java.util.List;

public interface PeriodoEscolarRepository extends JpaRepository<PeriodoEscolar, PeriodoEscolarId> {

    List<PeriodoEscolar> findByIdCicFk(String idCiclo);


    @Query(value = """
    (
        SELECT * FROM periodosescolares 
        WHERE Fechainicio_Per <= CURRENT_DATE
        AND Fechafin_Per >= CURRENT_DATE
        ORDER BY Id_Cic_FK DESC
        LIMIT 1
    )
    UNION
    (
        SELECT * FROM periodosescolares        
        ORDER BY Fechainicio_Per DESC
        LIMIT 1
    )
    LIMIT 1
    """, nativeQuery = true)
    PeriodoEscolar findUltimoCicloEscolar();
}

