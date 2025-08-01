package unpa.service.reportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unpa.entity.*;
import unpa.entity.reportes.RptConstanciaImparticion;
import unpa.entity.utils.FechaUtils;
import unpa.repository.*;
import unpa.service.MateriaImpartidaService;
import unpa.service.OficioAsignaturaService;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;

@Service
public class RPTDocumentoAsignacionImparticionService {

    @Autowired private DataSource dataSource;
    @Autowired private PeriodoEscolarRepository periodoEscolarRepository;
    @Autowired private JefeCarreraRepository jefeCarreraRepository;
    @Autowired private UniversidadRepository universidadRepository;
    @Autowired private OficioAsignaturaRepository oficioAsignaturaRepository;
    @Autowired private MateriaImpartidaService materiaImpartidaService;
    @Autowired private OficioAsignaturaService oficioAsignaturaService;


    public File generarPDFDoctoAsignacion( String materia,
                                                     String grupo,
                                                     String ciclo,
                                                     String periodoEscolar,
                                                     String cveCarrera,
                                                     String plan) {
        return generarPDF(materia ,grupo, ciclo,periodoEscolar,cveCarrera,plan,2);
    }



    public File generarPDFDoctoImparticion( String materia,
                String grupo,
                String ciclo,
                String periodoEscolar,
                String cveCarrera,
                String plan) {
        return generarPDF(materia ,grupo, ciclo,periodoEscolar,cveCarrera,plan,1);
    }

    public File generarPDF( String materia,
                                                     String grupo,
                                                     String ciclo,
                                                     String periodoEscolar,
                                                     String cveCarrera,
                                                     String plan, int tipoOficio) {
        InputStream jasperStream;
        OficioAsignatura oficioAsignatura;
        try {
            if (tipoOficio==1){
                jasperStream = getClass().getResourceAsStream("/reportes/ConstanciaImparticion.jasper");
            }else {
                jasperStream = getClass().getResourceAsStream("/reportes/ConstanciaAsignacion.jasper");
                System.out.println("DOCUMENTO DE ASIGNACION");
            }
            PeriodoEscolar periodoEscolarObject=periodoEscolarRepository.getReferenceById(new PeriodoEscolarId(periodoEscolar,ciclo));
            OficioAsignaturaId oficioAsignaturaId = new OficioAsignaturaId(grupo, materia, plan, ciclo, tipoOficio, periodoEscolar);
            if (oficioAsignaturaRepository.existsByIdGpoAndIdMatAndIdPlaAndIdCicAndTipoOfiAndIdPer(grupo,materia,plan,ciclo,tipoOficio,periodoEscolar)) {
                oficioAsignatura = oficioAsignaturaRepository.getReferenceById(oficioAsignaturaId);
            }else{
                oficioAsignatura=new OficioAsignatura(grupo,materia,plan,ciclo,tipoOficio,periodoEscolar, "",FechaUtils.getFechaActual());
                oficioAsignaturaService.registrar(oficioAsignatura,cveCarrera,periodoEscolarObject);
                System.out.println("no existe oficio y se crea");
            }

            UniversidadProjection universidad = universidadRepository.findDatosUniversidad("20MSU0020Q");
            CampusProjection campus=universidadRepository.obtenerCampus("20MSU0020Q","20ESU3001N");
            MateriaImpartidaData materiaImpartidaData=materiaImpartidaService.getMateriaImpartidaData(materia,plan,grupo,ciclo,periodoEscolar);
            JefeCarreraDTO jefeCarrera= jefeCarreraRepository.findJefeCarreraByIdCarrera(cveCarrera);
            RptConstanciaImparticion rpt=new RptConstanciaImparticion(grupo, materia, plan, ciclo, periodoEscolar, dataSource);
            if (tipoOficio==1){
                return  rpt.generateTemporaryPDFAsignacion(jasperStream,oficioAsignatura,periodoEscolarObject,jefeCarrera,campus,universidad, materiaImpartidaData);
            }else{
                return  rpt.generateTemporaryPDFImparticion(jasperStream,oficioAsignatura,periodoEscolarObject,jefeCarrera,campus,universidad, materiaImpartidaData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
