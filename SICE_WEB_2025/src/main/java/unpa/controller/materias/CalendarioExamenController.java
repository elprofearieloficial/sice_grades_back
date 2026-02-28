package unpa.controller.materias;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpa.dto.CalendarioDTO;
import unpa.entity.materias.CalendarioExamenResponse;
import unpa.service.materias.CalendarioExamenService;


import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class CalendarioExamenController {

    @Autowired
    private CalendarioExamenService service;


    @GetMapping("/{alumnoId}/calendarioporcicloyperiodo")
    public ResponseEntity<List<CalendarioDTO>> obtenerCalendarioPorCicloYPeriodo(
            @PathVariable String alumnoId,
            @RequestParam String ciclo,
            @RequestParam String periodo) {
        return ResponseEntity.ok(service.obtenerCalendarioExamenesByCicloAndPeriodo(alumnoId, ciclo, periodo));
    }

    @GetMapping("/{alumnoId}/calendarios")
    public ResponseEntity<List<CalendarioDTO>> obtenerCalendario(
            @PathVariable String alumnoId
           ) {
        return ResponseEntity.ok(service.obtenerCalendarioExamenes(alumnoId));
    }
}

