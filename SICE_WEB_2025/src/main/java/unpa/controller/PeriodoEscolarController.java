package unpa.controller;

import org.springframework.web.bind.annotation.*;
import unpa.entity.PeriodoEscolar;
import unpa.service.PeriodoEscolarService;

import java.util.List;

@RestController
@RequestMapping("/periodos")
public class PeriodoEscolarController {

    private final PeriodoEscolarService service;

    public PeriodoEscolarController(PeriodoEscolarService service) {
        this.service = service;
    }

    @GetMapping
    public List<PeriodoEscolar> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{idCiclo}")
    public List<PeriodoEscolar> buscarPorCiclo(@PathVariable String idCiclo) {
        return service.buscarPorCiclo(idCiclo);
    }

    @GetMapping("/{idCiclo}/{idPeriodo}")
    public PeriodoEscolar buscarPorClave(@PathVariable String idCiclo, @PathVariable String idPeriodo) {
        return service.buscarPorClave(idPeriodo, idCiclo);
    }
}
