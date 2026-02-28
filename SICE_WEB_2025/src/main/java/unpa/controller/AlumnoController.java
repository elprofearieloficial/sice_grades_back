package unpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unpa.dto.*;
import unpa.service.alumnos.AlumnoService;

import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/usuario/{matricula}/completo")
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable String matricula) {
        AlumnoDTO alumno = alumnoService.getAlumnoConMaterias(matricula);

        if (alumno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alumno);
    }


    @GetMapping("/usuariomock/{matricula}/completo")
    public ResponseEntity<AlumnoDTO> getAlumnoConMateriasMock(@PathVariable String matricula) {
        // Aquí generas el objeto igual que en tu mock
       /* CalificacionDTO calificacion1 = new CalificacionDTO(8.0, 7.5, 8.0, 8.5, 9.3, null, null, null);
        CalificacionDTO calificacion2 = new CalificacionDTO(8.0, 7.5, 8.0, 8.5, 9.3, null, null, null);
        CalificacionDTO calificacion3 = new CalificacionDTO(5.0, 6.5, 7.0, 6.5, 7.3, null, null, null);

        CalendarioDTO calendarioMatematicas = new CalendarioDTO("Matemáticas", "2025-01-10", null, null,
                "2024-12-05", "2024-09-10", "2024-10-15", "2024-11-20", "2024-2025 A");

        CalendarioDTO calendarioFisica = new CalendarioDTO("Física", null, null, "2025-01-15",
                "2024-12-08", "2024-09-12", "2024-10-18", "2024-11-25", "2024-2025 A");

        CalendarioDTO calendarioProgramacion = new CalendarioDTO("Programación", null, "2025-06-01", null,
                "2025-05-02", "2025-02-05", "2025-03-10", "2025-04-20", "2024-2025 B");

        List<MateriaDTO> materias = List.of(
                new MateriaDTO("MAT101", true, calificacion1, "2024-2025 A", "Matemáticas", 1, calendarioMatematicas),
                new MateriaDTO("FIS101", true, calificacion2, "2024-2025 A", "WEB I", 1, calendarioFisica),
                new MateriaDTO("PRO101", true, calificacion3, "2024-2025 B", "Programación", 2, calendarioProgramacion)
        );

        AlumnoDTO alumno = new AlumnoDTO(
                "1",
                matricula,
                "López",
                "Rodríguez",
                "Maritza",
                "Ingeniería en Computación",
                true,
                new UsuarioDTO(matricula,false),
                materias
        );*/

        return ResponseEntity.ok(null);
    }


}

