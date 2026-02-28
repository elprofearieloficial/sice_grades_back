/**
 * Versión: 1
 * Fecha de creación: 04/junio/2008
 * Ultima fecha de modificación: 15/Abril/2009
 *
 * @autor: José Antonio Cervantes Alvarez
 * @mail: jcervantes@unpa.edu.mx 
 * Se utiliza para determinar el ciclo escolar y
 * el periodo que el sistema debe mostrar en base a la fecha actual del
 * servidor, también, proporciona la información para el calendario escolar como
 * es: inicio de semestres. periódos de examenes ordinarios, finales,
 * extraordinarios, etc. Fecha de actualización: 29/02/2024
 */
package unpa.entity.utils;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.LinkedList;

public class CalendarioTMP {

    private String anio;
    private String mes;
    private String dia;
    private String ciclo = "";
    private LinkedList<String> diasFestivos = new LinkedList();
    private LinkedList<String> materia = new LinkedList();
    private LinkedList<String> grupo = new LinkedList();
    private LinkedList<String> parcial1 = new LinkedList();
    private LinkedList<String> parcial2 = new LinkedList();
    private LinkedList<String> parcial3 = new LinkedList();
    private LinkedList<String> efinal = new LinkedList();
    private LinkedList<String> extra1 = new LinkedList();
    private LinkedList<String> extra2 = new LinkedList();
    private LinkedList<String> especial = new LinkedList();

    public CalendarioTMP() {
        Calendar calendario = Calendar.getInstance();
        anio = String.valueOf(calendario.get(Calendar.YEAR));
        mes = String.valueOf(calendario.get(Calendar.MONTH) + 1);
        dia = String.valueOf(calendario.get(Calendar.DATE));
        if (Integer.parseInt(mes) < 10) {
            mes = 0 + mes;
        }
        if (Integer.parseInt(dia) < 10) {
            dia = 0 + dia;
        }
    }

    private void obtenerCiclo() {
        /*ResultSet registros;
        String sql, tmpFecha1;
        String tmpFecha2 = anio + "-" + mes + "-" + dia;
        //BaseDatos bd = new BaseDatos();
        if (Integer.parseInt(mes) < 8) {
            ciclo = ((Integer.parseInt(anio)) - 1) + "-" + anio;
        } else {
            //Estamos en el mes octavo y ya inicio un nuevo ciclo escolar para la SEP.
            ciclo = anio + "-" + ((Integer.parseInt(anio)) + 1);
            try {
                sql = "SELECT Inicio_Cic FROM ciclosescolares WHERE Id_Cic='" + ciclo + "';";
                registros = bd.ejecutarSelect(sql);
                if (registros != null) {
                    tmpFecha1 = registros.getString("Inicio_Cic");
                    if ((tmpFecha1 == null) || ((tmpFecha2.compareTo(tmpFecha1)) < 0)) {
                        //Aun no se registra el calendario del nuevo ciclo escolar
                        // o ya esta registrado pero aun no inicia el nuevo ciclo
                        //escolar
                        ciclo = ((Integer.parseInt(anio)) - 1) + "-" + anio;
                    }
                } else {
                    ciclo = ((Integer.parseInt(anio)) - 1) + "-" + anio;
                }

            } catch (Exception e) {                
                //ErrorSE.guardar(0, "CalendarioTMP", "obtenerCiclo", "alopez", e);
            } finally {
                //bd.desconectar();
            }
        }*/
        ciclo="";
    }

