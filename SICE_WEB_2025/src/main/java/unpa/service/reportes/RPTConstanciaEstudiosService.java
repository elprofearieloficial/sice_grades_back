package unpa.service.reportes;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.dto.AlumnoConstanciaDTO;
import unpa.entity.Alumnos.ReinscripcionPeriodoProjection;
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
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RPTHistorialAcademicoService  rptHistorialAcademicoService;

    public File generarConstanciaSinHistorial(String matricula) {
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

    public File generarConstanciaConHistorialAcademico(String matricula) {
        try {
            JasperPrint jasperPrint1 =this.generarJasperPrintConstanciaConHistorial(matricula);
            JasperPrint jasperPrint2 = rptHistorialAcademicoService.generarJasperPrint(matricula);
            List<JasperPrint> reportes = new ArrayList<>();
            reportes.add(jasperPrint1);
            reportes.add(jasperPrint2);
            File archivoTemporal = File.createTempFile("constanciaconhistorial", ".pdf");

            // Configurar exportador
            JRPdfExporter exportador = new JRPdfExporter();
            exportador.setExporterInput(SimpleExporterInput.getInstance(reportes));
            exportador.setExporterOutput(new SimpleOutputStreamExporterOutput(archivoTemporal));

            SimplePdfExporterConfiguration configuracion = new SimplePdfExporterConfiguration();
            exportador.setConfiguration(configuracion);

            // Exportar
            exportador.exportReport();

            return archivoTemporal;
        }catch (Exception error){
            error.printStackTrace();
            return null;
        }
    }

    private JasperPrint generarJasperPrintConstanciaConHistorial(String matricula) {
        ReinscripcionPeriodoProjection reinscripcionPeriodoProjection = alumnoService.obtenerReinscripcionPeriodo(matricula);
        PeriodoEscolarId inicial = new PeriodoEscolarId(reinscripcionPeriodoProjection.getPrimerCiclo(), reinscripcionPeriodoProjection.getPrimerPeriodo());
        PeriodoEscolarId fin = new PeriodoEscolarId(reinscripcionPeriodoProjection.getUltimoCiclo(), reinscripcionPeriodoProjection.getUltimoPeriodo());
        PeriodoEscolar periodoEscolarInicial = periodoEscolarRepository.getReferenceById(inicial);
        PeriodoEscolar periodoEscolarFinal = periodoEscolarRepository.getReferenceById(fin);
        InputStream jasperStream;
        try {
            jasperStream = getClass().getResourceAsStream("/reportes/constanciahistorial.jasper");
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus = universidadRepository.obtenerCampus("20MSU0020Q", "20ESU3001N");
            AlumnoConstanciaDTO alumno = alumnoService.obtenerDatosConstancia(matricula);
            RptConstanciaEstudios rpt = new RptConstanciaEstudios(dataSource);
            return rpt.generateJasperPrintConstanciaConHistorial(jasperStream, universidad, campus, alumno, reinscripcionPeriodoProjection, periodoEscolarInicial, periodoEscolarFinal);
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

}
