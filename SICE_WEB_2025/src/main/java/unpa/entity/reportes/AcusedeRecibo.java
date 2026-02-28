package unpa.entity.reportes;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import net.sf.jasperreports.engine.*;
import unpa.entity.utils.CalendarioTMP;
import unpa.entity.utils.ErrorSE;
import unpa.entity.profesores.ProfesorReporteProjection;
import unpa.entity.profesores.SinodalProjection;
import unpa.entity.actas.Acta;
import unpa.entity.universidad.CampusProjection;
import unpa.entity.universidad.UniversidadProjection;
import unpa.entity.utils.FechaUtils;

import javax.sql.DataSource;


/**
 * Esta clase genera un acuse de recibo del acta que el profesor ha registrado
 * ya sea de un examen parcial, extraordinario o especial. <br>
 * Fecha de creaci&oacute;n: 04 de Agosto de 2008 <br>
 * Ultima fecha de modificaci&oacute;n: 26 de Noviembre de 2008 <br>
 *
 * @author Jos&eacute; Antonio Cervantes &Aacute;lvarez E-mail:
 * jcervantes@unpa.edu.mx
 * @version BETA
 *
 */
public class AcusedeRecibo extends Reporte {

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



    /**
     * Crea una nueva instacia de la clase inicializando los atributos.
     *
     * @param idGrupo Identificador de un grupo.
     * @param idMateria Identificador de una materia.
     * @param idPlan Identificador del plan de estudios al que pertenece un
     * adeterminada materia.
     * @param ciclo Indentificador de un ciclo escolar, ejemplo: 2008-2009 o
     * 2009-2010.
     * @param periodo Identificador del periodo del ciclo escolar, ehjemplo: "A"
     * o "B".
     * @param tipoExamen Indica el tipo de examen, ejemplo: 1 Primer parcial, 2
     * Segundo parcial, 3 Tercer parcial, 4 Examen final, 5 Examen
     * Extraordinario I, 6 Examen Extraordinario II o 7 Examen Especial. por
     * ejemplo: 1 indica primer parcial, 2 segundo parcial, 3 tercer parcial, 4
     * examen final, 5 extraordinario I, 6 extraordinario II y 7 especial.
     */
    public AcusedeRecibo(String idGrupo, String idMateria, String idPlan, String ciclo, String periodo, int tipoExamen,DataSource dataSource) {
        super(dataSource);
        this.idGrupo = idGrupo;
        this.idMateria = idMateria;
        this.idPlan = idPlan;
        this.ciclo = ciclo;
        this.periodo = periodo;
        this.tipoExamen = tipoExamen;
    }

   
    public File generateTemporaryPDF(InputStream jasperStream, CampusProjection encabezado, ProfesorReporteProjection prof, List<SinodalProjection> sinodales, String carrera, Optional<Acta> actaOpt , UniversidadProjection universidad) {
        try {
            //File reportFile = new File(ruta + "/" + getArchivoJasper());
            setParametros(encabezado,prof,sinodales,carrera,actaOpt,universidad);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, getParametros(), miConexion);
            File tempFile = File.createTempFile("output", ".pdf");            
            JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());
            return tempFile;
        } catch (Exception i) {
            i.printStackTrace();
            //ErrorSE.guardar(1, "AcuseRecibo generateTemporaryPDF ", "Problemas al generar el acuse de recibo para PDF", "Ariel López", i);

        } finally {
            //this.desconectar();
        }
        return null;
    }

    
   

    /**
     * Establece el nombre del archivo jasper que va a crear una acta.
     *
     * @return Retorna el nombre del archivo jasper a utilizar en la
     * creaci&oacute;n de la acta.
     */
    private String getArchivoJasper() {
        String archivo = new String();
        switch (tipoExamen) {
            case 1:
                archivo = "acuseCalificacionesOrd1.jasper";
                break;
            case 2:
                archivo = "acuseCalificacionesOrd2.jasper";
                break;
            case 3:
                archivo = "acuseCalificacionesOrd3.jasper";
                break;
            case 4:
                archivo = "acuseCalificacionesOrd4.jasper";
                break;
            case 5:
                archivo = "acuseCalificacionExt.jasper";
                break;
            case 6:
                archivo = "acuseCalificacionExt.jasper";
                break;
            case 7:
                archivo = "acuseCalificacionEsp.jasper";
                break;
        }
        return archivo;
    }

    

    

    /**
     * Buasca en la base de datos algunos parametros para realizar el llenado
     * del acta. Por ejemplo: nombre de la universidad, direcci&oacute;n, normde
     * del profesor, nombre de la materia, etc.
     *
     */
    private void setParametros(CampusProjection encabezado, ProfesorReporteProjection prof, List<SinodalProjection> sinodales, String carrera, Optional<Acta> actaOpt, UniversidadProjection universidadDTO) {
        CalendarioTMP fecha = new CalendarioTMP();
        String universidad = "";
        String Leyenda = new String();
        String clave_universidad = "";
        String direccion_campus = "";
        String ciudad_campus = "";
        String estado_campus = "";
        String cp_campus = "";
        String tel_campus = "";
        String fax_campus = "";
        String campus = "";
        String parcial ;
        String materia = "";
        String profesor = "";
        String profesor2 = ""; //Se utiliza cuando el examen es aplicado por dos profesores, ejemplo: Extraordinario 1 o 2.
        String profesor3 = ""; //Se utiliza cuando el examen es aplicado por tres profesores, por ejemplo para el examen especial.
        String semestre = "";

        String sql ;
        ResultSet registros;
        
        try {

            if (encabezado!= null ) {
                universidad = universidadDTO.getNombre();
                Leyenda = universidadDTO.getLeyenda();
                clave_universidad = String.valueOf(universidadDTO.getClave());
                campus = encabezado.getNombreCam();
                direccion_campus = encabezado.getDomicilioCam();
                ciudad_campus = encabezado.getCiudadCam();
                estado_campus = encabezado.getNombreEdo();
                cp_campus = encabezado.getCodigopCam();
                tel_campus = encabezado.getTelefonoCam();
                fax_campus = encabezado.getFaxCam();
            }
            if (this.tipoExamen <= 4) {
                if (prof != null) {
                    materia = prof.getNombreMat();
                    semestre = String.valueOf(prof.getSemestreMat());
                    String nombreProfesor = String.format("%s %s %s %s",
                            prof.getSiglasgradomaxTra(),
                            prof.getNombreTra(),
                            prof.getApellidopTra(),
                            prof.getApellidomTra()
                    );
                    profesor = nombreProfesor.trim();

                }
            } else {
                // Nombre de materia y semestre solo lo tomamos del primero
                if (!sinodales.isEmpty()) {
                    materia = sinodales.get(0).getNombreMat();
                    semestre = String.valueOf(sinodales.get(0).getSemestreMat());
                }

                // Formateamos hasta 3 sinodales
                for (int i = 0; i < Math.min(3, sinodales.size()); i++) {
                    SinodalProjection s = sinodales.get(i);
                    String nombre = String.format("%s %s %s %s",
                            s.getSiglasgradomaxTra(),
                            s.getNombreTra(),
                            s.getApellidopTra(),
                            s.getApellidomTra()
                    ).trim();
                    switch(i){
                        case 0:profesor = nombre; break;
                        case 1:profesor2 = nombre; break;
                        default:profesor3 = nombre; break;

                    }
                }
            }

            actaOpt.ifPresent(acta -> {
                clave_acuse = acta.getClaveEntrega();
                fecha_recibo = acta.getFechaEntrega().toString();
                fecha_aplico =acta.getFechaAplico().toString();
                hora_acuse = acta.getHoraEntrega().toString();
            });



            if (tel_campus == null) {
                tel_campus = " ";
            }
            if (fax_campus == null) {
                fax_campus = " ";
            }
            switch (this.tipoExamen) {
                case 5: //Extraordinario 1
                    getParametros().put("Id_Examen", "e1");
                    break;
                case 6: //Extraordinario 1
                    getParametros().put("Id_Examen", "e2");
                    break;
                case 7: //Especial
                    getParametros().put("Id_Examen", "esp");
                    break;
            }
            parcial = this.getTipoActa(this.tipoExamen);
            getParametros().put("Universidad", universidad);
            getParametros().put("Leyenda", Leyenda);
            getParametros().put("Clave_Uni", clave_universidad);
            getParametros().put("Campus", campus);
            getParametros().put("Tipo_Examen", parcial);
            getParametros().put("Materia", materia);
            getParametros().put("Profesor", profesor);
            if (this.tipoExamen > 4) {
                getParametros().put("Profesor2", profesor2);
            }
            if (this.tipoExamen == 7) {
                getParametros().put("Profesor3", profesor3);
            }
            getParametros().put("Carrera", carrera);
            getParametros().put("Id_Gpo", idGrupo);
            getParametros().put("Id_Mat", idMateria);
            getParametros().put("Id_Pla", idPlan);
            getParametros().put("Semestre", semestre);
            getParametros().put("Fecha_aplico", FechaUtils.getFechaFormatoSimple(fecha_aplico));
            getParametros().put("Id_Cic", ciclo);
            getParametros().put("Id_Per", periodo);
            getParametros().put("Fecha_actual", FechaUtils.getFechaFormatoSimple(fecha.getAno() + "/" + fecha.getMes() + "/" + fecha.getDia()));
            getParametros().put("Clave_Acuse", clave_acuse);
            getParametros().put("Hora_Recibo", hora_acuse);
            getParametros().put("Fecha_Recibo", FechaUtils.getFechaFormatoSimple(fecha_recibo));
            getParametros().put("Estado", estado_campus);
            getParametros().put("Direccion_Universidad", universidad + ", " + direccion_campus + ", " + ciudad_campus + ", " + estado_campus + ", C.P. " + cp_campus + ", Tel: " + tel_campus + ", Fax: " + fax_campus);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorSE.guardar(3, "El método Parametros de la clase AcusedeRecibo para el sitio Web", "Error desconocido al tratar llebçnar los parámetros para generar el acuse de recibo", "Antonio Cervantes", e);
        }
    }

    /**
     * Esta función determina el semestre en texto de un n&uacute;mero dado.
     * <br>
     * Ejemplo: getSemestre(1) retorna PRIMERO, getSemestre(2) retorna SEGUNDO,
     * etc.
     *
     * @param semestre de tipo entero positivo no mayor a 12.
     * @return Retorna el nombre del semestre correspondiente al valor de
     * entrada.
     */
    private String getSemestre(int semestre) {
        String resultado = new String();
        switch (semestre) {
            case 1:
                resultado = "PRIMERO";
                break;
            case 2:
                resultado = "SEGUNDO";
                break;
            case 3:
                resultado = "TERCERO";
                break;
            case 4:
                resultado = "CUARTO";
                break;
            case 5:
                resultado = "QUINTO";
                break;
            case 6:
                resultado = "SEXTO";
                break;
            case 7:
                resultado = "SÉPTIMO";
                break;
            case 8:
                resultado = "OCTAVO";
                break;
            case 9:
                resultado = "NOVENO";
                break;
            case 10:
                resultado = "DÉCIMO";
                break;
            case 11:
                resultado = "DECIMOPRIMERO";
                break;
            case 12:
                resultado = "DECIMOSEGUNDO";
                break;
            default:
                resultado = "INDEFINIDO";

        }
        return resultado;
    }

       private String getClaveExamen(int examen) {
        String resultado = switch (examen) {
            case 1 -> "p1";
            case 2 -> "p2";
            case 3 -> "p3";
            case 4 -> "f";
            case 5 -> "e1";
            case 6 -> "e2";
            case 7 -> "esp";
            default -> "indefinido";
        };
           return resultado;
    }


    private String getTipoActa(int examen) {
        String resultado = switch (examen) {
            case 1 -> "PRIMER PARCIAL";
            case 2 -> "SEGUNDO PARCIAL";
            case 3 -> "TERCER PARCIAL";
            case 4 -> "EXAMEN FINAL";
            case 5 -> "EXAMEN EXTRAORDINARIO I";
            case 6 -> "EXAMEN EXTRAORDINARIO II";
            case 7 -> "EXAMEN ESPECIAL";
            default -> "INDEFINIDO";
        };
        return resultado;
    }

    

    /**
     * Asigna un identificador de materia tipo String.
     *
     * @param idMateria de tipo String.
     */
    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    /**
     * Asigna un identificador de plan de estudios tipo String.
     *
     * @param idPlan de tipo String.
     */
    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    /**
     * Asigna un identificador de grupo tipo String.
     *
     * @param idGrupo de tipo String.
     */
    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Asigna un identificador de ciclo tipo String.
     *
     * @param ciclo de tipo String.
     */
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    /**
     * Asigna un identificador de periodo tipo String.
     *
     * @param periodo de tipo String.
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    /**
     * Asigna un identificador de examen de tipo num&eacute;rico.
     *
     * @param tipoExamen de tipo int.
     */
    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = Integer.parseInt(tipoExamen);
    }
}
