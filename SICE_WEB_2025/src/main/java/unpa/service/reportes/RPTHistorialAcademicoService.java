package unpa.service.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.Alumnos.AlumnoConstanciaDTO;
import unpa.entity.Alumnos.ReinscripcionPeriodoProjection;
import unpa.entity.actas.OficioAsignatura;
import unpa.entity.reportes.RptConstanciaEstudios;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.PeriodoEscolar;
import unpa.entity.universidad.PeriodoEscolarId;
import unpa.entity.universidad.UniversidadProjection;
import unpa.repository.universidad.PeriodoEscolarRepository;
import unpa.repository.universidad.UniversidadRepository;
import unpa.service.alumnos.AlumnoService;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;

@Service
public class RPTConstanciaEstudiosService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private UniversidadRepository universidadRepository;
    @Autowired
    private PeriodoEscolarRepository periodoEscolarRepository;


    public File generarPDF(String matricula, int tipoConstancia) {

        if (tipoConstancia == 1) {
            return generarPDFSinHistorial(matricula);
        }else {
            return generarPDFConHistorial(matricula);
        }
    }


    public File generarPDFSinHistorial(String matricula) {
        InputStream jasperStream;
        try {
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus = universidadRepository.obtenerCampus("20MSU0020Q", "20ESU3001N");
            AlumnoConstanciaDTO alumno = alumnoService.obtenerDatosConstancia(matricula);
            RptConstanciaEstudios rpt = new RptConstanciaEstudios(dataSource);
            PeriodoEscolar periodoEscolarObject = periodoEscolarRepository.findUltimoCicloEscolar();
            jasperStream = getClass().getResourceAsStream("/reportes/constanciaestudios.jasper");
            System.out.println("CONSTANCIA de estudios");
            return rpt.generateTemporaryPDFSinHistorial(jasperStream, universidad, campus, alumno, periodoEscolarObject);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    public File generarPDFConHistorial(String matricula) {

        ReinscripcionPeriodoProjection reinscripcionPeriodoProjection = alumnoService.obtenerReinscripcionPeriodo(matricula);
        PeriodoEscolarId inicial = new PeriodoEscolarId(reinscripcionPeriodoProjection.getPrimerCiclo(), reinscripcionPeriodoProjection.getPrimerPeriodo());
        PeriodoEscolarId fin = new PeriodoEscolarId(reinscripcionPeriodoProjection.getUltimoCiclo(), reinscripcionPeriodoProjection.getUltimoPeriodo());
        PeriodoEscolar periodoEscolarInicial = periodoEscolarRepository.getReferenceById(inicial);
        PeriodoEscolar periodoEscolarFinal = periodoEscolarRepository.getReferenceById(fin);
        InputStream jasperStream;
        try {
            jasperStream = getClass().getResourceAsStream("/reportes/constanciahistorial.jasper");
            System.out.println("CONSTANCIA CON HISTORIAL");
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus = universidadRepository.obtenerCampus("20MSU0020Q", "20ESU3001N");
            AlumnoConstanciaDTO alumno = alumnoService.obtenerDatosConstancia(matricula);
            RptConstanciaEstudios rpt = new RptConstanciaEstudios(dataSource);
            System.out.println("CONSTANCIA CON HISTORIAL");
            return rpt.generateTemporaryPDFConHistorial(jasperStream, universidad, campus, alumno, reinscripcionPeriodoProjection, periodoEscolarInicial, periodoEscolarFinal);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

}
