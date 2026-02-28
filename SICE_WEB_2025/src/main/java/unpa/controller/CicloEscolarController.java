package unpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unpa.entity.universidad.CicloEscolar;
import unpa.service.universidad.CicloEscolarService;

import java.util.List;

@RestController
@RequestMapping("/api/ciclos")
public class CicloEscolarController {

    @Autowired
    private CicloEscolarService service;

    @GetMapping
    public List<CicloEscolar> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CicloEscolar> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CicloEscolar crear(@RequestBody CicloEscolar ciclo) {
        return service.guardar(ciclo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CicloEscolar> actualizar(@PathVariable String id, @RequestBody CicloEscolar ciclo) {
        if (!service.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ciclo.setIdCic(id);
        return ResponseEntity.ok(service.guardar(ciclo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!service.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
