package unpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import unpa.entity.MateriaImpartidaData;
import unpa.repository.FolioRepository;
import unpa.repository.MateriaImpartidaRepository;

@Service
public class MateriaImpartidaService {

    @Autowired
    private MateriaImpartidaRepository repository;

    public MateriaImpartidaData getMateriaImpartidaData(String idMateria,String idPlan,String idGrupo,String idCiclo,String idPeriodo){
        return repository.findMateriaImpartidaBy(  idMateria,idPlan,idGrupo,idCiclo,idPeriodo);
    }
}
