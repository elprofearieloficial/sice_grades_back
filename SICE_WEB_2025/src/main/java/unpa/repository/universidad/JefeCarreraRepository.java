package unpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unpa.entity.JefeCarrera;
import unpa.entity.JefeCarreraDTO;

@Repository
public interface JefeCarreraRepository extends JpaRepository<JefeCarrera, String> {
    @Query(value = """
        SELECT t.Sexo_Tra as sexo,t.Id_Tra AS claveTrabajador, t.Nombre_Tra AS nombre, t.Apellidop_Tra AS apellidop, t.Apellidom_Tra AS apellidom
        FROM jefescarreras j
        JOIN trabajadores t ON j.Id_Tra_FK = t.Id_Tra
        WHERE j.Id_Car_FK = :idCarrera
        """, nativeQuery = true)
    JefeCarreraDTO findJefeCarreraByIdCarrera(@Param("idCarrera") String idCarrera);
}
