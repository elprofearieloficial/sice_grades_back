package unpa.service.reportes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.actas.Acta;
import unpa.entity.actas.ActaId;
import unpa.repository.reportes.ActaRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcuseService {

    private final ActaRepository actaRepo;

    public Optional<Acta> obtenerAcuse(String idMateria, String idPlan, String idGrupo,
                                       String idCiclo, String idPeriodo, String tipoExamen) {

        String idAct = getClaveExamen(tipoExamen); // tu lógica interna para obtenerlo

        System.out.println(tipoExamen+ " " + idAct + " " +  idGrupo  + " " + idMateria + " " + idCiclo + " " + idPeriodo + " " + idPlan);
        ActaId actaId = new ActaId(idAct, idGrupo, idMateria, idCiclo, idPeriodo, idPlan);

        return actaRepo.findById(actaId);
    }

    // Simulación del método para traducir tipo de examen a clave
    private String getClaveExamen(String tipoExamen) {
        return switch (tipoExamen) {
            case "1" -> "p1";
            case "2" -> "p2";
            case "3"     -> "f";
            case "4"  -> "esp";
            default          -> "x";
        };
    }
}

