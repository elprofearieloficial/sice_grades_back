package unpa.repository.universidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.Universidad;
import unpa.entity.universidad.UniversidadProjection;

public interface UniversidadRepository extends JpaRepository<Universidad, String> {
    @Query(value = """
    SELECT
        u.Nombre_Uni AS nombre,
        u.Id_Uni AS clave,
        CONCAT(t1.Nombre_Tra, ' ', t1.Apellidop_Tra, ' ', t1.Apellidom_Tra) AS rector,
        t1.Sexo_Tra AS generoRector,
        CONCAT(t2.Nombre_Tra, ' ', t2.Apellidop_Tra, ' ', t2.Apellidom_Tra) AS viceAcademico,
        t2.Sexo_Tra AS generoAcademico,
        CONCAT(t3.Nombre_Tra, ' ', t3.Apellidop_Tra, ' ', t3.Apellidom_Tra) AS viceAdministrativo,
        t3.Sexo_Tra AS generoAdministrativo,
        CONCAT(t4.Nombre_Tra, ' ', t4.Apellidop_Tra, ' ', t4.Apellidom_Tra) AS jefeEscolares,
        t4.Sexo_Tra AS generoEscolares,
        t1.Id_Tra AS idRector,
        t2.Id_Tra AS idAcademico,
        t3.Id_Tra AS idAdministrativo,
        t4.Id_Tra AS idJefeEscolares,
        u.Leyenda_Uni AS leyenda
    FROM universidad u
    LEFT JOIN jefesareas j1 ON j1.Id_Uni_FK = u.Id_Uni AND j1.Id_Pue_FK = 1
    LEFT JOIN trabajadores t1 ON t1.Id_Tra = j1.Id_Tra_FK
    LEFT JOIN jefesareas j2 ON j2.Id_Uni_FK = u.Id_Uni AND j2.Id_Pue_FK = 2
    LEFT JOIN trabajadores t2 ON t2.Id_Tra = j2.Id_Tra_FK
    LEFT JOIN jefesareas j3 ON j3.Id_Uni_FK = u.Id_Uni AND j3.Id_Pue_FK = 3
    LEFT JOIN trabajadores t3 ON t3.Id_Tra = j3.Id_Tra_FK
    LEFT JOIN jefesareas j4 ON j4.Id_Uni_FK = u.Id_Uni AND j4.Id_Pue_FK = 4
    LEFT JOIN trabajadores t4 ON t4.Id_Tra = j4.Id_Tra_FK
    WHERE u.Id_Uni = :idUniversidad
""", nativeQuery = true)
    UniversidadProjection findDatosUniversidad(@Param("idUniversidad") String idUniversidad);

    @Query(value = """
        SELECT           
            c.Nombre_Cam as nombreCam,
            c.Domicilio_Cam as domicilioCam,
            c.Ciudad_Cam as ciudadCam,
            c.Id_Cam as idCam,
            e.Nombre_Edo as nombreEdo,
            c.Codigop_Cam as codigopCam,
            c.Telefono_Cam as telefonoCam,
            c.Fax_Cam as faxCam
        FROM universidad u
        JOIN campus c ON c.Id_Uni_FK = u.Id_Uni 
        JOIN estados e ON c.Id_Edo_FK = e.Id_Edo
        WHERE u.Id_Uni = :idUni and c.Id_Cam =:idCampus
        """, nativeQuery = true)
    CampusProjection obtenerCampus(@Param("idUni") String idUni, @Param("idCampus") String idCampus);
}
