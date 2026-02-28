package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarioDTO {
    private String materia;
    private String e1; // fecha extra1
    private String e2; // fecha extra2
    private String esp; // fecha especial
    private String f; // fecha final
    private String p1; // fecha parcial 1
    private String p2; // fecha parcial 2
    private String p3; // fecha parcial 3
    private String ciclo;
}
