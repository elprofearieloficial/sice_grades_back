package unpa.repository.profesores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unpa.entity.profesores.Profesor;
import unpa.entity.profesores.ProfesorDTO;

import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, String> {

    @Query(value = """
    SELECT 
        p.Id_Pro_FK AS idProfesor,
        pc.Id_Car_FK AS idCarrera,
        p.Categoria_Pro AS categoria,
        p.Nivel_Pro AS nivel,
        cam.Nombre_Cam AS campus,
        t.Nombre_Tra AS nombre,
        t.Apellidop_Tra AS apellidoPaterno,
        t.Apellidom_Tra AS apellidoMaterno,
        t.Sexo_Tra AS sexo,
        t.Siglasgradomax_Tra AS siglasGradoMax,
        t.Cargo_Tra AS cargo
    FROM profesores p
    JOIN trabajadores t ON p.Id_Pro_FK = t.Id_Tra
    JOIN profesores_carreras pc ON p.Id_Pro_FK = pc.Id_Pro_FK
    JOIN campus cam ON t.Id_Cam_FK = cam.Id_Cam
""", nativeQuery = true)
    List<ProfesorDTO> obtenerProfesoresConInfoCompleta();

    @Query(value = """
    SELECT 
        p.Id_Pro_FK AS idProfesor,
        pc.Id_Car_FK AS idCarrera,
        p.Categoria_Pro AS categoria,
        p.Nivel_Pro AS nivel,
        cam.Nombre_Cam AS campus,
        t.Nombre_Tra AS nombre,
        t.Apellidop_Tra AS apellidoPaterno,
        t.Apellidom_Tra AS apellidoMaterno,
        t.Sexo_Tra AS sexo,
        t.Siglasgradomax_Tra AS siglasGradoMax,
        t.Cargo_Tra AS cargo
    FROM profesores p
    JOIN trabajadores t ON p.Id_Pro_FK = t.Id_Tra
    JOIN profesores_carreras pc ON p.Id_Pro_FK = pc.Id_Pro_FK
    JOIN campus cam ON t.Id_Cam_FK = cam.Id_Cam
    WHERE p.Id_Pro_FK = :idProfesor
""", nativeQuery = true)
    ProfesorDTO obtenerProfesorPorId(@Param("idProfesor") String idProfesor);


}

