package unpa.dto;

import lombok.Data;

@Data
public class ReporteAccesoSolicitarCodigoRequest {
    private String matricula;
    private String captchaToken;
}
