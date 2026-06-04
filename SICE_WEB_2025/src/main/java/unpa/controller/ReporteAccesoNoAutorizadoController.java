package unpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unpa.dto.ReporteAccesoNoAutorizadoAccionRequest;
import unpa.dto.ReporteAccesoNoAutorizadoCreateRequest;
import unpa.dto.ReporteAccesoNoAutorizadoResponse;
import unpa.dto.ReporteAccesoSolicitarCodigoRequest;
import unpa.dto.ReporteAccesoValidarCodigoRequest;
import unpa.service.seguridad.ReporteAccesoNoAutorizadoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seguridad/reportes-acceso-no-autorizado")
public class ReporteAccesoNoAutorizadoController {

    private final ReporteAccesoNoAutorizadoService service;

    public ReporteAccesoNoAutorizadoController(ReporteAccesoNoAutorizadoService service) {
        this.service = service;
    }

    @PostMapping("/solicitar-codigo")
    public ResponseEntity<?> solicitarCodigo(@RequestBody ReporteAccesoSolicitarCodigoRequest request) {
        try {
            service.solicitarCodigoVerificacion(request);
            return ResponseEntity.ok(Map.of(
                    "message", "Codigo enviado al correo registrado",
                    "captchaMode", "temporal-local-checkbox"
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody ReporteAccesoValidarCodigoRequest request) {
        try {
            ReporteAccesoNoAutorizadoResponse response = service.crearReporteConCodigo(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Tu reporte fue enviado a servicios escolares. Tu cuenta sera bloqueada al validar la solicitud.",
                    "reporte", response
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReporteAccesoNoAutorizadoCreateRequest request) {
        try {
            ReporteAccesoNoAutorizadoResponse response = service.crearReporte(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) Short estatus) {
        try {
            List<ReporteAccesoNoAutorizadoResponse> response = service.listarReportes(estatus);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "No se pudieron consultar los reportes"));
        }
    }

    @PutMapping("/{id}/bloquear")
    public ResponseEntity<?> bloquearCuenta(
            @PathVariable("id") Long id,
            @RequestBody(required = false) ReporteAccesoNoAutorizadoAccionRequest request,
            Authentication authentication
    ) {
        try {
            String usuarioAccion = authentication == null ? null : authentication.getName();
            ReporteAccesoNoAutorizadoResponse response = service.bloquearCuenta(
                    id,
                    usuarioAccion,
                    request == null ? new ReporteAccesoNoAutorizadoAccionRequest() : request
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
        }
    }

    @PutMapping({"/{id}/reiniciar-cuenta", "/{id}/reiniciar-password"})
    public ResponseEntity<?> reiniciarPassword(
            @PathVariable("id") Long id,
            @RequestBody(required = false) ReporteAccesoNoAutorizadoAccionRequest request,
            Authentication authentication
    ) {
        try {
            String usuarioAccion = authentication == null ? null : authentication.getName();
            ReporteAccesoNoAutorizadoResponse response = service.reiniciarPassword(
                    id,
                    usuarioAccion,
                    request == null ? new ReporteAccesoNoAutorizadoAccionRequest() : request
            );
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", ex.getMessage()));
        }
    }
}
