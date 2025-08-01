package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.JefeCarreraDTO;
import unpa.repository.JefeCarreraRepository;

@Service
public class JefeCarreraService {

    @Autowired
    private JefeCarreraRepository repository;

    public JefeCarreraDTO obtenerJefeCarrera(String idCarrera) {
        return repository.findJefeCarreraByIdCarrera(idCarrera);
    }
}