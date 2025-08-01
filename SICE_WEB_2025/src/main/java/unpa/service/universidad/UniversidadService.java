package unpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.UniversidadProjection;
import unpa.repository.UniversidadRepository;

@Service
@RequiredArgsConstructor
public class UniversidadService {

    private final UniversidadRepository universidadRepository;

    public UniversidadProjection obtenerDatosUniversidad(String idUniversidad) {
        return universidadRepository.findDatosUniversidad(idUniversidad);
    }
}
