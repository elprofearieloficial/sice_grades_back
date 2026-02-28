package unpa.service.universidad;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.universidad.Carrera;
import unpa.entity.materias.Materia;
import unpa.entity.materias.MateriaId;
import unpa.entity.universidad.PlanEstudios;
import unpa.repository.materias.MateriaRepository;

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

