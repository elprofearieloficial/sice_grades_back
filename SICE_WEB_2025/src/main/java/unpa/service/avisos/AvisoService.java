package unpa.service.avisos;

import org.springframework.stereotype.Service;
import unpa.dto.AvisoRequest;
import unpa.dto.AvisoResponse;
import unpa.entity.avisos.Aviso;
import unpa.entity.avisos.AvisoId;
import unpa.entity.avisos.AvisoMapper;
import unpa.repository.avisos.AvisoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvisoService {

    private final AvisoRepository repository;

    public AvisoService(AvisoRepository repository) {
        this.repository = repository;
    }

    public List<AvisoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(AvisoMapper::toResponse)
                .toList();
    }

    public Optional<AvisoResponse> buscarPorId(AvisoId id) {
        return repository.findById(id)
                .map(AvisoMapper::toResponse);
    }

    public AvisoResponse guardar(AvisoRequest request) {
        Aviso aviso = AvisoMapper.toEntity(request);
        Aviso saved = repository.save(aviso);
        return AvisoMapper.toResponse(saved);
    }

    public AvisoResponse actualizar(AvisoId id, AvisoRequest request) {
        Aviso aviso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso no encontrado"));

        // actualizar los campos
        aviso.setAviso(request.getAviso());
        aviso.setFecha(request.getFecha());
        aviso.setStatus(request.getStatus());
        aviso.setDirigir(request.getDirigir());

        Aviso actualizado = repository.save(aviso);
        return AvisoMapper.toResponse(actualizado);
    }

    public void eliminar(AvisoId id) {
        repository.deleteById(id);
    }
}