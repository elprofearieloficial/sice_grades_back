package unpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.Carrera;
import unpa.entity.Materia;
import unpa.entity.MateriaId;
import unpa.entity.PlanEstudios;
import unpa.repository.MateriaRepository;

@Service
@RequiredArgsConstructor
public class MateriaService {

    private final MateriaRepository materiaRepo;

    public String obtenerCarreraDesdeMateria(String idMateria, String idPlan) {
        MateriaId materiaId = new MateriaId(idMateria, idPlan);
        return materiaRepo.findById(materiaId)
                .map(Materia::getPlan)
                .map(PlanEstudios::getCarrera)
                .map(Carrera::getNombre)
                .orElse("Carrera no encontrada");
    }
}

