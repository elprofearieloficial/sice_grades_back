package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unpa.entity.Folio;
import unpa.repository.FolioRepository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class FolioService {

    @Autowired
    private FolioRepository folioRepository;

    /**
     * Devuelve el siguiente folio disponible, actualizando el consecutivo.
     * @param idFolio el ID del tipo de folio.
     * @return el siguiente número de folio o -1 si no existe.
     */
    @Transactional
    public int obtenerSiguienteFolio(String idFolio) {
        Optional<Folio> optionalFolio = folioRepository.findById(idFolio);

        if (optionalFolio.isEmpty()) {
            return -1; // Folio no encontrado
        }

        Folio folio = optionalFolio.get();
        int anioActual = Year.now().getValue();

        if (folio.getAnio() != 0 && folio.getAnio() != anioActual) {
            // Reiniciar folio
            folio.setAnio(anioActual);
            folio.setConsecutivo(1);
        } else {
            // Incrementar folio
            folio.setConsecutivo(folio.getConsecutivo() + 1);
        }

        folioRepository.save(folio);
        return folio.getConsecutivo();
    }

    public List<Folio> obtenerTodos() {
        return folioRepository.findAll();
    }

    public Optional<Folio> buscarPorId(String id) {
        return folioRepository.findById(id);
    }

    public Folio guardar(Folio folio) {
        return folioRepository.save(folio);
    }

    public void eliminarPorId(String id) {
        folioRepository.deleteById(id);
    }

    public boolean existePorId(String id) {
        return folioRepository.existsById(id);
    }
}
