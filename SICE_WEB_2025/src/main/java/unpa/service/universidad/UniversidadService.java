package unpa.service.universidad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.universidad.UniversidadProjection;
import unpa.repository.universidad.UniversidadRepository;

@Service
@RequiredArgsConstructor
public class UniversidadService {

    private final UniversidadRepository universidadRepository;

    public UniversidadProjection obtenerDatosUniversidad(String idUniversidad) {
        return universidadRepository.findDatosUniversidad(idUniversidad);
    }
}
