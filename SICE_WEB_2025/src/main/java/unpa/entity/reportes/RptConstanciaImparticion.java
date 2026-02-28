package unpa.entity.reportes;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import unpa.entity.actas.OficioAsignatura;
import unpa.entity.materias.MateriaImpartidaData;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.JefeCarreraDTO;
import unpa.entity.universidad.PeriodoEscolar;
import unpa.entity.universidad.UniversidadProjection;
import unpa.entity.utils.FechaUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;

/**
 * Esta clase permite generar un oficio y/o constancia para los profesores que
 * imparten una determinada materia.<br>
 * Fecha de creación: 27 de Abril del 2011. <br>
 * Última modificación: 17 de Mayo del 2011. <br>
 *
 * @author José Antonio Cervantes A. E-mail: jcervantes@unpa.edu.mx
 * @version 1.5
 */
public class RptConstanciaImparticion extends Reporte {



    private String archivoPDF ;


    String fecha_aplico = "";
    String fecha_recibo = "";
    String  clave_acuse = "";
    String hora_acuse = "";
    private String idMateria;
    private String idPlan;
    private String idGrupo;
    private String ciclo;
    private String periodo;
    private int tipoExamen;

    public RptConstanciaImparticion(String idGrupo, String idMateria, String idPlan, String ciclo, String periodo, DataSource dataSource) {
        super(dataSource);
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idPlan = idPlan;
        this.ciclo = ciclo;
        this.periodo = periodo;
    }

    public File generateTemporaryPDFImparticion(InputStream jasperStream, OficioAsignatura oficio, PeriodoEscolar periodo, JefeCarreraDTO jefeCarreraDTO, CampusProjection encabezado, UniversidadProjection universidad, MateriaImpartidaData materiaImpartidaData) {
        return generateTemporaryPDF(jasperStream, oficio, periodo, jefeCarreraDTO, encabezado, universidad, materiaImpartidaData,2);
    }




    public File generateTemporaryPDFAsignacion(InputStream jasperStream, OficioAsignatura oficio, PeriodoEscolar periodo, JefeCarreraDTO jefeCarreraDTO, CampusProjection encabezado, UniversidadProjection universidad, MateriaImpartidaData materiaImpartidaData) {
        return generateTemporaryPDF(jasperStream, oficio, periodo, jefeCarreraDTO, encabezado, universidad, materiaImpartidaData,1);
    }

    private File generateTemporaryPDF(InputStream jasperStream, OficioAsignatura oficio, PeriodoEscolar periodo, JefeCarreraDTO jefeCarreraDTO, CampusProjection encabezado, UniversidadProjection universidad, MateriaImpartidaData materiaImpartidaData, int tipoReporte) {
        try {
            File reportFile;
            JasperPrint jasperPrint;
            setParametros(oficio,periodo,tipoReporte,jefeCarreraDTO,encabezado,universidad, materiaImpartidaData);
            System.out.println("PARAMETROS" + getParametros());
            jasperPrint = JasperFillManager.fillReport(jasperStream , getParametros(), miConexion);
            File tempFile = File.createTempFile("output", ".pdf");

            JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

            return tempFile;
        } catch (Exception i) {
            i.printStackTrace();

        } finally {
            //this.desconectar();
        }
        return null;
    }

    private void setParametros(OficioAsignatura oficio, PeriodoEscolar periodo, int tipoReporte, JefeCarreraDTO jefeCarreraDTO, CampusProjection campus, UniversidadProjection universidad, MateriaImpartidaData materiaImpartidaData) {
        String jefeCarrera;
        jefeCarrera = jefeCarreraDTO.getNombre() + " " + jefeCarreraDTO.getApellidop() + " " + jefeCarreraDTO.getApellidom();

        if(oficio!=null){
            getParametros().put("Fecha", FechaUtils.getFechaFormatoSimple(oficio.getFechaOfi()));
        }
        getParametros().put("Universidad", universidad.getNombre());
        getParametros().put("Campus", campus.getNombreCam());
        getParametros().put("ClaveUni", campus.getIdCam());
        getParametros().put("Lugar", campus.getCiudadCam() + ", " + campus.getNombreEdo());
        getParametros().put("JefeCarrera", jefeCarrera);

        if (jefeCarreraDTO.getClaveTrabajador().equals(  materiaImpartidaData.getIdProfesor())) {
            getParametros().put("MostrarVicerector", true);
        } else {
            getParametros().put("MostrarVicerector", false);
        }
        getParametros().put("SexoJefe", jefeCarreraDTO.getSexo());
        getParametros().put("Id_Car", materiaImpartidaData.getIdCarrera());
        getParametros().put("Ciclo", periodo.getIdCicFk());
        getParametros().put("Periodo", periodo.getIdPer());
        getParametros().put("Id_Pro", materiaImpartidaData.getIdProfesor());
        getParametros().put("Id_Mat", materiaImpartidaData.getIdMateria());
        getParametros().put("Id_Pla", materiaImpartidaData.getIdPlan());
        getParametros().put("Id_Gpo", materiaImpartidaData.getIdGrupo());
        getParametros().put("FechaInicio", FechaUtils.getFechaFormatoSimple(periodo.getFechainicio()));
        getParametros().put("FechaFin", FechaUtils.getFechaFormatoSimple(periodo.getFechafin()));
        getParametros().put("ViceAcad", universidad.getViceAcademico());
        getParametros().put("SexoVice", universidad.getGeneroAcademico());
        getParametros().put("JefeEsc", universidad.getJefeEscolares());
        getParametros().put("Leyenda", universidad.getLeyenda());
        getParametros().put("Nombre_Uni", universidad.getNombre());
        String direccionUni = campus.getCodigopCam();
        direccionUni = direccionUni + ", " + campus.getCiudadCam();
        direccionUni = direccionUni + ", " + campus.getNombreEdo();
        direccionUni = direccionUni + ", C.P. " + campus.getCodigopCam();
        direccionUni = direccionUni + ", Tel/Fax: " + campus.getFaxCam();
        direccionUni = direccionUni + ", Tel: " + campus.getTelefonoCam();
        getParametros().put("Domicilio", direccionUni);
    }



    private String getArchivoPDF() {
        return archivoPDF;
    }



    private boolean existePDF(String archivo) {
        try {
            File myArchivo = new File(archivo);
            return (myArchivo.exists());
        } catch (Exception i) {
            return false;
        }
    }

    private int getTotalAlumnos() {
        /*String sql = new String();
        ResultSet registro;
        int total = 0;
        try {
            sql = "SELECT COUNT(Id_Alu_FK) as total FROM grupos_alumnos WHERE Id_Gpo_FK='" + materia.getGrupo() + "' AND Id_Mat_FK='" + materia.getClave() + "' AND Id_Pla_FK='" + materia.getPlan() + "' "
                    + "AND Id_Cic_FK='" + periodo.getCiclo() + "' AND Id_Per_FK='" + periodo.getPeriodo() + "';";
            registro = getBd().ejecutarSelect(sql);
            registro.first();
            total = registro.getInt("total");
        } catch (SQLException ex) {
            ErrorSE.guardar(1, "el módulo getTotalAlumnos de CtrOficiosConstanciasCursos", "Error al consultar la base de datos", "José Antonio", ex);
        }
        return total;*/
        return 5;
    }
}
