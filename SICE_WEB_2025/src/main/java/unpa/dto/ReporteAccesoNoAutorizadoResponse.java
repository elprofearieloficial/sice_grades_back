package unpa.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReporteAccesoNoAutorizadoResponse {
    private Long id;
    private String matricula;
    private LocalDateTime fechaReporte;
    private Short estatus;
    private String detalle;
    private String bloqueadaPorUsuario;
    private LocalDateTime fechaBloqueo;
    private String observacionBloqueo;
    private String reinicioPorUsuario;
    private LocalDateTime fechaReinicio;
    private String observacionReinicio;
}
