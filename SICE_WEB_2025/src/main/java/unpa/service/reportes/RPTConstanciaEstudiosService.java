package unpa.service.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.*;
import unpa.entity.Alumnos.AlumnoConstanciaDTO;
import unpa.entity.reportes.RptConstanciaEstudios;
import unpa.entity.reportes.RptConstanciaImparticion;
import unpa.entity.utils.FechaUtils;
import unpa.repository.PeriodoEscolarRepository;
import unpa.repository.UniversidadRepository;
import unpa.service.AlumnoService;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
@Service
public class RPTConstanciaEstudiosService {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AlumnoService alumnoService;
    @Autowired private UniversidadRepository universidadRepository;
    @Autowired private PeriodoEscolarRepository periodoEscolarRepository;


    public File generarPDF(String matricula, int tipoConstancia) {
        InputStream jasperStream;
        OficioAsignatura oficioAsignatura;
        try {
            if (tipoConstancia==1){
                jasperStream = getClass().getResourceAsStream("/reportes/constanciaestudios.jasper");
                System.out.println("CONSTANCIA de estudios");
            }else {
                jasperStream = getClass().getResourceAsStream("/reportes/constanciahistorial.jasper");
                System.out.println("CONSTANCIA CON HISTORIAL");
            }
            PeriodoEscolar periodoEscolarObject=periodoEscolarRepository.findUltimoCicloEscolar();
            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus=universidadRepository.obtenerCampus("20MSU0020Q","20ESU3001N");
            AlumnoConstanciaDTO alumno= alumnoService.obtenerDatosConstancia(matricula);
            RptConstanciaEstudios rpt=new RptConstanciaEstudios(dataSource);
            return  rpt.generateTemporaryPDF(jasperStream,alumno,  periodoEscolarObject,universidad, campus, tipoConstancia);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
