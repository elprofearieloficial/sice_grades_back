package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalificacionDTO {
    private Double parcial1;
    private Double parcial2;
    private Double parcial3;
    private Double ordinario;
    private Double pFinal;
    private Double extra1;
    private Double extra2;
    private Double especial;
}