    public String obtenerPeriodo() {
        /*
        ResultSet registros;
        String sql, tmpPeriodo, tmpFecha1;
        String periodo = "";
        String tmpFecha2 = anio + "-" + mes + "-" + dia;
        int bandera = 0;
        obtenerCiclo();
        StringTokenizer tokens = new StringTokenizer(ciclo, "-");
        String tmpAno = tokens.nextToken(); //Toma en primer año del ciclo escolar
        BaseDatos bd = new BaseDatos();
        try {
            sql = "SELECT Id_Per, Fechainicio_Per FROM periodosescolares WHERE Id_Cic_FK='" + ciclo + "' ORDER BY (Id_Per);";
            registros = bd.ejecutarSelect(sql);
            while (registros.next() && bandera == 0) {
                tmpPeriodo = registros.getString("Id_Per");
                tmpFecha1 = registros.getString("Fechainicio_Per");                
                if (tmpPeriodo.equals("A")) {
                    if (tmpFecha1 == null) {
                        periodo = "A";
                    } else if ((tmpFecha2.compareTo(tmpFecha1)) < 0) {
                        periodo = "V";
                        if (tmpAno.compareTo(anio) == 0) {
                            ciclo = (Integer.parseInt(tmpAno) - 1) + "-" + (Integer.valueOf(tmpAno));
                        }
                        bandera = 1; //Rompe el while, ya no tiene caso ver el siguiente periodo
                    } else {
                        //porque aun no inicia el A, por lo tanto aun no ha iniciado el B.
                        periodo = "A";
                    }
                } else {
                    if (tmpPeriodo.equals("B")) {
                        if (tmpFecha1 == null) {
                            periodo = "A";
                        } else if ((tmpFecha2.compareTo(tmpFecha1)) < 0) {
                            periodo = "A";
                            bandera = 1;
                        } else {
                            periodo = "B";
                        }
                    } else {
                        if (tmpPeriodo.equals("V")) {
                            if (tmpFecha1 == null) {
                                periodo = "B";
                            } else if ((tmpFecha2.compareTo(tmpFecha1)) < 0) {
                                periodo = "B";
                                bandera = 1;
                            } else {
                                periodo = "V";
                            }
                        }
                    }
                }
            }
            if (periodo == null) {
                //Regresamos un ciclo escolar atras y 
                //utilizamos el periodo B en caso de 
                //que no encuentre el periodo A, B o V del ciclo actual
                ciclo = (Integer.parseInt(ciclo.substring(0, 4)) - 1) + "-" + (Integer.valueOf(ciclo.substring(0, 4)));
                periodo = "B";
            }
        } catch (Exception e) {            
            //ErrorSE.guardar(0, "CalendarioTMP", "obtenerPeriodo", "alopez", e);
        } finally {
           // bd.desconectar();
        }
        return periodo;
        */
         return  "A";
    }

    public void SetExamenes() {

    }

    public String getCiclo() {
        if (ciclo == null) {
            obtenerCiclo();
            obtenerPeriodo();
        }
        return ciclo;
    }

    /**
     * Devuelve la clave de los ciclos escolares de la Base de Datos.
     *
     * @return Retorna una lista de las clave de los ciclos escolares
     */
    public LinkedList<String> getCiclos() {
        /*LinkedList<String> ciclos = new LinkedList();
        ResultSet registros;
        //BaseDatos bd = new BaseDatos();
        String sql;
        try {
            sql = "SELECT Id_Cic FROM ciclosescolares ORDER BY Id_Cic DESC;";
            registros = bd.ejecutarSelect(sql);
            while (registros.next()) {
                ciclos.add(registros.getString("Id_Cic"));
            }
        } catch (Exception e) {            
            ErrorSE.guardar(0, "CalendarioTMP", "getCiclos", "alopez", e);
        } finally {
            bd.desconectar();
        }
        return ciclos;*/

        return null;

    }

    /**
     * Devuelve la clave de los ciclos escolares de la Base de Datos. pero en
     * formato <option> ciclo <option>
     *
     * @return Retorna una lista de las clave de los ciclos escolares
     */
    public String getCiclosToWeb() {
        LinkedList<String> ciclos;
        ciclos = getCiclos();
        String resultado="";
        for (int i = 0; i < ciclos.size(); i++) {
            resultado=resultado + ("<option value='" + ciclos.get(i) + "'>" + ciclos.get(i) + "</option>");
        }        
        return resultado;
    }
    
    /**
     * Devuelve la clave de los ciclos escolares de la Base de Datos.pero en
 formato <option> ciclo <option>
     *
     * @param ciclo el ciclo para marcar como selected
     * @return Retorna una lista de las clave de los ciclos escolares
     */
    public String getCiclosToWeb(String ciclo) {
        LinkedList<String> ciclos;
        ciclos = getCiclos();
        String resultado="";
        for (int i = 0; i < ciclos.size(); i++) {
            if(ciclo.compareToIgnoreCase(ciclos.get(i))==0){
                resultado=resultado + ("<option value='" + ciclos.get(i) + "' selected>" + ciclos.get(i) + "</option>");
            }else{
                resultado=resultado + ("<option value='" + ciclos.get(i) + "' >" + ciclos.get(i) + "</option>");
            }
            
        }        
        return resultado;
    }

    public String getFechaActual() {
        return anio + "-" + mes + "-" + dia;
    }

    public String getAno() {
        return anio;
    }

    public String getMes() {
        return mes;
    }

    public String getDia() {
        return dia;
    }

