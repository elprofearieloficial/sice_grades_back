package unpa.repository.alumnos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.Alumnos.DatosPersonales;
import unpa.entity.Alumnos.DatosPersonalesId;

@Repository
public interface DatosPersonalesRepository extends JpaRepository<DatosPersonales, DatosPersonalesId> {

    @Query("SELECT d FROM DatosPersonales d WHERE d.id.idDat = :id AND d.id.anoDat = :ano")
    DatosPersonales findById_IdDatAndId_AnoDat(@Param("id") Integer id, @Param("ano") Integer ano);
}