package unpa.service.universidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.universidad.CampusProjection;
import unpa.repository.universidad.UniversidadRepository;

@Service
public class CampusService {

    @Autowired
    private UniversidadRepository repository;


    public CampusProjection obtenerCampus(String idUniversidad, String idCampus) {
        return repository.obtenerCampus(idUniversidad, idCampus);
    }

    public String obtenerDireccionCampus(String idUniversidad, String idCampus) {
        CampusProjection campus = repository.obtenerCampus(idUniversidad, idCampus);
        String direccion = campus.getDomicilioCam() + ", " + campus.getCiudadCam() + ", " + campus.getNombreEdo() + ", C.P. " + campus.getCodigopCam();
        if (campus.getTelefonoCam().equals(campus.getFaxCam())) {
            direccion = direccion + ", Tel/Fax: " + campus.getTelefonoCam();
        } else {
            direccion = direccion + ", Tel.: " + campus.getTelefonoCam() + ", Fax: " + campus.getFaxCam();
        }
        return direccion;
    }
}
