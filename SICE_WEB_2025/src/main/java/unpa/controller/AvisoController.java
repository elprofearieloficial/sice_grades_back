package unpa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpa.dto.AvisoLeidoRequest;
import unpa.dto.AvisoRequest;
import unpa.dto.AvisoResponse;
import unpa.service.avisos.AvisoService;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class AvisoController {

    private final AvisoService service;

    public AvisoController(AvisoService service) {
        this.service = service;
    }

    @GetMapping
    public List<AvisoResponse> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/no-leidos")
    public List<AvisoResponse> listarNoLeidos(@RequestParam String matricula) {
        return service.listarNoLeidosPorMatricula(matricula);
    }

    @PostMapping
    public AvisoResponse crear(@RequestBody AvisoRequest request) {
        return service.guardar(request);
    }

    @PostMapping("/leer")
    public ResponseEntity<Void> marcarComoLeido(
            @RequestParam String matricula,
            @RequestBody AvisoLeidoRequest request
    ) {
        service.marcarComoLeido(matricula, request);
        return ResponseEntity.ok().build();
    }
}
