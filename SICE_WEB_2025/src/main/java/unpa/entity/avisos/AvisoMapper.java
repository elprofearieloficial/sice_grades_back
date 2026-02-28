package unpa.entity.avisos;

import unpa.dto.AvisoRequest;
import unpa.dto.AvisoResponse;

public class AvisoMapper {

    public static AvisoResponse toResponse(Aviso aviso) {
        return new AvisoResponse(
                aviso.getId().getId(),
                aviso.getId().getCicloId(),
                aviso.getId().getPeriodoId(),
                aviso.getAviso(),
                aviso.getFecha(),
                aviso.getStatus(),
                aviso.getDirigir()
        );
    }

    public static Aviso toEntity(AvisoRequest request) {
        Aviso aviso = new Aviso();
        aviso.setId(new AvisoId(
                request.getIdAvi(),
                request.getCicloId(),
                request.getPeriodoId()
        ));
        aviso.setAviso(request.getAviso());
        aviso.setFecha(request.getFecha());
        aviso.setStatus(request.getStatus());
        aviso.setDirigir(request.getDirigir());
        return aviso;
    }
}


