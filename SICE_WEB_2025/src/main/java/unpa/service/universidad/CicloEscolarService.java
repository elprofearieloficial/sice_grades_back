package unpa.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.CicloEscolar;
import unpa.repository.CicloEscolarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CicloEscolarService {

    @Autowired
    private CicloEscolarRepository repository;

    public List<CicloEscolar> obtenerTodos() {
        return repository.findAll();
    }

    public Optional<CicloEscolar> obtenerPorId(String idCic) {
        return repository.findById(idCic);
    }

    public CicloEscolar guardar(CicloEscolar ciclo) {
        return repository.save(ciclo);
    }

    public void eliminar(String idCic) {
        repository.deleteById(idCic);
    }
}

