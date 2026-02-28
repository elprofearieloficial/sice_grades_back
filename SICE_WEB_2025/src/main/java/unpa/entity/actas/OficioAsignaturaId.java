package unpa.entity.actas;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OficioAsignaturaId implements Serializable {
    private String idGpo;
    private String idMat;
    private String idPla;
    private String idCic;
    private Integer tipoOfi;
    private String idPer;
}

