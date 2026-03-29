package unpa.controller;


import org.springframework.web.bind.annotation.*;
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

    /*@GetMapping
    public List<AvisoResponse> listarTodos() {
        return service.listarTodos();
    }*/

    @GetMapping
    public List<AvisoResponse> listarAvisosParaAlumnos() {
        return service.listarParaAlumnos(); 
    }


    @PostMapping
    public AvisoResponse crear(@RequestBody AvisoRequest request) {
        return service.guardar(request);
    }

    

}
