package unpa.entity.universidad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodoEscolarId implements Serializable {
    private String idCicFk;
    private String idPer;

}