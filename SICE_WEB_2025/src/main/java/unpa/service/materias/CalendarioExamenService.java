package unpa.service.materias;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import unpa.dto.CalendarioDTO;
import unpa.entity.materias.CalendarioExamenResponse;
import unpa.repository.materias.CalendarioExamenRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarioExamenService {

    @Autowired
    private CalendarioExamenRepository repository;

    public List<CalendarioDTO> obtenerCalendarioExamenesByCicloAndPeriodo(
            String alumnoId,
            String ciclo,
            String periodo) {

        return repository.findCalendarioExamenesByCicloAndPeriodo(alumnoId, ciclo, periodo);
    }

    public List<CalendarioDTO> obtenerCalendarioExamenes(
            String alumnoId) {

        return repository.findCalendarioExamenes(alumnoId);
    }
}



