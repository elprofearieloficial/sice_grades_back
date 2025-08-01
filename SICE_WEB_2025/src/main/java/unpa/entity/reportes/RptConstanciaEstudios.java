package unpa.entity.reportes;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import unpa.entity.*;
import unpa.entity.Alumnos.AlumnoConstanciaDTO;
import unpa.entity.Alumnos.AlumnoMatriculado;
import unpa.entity.utils.FechaUtils;
import unpa.entity.utils.SemestreUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;

public class RptConstanciaEstudios extends Reporte {
    public RptConstanciaEstudios(DataSource dataSource) {
        super(dataSource);
    }


    public boolean obtenerReporte(AlumnoConstanciaDTO alumno, PeriodoEscolar periodoEscolar, UniversidadProjection universidad, CampusProjection campus, int tipoConstancia) {
        try {
            setParametrosComunes(alumno, universidad, campus);
            switch (tipoConstancia) {
                case 1:
                    setParametrosConstancia(alumno, periodoEscolar);
                    //jasperStream = getClass().getResourceAsStream("/reportes/constanciaestudios.jasper");
                    break;
                default:
                    setParametrosConstanciaHistorial(alumno);
                    //jasperStream = getClass().getResourceAsStream("/reportes/constanciahistorial.jasper");
                    //CtrHistorialAcademico historial = new CtrHistorialAcademico();
                    //historial.obtenerReporte(alumno);
                    break;
            }


        } catch (Exception j) {

        }
        return false;
    }

    private void setParametrosComunes(AlumnoConstanciaDTO alumno, UniversidadProjection universidad, CampusProjection campus) {
        String fechaActual;
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
        ResultSet registros;
        String fechaInicio = "";
        String fechaFin = "";
        String tmp;

        getParametros().put("Ciclo", periodoEscolar.getIdCicFk());
        getParametros().put("Periodo", periodoEscolar.getIdPer());
        fechaInicio = FechaUtils.getFechaFormatoSimple(periodoEscolar.getFechainicio());
        fechaFin = FechaUtils.getFechaFormatoSimple(periodoEscolar.getFechafin());

        tmp = SemestreUtils.getSemestreTxt(alumno.getUltimoSemestre());
        if (tmp.equalsIgnoreCase("Primero") || tmp.equalsIgnoreCase("Tercero")) {
            getParametros().put("Semestre", tmp.substring(0, tmp.length() - 1));
        } else {
            getParametros().put("Semestre", tmp);
        }

        getParametros().put("Fechainicio_Per", fechaInicio);
        getParametros().put("Fechafin_Per", fechaFin);
        String imagePath = getClass().getResource("/reportes/Logo.jpg").toExternalForm();
        getParametros().put("ImagePath", imagePath);

    }

