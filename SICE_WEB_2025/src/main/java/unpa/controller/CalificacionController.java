package unpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unpa.entity.CalificacionMateriaDTO;
import unpa.service.CalificacionService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService service;



    @GetMapping("/extraordinarios")
    public List<CalificacionMateriaDTO> obtenerCalificaciones(
            @RequestParam String parcial,
            @RequestParam String matricula,
            @RequestParam String ciclo,
            @RequestParam String periodo) {
        return service.obtenerCalificaciones(parcial, matricula, ciclo, periodo);
    }

    // Nuevo endpoint para parciales (ej. Parcial1)
    @GetMapping("/parciales/1")
    public List<CalificacionMateriaDTO> getCalificacionesParcial1(
            @RequestParam String matricula,
            @RequestParam String ciclo,
            @RequestParam String periodo
    ) {
        return service.obtenerCalificacionesParcial1(matricula, ciclo, periodo);
    }
}
