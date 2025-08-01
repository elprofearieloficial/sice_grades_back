package unpa.entity.reportes;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import unpa.entity.Alumnos.AlumnoConstanciaDTO;
import unpa.entity.Alumnos.ReinscripcionPeriodoProjection;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.PeriodoEscolar;
import unpa.entity.universidad.PeriodoEscolarId;
import unpa.entity.universidad.UniversidadProjection;
import unpa.entity.utils.FechaUtils;
import unpa.entity.utils.SemestreUtils;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RptConstanciaEstudios extends Reporte {
    public RptConstanciaEstudios(DataSource dataSource) {
        super(dataSource);
    }

    private void setParametrosComunes(UniversidadProjection universidad, CampusProjection campus, AlumnoConstanciaDTO alumno) {
        getParametros().put("Universidad", universidad.getNombre());
        getParametros().put("Leyenda", universidad.getLeyenda());
        getParametros().put("IdCampus", campus.getIdCam());
        getParametros().put("JefeEscolares", universidad.getJefeEscolares());
        getParametros().put("SexoJefe", universidad.getGeneroEscolares());
        getParametros().put("Matricula", alumno.getMatricula());
        getParametros().put("Apellidop", alumno.getApellidoPaterno());
        getParametros().put("Apellidom", alumno.getApellidoMaterno());
        getParametros().put("Nombre", alumno.getNombre());
        getParametros().put("Carrera", alumno.getCarrera());
        getParametros().put("Clave", universidad.getClave());
        getParametros().put("fecha", FechaUtils.getDateToText(FechaUtils.getFechaActual()));
    }

    /*
     * Este método coloca los parametros para crear una constancia simple
     */
    private void setParametrosConstancia(AlumnoConstanciaDTO alumno, PeriodoEscolar periodoEscolar) {
        String tmp;
        getParametros().put("Ciclo", periodoEscolar.getIdCicFk());
        getParametros().put("Periodo", periodoEscolar.getIdPer());


        tmp = SemestreUtils.getSemestreTxt(alumno.getUltimoSemestre());
        if (tmp.equalsIgnoreCase("Primero") || tmp.equalsIgnoreCase("Tercero")) {
            getParametros().put("Semestre", tmp.substring(0, tmp.length() - 1));
        } else {
            getParametros().put("Semestre", tmp);
        }
        getParametros().put("Fechainicio_Per", FechaUtils.getFechaFormatoSimple(periodoEscolar.getFechainicio()));
        getParametros().put("Fechafin_Per", FechaUtils.getFechaFormatoSimple(periodoEscolar.getFechafin()));


        InputStream logoStream = getClass().getResourceAsStream("/reportes/Logo.jpg");
        if (logoStream == null) {
            throw new RuntimeException("No se encontró Logo.jpg");
        }

        byte[] bytes = null;
        try {
            bytes = logoStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("Tamaño del logo: " + bytes.length + " bytes");

        getParametros().put("ImagePath", new ByteArrayInputStream(bytes));

    }

    private void setParametrosConstanciaHistorial(AlumnoConstanciaDTO alumno, ReinscripcionPeriodoProjection reinscripcionPeriodoProjection, PeriodoEscolar periodoInicial, PeriodoEscolar periodoFinal) {
        Double promedio = 0.0;
        promedio = alumno.getPromedioGeneral();
        getParametros().put("Primer_Semestre", SemestreUtils.getSemestreTxt(reinscripcionPeriodoProjection.getPrimerSemestreNum()));
        getParametros().put("Ultimo_Semestre", SemestreUtils.getSemestreTxt(reinscripcionPeriodoProjection.getUltimoSemestreNum()));
        getParametros().put("Creditos", alumno.getTotalCreditos() + "");
        if (promedio >= 10.0) {
            getParametros().put("Promedio", "10");
        } else {
            getParametros().put("Promedio", (promedio + "").substring(0, 3));
        }
        getParametros().put("Fechainicio_Per", FechaUtils.getFechaFormatoSimple(periodoInicial.getFechainicio()));
        getParametros().put("Fechafin_Per", FechaUtils.getFechaFormatoSimple(periodoFinal.getFechafin()));
        String imagePath = getClass().getResource("/reportes/Logo.jpg").toExternalForm();
        getParametros().put("ImagePath", imagePath);

    }


    public File generateTemporaryPDFConHistorial(InputStream jasperStream, UniversidadProjection universidad, CampusProjection campus, AlumnoConstanciaDTO alumno, ReinscripcionPeriodoProjection reinscripcionPeriodoProjection, PeriodoEscolar periodoInicial, PeriodoEscolar periodoFinal) {
        try {
            File reportFile;
            JasperPrint jasperPrint;
            setParametrosComunes(universidad, campus, alumno);
            setParametrosConstanciaHistorial(alumno, reinscripcionPeriodoProjection, periodoInicial, periodoFinal);
            //CtrHistorialAcademico historial = new CtrHistorialAcademico();
            //historial.obtenerReporte(alumno);


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
        } catch (Exception i) {
            i.printStackTrace();

        }
        return null;
    }

    public File generateTemporaryPDFSinHistorial(InputStream jasperStream, UniversidadProjection universidad, CampusProjection campus,AlumnoConstanciaDTO alumno, PeriodoEscolar periodoEscolar) {

        try {
            File reportFile;
            JasperPrint jasperPrint;
            setParametrosComunes(universidad, campus, alumno);
            setParametrosConstancia(alumno, periodoEscolar);
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


}

