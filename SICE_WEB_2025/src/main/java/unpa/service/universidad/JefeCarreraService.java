package unpa.service.universidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.universidad.JefeCarreraDTO;
import unpa.repository.universidad.JefeCarreraRepository;

@Service
public class JefeCarreraService {

    @Autowired
    private JefeCarreraRepository repository;

    public JefeCarreraDTO obtenerJefeCarrera(String idCarrera) {
        return repository.findJefeCarreraByIdCarrera(idCarrera);
    }
}