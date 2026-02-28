package unpa.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

public class AvisoRequest {

    private Integer idAvi;       // Id_Avi (puede ser null para POST)
    private String cicloId;      // Id_Cic_FK
    private String periodoId;    // Id_Per_FK
    private String aviso;        // Aviso_Avi
    private LocalDate fecha;     // Fecha_Avi
    private Boolean status;      // Status_Avi
    private Integer dirigir;     // Dirigir_Avi

    public AvisoRequest() {}

    public AvisoRequest(Integer idAvi, String cicloId, String periodoId, String aviso,
                        LocalDate fecha, Boolean status, Integer dirigir) {
        this.idAvi = idAvi;
        this.cicloId = cicloId;
        this.periodoId = periodoId;
        this.aviso = aviso;
        this.fecha = fecha;
        this.status = status;
        this.dirigir = dirigir;
    }



}