    public int getHora() {
        Calendar x = Calendar.getInstance();
        return x.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutos() {
        Calendar x = Calendar.getInstance();
        return x.get(Calendar.MINUTE);
    }

    public int getSegundos() {
        Calendar x = Calendar.getInstance();
        return x.get(Calendar.SECOND);
    }

    public LinkedList<String> getDiasFestivos() {
        return diasFestivos;
    }

    public int getTotalMaterias() {
        return materia.size();
    }

    public String getMateria(int x) {
        return materia.get(x);
    }

    public String getGrupo(int x) {
        return grupo.get(x);
    }

    public String getParcial1(int x) {
        return parcial1.get(x);
    }

    public String getParcial2(int x) {
        return parcial2.get(x);
    }

    public String getParcial3(int x) {
        return parcial3.get(x);
    }

    public String getFinal(int x) {
        return efinal.get(x);
    }

    public String getExtra1(int x) {
        return extra1.get(x);
    }

    public String getExtra2(int x) {
        return extra2.get(x);
    }

    public String getEspecial(int x) {
        return especial.get(x);
    }

    public String getFechaLimite(String fechaInicial, String ciclo) {
        String diaInicial, mesInicial, anoInicial, limite;
        String diaLimite, mesLimite, anoLimite;
        int x;
        int incremento = 5;
        StringTokenizer tokens = new StringTokenizer(fechaInicial, "-");
        anoInicial = tokens.nextToken();
        mesInicial = tokens.nextToken();
        diaInicial = tokens.nextToken();
        this.obtenerDiasFestivos(ciclo);
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, Integer.parseInt(anoInicial));
        calendario.set(Calendar.MONTH, Integer.parseInt(mesInicial) - 1);
        calendario.set(Calendar.DATE, Integer.parseInt(diaInicial));
        int diatmp = calendario.get(Calendar.DAY_OF_WEEK);
        // ESTE CÓDIGO DEL IF ES PARA 3 DÍAS 
        //HABILES PARA ENTREGAR CALIFICACIONES 
        //EN EL CASO DE 5 NO ES NECESARIO
        //para bricar días no laborables como sabado y domingo
        if (diatmp >= 4) {
            incremento = incremento + 2;
        }
        calendario.add(Calendar.DATE, incremento);
        anoLimite = String.valueOf(calendario.get(Calendar.YEAR));
        mesLimite = String.valueOf(calendario.get(Calendar.MONTH) + 1);
        diaLimite = String.valueOf(calendario.get(Calendar.DATE));
        if (Integer.parseInt(mesLimite) < 10) {
            mesLimite = 0 + mesLimite;
        }
        if (Integer.parseInt(diaLimite) < 10) {
            diaLimite = 0 + diaLimite;
        }
        limite = anoLimite + "-" + mesLimite + "-" + diaLimite;
        for (x = 0; x < diasFestivos.size(); x++)//Se busca si exite un d�a festivo entre
        {//la fecha de examen y la fecha l�mite de entrega
            if (fechaInicial.compareTo(diasFestivos.get(x)) <= 0 && limite.compareTo(diasFestivos.get(x)) >= 0) {//si se encuntra dentro del rango de fecha de aplicaci�n y fecha limite de entrega
                //ver si no es sabdao o domingo, entonces dar un d�a m�s de tolerancia para entregar calificaciones
                tokens = new StringTokenizer(diasFestivos.get(x), "-");
                calendario.set(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()) - 1, Integer.parseInt(tokens.nextToken()));
                if (calendario.get(Calendar.DAY_OF_WEEK) > 1 && calendario.get(Calendar.DAY_OF_WEEK) < 7) {//Dar un d�a m�s de tolerancia por ser dis festivo entre semana
                    tokens = new StringTokenizer(limite, "-");
                    calendario.set(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()) - 1, Integer.parseInt(tokens.nextToken()));
                    calendario.add(Calendar.DATE, 1);
                    anoLimite = String.valueOf(calendario.get(Calendar.YEAR));
                    mesLimite = String.valueOf(calendario.get(Calendar.MONTH) + 1);
                    diaLimite = String.valueOf(calendario.get(Calendar.DATE));
                    if (Integer.parseInt(mesLimite) < 10) {
                        mesLimite = 0 + mesLimite;
                    }
                    if (Integer.parseInt(diaLimite) < 10) {
                        diaLimite = 0 + diaLimite;
                    }
                    limite = anoLimite + "-" + mesLimite + "-" + diaLimite;
                    switch (calendario.get(Calendar.DAY_OF_WEEK)) {
                        case 1://Domingo
                            incremento = 1;
                            break;
                        case 7://Sabado
                            incremento = 2;
                            break;
                        default:
                            incremento = 0;
                            break;

                    }
                    calendario.add(Calendar.DATE, incremento);
                    anoLimite = String.valueOf(calendario.get(Calendar.YEAR));
                    mesLimite = String.valueOf(calendario.get(Calendar.MONTH) + 1);
                    diaLimite = String.valueOf(calendario.get(Calendar.DATE));
                    if (Integer.parseInt(mesLimite) < 10) {
                        mesLimite = 0 + mesLimite;
                    }
                    if (Integer.parseInt(diaLimite) < 10) {
                        diaLimite = 0 + diaLimite;
                    }
                    limite = anoLimite + "-" + mesLimite + "-" + diaLimite;
                }
            }
        }
        return limite;
    }

    public void obtenerDiasFestivos(String ciclo) {
        /*ResultSet registros;
        String sql, tmp;
        BaseDatos bd = new BaseDatos();
        StringTokenizer tokens;
        int x, y;
        sql = "SELECT Diasdescanso_Cic FROM ciclosescolares WHERE Id_Cic='" + ciclo + "';";
        try {
            registros = bd.ejecutarSelect(sql);
            if (registros != null) {
                if(registros.first()){
                    tmp = registros.getString("Diasdescanso_Cic");
                    if (tmp != null && tmp.length() > 0) {
                        tmp = tmp.replace("/", "-");
                        tokens = new StringTokenizer(tmp, ",");
                        y = tokens.countTokens();
                        x = 0;
                        while (x < y) {
                            diasFestivos.add(tokens.nextToken());
                            x++;
                        }
                    }
                }
            }
        } catch (Exception e) {            
            ErrorSE.guardar(0, "CalendarioTMP", "obtenerDiasFestivos", "alopez", e);
        } finally {
            bd.desconectar();
        }*/
    }

    public String getFechaTxt(String fecha) {
        String fechaTxt;
        Calendar calendario = Calendar.getInstance();
        StringTokenizer tokens = new StringTokenizer(fecha, "-");
        String anotmp = tokens.nextToken();
        String mestmp = tokens.nextToken();
        String diatmp = tokens.nextToken();
        calendario.set(Calendar.YEAR, Integer.parseInt(anotmp));
        calendario.set(Calendar.MONTH, Integer.parseInt(mestmp) - 1);
        calendario.set(Calendar.DATE, Integer.parseInt(diatmp));
        int diasemana = calendario.get(Calendar.DAY_OF_WEEK);

        String diasSemana[] = {"Domingo", "Lunes", "Martes", "Miércoles",
            "Jueves", "Viernes", "Sábado"};
        String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "Septiembre", "Octubre",
            "Noviembre", "Diciembre"};
        fechaTxt = diasSemana[diasemana - 1];
        fechaTxt = fechaTxt +" "+ diatmp + " ";
        fechaTxt = fechaTxt + "de " + meses[Integer.parseInt(mestmp) - 1] + " de ";
        fechaTxt = fechaTxt + anotmp;
        return fechaTxt;
    }

    public String getFechaTxt2(String fecha) {
        String fechaTxt;
        Calendar calendario = Calendar.getInstance();
        StringTokenizer tokens = new StringTokenizer(fecha, "-");
        String anotmp = tokens.nextToken();
        String mestmp = tokens.nextToken();
        String diatmp = tokens.nextToken();
        calendario.set(Calendar.YEAR, Integer.parseInt(anotmp));
        calendario.set(Calendar.MONTH, Integer.parseInt(mestmp) - 1);
        calendario.set(Calendar.DATE, Integer.parseInt(diatmp));
        fechaTxt = diatmp + " ";
        String meses[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "Septiembre", "Octubre",
            "Noviembre", "Diciembre"};
        fechaTxt = fechaTxt + " de " + meses[Integer.parseInt(mestmp) - 1] + " de ";
        fechaTxt = fechaTxt + anotmp;
        return fechaTxt;
    }

    public void obtenerCalendarioExamenes(int tipoUsuario, String identificador, String ciclo, String periodo) {//valores del parametro tipo usuario 1=Profesor y 2=Alumno
        /*String sql = new String();
        BaseDatos bd = new BaseDatos();
        ResultSet registros;
        switch (tipoUsuario) {
            case 1:
                sql = "SELECT imparten.Id_Pla_FK,Nombre_Mat,Id_Gpo_FK,Fechap1_Imp,Fechap2_Imp,Fechap3_Imp,Fechaf_Imp,Fechae1_Imp,Fechae2_Imp,Fechaesp_Imp " +
                        " FROM imparten JOIN materias ON (imparten.Id_Mat_FK=materias.Id_Mat AND imparten.Id_Pla_FK=materias.Id_Pla_FK) " +
                        " WHERE Id_Pro_FK='" + identificador + "' " +
                        " AND Id_Cic_FK='" + ciclo + "' " +
                        " AND Id_Per_FK='" + periodo + "' " +
                        " ORDER BY Id_Gpo_FK,Nombre_Mat;";
                break;
            case 2:
                sql = "SELECT Nombre_Mat,grupos_alumnos.Id_Gpo_FK,Fechap1_Imp,Fechap2_Imp,Fechap3_Imp,Fechaf_Imp,Fechae1_Imp,Fechae2_Imp,Fechaesp_Imp " +
                        " FROM imparten JOIN materias ON (imparten.Id_Mat_FK=materias.Id_Mat AND imparten.Id_Pla_FK=materias.Id_Pla_FK) " +
                        " JOIN grupos_alumnos ON (grupos_alumnos.Id_Mat_FK=materias.Id_Mat " +
                        " AND grupos_alumnos.Id_Pla_FK=materias.Id_Pla_FK AND grupos_alumnos.Id_Cic_FK=imparten.Id_Cic_FK " +
                        " AND grupos_alumnos.Id_Per_FK=imparten.Id_Per_FK AND grupos_alumnos.Id_Gpo_FK=imparten.Id_Gpo_FK) " +
                        " WHERE Id_Alu_FK='" + identificador + "' AND grupos_alumnos.Id_Cic_FK='" + ciclo + "' " +
                        " AND grupos_alumnos.Id_Per_FK='" + periodo + "' ORDER BY grupos_alumnos.Id_Gpo_FK,Nombre_Mat;";
                break;
        }
        try {
            registros = bd.ejecutarSelect(sql);
            if (registros != null) {
                while (registros.next()) {
                    if (registros.getString("Nombre_Mat") != null) {
                        materia.add(registros.getString("Nombre_Mat"));
                        if (registros.getString("Id_Gpo_FK") != null) {
                            grupo.add(registros.getString("Id_Gpo_FK"));
                        } else {
                            grupo.add("");
                        }
                        if (registros.getString("Fechap1_Imp") != null && registros.getString("Fechap1_Imp").length() > 0) {
                            parcial1.add(getFechaTxt2(registros.getString("Fechap1_Imp")));
                        } else {
                            parcial1.add("");
                        }
                        if (registros.getString("Fechap2_Imp") != null && registros.getString("Fechap2_Imp").length() > 0) {
                            parcial2.add(getFechaTxt2(registros.getString("Fechap2_Imp")));
                        } else {
                            parcial2.add("");
                        }
                        if (registros.getString("Fechap3_Imp") != null && registros.getString("Fechap3_Imp").length() > 0) {
                            parcial3.add(getFechaTxt2(registros.getString("Fechap3_Imp")));
                        } else {
                            parcial3.add("");
                        }
                        if (registros.getString("Fechaf_Imp") != null && registros.getString("Fechaf_Imp").length() > 0) {
                            efinal.add(getFechaTxt2(registros.getString("Fechaf_Imp")));
                        } else {
                            efinal.add("");
                        }
                        if (registros.getString("Fechae1_Imp") != null && registros.getString("Fechae1_Imp").length() > 0) {
                            extra1.add(getFechaTxt2(registros.getString("Fechae1_Imp")));
                        } else {
                            extra1.add("");
                        }
                        if (registros.getString("Fechae2_Imp") != null && registros.getString("Fechae2_Imp").length() > 0) {
                            extra2.add(getFechaTxt2(registros.getString("Fechae2_Imp")));
                        } else {
                            extra2.add("");
                        }
                        if (registros.getString("Fechaesp_Imp") != null && registros.getString("Fechaesp_Imp").length() > 0) {
                            especial.add(getFechaTxt2(registros.getString("Fechaesp_Imp")));
                        } else {
                            especial.add("");
                        }
                    }
                }
            }
        } catch (Exception e) {           
            ErrorSE.guardar(0, "CalendarioTMP", "obtenerCalendarioExamenes", "alopez", e);
        } finally {
            bd.desconectar();
        }*/
    }
}
