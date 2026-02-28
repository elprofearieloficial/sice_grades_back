package unpa.service.universidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.materias.MateriaImpartidaData;
import unpa.repository.materias.MateriaImpartidaRepository;

@Service
public class MateriaImpartidaService {

    @Autowired
    private MateriaImpartidaRepository repository;

    public MateriaImpartidaData getMateriaImpartidaData(String idMateria,String idPlan,String idGrupo,String idCiclo,String idPeriodo){
        return repository.findMateriaImpartidaBy(  idMateria,idPlan,idGrupo,idCiclo,idPeriodo);
    }
}