    /*
     * Este método coloca los parametros para crear una constancia con historial
     */
    private void setParametrosConstanciaHistorial(AlumnoConstanciaDTO alumno) {
        /*ResultSet registros;
        String primerSemestre = new String();
        int primerSemestreNum = 0;
        String ultimoSemestre = new String();
        int ultimoSemestreNum = 0;
        String primerCiclo = new String();
        String ultimoCiclo = new String();
        String primerPeriodo = new String();
        String ultimoPeriodo = new String();
        String creditos = new String("0");
        Double promedio = 0.0;
        String fechaInicio = new String();
        String fechaFin = new String();
        String fechaActual = new String();
        try {
            registros = bd.ejecutarSelect("SELECT t1.SemestreNum_Rei as Primer_SemestreNum, t1.Id_Cic_FK as Primer_Ciclo, t1.Id_Per_FK as Primer_Periodo, t2.SemestreNum_Rei as Ultimo_SemestreNum, t2.Id_Cic_FK as Ultimo_Ciclo, t2.Id_Per_FK as Ultimo_Periodo FROM reinscripcion as t1 JOIN reinscripcion as t2 ON (t1.Id_Alu_FK=t2.Id_Alu_FK AND t1.Id_Alu_FK='" + alumno.getMatricula() + "') WHERE t1.SemestreNum_Rei = (SELECT min(SemestreNum_Rei) FROM reinscripcion WHERE Id_Alu_FK='" + alumno.getMatricula() + "') AND t2.SemestreNum_Rei = (SELECT max(SemestreNum_Rei) FROM reinscripcion WHERE Id_Alu_FK='" + alumno.getMatricula() + "') ORDER BY Primer_Ciclo,Primer_Periodo;");
            if (registros != null) {
                if (registros.first()) {
                    primerSemestreNum = registros.getInt("Primer_SemestreNum");
                    ultimoSemestreNum = registros.getInt("Ultimo_SemestreNum");
                    primerSemestre = super.getSemestreTxt(primerSemestreNum);
                    if (validaDato(registros.getString("Primer_Ciclo"))) {
                        primerCiclo = registros.getString("Primer_Ciclo");
                    } else {
                        primerCiclo = "__________";
                    }
                    if (validaDato(registros.getString("Primer_Periodo"))) {
                        primerPeriodo = registros.getString("Primer_Periodo");
                    } else {
                        primerPeriodo = "_____";
                    }
                    ultimoSemestre = super.getSemestreTxt(ultimoSemestreNum);
                    if (validaDato(registros.getString("Ultimo_Ciclo"))) {
                        ultimoCiclo = registros.getString("Ultimo_Ciclo");
                    } else {
                        ultimoCiclo = "__________";
                    }
                    if (validaDato(registros.getString("Ultimo_Periodo"))) {
                        ultimoPeriodo = registros.getString("Ultimo_Periodo");
                    } else {
                        ultimoPeriodo = "_____";
                    }
                }
            }
            registros = bd.ejecutarSelect("SELECT CURDATE() as hoy, t1.Fechainicio_Per as inicio, t2.Fechafin_Per as fin FROM periodosescolares as t1, periodosescolares as t2 WHERE t1.Id_Cic_FK='" + primerCiclo + "' AND t1.Id_Per='" + primerPeriodo + "' AND t2.Id_Cic_FK='" + ultimoCiclo + "' AND t2.Id_Per='" + ultimoPeriodo + "';");
            if (registros != null) {
                if (registros.first()) {
                    fechaActual = registros.getString("hoy");
                    if (validaDato(registros.getString("inicio"))) {
                        fechaInicio = getFechaTextual(registros.getString("inicio"));
                    } else {
                        fechaInicio = getFechaTextual("________-_______________-_____");
                    }
                    if (validaDato(registros.getString("fin"))) {
                        fechaFin = getFechaTextual(registros.getString("fin"));
                    } else {
                        fechaFin = getFechaTextual("________-_______________-_____");
                    }
                }
            }
            registros = bd.ejecutarSelect("SELECT sum(Numerocred_Mat) as creditos FROM grupos_alumnos JOIN materias ON (materias.Id_Mat=grupos_alumnos.Id_Mat_FK AND materias.Id_Pla_FK=grupos_alumnos.Id_Pla_FK) WHERE Id_Alu_FK='" + alumno.getMatricula() + "' AND Calificacionmateria_Gra>=6 AND Calificacionmateria_Gra<=10;");
            if (registros != null) {
                if (registros.first()) {
                    if (validaDato(registros.getString("creditos"))) {
                        creditos = registros.getString("creditos");
                    } else {
                        creditos = "0";
                    }
                    if (Integer.parseInt(creditos) > 0) {
                        promedio = promedioAlumno(alumno.getMatricula());
                    } else {
                        promedio = 0.0;
                    }
                } else {
                    promedio = 0.0;
                    creditos = "0";
                }
            } else {
                promedio = 0.0;
                creditos = "0";
            }
            misparametros.put("Primer_Semestre", primerSemestre);
            misparametros.put("Ultimo_Semestre", ultimoSemestre);
            misparametros.put("Creditos", creditos);
            if (promedio >= 10.0) {
                misparametros.put("Promedio", "10");
            } else {
                misparametros.put("Promedio", new String(promedio + "").substring(0, 3));
            }
            misparametros.put("Fechainicio_Per", fechaInicio);
            misparametros.put("Fechafin_Per", fechaFin);
            String imagePath = getClass().getResource("/reportes/Logo.jpg").toExternalForm();
            misparametros.put("ImagePath", imagePath);
        } catch (Exception j) {
            ErrorSE.guardar(3, "El método setParametrosConstanciaHistorial de la clase CtrConstanciaEstudios", "Error al conectarce a la base de datos", "Jos Antonio Cervantes", j);
        } finally {
            bd.desconectar();
            bd = null;
        }*/
    }


    public File generateTemporaryPDF(InputStream jasperStream,AlumnoConstanciaDTO alumno, PeriodoEscolar periodoEscolar, UniversidadProjection universidad, CampusProjection campus, int tipoConstancia) {
        try {
            File reportFile;
            JasperPrint jasperPrint;
            setParametrosComunes(alumno, universidad, campus);
            switch (tipoConstancia) {
                case 1:
                    setParametrosConstancia(alumno, periodoEscolar);
                    //jasperStream = getClass().getResourceAsStream("/reportes/constanciaestudios.jasper");
                    break;
                default:
                    setParametrosConstanciaHistorial(alumno);
                    //jasperStream = getClass().getResourceAsStream("/reportes/constanciahistorial.jasper");
                    //CtrHistorialAcademico historial = new CtrHistorialAcademico();
                    //historial.obtenerReporte(alumno);
                    break;
            }
            System.out.println("PARAMETROS" + getParametros());
            jasperPrint = JasperFillManager.fillReport(jasperStream, getParametros(), miConexion);
            File tempFile = File.createTempFile("output", ".pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());
            return tempFile;
        } catch (Exception i) {
            i.printStackTrace();

        }
        return null;
    }

}

