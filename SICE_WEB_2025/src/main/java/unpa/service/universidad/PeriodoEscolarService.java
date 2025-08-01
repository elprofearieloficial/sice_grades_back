package unpa.service;

import org.springframework.stereotype.Service;
import unpa.entity.PeriodoEscolar;
import unpa.entity.PeriodoEscolarId;
import unpa.repository.PeriodoEscolarRepository;

import java.util.List;

@Service
public class PeriodoEscolarService {

    private final PeriodoEscolarRepository repository;

    public PeriodoEscolarService(PeriodoEscolarRepository repository) {
        this.repository = repository;
    }

    public List<PeriodoEscolar> listarTodos() {
        return repository.findAll();
    }

    public List<PeriodoEscolar> buscarPorCiclo(String idCiclo) {
        return repository.findByIdCicFk(idCiclo);
    }

    public PeriodoEscolar buscarPorClave(String idPer, String idCiclo) {
        return repository.findById(new PeriodoEscolarId(idPer, idCiclo)).orElse(null);
    }
}
