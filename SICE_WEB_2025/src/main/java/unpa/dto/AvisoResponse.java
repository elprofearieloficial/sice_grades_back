package unpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data



public class AvisoResponse {

    private Integer id;          // Id_Avi
    private String cicloId;      // Id_Cic_FK
    private String periodoId;    // Id_Per_FK
    private String aviso;        // Aviso_Avi
    private LocalDate fecha;     // Fecha_Avi
    private Boolean status;      // Status_Avi
    private Integer dirigir;     // Dirigir_Avi

    public AvisoResponse() {
    }

    public AvisoResponse(Integer id, String cicloId, String periodoId, String aviso,
                         LocalDate fecha, Boolean status, Integer dirigir) {
        this.id = id;
        this.cicloId = cicloId;
        this.periodoId = periodoId;
        this.aviso = aviso;
        this.fecha = fecha;
        this.status = status;
        this.dirigir = dirigir;
    }
}


