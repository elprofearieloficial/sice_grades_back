package unpa.entity.reportes;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import unpa.dto.AlumnoConstanciaDTO;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.UniversidadProjection;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;

public class RptHistorialAcademico extends Reporte {
    public RptHistorialAcademico(DataSource dataSource) {
        super(dataSource);
    }

    private void setParametros(UniversidadProjection universidad, CampusProjection campus,String domicilioCampus, AlumnoConstanciaDTO alumno) {
        getParametros().put("Universidad", universidad.getNombre());
        getParametros().put("Campus", campus.getNombreCam());
        getParametros().put("Clavecampus", campus.getCodigopCam());
        getParametros().put("Matricula", alumno.getMatricula());
        getParametros().put("Alumno", alumno.getNombreCompleto());
        getParametros().put("Carrera", alumno.getNombreCarrera());
        getParametros().put("Planestudio", alumno.getPlanEstudio());
        getParametros().put("Domicilio", domicilioCampus);
        getParametros().put("Creditos", String.valueOf(alumno.getTotalCreditos()));

        if (alumno.getPromedioGeneral() >= 10.0) {
            getParametros().put("Promedio", "10");
        } else {
            getParametros().put("Promedio", (alumno.getPromedioGeneral() + "").substring(0, 3));
        }
        String imagePath = getClass().getResource("/reportes/Logo.jpg").toExternalForm();
        getParametros().put("ImagePath", imagePath);


    }





    public File generateTemporaryPDF(InputStream jasperStream,UniversidadProjection universidad, CampusProjection campus,String domicilioCampus,AlumnoConstanciaDTO alumno) {

        try {
            File reportFile;
            JasperPrint jasperPrint;
            setParametros(universidad,campus,domicilioCampus, alumno);
            System.out.println("PARAMETROS" + getParametros());
            if (jasperStream == null) {
                throw new RuntimeException("No se encontró el archivo jasper.");
            }
            if (miConexion == null) {
                System.out.println("ERROR DE CONEXION");
            } else {
                System.out.println("CONEXION exitosa" + miConexion);
            }
            jasperPrint = JasperFillManager.fillReport(jasperStream, getParametros(), miConexion);
            File tempFile = File.createTempFile("output", ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());
            return tempFile;
        } catch (Exception error) {
            error.printStackTrace();

        }
        return null;
    }

    public JasperPrint generateJasperPrint(InputStream jasperStream,UniversidadProjection universidad, CampusProjection campus,String domicilioCampus,AlumnoConstanciaDTO alumno) {
        try {

            setParametros(universidad,campus,domicilioCampus, alumno);
            System.out.println("PARAMETROS" + getParametros());
            if (jasperStream == null) {
                throw new RuntimeException("No se encontró el archivo jasper.");
            }
            if (miConexion == null) {
                System.out.println("ERROR DE CONEXION");
            } else {
                System.out.println("CONEXION exitosa" + miConexion);
            }
            return JasperFillManager.fillReport(jasperStream, getParametros(), miConexion);
        } catch (Exception error) {
            error.printStackTrace();
        }
        return null;
    }



}

