package unpa.service.reportes;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.dto.AlumnoConstanciaDTO;
import unpa.entity.reportes.RptHistorialAcademico;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.UniversidadProjection;
import unpa.repository.universidad.UniversidadRepository;
import unpa.service.alumnos.AlumnoService;
import unpa.service.universidad.CampusService;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;

@Service
public class RPTHistorialAcademicoService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private UniversidadRepository universidadRepository;

    @Autowired
    private CampusService campusService;

    public File generarPDF(String matricula) {
        InputStream jasperStream;
        try {
            // 1. Obtenemos datos (esto es rápido y no usa la conexión directa de JDBC)
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus = universidadRepository.obtenerCampus("20MSU0020Q", "20ESU3001N");
            AlumnoConstanciaDTO alumno = alumnoService.obtenerDatosConstancia(matricula);
            String domicilioCampus = campusService.obtenerDireccionCampus("20MSU0020Q", "20ESU3001N");

            // 2. AQUÍ ESTÁ EL ARREGLO: 'try (...)'
            // Esto asegura que rpt.close() se ejecute automáticamente al llegar a la llave de cierre '}'
            try (RptHistorialAcademico rpt = new RptHistorialAcademico(dataSource)) {

                jasperStream = getClass().getResourceAsStream("/reportes/historial.jasper");
                System.out.println("Historial Academico");

                // Generamos y retornamos
                return rpt.generateTemporaryPDF(jasperStream, universidad, campus, domicilioCampus, alumno);
            }
            // 3. ¡Aquí la conexión ya se cerró sola y volvió al pool!

        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    public JasperPrint generarJasperPrint(String matricula) {
        InputStream jasperStream;
        try {
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus = universidadRepository.obtenerCampus("20MSU0020Q", "20ESU3001N");
            AlumnoConstanciaDTO alumno = alumnoService.obtenerDatosConstancia(matricula);
            String domicilioCampus = campusService.obtenerDireccionCampus("20MSU0020Q", "20ESU3001N");

            // 2. APLICAMOS EL MISMO ARREGLO AQUÍ
            try (RptHistorialAcademico rpt = new RptHistorialAcademico(dataSource)) {

                jasperStream = getClass().getResourceAsStream("/reportes/historial.jasper");
                System.out.println("Historial Academico");

                return rpt.generateJasperPrint(jasperStream, universidad, campus, domicilioCampus, alumno);
            }
            // 3. Conexión liberada

        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }
}