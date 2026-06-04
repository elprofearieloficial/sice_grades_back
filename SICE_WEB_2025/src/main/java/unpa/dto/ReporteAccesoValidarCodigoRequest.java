package unpa.dto;

import lombok.Data;

@Data
public class ReporteAccesoValidarCodigoRequest {
    private String matricula;
    private String codigo;
    private String detalle;
}
