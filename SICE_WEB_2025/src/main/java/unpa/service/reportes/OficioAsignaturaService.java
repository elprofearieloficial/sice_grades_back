package unpa.service.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.actas.OficioAsignatura;
import unpa.entity.actas.OficioAsignaturaId;
import unpa.entity.universidad.PeriodoEscolar;
import unpa.repository.utils.FolioRepository;
import unpa.repository.reportes.OficioAsignaturaRepository;
import unpa.service.utils.FolioService;

import java.time.Year;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class OficioAsignaturaService {

    @Autowired
    private OficioAsignaturaRepository repository;

    @Autowired
    FolioRepository folioRepository;

    @Autowired
    FolioService folioService;


    public OficioAsignatura registrar(OficioAsignatura oficio, String claveCarrera, PeriodoEscolar periodo) {
        StringTokenizer token;
        String idFolio;
        String aux ;
        token = new StringTokenizer(obtenerFolioCarreraOficio(claveCarrera), "-");
        aux = "" + folioService.obtenerSiguienteFolio(obtenerFolioCarreraOficio(claveCarrera));
        aux = switch (aux.length()) {
            case 1 -> "000" + aux;
            case 2 -> "00" + aux;
            case 3 -> "0" + aux;
            default -> aux;
        };
        token.nextToken();
        idFolio = "JC-" + token.nextToken() + "-" + aux + "-" + Year.now().getValue() % 100 + "A";
        oficio.setIdOfi(idFolio);
        return repository.save(oficio);
    }

    public OficioAsignatura actualizar(OficioAsignatura oficio) {
        return repository.save(oficio);
    }

    private String obtenerFolioCarreraOficio(String idCarrera) {
        //zootecnia
        if (idCarrera.equalsIgnoreCase("5AC01008")) return "A-LZ";
        //acuicultura
        if (idCarrera.equalsIgnoreCase("5AD04007")) return "A-IA";
        //enfermería
        if (idCarrera.equalsIgnoreCase("5BA01906")) return "";
        if (idCarrera.equalsIgnoreCase("5CG03001"))//matematicas
            return "A-LMA";
        if (idCarrera.equalsIgnoreCase("5CI01001"))//química
            return "";
        if (idCarrera.equalsIgnoreCase("5DA0500H"))//empresariales
            return "";
        if (idCarrera.equalsIgnoreCase("5FA02009"))//diseño
          return "A-ID";
        if (idCarrera.equalsIgnoreCase("5FB02906"))//biotecnología
            return "";
        if (idCarrera.equalsIgnoreCase("5FC02002"))//computación
          return "A-IC";
        if (idCarrera.equalsIgnoreCase("5FD10027"))//mecatrónica
            return "A-IM";
        if (idCarrera.equalsIgnoreCase("5FD23005"))//alimnetos
            return "";
         //agricola tropical
        if (idCarrera.equalsIgnoreCase("5FD26928")) return "A-IAT";
        return "";

    }

    public Optional<OficioAsignatura> buscarPorId(OficioAsignaturaId id) {
        return repository.findById(id);
    }

    public boolean existe(OficioAsignaturaId id) {
        return repository.existsById(id);
    }

    public void eliminar(OficioAsignaturaId id) {
        repository.deleteById(id);
    }
}

