package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.CampusProjection;
import unpa.repository.EncabezadoReporteRepository;
import unpa.repository.UniversidadRepository;

@Service
public class CampusService {

    @Autowired
    private UniversidadRepository repository;



    public CampusProjection obtenerDatosEncabezado(String idUniversidad, String idCampus) {
        return  repository.obtenerCampus(idUniversidad,idCampus);
    }
}
