package unpa.service.profesores;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unpa.entity.profesores.SinodalProjection;
import unpa.repository.profesores.SinodalesExtraordinariosRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SinodalService {

    private final SinodalesExtraordinariosRepository sinodalRepo;

    /*public Map<String, Object> obtenerSinodales(String idExamen, String idMateria, String idPlan,
                                                String idGrupo, String idCiclo, Integer idPeriodo) {
        List<SinodalProjection> sinodales = sinodalRepo.findSinodalesByExamen(
                idExamen, idMateria, idPlan, idGrupo, idCiclo, idPeriodo
        );

        Map<String, Object> parametros = new HashMap<>();

        // Nombre de materia y semestre solo lo tomamos del primero
        if (!sinodales.isEmpty()) {
            parametros.put("Nombre_Mat", sinodales.get(0).getNombreMat());
            parametros.put("Semestre_Mat", sinodales.get(0).getSemestreMat());
        }

        // Formateamos hasta 3 sinodales
        for (int i = 0; i < Math.min(3, sinodales.size()); i++) {
            SinodalProjection s = sinodales.get(i);
            String nombre = String.format("%s %s %s %s",
                    s.getSiglasgradomaxTra(),
                    s.getNombreTra(),
                    s.getApellidopTra(),
                    s.getApellidomTra()
            ).trim();
            parametros.put("Profesor" + (i + 1), nombre);  // Ej.: Profesor1, Profesor2, Profesor3
        }

        return parametros;
    }*/


    public List<SinodalProjection> obtenerSinodales(String idExamen, String idMateria, String idPlan,
                                                String idGrupo, String idCiclo, Integer idPeriodo) {
        return sinodalRepo.findSinodalesByExamen(idExamen, idMateria, idPlan, idGrupo, idCiclo, idPeriodo);
    }
}

